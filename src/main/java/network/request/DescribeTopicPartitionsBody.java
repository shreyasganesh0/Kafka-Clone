package network.request;

public class DescribeTopicPartitionsBody {

    public int topic_array_length;
    public RequestTopic[] topic_array;
    public int response_partition_limit;
    public Optional<byte> cursor;
    public byte tag_buffer;

    public DescribeTopicPartitionsBody(ByteBuffer buf) {

        this.topic_array_length = Varint.decodeVarint(buf);
        this.topic_array = new RequestTopic[this.topic_array_length - 1];

        for (int i = 1; i < this.topic_array_length; i++) {
            
            topic_array[i - 1] = new RequestTopic(buf);
        }

        this.response_partition_limit = buf.getInt();
        this.cursor = buf.getShort();
        this.tag_buffer = buf.getShort();

        buf.flip();

    }
}

public class RequestTopic {


    public int topic_name_length;
    public byte[] topic_name;
    public byte topic_tag_buffer;

    public RequestTopic(ByteBuffer buf) {

        this.topic_name_length = Varint.decodeVarint(buf);

        topic_name = new byte[this.topic_name_length - 1];

        buf.get(topic_name);

        this.topic_tag_buffer = 0x00;
    }
}




