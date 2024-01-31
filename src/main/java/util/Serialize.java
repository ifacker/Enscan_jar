package util;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
    public static Object deserializeObject(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Object) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象序列化为 json 串
     * @param object 传入需要序列化的对象
     * @return 返回一个序列化好的 json 串
     */
    public static String object2json(Object object) {
        // 创建 Gson 实例
        Gson gson = new Gson();

        // 对象序列化为 JSON 字符串
        return gson.toJson(object);
    }

    /**
     * json 反序列化为对象
     * @param jsonStr 输入需要反序列化的 json 串
     * @param cfile 输入反序列化时需要用到的模板（class文件）
     * @return 最后返回一个对象
     */
    public static Object json2object(String jsonStr, Class<?> cfile) {
        Gson gson = new Gson();

        Object object = null;

        try{
            object =  gson.fromJson(jsonStr, cfile);
        }catch (JsonSyntaxException e){
            System.out.println("json 反序列化异常");
        }
        return object;
    }

}
