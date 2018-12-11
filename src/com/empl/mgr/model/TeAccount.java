package com.empl.mgr.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * TeAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "te_account", uniqueConstraints = @UniqueConstraint(columnNames = "acctName"))
public class TeAccount implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long acctId;
	private Date timestamp;
	private String acctName;
	private String acctNickname;
	private String acctPassword;
	private String mobile;
	private String telephone;
	private String email;
	private long department;
	private long position;
	private Integer acctState;
	private boolean acctSuper;
	private boolean acctDeleteState;
	private Date createTime;
	private String creator;
	private String validataCode;
	private Date outDate;

	// Constructors

	/** default constructor */
	public TeAccount() {
	}

	/** full constructor */
	public TeAccount(String acctName, String acctNickname, String acctPassword, String email,String mobile,String telephone, Integer acctState, boolean acctSuper,
			boolean acctDeleteState, Date createTime, String creator) {
		this.acctName = acctName;
		this.acctNickname = acctNickname;
		this.acctPassword = acctPassword;
		this.email = email;
		this.mobile = mobile;
		this.telephone = telephone;
		this.acctState = acctState;
		this.acctSuper = acctSuper;
		this.acctDeleteState = acctDeleteState;
		this.createTime = createTime;
		this.creator = creator;
	}

	// Property accessory
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "acctId", unique = true, nullable = false)
	public long getAcctId() {
		return this.acctId;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}

	@Version
	@Column(name = "timestamp", nullable = false, length = 19)
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "acctName", unique = true, length = 64)
	public String getAcctName() {
		return this.acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	@Column(name = "acctNickname", length = 64)
	public String getAcctNickname() {
		return this.acctNickname;
	}

	public void setAcctNickname(String acctNickname) {
		this.acctNickname = acctNickname;
	}

	@Column(name = "acctPassword", length = 64)
	public String getAcctPassword() {
		return this.acctPassword;
	}

	public void setAcctPassword(String acctPassword) {
		this.acctPassword = acctPassword;
	}
	
	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "telephone", length = 20)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "department")
	public long getDepartment() {
		return department;
	}

	public void setDepartment(long department) {
		this.department = department;
	}

	@Column(name = "position")
	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	@Column(name = "acctState")
	public Integer getAcctState() {
		return this.acctState;
	}

	public void setAcctState(Integer acctState) {
		this.acctState = acctState;
	}

	@Column(name = "acctSuper")
	public boolean getAcctSuper() {
		return this.acctSuper;
	}

	public void setAcctSuper(boolean acctSuper) {
		this.acctSuper = acctSuper;
	}

	@Column(name = "acctDeleteState")
	public boolean getAcctDeleteState() {
		return this.acctDeleteState;
	}

	public void setAcctDeleteState(boolean acctDeleteState) {
		this.acctDeleteState = acctDeleteState;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "creator", length = 64)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "validataCode", length = 50)
	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}

	@Column(name = "outDate", length = 19)
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

}