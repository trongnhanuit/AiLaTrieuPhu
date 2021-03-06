package model;

// Generated Jul 19, 2014 11:26:44 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Player generated by hbm2java
 */
@Entity
@Table(name = "player", catalog = "altp", uniqueConstraints = @UniqueConstraint(columnNames = "govermentid"))
public class Player implements java.io.Serializable {

	private Integer playerId;
	private String username;
	private String password;
	private String playername;
	private String address;
	private int birthday;
	private String govermentid;
	private boolean sex;
	private Integer status;
	private Set<Round> rounds = new HashSet<Round>(0);

	public Player() {
	}

	public Player(String username, String password, String playername,
			String address, int birthday, String govermentid, boolean sex) {
		this.username = username;
		this.password = password;
		this.playername = playername;
		this.address = address;
		this.birthday = birthday;
		this.govermentid = govermentid;
		this.sex = sex;
	}

	public Player(String username, String password, String playername,
			String address, int birthday, String govermentid, boolean sex,
			Integer status, Set<Round> rounds) {
		this.username = username;
		this.password = password;
		this.playername = playername;
		this.address = address;
		this.birthday = birthday;
		this.govermentid = govermentid;
		this.sex = sex;
		this.status = status;
		this.rounds = rounds;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "playerID", unique = true, nullable = false)
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 1024)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "playername", nullable = false, length = 45)
	public String getPlayername() {
		return this.playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	@Column(name = "address", nullable = false, length = 45)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "birthday", nullable = false)
	public int getBirthday() {
		return this.birthday;
	}

	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	@Column(name = "govermentid", unique = true, nullable = false, length = 45)
	public String getGovermentid() {
		return this.govermentid;
	}

	public void setGovermentid(String govermentid) {
		this.govermentid = govermentid;
	}

	@Column(name = "sex", nullable = false)
	public boolean isSex() {
		return this.sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
	public Set<Round> getRounds() {
		return this.rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

}
