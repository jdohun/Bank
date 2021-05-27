package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.service.Service;

public class WithdrawController implements Controller {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		int money = Integer.parseInt(request.getParameter("money"));
		
		Service service = Service.getInstance();
		int totalMoney = service.withdraw(id, money);
		
		if(totalMoney < 0) {
			request.setAttribute("result", "Money is not enough");
		}
		
		request.setAttribute("money", money);
		request.setAttribute("totalMoney", totalMoney);
		
		String path = "./Result/withdrawResult.jsp";
		HttpUtil.forward(request, response, path);
	}
}
