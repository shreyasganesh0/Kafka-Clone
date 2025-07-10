package helper;

import java.nio.ByteBuffer;

public class Varint {
    public int decodeVarint(ByteBuffer buf) {
        int result = 0;
        int shift = 0; // Number of bit positions to shift for current byte
        byte currentByte;
        
        do {
            currentByte = buf.get();
            
            int dataBits = currentByte & 0x7F;
            
            result |= (dataBits << shift);
            
            shift += 7;
        } while ((currentByte & 0x80) != 0);
        
        return result;
    }

}




