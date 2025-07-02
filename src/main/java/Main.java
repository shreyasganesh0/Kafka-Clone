import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.io.OutputStream;
import java.io.InputStream;
import network.request.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.io.EOFException;
import network.response.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

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

          KafkaRequest request = new KafkaRequest();
          ByteBuffer buf = ByteBuffer.allocate(4);

          while(buf.hasRemaining()) {

              int bytes_read = read_channel.read(buf);
              System.err.println("Read " + bytes_read + " bytes into size buffer");
              if (bytes_read == -1) throw new EOFException();
          }

          buf.flip();

          request.message_size = buf.getInt();

          ByteBuffer header_buf = ByteBuffer.allocate(request.message_size);

          while (header_buf.hasRemaining()) {

              int bytes_read = read_channel.read(header_buf);
              System.err.println("Read " + bytes_read + " bytes into size buffer");
              if (bytes_read == -1) throw new EOFException();
          }
          header_buf.flip();

          KafkaHeaderV2 header = new KafkaHeaderV2(header_buf);

          request.header = header;

          return request;
  }

  public static void writeResponse(WritableByteChannel write_channel, int correlation_id, short request_api_version) throws IOException {


       ApiVersionResponse resp = new ApiVersionResponse(correlation_id, request_api_version);

       ByteBuffer buf = resp.WriteToBuf();

       while (buf.hasRemaining()) {

           write_channel.write(buf);
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
              writeResponse(write_channel, req.header.correlation_id, req.header.request_api_version);
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
        e.printStackTrace();
      }

  }

}
