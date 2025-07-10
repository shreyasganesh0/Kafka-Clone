package network.request;
import java.nio.ByteBuffer;
import helper.*;

public class RequestTopic {
    public int topic_name_length;
    public byte[] topic_name;
    public byte topic_tag_buffer;
    public RequestTopic(ByteBuffer buf) {
        Varint varint = new Varint();
        this.topic_name_length = varint.decodeVarint(buf);
        topic_name = new byte[this.topic_name_length - 1];
        buf.get(topic_name);
        this.topic_tag_buffer = buf.get();
    }
}
