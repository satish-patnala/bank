package org.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bank.domain.UserDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;

public class UserDetailsUtil {
	String url = "jdbc:mysql://localhost:3306/hibernate";
	Connection con;
	PreparedStatement preStmt;
	
	TransactionDetailsUtil txUtil = new TransactionDetailsUtil();

	public void getConnection() {
		try {
			con = DriverManager.getConnection(url, "root", "12345");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insertDetails(UserDetails user) {
		try {
			getConnection();
			preStmt = con.prepareStatement("insert into user_info values(?,?,?,?,?,?,?,?)");
			preStmt.setString(1, user.getAccountNo());
			preStmt.setString(2, user.getUserName());
			preStmt.setString(3, user.getTypeOfAccount());
			preStmt.setString(4, user.getBranch());
			preStmt.setInt(5, (int) user.getBalance());
			preStmt.setInt(6, user.getAge());
			preStmt.setTimestamp(7, Timestamp.valueOf(LocalDate.now() + " " + LocalTime.now()));
			preStmt.setTimestamp(8, Timestamp.valueOf(LocalDate.now() + " " + LocalTime.now()));
			preStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return true;
	}

	public List<UserDetails> viewAllDetails() {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info");
			ResultSet rs = preStmt.executeQuery();
			return fetchDetailsInResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public UserDetails getDetailsByAcntNo(String acntNo) {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info where useracno = ?");
			preStmt.setString(1, acntNo);
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				String accNo = rs.getString(1);
				String name = rs.getString(2);
				String typeOfAcnt = rs.getString(3);
				String branch = rs.getString(4);
				int blnce = rs.getInt(5);
				int age = rs.getInt(6);
				Timestamp dateofUpdate = rs.getTimestamp(7);
				Timestamp dateOfCreate = rs.getTimestamp(8); 
				return new UserDetails(accNo, name, typeOfAcnt, blnce, branch, age, dateOfCreate,dateofUpdate,txUtil.getTransactionDetailsByAcntNo(acntNo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public boolean delete(String acntNo) {
		getConnection();
		try {
			preStmt = con.prepareStatement("delete from user_info where useracno = ?");
			preStmt.setString(1, acntNo);
			return preStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	public List<UserDetails> topNRows(int count) {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info limit 0,?");
			preStmt.setInt(1, count);
			ResultSet rs = preStmt.executeQuery();
			return fetchDetailsInResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public List<UserDetails> sortByName() {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info order by username");
			ResultSet rs = preStmt.executeQuery();
			return fetchDetailsInResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public boolean updateAmount(int amount, String name, TypeOfTransaction typeOfTransaction) {
		getConnection();
		try {
			if (typeOfTransaction.equals(TypeOfTransaction.valueOf("WITHDRAW"))) {
				preStmt = con.prepareStatement("select userblnc from user_info where username = ?");
				preStmt.setString(1, name);
				ResultSet rs = preStmt.executeQuery();
				while(rs.next()) {
				if (rs.getInt(1) > 0 || amount < rs.getInt(1)) {
					preStmt = con.prepareStatement("update user_info set userblnc = userblnc - ? , dateofacntupdation = ? where username = ? ");
					preStmt.setInt(1, amount);
					preStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
					preStmt.setString(3, name);
					preStmt.execute();
					return true;
				}}

			} else if (typeOfTransaction.equals(TypeOfTransaction.valueOf("DEPOSIT"))) {
				preStmt = con.prepareStatement("update user_info set userblnc = userblnc + ? ,dateofacntupdation = ? where username = ? ");
				preStmt.setInt(1, amount);
				preStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
				preStmt.setString(3, name);
				preStmt.execute();
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	public List<UserDetails> sortByDate() {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info order by dateOfAcntCreation");
			ResultSet rs = preStmt.executeQuery();
			return fetchDetailsInResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public List<UserDetails> matchByNamePattern(String ch) {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info where username like concat(?,'%')");
			preStmt.setString(1, ch);
			ResultSet rs = preStmt.executeQuery();
			return fetchDetailsInResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public List<UserDetails> fetchDetailsInResultSet(ResultSet rs) {
		List<UserDetails> ls = new ArrayList<>();
		try {
			while (rs.next()) {
				String acntNo = rs.getString(1);
				String name = rs.getString(2);
				String typeOfAcnt = rs.getString(3);
				String branch = rs.getString(4);
				int blnce = rs.getInt(5);
				int age = rs.getInt(6);
				Timestamp dateofUpdate = rs.getTimestamp(7);
				Timestamp dateOfCreate = rs.getTimestamp(8);
				ls.add(new UserDetails(acntNo, name, typeOfAcnt, blnce, branch, age, dateOfCreate,dateofUpdate,txUtil.getTransactionDetailsByAcntNo(acntNo)));
			}
			return ls;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet joinTwoTables() {
		getConnection();
		try {
			preStmt = con.prepareStatement("select * from user_info u, tx_details t where u.useracno=t.acntno ");
			ResultSet rs = preStmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
