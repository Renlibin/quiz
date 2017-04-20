package cn.clubox.quiz.service.impl.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.impl.dao.UserDaoExt;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseUserDetailsService.class);
	
	@Autowired
	private UserDaoExt userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("The user {} is going to be retrieved from DB", username);
		
		List<cn.clubox.quiz.jooq.domain.tables.pojos.User> users = userDao.fetchByName(username);
		
		if(users == null || users.isEmpty()){
			throw new UsernameNotFoundException(String.format("The user {} could not be found", username));
		}
		
		//Temporary solution
		Role role = new Role();
		role.setId(1);
		role.setName("USER");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		
//		User user = new User();
//		user.setId(users.get(0).getId());
//		user.setUsername(users.get(0).getName());
//		user.setPassword(users.get(0).getPassword());
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		for (Role r : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(r.getName()));
		}
		
		User user = new User.Builder().id(users.get(0).getId()).username(users.get(0)
				.getName()).password(users.get(0).getPassword()).nickname(users.get(0).getNickname())
				.accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
				.enabled(true).authorities(grantedAuthorities).build();

//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				grantedAuthorities);
		
		return user;
	}
	
	public static class User implements UserDetails {
		
	    /**
		 * 
		 */
		private static final long serialVersionUID = -3393080475869680356L;
		
		private final int id;
	    private final String username;
	    private final String nickname;
	    private final String password;
		private final Set<GrantedAuthority> authorities;
		private final boolean accountNonExpired;
		private final boolean accountNonLocked;
		private final boolean credentialsNonExpired;
		private final boolean enabled;
		
		private User(Builder builder){
			this.id = builder.getId();
			this.username = builder.getUsername();
			this.nickname = builder.getNickname();
			this.password = builder.getPassword();
			this.authorities = builder.getAuthorities();
			this.accountNonExpired = builder.isAccountNonExpired();
			this.accountNonLocked = builder.isAccountNonLocked();
			this.credentialsNonExpired = builder.isCredentialsNonExpired();
			this.enabled = builder.enabled;
		}
	    
		public int getId() {
			return this.id;
		}
		
		public String getNickname(){
			return this.nickname;
		}
		
		@Override
		public String getUsername() {
			return this.username;
		}
		@Override
		public String getPassword() {
			return this.password;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
		}

		@Override
		public boolean isAccountNonExpired() {
			return this.accountNonExpired;
		}

		@Override
		public boolean isAccountNonLocked() {
			return this.accountNonLocked;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.credentialsNonExpired;
		}

		@Override
		public boolean isEnabled() {
			return this.enabled;
		}
		
		public static class Builder { 
			
			private int id;
		    private String username;
		    private String nickname;
		    private String password;
			private Set<GrantedAuthority> authorities;
			private boolean accountNonExpired;
			private boolean accountNonLocked;
			private boolean credentialsNonExpired;
			private boolean enabled;
			
			public int getId() {
				return id;
			}

			public String getUsername() {
				return username;
			}

			public String getNickname() {
				return nickname;
			}

			public String getPassword() {
				return password;
			}

			public Set<GrantedAuthority> getAuthorities() {
				return authorities;
			}

			public boolean isAccountNonExpired() {
				return accountNonExpired;
			}

			public boolean isAccountNonLocked() {
				return accountNonLocked;
			}

			public boolean isCredentialsNonExpired() {
				return credentialsNonExpired;
			}

			public boolean isEnabled() {
				return enabled;
			}

			public Builder id(int id){
				this.id = id;
				return this;
			}
			
			public Builder username(String username){
				this.username = username;
				return this;
			}
			
			public Builder nickname(String nickname){
				this.nickname = nickname;
				return this;
			}
			
			public Builder password(String password){
				this.password = password;
				return this;
			}
			
			public Builder authorities(Set<GrantedAuthority> authorities){
				this.authorities = authorities;
				return this;
			}
			
			public Builder accountNonExpired(boolean accountNonExpired){
				this.accountNonExpired = accountNonExpired;
				return this;
			}
			
			public Builder accountNonLocked(boolean accountNonLocked){
				this.accountNonLocked = accountNonLocked;
				return this;
			}
			
			public Builder credentialsNonExpired(boolean credentialsNonExpired){
				this.credentialsNonExpired = credentialsNonExpired;
				return this;
			}
			
			public Builder enabled(boolean enable){
				this.enabled = enable;
				return this;
			}
			
			public User build(){
				return new User(this);
			}
			
		}
	}
	
	public static class Role {
	    private int id;
	    private String name;
	    private Set<User> users;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Set<User> getUsers() {
			return users;
		}
		public void setUsers(Set<User> users) {
			this.users = users;
		}
	}
}
