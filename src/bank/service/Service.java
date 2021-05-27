package bank.service;

import bank.dao.BankDAO;
import bank.vo.Account;

public class Service {
	private static Service service = new Service();
	private Service() {}
	
	private BankDAO dao = BankDAO.getInstance();
	
	public static Service getInstance() {
		return service;
	}

	public void join(Account account) {
		dao.join(account);
	}

	public boolean login(Account account) {
		boolean result = dao.login(account);
		return result;
	}

	public int deposit(String id, int money) {
		int totalMoney = dao.deposit(id, money);
		return totalMoney;
	}

	public int withdraw(String id, int money) {
		int totalMoney = dao.withdraw(id, money);
		return totalMoney;
	}

	public int query(String id) {
		return dao.query(id);
	}

	public boolean search(String rId) {
		return dao.search(rId);
	}
	
	public int transfer(String id, String rId, int money) {
		return dao.transfer(id, rId, money);
	}
}