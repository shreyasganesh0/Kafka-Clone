package network.request;
import java.nio.ByteBuffer;

public class KafkaHeaderV2 {

    public short request_api_key;
    public short request_api_version;
    public int correlation_id; 
    public byte[] client_id; 
    public int TAG_BUFFER_size;
    public short TAG_ITEM_size;
    public byte[] TAG_BUFFER; 

    public KafkaHeaderV2(ByteBuffer buf) {

        this.request_api_key = buf.getShort();
        this.request_api_version = buf.getShort();
        this.correlation_id = buf.getInt();
        
        short client_id_size = buf.getShort();
        this.client_id = new byte[client_id_size];
        buf.get(client_id);

        //this.TAG_BUFFER = new byte[this.TAG_BUFFER_size * this.TAG_ITEM_size];
        System.err.println("Remaining bytes after header: " + buf.remaining());

    }
}



