package com.ot.VendorTool.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vendor extends User {

	{
		super.setRole("ROLE_VENDOR");
	}
	
	@NotBlank(message = "Please Enter The Shop-Name")
	@ApiModelProperty(required = true)
	private String shopName;
	
	@NotBlank(message = "Please Enter The Shops-Number")
	@ApiModelProperty(required = true)
	private String shopNumber;
	
	@NotBlank(message = "Please Enter The GstIN")
	@ApiModelProperty(required = true)
	private String gstIN;
	
	@NotBlank(message = "Please Enter The Address")
	@ApiModelProperty(required = true)
	private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	@JoinColumn
	private VendorGstImage gstImage;

	@JsonIgnore
	public VendorGstImage getGstImage() {
		return gstImage;
	}

	@JsonIgnore
	public void setGstImage(VendorGstImage gstImage) {
		this.gstImage = gstImage;
	}



	@OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
	@JsonManagedReference("vendor")
	private List<Product> products;
}
