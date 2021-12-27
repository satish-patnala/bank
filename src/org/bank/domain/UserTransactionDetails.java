package org.bank.domain;

import java.sql.Timestamp;

public class UserTransactionDetails {
	private String transactionId;
	private String userAcntNo;
	private double amountWithdrawn;
	private Timestamp timeOfTransaction;
	private String location;
	private double amountDeposited;

	public UserTransactionDetails(String transactionId, String userAcntNo, double amountWithdrawn,
			Timestamp timeOfTransaction, String location, double amountDeposited) {
		super();
		this.transactionId = transactionId;
		this.userAcntNo = userAcntNo;
		this.amountWithdrawn = amountWithdrawn;
		this.timeOfTransaction = timeOfTransaction;
		this.location = location;
		this.amountDeposited = amountDeposited;
	}

	public UserTransactionDetails() {

	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserName() {
		return userAcntNo;
	}

	public void setUserName(String userName) {
		this.userAcntNo = userName;
	}

	public double getAmountWithdrawn() {
		return amountWithdrawn;
	}

	public void setAmountWithdrawn(double amountWithdrawn) {
		this.amountWithdrawn = amountWithdrawn;
	}

	public Timestamp getTimeOfTransaction() {
		return timeOfTransaction;
	}

	public void setTimeOfTransaction(Timestamp timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getAmountDeposited() {
		return amountDeposited;
	}

	public void setAmountDeposited(double amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	@Override
	public String toString() {
		return "UserTransactionDetails [transactionId=" + transactionId + ", userAcntNo=" + userAcntNo
				+ ", amountWithdrawn=" + amountWithdrawn + ", timeOfTransaction=" + timeOfTransaction + ", location="
				+ location + ", amountDeposited=" + amountDeposited + "]";
	}

}
