package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.service.Service;
import bank.vo.Account;

public class DepositController implements Controller {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int money = Integer.parseInt(request.getParameter("money"));
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		Service service = Service.getInstance();
		int totalMoney = service.deposit(id, money);
		
		request.setAttribute("money", money);
		request.setAttribute("totalMoney", totalMoney);
		
		String path = "./Result/depositResult.jsp";
		HttpUtil.forward(request, response, path);
	}
}
