package by.rss.reader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.oval.constraint.EqualToField;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;

import org.apache.commons.validator.EmailValidator;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

@Entity
@Table(name = "user_account")
@NamedQueries ({
	@NamedQuery (name = "user.by.login", query = "SELECT u FROM User u WHERE u.login = :login")
})
public class User implements UserDetails {
	private static final long serialVersionUID = -1514435170832409067L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull(errorCode = "login.required")
	@Length(errorCode = "login.invalidLength", min = 3, max = 50)
	protected String login;

	@NotNull(errorCode = "password.required")
	@Length(errorCode = "password.invalidLength", min = 3, max = 50)
	protected String password;

	@NotNull(errorCode = "confirmPassword.required")
	@NotEmpty(errorCode = "confirmPassword.required")
	@EqualToField(errorCode = "password.passwordAndConfirmationNotEquals", value = "password")
	@Transient
	protected String confirmPassword;

	@NotNull(errorCode = "email.required")
	@NotEmpty(errorCode = "email.required")
	@Length(errorCode = "email.invalidLength", max = 250)
	@ValidateWithMethod(errorCode = "email.invalidFormat", methodName = "isEmailValid", ignoreIfNull = true, parameterType = String.class)
	protected String email;

	//List<Feed> feedSubscribed
	
	// UserDetails fields
	@Transient
	protected GrantedAuthority[] authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	// UserDetails implementation

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public String getUsername() {
		return getLogin();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	// Validation

	public static boolean isEmailValid(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	@Override
	public String toString() {
		return "[id=" + id + "], [login=" + login + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
