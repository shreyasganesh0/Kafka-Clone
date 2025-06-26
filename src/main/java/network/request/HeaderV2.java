package network.request;

public class HeaderV2 {

    public short request_api_key;
    public short request_api_version;
    public int correlation_id; 
    public int client_id_size; 
    public byte[] client_id; 
    public int TAG_BUFFER_size;
    public short TAG_ITEM_size;
    public byte[] TAG_BUFFER; 

    public HeaderV2(int client_id_size, int TAG_BUFFER_size, int TAG_ITEM_size) {

        this.request_api_key = readInt();
        this.client_id_size = readInt();
        this.client_id_size = readInt();
        this.TAG_BUFFER_size = TAG_BUFFER_size;
        this.TAG_ITEM_size = TAG_ITEM_size;

        this.client_id = new byte[this.client_id_size];
        this.TAG_BUFFER = new byte[this.TAG_BUFFER_size * this.TAG_ITEM_size];
    }
}



