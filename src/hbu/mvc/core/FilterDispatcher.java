package hbu.mvc.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterDispatcher implements Filter {
	//这是共享的
	private Map<String,ActionConfig> actionMap=new HashMap<String,ActionConfig>();
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String uri=request.getServletPath();
		
		if(!uri.endsWith(".action")){
			arg2.doFilter(arg0, arg1);
			return;
		}
		int start=uri.indexOf('/');
		int end=uri.indexOf('.');
		String actionName=uri.substring(start+1, end);
		
		
		ActionConfig config=actionMap.get(actionName);
		if(config==null){
			response.setStatus(response.SC_NOT_FOUND);
			return;
		}
		
		String clazzName=config.getClzName();
		Object action=null;
		try {
			action = getAction(clazzName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(action==null){
			response.setStatus(response.SC_NOT_FOUND);
			return;
		}
		
		BeanUtil.requestToAction(request, action);
		String result=executeAction(config,action); //是一个路径
		BeanUtil.actionToRequest(request, action);
		
		
		if(result==null){
			response.setStatus(response.SC_NOT_FOUND);
			return;
		}
		request.getRequestDispatcher(result).forward(request, response);
	}

	private String executeAction(ActionConfig config,Object action) {
		String method=config.getMethod();
		String result=null; //success 等
		try {
			Method callMethod=action.getClass().getDeclaredMethod(method, new Class[]{});
			result=(String) callMethod.invoke(action, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config.getResultMap().get(result);
	}

	private Object getAction(String clazzName) throws Exception {
		return Class.forName(clazzName).newInstance();
	}

	public void init(FilterConfig arg0) throws ServletException {
		String webPath=arg0.getServletContext().getRealPath("\\WEB-INF\\classes\\mvc.xml");
//		String webPath=getClass().getClassLoader().getResource("mvc.xml").getPath();
		ConfigUtil.parseConfig(webPath, actionMap);
	}


}
