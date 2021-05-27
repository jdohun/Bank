package bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.vo.Account;

public class BankDAO {
	private static BankDAO dao = new BankDAO();
	private BankDAO() {}
	
	public static BankDAO getInstance() {
		return dao;
	}

	private Connection connect() {
		Connection con = null;
		String dbUrl = "jdbc:mysql://localhost/BankDB?characterEncoding=UTF-8&serverTimezone=UTC";
		String user = "root";
		String passwd = "1234";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dbUrl, user, passwd);
		} catch (ClassNotFoundException e) {
			System.out.print("MemberDAO-connect:ClassNotFound " + e);
		} catch (SQLException e) {
			System.out.print("MemberDAO-connect:SQL " + e);
		}
		
		return con;
	}
	
	private void close(PreparedStatement pstmt, Connection con) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.print("pstmt.close error " + e);
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.print("con.close error " + e);
			}
		}
	}
	
	private void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.print("rs.close error " + e);
			}
		}
		
		close(pstmt, con);
	}
	
	public void join(Account account) {
		Connection con = connect();
		String sql = "insert into account values(?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getId());
			pstmt.setString(2, account.getPwd());
			pstmt.setInt(3, account.getMoney());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("join error" + e);
		} finally {
			close(pstmt, con);
		}
	}

	public boolean login(Account account) {
		boolean result = false;
		
		Connection con = connect();
		String sql = "select * from account where id=? and pwd=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getId());
			pstmt.setString(2, account.getPwd());
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = true;
			
		} catch (SQLException e) {
			System.out.println("login error" + e);
		} finally {
			close(rs, pstmt, con);
		}
		
		return result;
	}

	public int deposit(String id, int money) {
		Connection con = connect();
		String sql = "select money from account where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int moneyDB = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				moneyDB = rs.getInt("money");
			}
			moneyDB += money;

			sql = "update account set money=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, moneyDB);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deposit error" + e);
		} finally {
			close(rs, pstmt, con);
		}
		return moneyDB;
	}

	public int withdraw(String id, int money) {
		Connection con = connect();
		String sql = "select money from account where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int moneyDB = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				moneyDB = rs.getInt("money");
			}
			
			if(money > moneyDB) {
				return -1;
			}
			moneyDB -= money;

			sql = "update account set money=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, moneyDB);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("withdraw error" + e);
		} finally {
			close(rs, pstmt, con);
		}
		return moneyDB;
	}
	
	public int query(String id) {
		Connection con = connect();
		String sql = "select money from account where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int moneyDB = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				moneyDB = rs.getInt("money");
			}
			
		} catch (SQLException e) {
			System.out.println("query error" + e);
		} finally {
			close(rs, pstmt,con);
		}
		
		return moneyDB;
	}

	public boolean search(String rId) {
		boolean result = false;
		Connection con = connect();
		String sql = "select money from account where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = true;
			
		} catch (SQLException e) {
			System.out.println("search error" + e);
		} finally {
			close(rs, pstmt,con);
		}
		return result;
	}
	
	public int transfer(String id, String rId, int money) {
		int TotalMoney = withdraw(id, money);
		if(TotalMoney < 0) {
			return TotalMoney;
		}
		deposit(rId, money);
		return TotalMoney;
	}
}
