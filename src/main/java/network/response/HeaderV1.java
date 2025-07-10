package network.response;
import java.nio.ByteBuffer;
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
    public void WriteToBuf(ByteBuffer buf) {
        buf.putInt(this.correlation_id);
        buf.put(this.tag_buffer);
    }
}
