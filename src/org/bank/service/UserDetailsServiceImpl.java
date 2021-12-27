package org.bank.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bank.domain.UserDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;

public class UserDetailsServiceImpl implements UserDetailsService {

	List<UserDetails> users = new ArrayList<>();

	Map<String, String> nameAcntNo = new HashMap<>();

	TransactionDetailsService serviceImpl = new TransactionServiceImpl();

	@Override
	public boolean addDetails(UserDetails user) {
		users.add(user);
		nameAcntNo.put(user.getUserName(), user.getAccountNo());
		return true;
	}

	@Override
	public List<UserDetails> getDetails() {
		return users;
	}

	@Override
	public UserDetails getDetailsByAcntNo(String acntNo) {
		for (UserDetails userDetails : users) {
			if (userDetails.getAccountNo() == acntNo) {
				return userDetails;
			}
		}
		return null;
	}

	@Override
	public void deleteUserDetails(UserDetails user) {
		users.remove(user);
	}

	@Override
	public List<UserDetails> getTopNUsers(int count) {
		return users.subList(0, count);
	}

	@Override
	public List<UserDetails> sortByName() {
		Collections.sort(users, new Comparator<UserDetails>() {
			@Override
			public int compare(UserDetails o1, UserDetails o2) {
				String s1 = (String) o1.getUserName();
				String s2 = (String) o2.getUserName();
				int v = s1.compareTo(s2);
				return v;
			}
		});
		return users;
	}

	@Override
	public void withdraw(int amount, UserDetails user) {
		for (UserDetails userObj : users) {
			if (userObj == user) {
				userObj.setBalance(userObj.getBalance() - amount);
				user.setDateOfAcntUpdation(Timestamp.valueOf(LocalDateTime.now()));
				serviceImpl.addTranactionDetails(user, amount,TypeOfTransaction.valueOf("WITHDRAW"));
			}
		}

	}

	@Override
	public void deposit(int amount, UserDetails user) {
		for (UserDetails userObj : users) {
			if (userObj == user) {
				userObj.setBalance(userObj.getBalance() + amount);
				user.setDateOfAcntUpdation(Timestamp.valueOf(LocalDateTime.now()));
				serviceImpl.addTranactionDetails(user, amount,TypeOfTransaction.valueOf("DEPOSIT"));
			}
		}
	}

	@Override
	public Map<String, String> nameAcntNo() {
		return nameAcntNo;
	}

	@Override
	public List<UserDetails> sortByDate() {
		Collections.sort(users, new Comparator<UserDetails>() {
			@Override
			public int compare(UserDetails o1, UserDetails o2) {
				Timestamp d1 = o1.getDateOfAcntCreation();
				Timestamp d2 = o2.getDateOfAcntCreation();
				int v = d1.compareTo(d2);
				return v;
			}
		});
		return users;
	}

	@Override
	public List<UserDetails> patternMatch(String str) {
		List<UserDetails> matchList = new ArrayList<>();
		Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
		for (UserDetails userDetails : users) {
			Matcher matcher = pattern.matcher(userDetails.getUserName());
			if (matcher.find()) {
				matchList.add(userDetails);
			}
		}
		return matchList;
	}

}
