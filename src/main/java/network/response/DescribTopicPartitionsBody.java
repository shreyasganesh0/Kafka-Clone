package network.response;

import network.request.*;
import java.nio.ByteBuffer;

public class DescribeTopicPartitionsResponseBody {

    public int throttle_time;
    public int topic_array_length;//varint
    public ResponseTopic[] topics;
    public byte next_cursor;
    public byte tag_buffer;

    public DescribeTopicPartitionsBody(DescribeTopicPartitionsReqBody req) {

        this.throttle_time = 0;

        this.topic_array_length = req.topic_array_length;

        topics = new ResponseTopic[this.topic_array_length];

        for (int i = 1; i < this.topic_array_length; i++) {

            topics[i - 1] = new ResponseTopic(req.topic_array[i - 1]);
        }

        this.next_cursor = 0xFF;
        this.tag_buffer = 0x00;
    }
}

