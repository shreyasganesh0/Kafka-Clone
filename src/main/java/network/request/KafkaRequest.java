package network.request;
import network.request.*;
import java.nio.ByteBuffer;
public class KafkaRequest {
    public int message_size;
    public KafkaHeaderV2 header;
    public RequestBodyType body;
    public KafkaRequest(ByteBuffer buf) {
    
        buf.rewind();
        this.message_size = buf.getInt();
        this.header = new KafkaHeaderV2(buf);
        if (header.request_api_key == 18) {
    
            this.body = new ApiVersionsRequestBody(buf);
        } else if (header.request_api_key == 75) {
            this.body = new DescribeTopicPartitionsRequestBody(buf);
        }
        buf.flip();
    }
}
