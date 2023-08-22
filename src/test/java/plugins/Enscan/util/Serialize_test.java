package plugins.Enscan.util;

public class Serialize_test {
    public static void main(String[] args) {
        String a = "fuck";
        Serialize.serializeObject(a, "test.per");

        String b = (String)Serialize.unSerializeObject("test.per");
        System.out.println(b);
    }
}
