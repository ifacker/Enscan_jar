package plugins.Enscan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import plugins.Enscan.DataType.EnscanResult;

public class JacksonJson {
    public static EnscanResult unSerialization(String content){
        ObjectMapper objectMapper = new ObjectMapper();
        EnscanResult enscanResult = null;
        try {
            enscanResult = objectMapper.readValue(content, EnscanResult.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return enscanResult;
    }
}
