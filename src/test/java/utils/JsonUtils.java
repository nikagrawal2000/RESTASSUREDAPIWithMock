package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJsonAsObject(String filePath, Class<T> clazz) throws Exception {
        return objectMapper.readValue(new File(filePath), clazz);
    }

    public static String readJsonAsString(String filePath) throws Exception {
        return new String(java.nio.file.Files.readAllBytes(new File(filePath).toPath()));
    }
}