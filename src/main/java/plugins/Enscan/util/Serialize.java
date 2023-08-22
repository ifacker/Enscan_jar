package plugins.Enscan.util;


import java.io.*;

public class Serialize {


    // 对象序列化
    public static void serializeObject(Object object, String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath); ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 对象反序列化
    public static Object unSerializeObject(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Object) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
