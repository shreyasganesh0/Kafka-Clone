package network.response;

import network.request.RequestBodyType;
import network.response.*;

public class DescribeTopicPartitionsResponseBody {

    public int throttle_time;
    public int topic_array_length;//varint
    public ResponseTopic[] topic_array;
    public byte next_cursor;
    public byte tag_buffer;

    public DescribeTopicPartitionsResponseBody(RequestBodyType req) {

        this.throttle_time = 0;

        this.topic_array_length = req.topic_array_length;

        this.topic_array = new ResponseTopic[this.topic_array_length];

        for (int i = 1; i < this.topic_array_length; i++) {

            this.topic_array[i - 1] = new ResponseTopic(req.topic_array[i - 1]);
        }

        this.next_cursor = (byte)0xFF;
        this.tag_buffer = 0x00;
    }

    public int GetSize() {

        return 4 + 1 + this.topic_array_length * ResponseTopic.GetSize() + 1 + 1;
    }

    public WriteToBuf(ByteBuffer buf) {

        buf.putInt(this.throttle_time);
        buf.put((byte)this.topic_array_length);

        for (int i = 0; i < this.topic_array_length; i++) {

            this.topic_array[i].WriteToBuf(buf);
        }

        buf.put(this.next_cursor);
        buf.put(this.tag_buffer);
    }
}

