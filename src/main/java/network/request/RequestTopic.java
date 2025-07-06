package network.request;

import java.nio.ByteBuffer;

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
