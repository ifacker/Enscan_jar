package plugins.Enscan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class S2L {
    public static List<String> Str2List(String str) {
        List<String> result = new ArrayList<>();

        // 移除开头和结尾的方括号
        str = str.substring(1, str.length() -1);
        String[] parts = str.split(",");
        for (String part : parts){
            result.add(part.trim());
        }
        return result;
    }
}
