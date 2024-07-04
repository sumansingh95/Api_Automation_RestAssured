package lib;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class FileReader {

        public static String readFile(String filePath) throws IOException {
            // Read the file content into a String
            File file = new File(filePath);
            String jsonContent = FileUtils.readFileToString(file, "UTF-8");
            // Return the JSON content as a String
            return jsonContent;
        }

    public static Map<String,String> getPrperties(String filePath) throws IOException {
        FileInputStream fis=new FileInputStream(filePath);
        Properties properties = new Properties();
        properties.load(fis);
        Map<String,String> map = (Map) properties;
        return map;
    }
}
