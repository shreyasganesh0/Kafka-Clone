package network.response;

import java.nio.ByteBuffer;

public class ApiVersion4 {

    public int api_key;
    public int min_sup_version;
    public int max_sup_version;

    public ApiVersion4() {

        this.api_key = 18; //hardcoded for apiversion key
        this.min_sup_version = 0;
        this.max_sup_version = 4;
    }

    public int GetSize() {

        return 12;
    }

    public void WriteToBuf(ByteBuffer buf) {
        
        buf.putInt(api_key);
        buf.putInt(min_sup_version);
        buf.putInt(max_sup_version);
    }
}



