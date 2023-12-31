package com.ot.VendorTool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "AutoGenerated")
	private long id;

	@NotBlank(message = "Please Enter The User-Name")
	@ApiModelProperty(required = true)
	private String userName;

	@Column(unique = true)
	@NotBlank(message = "Please Enter The User-Email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a Valid User-Email")
	@ApiModelProperty(required = true)
	private String email;

	@Column(unique = true)
	@NotBlank(message = "Please Enter The User-PhoneNumber")
	@ApiModelProperty(required = true)
	@Pattern(regexp = "^[6-9]{1}[0-9]{9}+$", message = "Enter Proper User-PhoneNumber")
	private String phone;

	@NotBlank(message = "Please Enter the User-Password")
	@ApiModelProperty(required = true)
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%]).{6,15})", message = "Enter Proper User-Password "
			+ "\n" + " The User-Password Should Have Atleast " + "\n" + " 1 Upper Case " + "\n" + " 1 Lower Case "
			+ "\n" + " 1 Special Character " + "\n" + " And 1 Numric Character " + "\n"
			+ " The Length OF The Password Must Be Minimum OF 6 Character And Maximum OF 15 Character ")
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private String password;

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	@ApiModelProperty(required = true)
	@JsonIgnore
	private String role;

	@ApiModelProperty(required = false)
	@JsonIgnore
	private int otp;

	@ApiModelProperty(required = false)
	@JsonIgnore
	private String uuid;

}
