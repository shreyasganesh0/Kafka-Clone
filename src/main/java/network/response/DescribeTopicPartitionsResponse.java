package network.response;

import network.request.KafkaRequest;
import network.response.*;

public class DescribeTopicPartitionsResponse {

    public int message_size;
    public HeaderV1 header;
    public DescribeTopicPartitionsResponseBody body;

    public DescribeTopicParitionsResponse(KafkaRequest req) {

        this.header = new HeaderV1(req.header.correlation_id);
        this.body = new DescribeTopicParitionsBody(req.body);
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

