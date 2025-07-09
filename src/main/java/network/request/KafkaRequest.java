package network.request;

import network.response.ApiVersionBody;

public class KafkaRequest {

    public int message_size;
    public KafkaHeaderV2 header;
    public RequestBodyType body;

    public KafkaRequest(ByteBuffer buf) {
    
        buf.rewind();
        this.message_size = buf.getInt();

        this.header = new KafkaHeaderV2(buf);

        if (header.correlation_id == 18) {
    
            this.body = new ApiVersionBody(buf);
        } else if (header.correlation_id == 75) {

            this.body = new DescribeTopicPartitionsRequestBody(buf);
        }

        buf.flip();

    }

}
