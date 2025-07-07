package network.request;

import java.nio.ByteBuffer;
import helper.*;

public class ApiVersionsRequestBody implements RequestType {

    public byte client_id_length;
    public byte[] client_id;
    public byte client_software_version_length;
    public byte[] client_software_version;
    public byte tag_buffer;

    public ApiVersionRequestBody(ByteBuffer buf) {

        this.client_id_length = buf.get();
        this.client_id = new byte(this.client_id_length); 
        buf.get(client_id);

        this.client_software_version_length = buf.get();
        this.client_software_version = new byte(this.client_software_version_length); 
        buf.get(client_software_version);

        this.tag_buffer = 0x00;
    }
}
