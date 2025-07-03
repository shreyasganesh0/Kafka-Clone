package network.response;

import java.nio.ByteBuffer;
import network.response.ApiKey;

public class ApiVersion4 {

    public short api_key;
    public short min_sup_version;
    public short max_sup_version;
    public byte tag_buffer;

    public ApiVersion4(ApiKey api_key) {

        
        this.api_key = api_key.GetApiKey(); //hardcoded for apiversion key
        this.min_sup_version = api_key.GetMinVersion();
        this.max_sup_version = api_key.GetMaxVersion();
        this.tag_buffer = 0;
    }

    public int GetSize() {

        return 7;
    }

    public void WriteToBuf(ByteBuffer buf) {
        
        buf.putShort(api_key);
        buf.putShort(min_sup_version);
        buf.putShort(max_sup_version);
        buf.put(tag_buffer);
    }

}

