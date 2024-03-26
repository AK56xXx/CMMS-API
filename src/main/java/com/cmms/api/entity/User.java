package com.cmms.api.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	    private String fname;
	    private String lname;
	    private String username;
	    private String password;
	    private String email;
	    private String phone;
	    private String photo;
	    @Enumerated(value= EnumType.STRING)
	    private Role role;





		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getFname() {
			return fname;
		}


		public void setFname(String fname) {
			this.fname = fname;
		}


		public String getLname() {
			return lname;
		}


		public void setLname(String lname) {
			this.lname = lname;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPhone() {
			return phone;
		}


		public void setPhone(String phone) {
			this.phone = phone;
		}


		public String getPhoto() {
			return photo;
		}


		public void setPhoto(String photo) {
			this.photo = photo;
		}


		public Role getRole() {
			return role;
		}


		public void setRole(Role role) {
			this.role = role;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		//UserDetails methods

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return List.of(new SimpleGrantedAuthority(role.name()));
		}


		@Override
		public boolean isAccountNonExpired() {
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
		@Override
		public boolean isEnabled() {
			return true;
		}


		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return this.password;
		}


		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.username;
		}

}
