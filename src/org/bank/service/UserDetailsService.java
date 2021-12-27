package org.bank.service;

import java.util.List;
import java.util.Map;

import org.bank.domain.UserDetails;

public interface UserDetailsService {

	public boolean addDetails(UserDetails user);

	public List<UserDetails> getDetails();

	public UserDetails getDetailsByAcntNo(String acntNo);

	public void deleteUserDetails(UserDetails user);

	public List<UserDetails> getTopNUsers(int count);

	public List<UserDetails> sortByName();

	public void withdraw(int amount, UserDetails user);

	public void deposit(int amount, UserDetails user);

	public Map<String, String> nameAcntNo();

	public List<UserDetails> sortByDate();

	public List<UserDetails> patternMatch(String str);

}
