package hbu.mvc.core;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig {
	   private String name;

	    private String clzName;

	    private String method;

	    private Map<String, String> resultMap = new HashMap<String, String>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClzName() {
			return clzName;
		}

		public void setClzName(String clzName) {
			this.clzName = clzName;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Map<String, String> getResultMap() {
			return resultMap;
		}

		public void setResultMap(Map<String, String> resultMap) {
			this.resultMap = resultMap;
		}
	    
	    

}
