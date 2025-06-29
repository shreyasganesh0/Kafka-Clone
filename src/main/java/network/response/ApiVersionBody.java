package network.response;

import network.response.ApiVersion4;
import java.nio.ByteBuffer;

public class ApiVersionBody {

    public short error_code;
    public byte api_version_array_size; //this is actually supposed to be var int which will need to be implemented
    public ApiVersion4[] api_version_array; // will need to change this to any apiversion array
    public int throttle_time;
    public byte tag_buffer; //not actually a char need to change this imple later


    public ApiVersionBody(int request_api_version) {
        
        // values are hardcoded for now except error code which will change
        this.error_code = 0;

       if (request_api_version != 4 || request_api_version != 0) {

           this.error_code = 35;
       }
        this.api_version_array_size = 2;

        this.api_version_array = new ApiVersion4[1];
        api_version_array[0] = new ApiVersion4();

        this.throttle_time = 0;
        this.tag_buffer = 0;
    }

    public int GetSize() {

        int sum = 0;

        for (ApiVersion4 api : this.api_version_array) {

            sum += api.GetSize(); //useful when i add support for different api versions
        }

        return 2 + 1 + sum + 4 + 1; 
    }

    public void WriteToBuf(ByteBuffer buf) {

        buf.putShort(error_code);
        buf.put(this.api_version_array_size);

        for (ApiVersion4 api : this.api_version_array) {

            api.WriteToBuf(buf);
        }

        buf.putInt(this.throttle_time);
        buf.put(tag_buffer);
    }
}

