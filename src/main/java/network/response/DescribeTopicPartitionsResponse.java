package network.response;
import network.request.KafkaRequest;
import network.request.DescribeTopicPartitionsRequestBody;
import network.response.*;
import java.nio.ByteBuffer;
public class DescribeTopicPartitionsResponse {
    public int message_size;
    public HeaderV1 header;
    public DescribeTopicPartitionsResponseBody body;
    public DescribeTopicPartitionsResponse(KafkaRequest req) {
        this.header = new HeaderV1(req.header.correlation_id);
        this.body = new DescribeTopicPartitionsResponseBody((DescribeTopicPartitionsRequestBody)req.body);
        this.message_size = header.GetSize() + body.GetSize();
    }
    public ByteBuffer WriteToBuf() {
        ByteBuffer buf = ByteBuffer.allocate(this.message_size + 4);
        buf.putInt(this.message_size);
        this.header.WriteToBuf(buf);
        this.body.WriteToBuf(buf);
        buf.flip();
        return buf;
    }
}
