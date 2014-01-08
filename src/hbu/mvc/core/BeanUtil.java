package hbu.mvc.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class BeanUtil {
	public static void requestToAction(HttpServletRequest request,Object action){
	       Class clzAction = action.getClass();

	       Enumeration<String> names = request.getParameterNames();

	       while (names.hasMoreElements()) {         

	           String name =  names.nextElement();        //获得每一个参数名      eg:username

	           String[] value = request.getParameterValues(name);   //获取这个参数对应的值  eg:leno

	           if (value != null) {

	              try {

	                  // 看Action里是否有name这个属性，并获取在Action里的数据类型

	                  Class fieldType = clzAction.getDeclaredField(name).getType();  

	                  String setName = "set"

	                         + name.substring(0, 1).toUpperCase()

	                         + name.substring(1);

	                  Method method = clzAction.getMethod(setName,

	                         new Class[] { fieldType });

	                  

	                  Object[] o = transfer(fieldType, value);

	                  //判断是否为数组属性 

	                  if (fieldType.isArray()) {

	                     method.invoke(action, new Object[]{o});

	                  } else {

	                     method.invoke(action, new Object[]{o[0]} );

	                  }

	              } catch (NoSuchFieldException e) {

	                  //e.printStackTrace();不用处理,因为有些表单字段在Action中没有对应的属性。

	              } catch (Exception e) {

	                  e.printStackTrace();

	              } 

	           }

	       }

	}
	public static void actionToRequest(HttpServletRequest request,Object action){
		   Field[] fields = action.getClass().getDeclaredFields();

	       for (int i = 0; i < fields.length; i++) {

	           String fieldName = fields[i].getName();

	           String getMethodName = "get"

	              + fieldName.substring(0, 1).toUpperCase()

	              + fieldName.substring(1);

	           try {

	              Method getMethod = action.getClass().getMethod(getMethodName, new Class[]{});

	              Object value = getMethod.invoke(action, new Object[]{});

	              if(value!=null){

	                  request.setAttribute(fieldName, value);

	              }

	           } catch (Exception e) {

	              // TODO Auto-generated catch block

	              e.printStackTrace();

	           }

	           

	       

	    }

	}
	
    private static Object[] transfer(Class fieldType, String[] value){

        Object[] os = null;

  

        String typeString = fieldType.getSimpleName().replace("[]", "");

        if("String".equalsIgnoreCase(typeString)){

            os = value;

        }

        else if("int".equals(typeString) || "Integer".equals(typeString)){

            os = new Integer[value.length];

            for(int i = 0; i < os.length; i++){

               os[i] = Integer.parseInt(value[i]);

            }

        }

        else if("float".equals(typeString) || "Float".equals(typeString)){

            os = new Float[value.length];

            for(int i = 0; i < os.length; i++){

               os[i] = Float.parseFloat(value[i]);

            }

        }

        else if("double".equals(typeString) || "Double".equals(typeString)){

            os = new Double[value.length];

            for(int i = 0; i < os.length; i++){

               os[i] = Double.parseDouble(value[i]);

            }

        }

        

        return os;

     }

}
