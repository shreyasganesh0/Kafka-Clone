import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import network.request.KafkaHeaderV2;
import network.request.KafkaRequest;
import network.response.ApiVersionResponse;
import network.response.DescribeTopicPartitionsResponse;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage

    ExecutorService threadPool = Executors.newFixedThreadPool(3);
    List<Future<?>> futures = new ArrayList<>();
    int port = 9092;

    try {

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
          // Since the tester restarts your program quite often, setting SO_REUSEADDR
          // ensures that we don't run into 'Address already in use' errors
          // Wait for connection from client.
          Socket clientSocket = serverSocket.accept();
          Future<?> task = threadPool.submit(() -> handleClient(clientSocket));
          futures.add(task);
        }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
      e.printStackTrace();

    } finally {

      threadPool.shutdown(); //maybe not needed
      try {
        if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {

            threadPool.shutdownNow();
        }

      } catch (InterruptedException e) {

        threadPool.shutdownNow();
        System.out.println("IOException: " + e.getMessage());
        e.printStackTrace();
      }

    }
  }


  public static KafkaRequest parseRequest(ReadableByteChannel read_channel) throws IOException {

          ByteBuffer buf = ByteBuffer.allocate(4);

          while(buf.hasRemaining()) {

              int bytes_read = read_channel.read(buf);
              System.err.println("Read " + bytes_read + " bytes into size buffer");
              if (bytes_read == -1) throw new EOFException();
          }

          int buffer_pos = buf.position();

          buf.flip();

          int message_size = buf.getInt() + 4;
          

          ByteBuffer header_buf = ByteBuffer.allocate(message_size);
          buf.rewind();
          header_buf.put(buf);

          while (header_buf.hasRemaining()) {

              int bytes_read = read_channel.read(header_buf);
              System.err.println("Read " + bytes_read + " bytes into size buffer");
              if (bytes_read == -1) throw new EOFException();
          }
          header_buf.flip();

          KafkaRequest req = new KafkaRequest(header_buf);

          return req;
  }

  public static void writeResponse(WritableByteChannel write_channel, 
          KafkaRequest req) throws IOException {
    
     ByteBuffer buf = null; 

     if (req.header.request_api_key == 18) {

       ApiVersionResponse resp = new ApiVersionResponse(req);
       buf = resp.WriteToBuf();

     } else if (req.header.request_api_key == 75) {

         DescribeTopicPartitionsResponse resp = new DescribeTopicPartitionsResponse(req);
         buf = resp.WriteToBuf();

     }

     if (buf != null) {
       while (buf.hasRemaining()) {

           write_channel.write(buf);
       }
     }
  }

  private static void handleClient(Socket socket) {

      try (Socket clientSocket = socket){
        WritableByteChannel write_channel; 
        ReadableByteChannel read_channel;
        KafkaRequest req;

        write_channel = Channels.newChannel(clientSocket.getOutputStream());
        read_channel = Channels.newChannel(clientSocket.getInputStream());
        while (true) {
              req = parseRequest(read_channel);
              writeResponse(write_channel, req);
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
        e.printStackTrace();
      }

  }

}
