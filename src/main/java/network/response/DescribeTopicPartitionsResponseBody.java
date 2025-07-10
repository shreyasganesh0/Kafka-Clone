package network.response;
import network.request.DescribeTopicPartitionsRequestBody;
import network.response.*;
import java.nio.ByteBuffer;
public class DescribeTopicPartitionsResponseBody {
    public int throttle_time;
    public int topic_array_length;//varint
    public ResponseTopic[] topic_array;
    public byte next_cursor;
    public byte tag_buffer;
    public DescribeTopicPartitionsResponseBody(DescribeTopicPartitionsRequestBody req) {
        this.throttle_time = 0;
        this.topic_array_length = req.topic_array_length;
        this.topic_array = new ResponseTopic[this.topic_array_length - 1];
        for (int i = 0; i < this.topic_array_length - 1; i++) {
            this.topic_array[i] = new ResponseTopic(req.topic_array[i]);
        }
        this.next_cursor = (byte)0xFF;
        this.tag_buffer = 0x00;
    }
    public int GetSize() {
        int totalSize = 4 + 1 + 1 + 1; // throttle_time + topic_array_length + next_cursor + tag_buffer
        for (ResponseTopic topic : this.topic_array) {
            totalSize += topic.GetSize();
        }
        return totalSize;
    }
    public void WriteToBuf(ByteBuffer buf) {
        buf.putInt(this.throttle_time);
        buf.put((byte)this.topic_array_length);
        for (int i = 0; i < this.topic_array.length; i++) {
            this.topic_array[i].WriteToBuf(buf);
        }
        buf.put(this.next_cursor);
        buf.put(this.tag_buffer);
    }
}
