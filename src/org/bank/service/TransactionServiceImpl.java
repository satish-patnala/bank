package org.bank.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bank.domain.UserDetails;
import org.bank.domain.UserTransactionDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;

public class TransactionServiceImpl implements TransactionDetailsService {

	@Override
	public List<UserTransactionDetails> getTransactionDetails(UserDetails user) {
		List<UserTransactionDetails> txDetailsList = new ArrayList<>();
		for (Map.Entry<String, UserTransactionDetails> entry : user.transactionDetails.entrySet()) {
			txDetailsList.add(entry.getValue());
		}
		return txDetailsList;
	}

	@Override
	public boolean addTranactionDetails(UserDetails user, int amount , TypeOfTransaction typeOfTx) {
		if (user != null) {
			String str = transactionIdGenerator();
			if(typeOfTx.equals(TypeOfTransaction.valueOf("WITHDRAW"))) {
				user.transactionDetails.put(str, new UserTransactionDetails(str, user.getAccountNo(), (double) amount,
						Timestamp.valueOf(LocalDateTime.now()), user.getBranch(), (double) 0.0));
				return true;
			}
			else if(typeOfTx.equals(TypeOfTransaction.valueOf("DEPOSIT"))) {
				user.transactionDetails.put(str, new UserTransactionDetails(str, user.getAccountNo(), (double) 0.0,
						Timestamp.valueOf(LocalDateTime.now()), user.getBranch(), (double) amount));
				return true;
			}
		}
		return false;
	}

	public String transactionIdGenerator() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVXYZ" + "1234567890";
		String transactionId = "";
		for (int i = 0; i < 11; i++) {
			int index = (int) (str.length() * Math.random());
			char ch = str.charAt(index);
			transactionId += ch;
		}
		return transactionId;
	}

	@Override
	public List<UserTransactionDetails> filterTransactionDetailsAboveCertainAmount(UserDetails user,int amount) {
		List<UserTransactionDetails> l = new ArrayList<>();
		for (Map.Entry<String, UserTransactionDetails> pair : user.transactionDetails.entrySet()) {
			if (pair.getValue().getAmountWithdrawn() > amount) {
				l.add(pair.getValue());
			}
		}
		return l;
	}

}
