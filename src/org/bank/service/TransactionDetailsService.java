package org.bank.service;

import java.util.List;

import org.bank.domain.UserDetails;
import org.bank.domain.UserTransactionDetails;
import org.bank.service.UserDetailsServiceJdbcImpl.TypeOfTransaction;

public interface TransactionDetailsService {
	public List<UserTransactionDetails> getTransactionDetails(UserDetails user);

	public boolean addTranactionDetails(UserDetails user, int amount ,TypeOfTransaction typeOfTx);
	
	public List<UserTransactionDetails> filterTransactionDetailsAboveCertainAmount(UserDetails user,int amount);
	
	public String transactionIdGenerator();
}
