package com.cmms.api.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cmms.api.security.AuthoritiesDeserializer;
import com.cmms.api.security.AuthoritiesSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String email;

	private String phone;
	private String photo;
	@Enumerated(value = EnumType.STRING)
	@Column(columnDefinition = "ENUM('ADMIN', 'CLIENT', 'TECHNICIAN') DEFAULT 'CLIENT'")
	private Role role = Role.CLIENT; // we set default role to client like in database or it will trigger error (*)

	@JsonIgnore
	@OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
	List<Maintenance> maintenance;

	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	List<Maintenance> maintenanceClient;

	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	List<Device> device;

	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	List<Ticket> ticket;

	/* */
	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	List<Feedback> feedback;

	@JsonIgnore
	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
	List<Announcement> announcement;


	@JsonSerialize(using = AuthoritiesSerializer.class)
    @JsonDeserialize(using = AuthoritiesDeserializer.class)
    private Collection<? extends GrantedAuthority> authorities;



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

	// UserDetails methods provided by spring security

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// using our roles for authority overriding the default provided by spring
		// security
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
		// overiding the password provided by spring security
		return this.password;
	}

	@Override
	public String getUsername() {
		// overiding the username provided by spring security
		return this.username;
	}

}
