package tn.esprit.spring.dao.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author 21654
 *
 */
@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  
	@Column
	private String role ; 
	

  @NotBlank
  @Size(max = 120)
  private String password;
  
  private String name;
  
  private String lastname;
  
  @Temporal (TemporalType.DATE)
  private Date dateBirth;
  
  private String gender;
  
 
  private String adress;
  
  @Column(nullable = true, length = 64)
  private String profilePic;
  
  @Column(nullable = true, length = 64)
  private String identitycardPic;
  
  @Column(nullable = true, length = 64)
  private String signaturePic;
  
  @Temporal (TemporalType.DATE)
  private Date creationDate;
  
  private Boolean active;
  
  private  Boolean verified;
  
  private String token;
  
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime tokenCreationDate;
  
  private String verificationCode;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  
 
  
  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
		@NotBlank @Size(max = 120) String password, String name, String lastname, Date dateBirth, String gender,
		 String adress, String profilePic, String identitycardPic, String signaturePic, Date creationDate,
		Boolean active, Boolean verified, String token, LocalDateTime tokenCreationDate, String verificationCode,
		Set<Role> roles) {
	super();
	this.username = username;
	this.email = email;
	this.password = password;
	this.name = name;
	this.lastname = lastname;
	this.dateBirth = dateBirth;
	this.gender = gender;
	
	this.adress = adress;
	this.profilePic = profilePic;
	this.identitycardPic = identitycardPic;
	this.signaturePic = signaturePic;
	this.creationDate = creationDate;
	this.active = active;
	this.verified = verified;
	this.token = token;
	this.tokenCreationDate = tokenCreationDate;
	this.verificationCode = verificationCode;
	this.roles = roles;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public Date getDateBirth() {
	return dateBirth;
}

public void setDateBirth(Date dateBirth) {
	this.dateBirth = dateBirth;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}



public String getAdress() {
	return adress;
}

public void setAdress(String adress) {
	this.adress = adress;
}

public String getProfilePic() {
	return profilePic;
}

public void setProfilePic(String profilePic) {
	this.profilePic = profilePic;
}

public String getIdentitycardPic() {
	return identitycardPic;
}

public void setIdentitycardPic(String identitycardPic) {
	this.identitycardPic = identitycardPic;
}

public String getSignaturePic() {
	return signaturePic;
}

public void setSignaturePic(String signaturePic) {
	this.signaturePic = signaturePic;
}

public Date getCreationDate() {
	return creationDate;
}

public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
}

public Boolean getActive() {
	return active;
}

public void setActive(Boolean active) {
	this.active = active;
}

public Boolean getVerified() {
	return verified;
}

public void setVerified(Boolean verified) {
	this.verified = verified;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public LocalDateTime getTokenCreationDate() {
	return tokenCreationDate;
}

public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
	this.tokenCreationDate = tokenCreationDate;
}

public String getVerificationCode() {
	return verificationCode;
}

public void setVerificationCode(String verificationCode) {
	this.verificationCode = verificationCode;
}

public Set<Role> getRoles() {
	return roles;
}

public void setRoles(Set<Role> roles) {
	this.roles = roles;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}


  
}