package network.response;
import network.response.ApiVersionResponseBody;
import network.request.KafkaRequest;
import java.nio.ByteBuffer;
public class ApiVersionResponse {
    public int message_size;
    public int header_v0; 
    public ApiVersionResponseBody body;
    public ApiVersionResponse(KafkaRequest req) {
        this.header_v0 = req.header.correlation_id;
        this.body = new ApiVersionResponseBody(req.header.request_api_key);
        this.message_size = this.body.GetSize() + 4;
    }
    public ByteBuffer WriteToBuf() {
        ByteBuffer buf = ByteBuffer.allocate(this.message_size + 4); 
        buf.putInt(this.message_size);
        buf.putInt(this.header_v0);
        this.body.WriteToBuf(buf);
        buf.flip();
        return buf;
    }
}
