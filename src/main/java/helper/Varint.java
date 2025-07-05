public class Varint {

    public int decodeVarint(ByteBuffer buf) {

        int ret_num = 0;

        byte curr_byte = buf.get();

        byte size_mask = 0x7F;

        int pos_mask = 0xFF;

        int curr_byte_pos = 0;
        
        while (curr_byte >> 7 & 0x01) {

            byte temp_rep = curr_byte & size_mask;

            temp_rep = temp_rep << (7 * curr_byte_pos);

            ret_num |= temp_rep;

            curr_byte_pos++;

            curr_byte = buf.get();
        }

        return ret_num;
    }

}




