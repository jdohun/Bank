package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.service.Service;

public class QueryController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = (String)request.getSession().getAttribute("id");
		
		String job = (String)request.getParameter("job");
		String path = null;
		if(job.equals("q")) {
			path = "/Result/queryResult.jsp";
		}else if(job.equals("w")) {
			path = "/withdraw.jsp";
		} 
		
		int money = Service.getInstance().query(id);
		
		request.setAttribute("money", money);
		
		HttpUtil.forward(request, response, path);
	}

}
