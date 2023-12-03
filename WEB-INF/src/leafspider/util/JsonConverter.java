package leafspider.util;

//import org.json.XML;
//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;

public class JsonConverter {

    public static String xmlToJson( String xml ) throws Exception {

//		return XML.toJSONObject(xml).toString();

        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xml);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(jsonNode);
    }

    public static String objectToJson( Object object ) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static ArrayList jsonToArrayList( String json ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ArrayList.class);
    }
}
