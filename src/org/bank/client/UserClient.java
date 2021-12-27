package org.bank.client;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.bank.domain.UserDetails;
import org.bank.service.UserDetailsService;
import org.bank.service.UserDetailsServiceJdbcImpl;
import org.bank.util.UserDetailsUtil;

public class UserClient {
	public static void main(String[] args) {
		UserDetails user1 = new UserDetails("1109100121", "raj", "salary", 12500.00, "rjy", 25,
				Timestamp.valueOf(LocalDateTime.now()));
		UserDetails user2 = new UserDetails("1109432671", "pooja", "salary", 13500.00, "vskpm", 23,
				Timestamp.valueOf(LocalDateTime.now()));
		UserDetails user3 = new UserDetails("1109845700", "sai", "salary", 13500.00, "hyd", 22,
				Timestamp.valueOf(LocalDateTime.now()));
		UserDetails user4 = new UserDetails("1109982309", "ravi", "salary", 13500.00, "vjw", 24,
				Timestamp.valueOf(LocalDateTime.now()));
		UserDetails user5 = new UserDetails("1234345456", "anil", "savings", 3400, "bnglre", 34,
				Timestamp.valueOf(LocalDateTime.now()));
		UserDetailsService service = new UserDetailsServiceJdbcImpl();
		System.out.println(service.getDetails());
		System.out.println(service.getTopNUsers(2));
		
	}
}
