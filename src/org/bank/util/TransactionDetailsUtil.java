package org.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bank.domain.UserDetails;
import org.bank.domain.UserTransactionDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;

public class TransactionDetailsUtil {
	String url = "jdbc:mysql://localhost:3306/hibernate";
	String username = "root";
	String password = "12345";

	Connection con;
	PreparedStatement preStmt;

	public void getConnection() {
		try {
			con = DriverManager.getConnection(url, username, password);
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

	public boolean addTransactionDetails(String txId, UserDetails user, int amount, TypeOfTransaction typeOfTx) {
		getConnection();
		try {
			preStmt = con.prepareStatement("insert into tx_details values(?,?,?,?,?,?)");
			preStmt.setString(1, txId);
			preStmt.setString(2, user.getAccountNo());
			preStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			preStmt.setString(6, user.getBranch());
			if (typeOfTx.equals(TypeOfTransaction.valueOf("WITHDRAW"))) {
				preStmt.setInt(4, amount);
				preStmt.setInt(5, 0);
			} else if (typeOfTx.equals(TypeOfTransaction.valueOf("DEPOSIT"))) {
				preStmt.setInt(5, amount);
				preStmt.setInt(4, 0);
			}
			preStmt.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return false;
	}
	
	public Map<String,UserTransactionDetails> getTransactionDetails(UserDetails user) {
		getConnection();
		Map<String,UserTransactionDetails> txDetails = new HashMap<>();
		try {
			preStmt = con.prepareStatement("select * from tx_details where acntno = ? ");
			preStmt.setString(1, user.getAccountNo());
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				txDetails.put(rs.getString(1), new UserTransactionDetails(rs.getString(1),rs.getString(2),rs.getInt(4),rs.getTimestamp(3),rs.getString(6),rs.getInt(5)));
			}
		return txDetails;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String,UserTransactionDetails> getTransactionDetailsByAcntNo(String acntNo){
		getConnection();
		Map<String,UserTransactionDetails> txDetails = new HashMap<>();
		try {
			preStmt = con.prepareStatement("select * from tx_details where acntno = ? ");
			preStmt.setString(1, acntNo);
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()) {
				txDetails.put(rs.getString(1), new UserTransactionDetails(rs.getString(1),rs.getString(2),rs.getInt(4),rs.getTimestamp(3),rs.getString(6),rs.getInt(5)));
			}
		return txDetails;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<UserTransactionDetails> filterTxDetails(UserDetails user, int amount) {
		getConnection();
		List<UserTransactionDetails> list = new ArrayList<>();
		try {
			preStmt = con.prepareStatement("select * from tx_details where acntno = ? and amntdeposited > ? ");
			preStmt.setString(1, user.getAccountNo());
			preStmt.setInt(2, amount);
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				list.add(new UserTransactionDetails(rs.getString(1),rs.getString(2),rs.getInt(4),rs.getTimestamp(3),rs.getString(6),rs.getInt(5)));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserTransactionDetails> sortBydate(UserDetails user) {
		getConnection();
		List<UserTransactionDetails> list = new ArrayList<>();
		try {
			preStmt = con.prepareStatement("select * from tx_details where acntno = ? order by timeoftx ");
			preStmt.setString(1, user.getAccountNo() );
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				list.add(new UserTransactionDetails(rs.getString(1),rs.getString(2),rs.getInt(4),rs.getTimestamp(3),rs.getString(6),rs.getInt(5)));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
