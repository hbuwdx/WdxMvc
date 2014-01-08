package hbu.mvc.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigUtil {
	public static void parseConfig(String configFile,Map<String, ActionConfig> map){
		SAXReader reader=new SAXReader();
		Document document=null;
		try {
			document=reader.read(new File(configFile));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		if(document==null){
			return;
		}
		
		Element root=document.getRootElement();
		List<Element> list=root.selectNodes("package/action");
		for(Element element:list){
			String name=element.attributeValue("name");
			String clazz=element.attributeValue("class");
			String method=element.attributeValue("method");
			if(method == null || "".equals(method)){
                method="execute";
            }
			List<Element> results=element.selectNodes("result");
			Map<String, String> resultMap=new HashMap<String, String>();
			for(Element result:results){
				resultMap.put(result.attributeValue("name"), result.getText());
			}
			ActionConfig config=new ActionConfig();
			config.setName(name);
			config.setClzName(clazz);
			config.setMethod(method);
			config.setResultMap(resultMap);
			
			map.put(name, config);
		}
	}
}
