package plugins.Enscan.util;

import plugins.Enscan.DataType.EnscanResult;

public class JacksonJson_test {
    public static void main(String[] args) {

        String content = FileIO.readFile("exec/outs/小米科技有限责任公司--2023-08-07--1691395951.json");

        EnscanResult enscanResult = JacksonJson.unSerialization(content);
        System.out.printf("");
    }
}
