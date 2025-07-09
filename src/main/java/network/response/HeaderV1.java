package network.response;

public class HeaderV1 {

    public int correlation_id;
    public byte tag_buffer;

    public HeaderV1(int correlation_id) {

        this.correlation_id = correlation_id;
        this.tag_buffer = 0x00;
    }

    public int GetSize() {

        return 4 + 1;
    }
}
