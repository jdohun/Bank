package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.service.Service;

public class TransferController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = (String)request.getSession().getAttribute("id");
		String rId = request.getParameter("rId");
		int money = Integer.parseInt(request.getParameter("money"));
		
		int TotalMoney = Service.getInstance().transfer(id, rId, money);
		if(TotalMoney < 0) {
			request.setAttribute("result", "Not enough Money");
		}
		else {
			request.setAttribute("id", id);
			request.setAttribute("rId", rId);
			request.setAttribute("money", money);
			request.setAttribute("TotalMoney", TotalMoney);
		}
		
		String path = "/Result/transferResult.jsp";
		HttpUtil.forward(request, response, path);
	}

}
