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
 

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 9092;
    try {
      serverSocket = new ServerSocket(port);
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      // Wait for connection from client.
      clientSocket = serverSocket.accept();

      WritableByteChannel write_channel = Channels.newChannel(clientSocket.getOutputStream());
      ReadableByteChannel read_channel = Channels.newChannel(clientSocket.getInputStream());

      KafkaRequest req = parseRequest(read_channel); 
      writeResponse(write_channel, req.header.correlation_id, req.header.request_api_version);

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
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

       ByteBuffer buf = ByteBuffer.allocate(12);

       int message_size = 0;
       buf.putInt(message_size);
       buf.putInt(correlation_id);

       short error_code = 0;

       if (request_api_version != 4 || request_api_version != 0) {

           error_code = 35;
       }
       buf.putShort(error_code);
       System.out.println("Sending correlation id " + correlation_id);
       buf.flip();

       while (buf.hasRemaining()) {

           write_channel.write(buf);
       }
  }

}
