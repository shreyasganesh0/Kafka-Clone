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

public class ResponseTopic {


    public short error_code;
    public int topic_name_length;//varint
    public byte[] topic_name;
    public long topic_id;
    public byte is_internal;
    public int partition_array_length; //varint
    public byte[] partition_array; //nullable if length = 1 maybe we can add the type later as some class
    public int authorized_ops;
    public byte tag_buffer;


    public ResponseTopic(RequestTopic topic) {

        this.error_code = 3; // for unkown topic will have to make this dynamic later
        this.topic_name_length = topic.topic_name_length;
        this.topic_name = Array.copyOf(topic.topic_name, topic.topic_name.length);
        this.topic_id = 0; //unassigned or null uuid will have to make dynamic later
        this.is_internal = 0x00;
        this.partition_array_length = 1; //hardcoded for now will have to see where to parse it from
        this.authorized_ops = {0x00, 0x00, 0x0D, 0xF8};
        this.tag_buffer = 0x00;
    }
}
