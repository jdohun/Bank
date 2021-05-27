package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.service.Service;
import bank.vo.Account;

public class LoginController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		Account account = new Account(id, pwd);
		Service service = Service.getInstance();
		boolean result = service.login(account);
		
		String path = null;
		
		if(result) {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			path = "/menu.jsp";
		}else {
			path = "/login.jsp";
		}
		
		HttpUtil.forward(request, response, path);
	}
}
