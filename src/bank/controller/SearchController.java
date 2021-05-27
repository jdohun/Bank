package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.service.Service;

public class SearchController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rId = request.getParameter("rId");
		
		boolean result = Service.getInstance().search(rId);
		String msg = null;
		if(result) msg = "true";
		else msg = "false";

		request.setAttribute("rId", rId);
		request.setAttribute("msg", msg);
		
		String path = "/transfer.jsp";
		HttpUtil.forward(request, response, path);
	}
}