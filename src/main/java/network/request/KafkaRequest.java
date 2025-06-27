package network.request;

import network.request.KafkaHeaderV2;

public class KafkaRequest {

    public int message_size;
    public KafkaHeaderV2 header;
    public byte[] body;

}
