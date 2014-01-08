package hbu.app.action;

public class LoginAction {
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String logon(){
		System.out.println("Login:username:"+username+" password:"+password);
		if(username.equals("hello")&&password.equals("123")){
			return "success";
		}else{
			return "login";
		}
	}
	
	
}
