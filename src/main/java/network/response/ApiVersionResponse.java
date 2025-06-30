package network.response;

import network.response.ApiVersionBody;
import java.nio.ByteBuffer;

public class ApiVersionResponse {

    public int message_size;
    public int header_v0; 
    public ApiVersionBody body;

    public ApiVersionResponse(int correlation_id, int request_api_version) {

        this.header_v0 = correlation_id;
        this.body = new ApiVersionBody(request_api_version);
        this.message_size = this.body.GetSize() + 4;
    }

    public ByteBuffer WriteToBuf() {

        ByteBuffer buf = ByteBuffer.allocate(this.message_size + 4); 
        buf.putInt(this.message_size);
        buf.putInt(this.header_v0);

        this.body.WriteToBuf(buf);

        buf.flip();

        return buf;
    }

}
