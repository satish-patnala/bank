package org.bank.service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.bank.domain.UserDetails;
import org.bank.util.UserDetailsUtil;

public class UserDetailsServiceJdbcImpl implements UserDetailsService {

	UserDetailsUtil userUtil = new UserDetailsUtil();

	TransactionDetailsService txJdbcImpl = new TransactionServiceJdbcImpl();

	public enum TypeOfTransaction {
		WITHDRAW, DEPOSIT;
	}

	@Override
	public boolean addDetails(UserDetails user) {
		userUtil.insertDetails(user);
		return true;
	}

	@Override
	public List<UserDetails> getDetails() {
		List<UserDetails> ls = userUtil.viewAllDetails();
		return ls;
	}

	@Override
	public UserDetails getDetailsByAcntNo(String acntNo) {
		UserDetails user = userUtil.getDetailsByAcntNo(acntNo);
		return user;
	}

	@Override
	public void deleteUserDetails(UserDetails user) {
		if (userUtil.delete(user.getAccountNo())) {
			System.out.println("deleted...");
		} else {
			System.out.println("account does not exist");
		}
	}

	@Override
	public List<UserDetails> getTopNUsers(int count) {
		List<UserDetails> ls = userUtil.topNRows(count);
		return ls;
	}

	@Override
	public List<UserDetails> sortByName() {
		List<UserDetails> list = userUtil.sortByName();
		return list;
	}

	@Override
	public void withdraw(int amount, UserDetails user) {
		if (userUtil.updateAmount(amount, user.getUserName(), TypeOfTransaction.valueOf("WITHDRAW"))) {
			System.out.println("amount withdrawn");
			txJdbcImpl.addTranactionDetails(user, amount, TypeOfTransaction.valueOf("WITHDRAW"));
			System.out.println("transaction details added..");
		} else {
			System.out.println("insufficient funds");
		}
	}

	@Override
	public void deposit(int amount, UserDetails user) {
		if (userUtil.updateAmount(amount, user.getUserName(), TypeOfTransaction.valueOf("DEPOSIT"))) {
			System.out.println("amount deposited");
			txJdbcImpl.addTranactionDetails(user, amount, TypeOfTransaction.valueOf("DEPOSIT"));
			System.out.println("transaction details added..");
		} else {
			System.out.println("insufficient funds");
		}
	}

	@Override
	public Map<String, String> nameAcntNo() {
		return null;
	}

	@Override
	public List<UserDetails> sortByDate() {
		List<UserDetails> list = userUtil.sortByDate();
		return list;
	}

	@Override
	public List<UserDetails> patternMatch(String str) {
		List<UserDetails> list = userUtil.matchByNamePattern(str);
		return list;
	}
	
	public ResultSet joinTables() {
		return userUtil.joinTwoTables();
	}

}
