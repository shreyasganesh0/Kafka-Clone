package network.response;
import java.nio.ByteBuffer;
import java.util.Arrays;

import network.request.RequestTopic;
public class ResponseTopic {
    public short error_code;
    public int topic_name_length;//varint
    public byte[] topic_name;
    public long topic_id;
    public byte is_internal;
    public int partition_array_length; //varint
    public byte[] partition_array; //nullable if length = 1 maybe we can add the type later as some class
    public byte[] authorized_ops;
    public byte tag_buffer;
    public ResponseTopic(RequestTopic topic) {
        this.error_code = 3; // for unkown topic will have to make this dynamic later
        this.topic_name_length = topic.topic_name_length;
        this.topic_name = Arrays.copyOf(topic.topic_name, topic.topic_name.length);
        this.topic_id = 0; //unassigned or null uuid will have to make dynamic later
        this.is_internal = 0x00;
        this.partition_array_length = 1; //hardcoded for now will have to see where to parse it from
        this.authorized_ops = new byte[]{0x00, 0x00, 0x0D, (byte)0xF8};
        this.tag_buffer = 0x00;
    }
    public int GetSize() {
        return 2 + 4 + this.topic_name_length + 8 + 1 + 4 + this.partition_array_length + this.authorized_ops.length + 1;
    }
    public void WriteToBuf(ByteBuffer buf) {
        buf.putShort(this.error_code);
        buf.putInt(this.topic_name_length);
        buf.put(this.topic_name);
        buf.putLong(this.topic_id);
        buf.put(is_internal);
        buf.putInt(this.partition_array_length);
        if (this.partition_array != null) {
            buf.put(this.partition_array);
        }
        buf.put(this.authorized_ops);
        buf.put(this.tag_buffer);
    }
}
