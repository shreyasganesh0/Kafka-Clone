package network.response;

public class DescribeTopicPartitionsResponse {

    public int message_size;
    public HeaderV1 header;
    public DescribeTopicPartitionsBody body;

    public DescribeTopicParitionsResponse(KafkaRequest req) {

        this.header = new HeaderV1(correlation_id);
        this.body = new DescribeTopicParitionsBody();
        this.message_size = header.GetSize() + body.GetSize();
    }

}

public class HeaderV1 {

    public int correlation_id;
    public byte tag_buffer;

    public HeaderV1(int correlation_id) {

        this.correlation_id = correlation_id;
        this.tag_buffer = 0x00;
    }
}
