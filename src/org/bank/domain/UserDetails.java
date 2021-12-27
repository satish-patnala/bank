package org.bank.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class UserDetails {
	private String accountNo;
	private String userName;
	private String typeOfAccount;
	private double balance;
	private String branch;
	private int age;
	private Timestamp dateOfAcntCreation;
	private Timestamp dateOfAcntUpdation;
	public Map<String, UserTransactionDetails> transactionDetails = new HashMap<>();
	
	public UserDetails(String accountNo, String userName, String typeOfAccount, double balance, String branch, int age,
			Timestamp dateOfAcntCreation, Timestamp dateOfAcntUpdation,
			Map<String, UserTransactionDetails> transactionDetails) {
		super();
		this.accountNo = accountNo;
		this.userName = userName;
		this.typeOfAccount = typeOfAccount;
		this.balance = balance;
		this.branch = branch;
		this.age = age;
		this.dateOfAcntCreation = dateOfAcntCreation;
		this.dateOfAcntUpdation = dateOfAcntUpdation;
		this.transactionDetails = transactionDetails;
	}

	public UserDetails(String accountNo, String userName, String typeOfAccount, double balance, String branch, int age,
			Timestamp dateOfAcntCreation) {
		super();
		this.accountNo = accountNo;
		this.userName = userName;
		this.typeOfAccount = typeOfAccount;
		this.balance = balance;
		this.branch = branch;
		this.age = age;
		this.dateOfAcntCreation = dateOfAcntCreation;
	}

	public UserDetails() {

	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTypeOfAccount() {
		return typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Timestamp getDateOfAcntCreation() {
		return dateOfAcntCreation;
	}

	public void setDateOfAcntCreation(Timestamp dateOfAcntCreation) {
		this.dateOfAcntCreation = dateOfAcntCreation;
	}

	public Timestamp getDateOfAcntUpdation() {
		return dateOfAcntUpdation;
	}

	public void setDateOfAcntUpdation(Timestamp dateOfAcntUpdation) {
		this.dateOfAcntUpdation = dateOfAcntUpdation;
	}

	@Override
	public String toString() {
		return "UserDetails [accountNo=" + accountNo + ", userName=" + userName + ", typeOfAccount=" + typeOfAccount
				+ ", balance=" + balance + ", branch=" + branch + ", age=" + age + ", dateOfAcntCreation="
				+ dateOfAcntCreation + ", dateOfAcntUpdation=" + dateOfAcntUpdation + ", transactionDetails="
				+ transactionDetails + "]";
	}
}
