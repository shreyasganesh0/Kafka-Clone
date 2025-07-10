package network.request;
import java.nio.ByteBuffer;
import helper.*;
public class DescribeTopicPartitionsRequestBody implements RequestBodyType {
    public int topic_array_length;
    public RequestTopic[] topic_array;
    public int response_partition_limit;
    public byte cursor;
    public byte tag_buffer;
    public DescribeTopicPartitionsRequestBody(ByteBuffer buf) {
        Varint varint = new Varint();
        this.topic_array_length = varint.decodeVarint(buf);
        this.topic_array = new RequestTopic[this.topic_array_length - 1];
        for (int i = 1; i < this.topic_array_length; i++) {
            
            topic_array[i - 1] = new RequestTopic(buf);
        }
        this.response_partition_limit = buf.getInt();
        this.cursor = (byte) buf.getShort();
        this.tag_buffer = buf.get();
    }
}
