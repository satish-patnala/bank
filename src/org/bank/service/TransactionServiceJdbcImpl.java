package org.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bank.domain.UserDetails;
import org.bank.domain.UserTransactionDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;
import org.bank.util.TransactionDetailsUtil;

public class TransactionServiceJdbcImpl implements TransactionDetailsService {

	TransactionDetailsUtil txUtil = new TransactionDetailsUtil();

	@Override
	public List<UserTransactionDetails> getTransactionDetails(UserDetails user) {
		List<UserTransactionDetails> txListOfUser = new ArrayList<>();
		user.transactionDetails = txUtil.getTransactionDetails(user);
		for (Map.Entry<String, UserTransactionDetails> set : txUtil.getTransactionDetails(user).entrySet()) {
			txListOfUser.add(set.getValue());
		}
		return txListOfUser;
	}

	@Override
	public boolean addTranactionDetails(UserDetails user, int amount, TypeOfTransaction txType) {
		if (txType.equals(TypeOfTransaction.valueOf("WITHDRAW"))) {
			txUtil.addTransactionDetails(new TransactionServiceImpl().transactionIdGenerator(), user, amount,
					TypeOfTransaction.valueOf("WITHDRAW"));
			return true;
		} else if (txType.equals(TypeOfTransaction.valueOf("DEPOSIT"))) {
			txUtil.addTransactionDetails(new TransactionServiceImpl().transactionIdGenerator(), user, amount,
					TypeOfTransaction.valueOf("DEPOSIT"));
			return true;
		}
		return false;
	}

	@Override
	public List<UserTransactionDetails> filterTransactionDetailsAboveCertainAmount(UserDetails user, int amount) {
		return txUtil.filterTxDetails(user, amount);
	}
	
	public List<UserTransactionDetails> sortTxDetailsByDate(UserDetails user){
		return txUtil.sortBydate(user);
	}

	@Override
	public String transactionIdGenerator() {
		return null;
	}

}
