package com.carry.entity;

import java.util.Date;

/**
 * CarryYh entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CarryYh implements java.io.Serializable {

	// Fields

	private String yhbh;
	private String dlmc;
	private String mm;
	private String yhmc;
	private String bm;
	private String zt;
	private String bz;
	private Date gxsj;
	private String lxdh;
	private String ly;
	private String ipxz;
	private String macxz;
	private String fzid;
	// Constructors

	
	public String getMacxz() {
		return macxz;
	}

	public String getFzid() {
		return fzid;
	}

	public void setFzid(String fzid) {
		this.fzid = fzid;
	}

	public void setMacxz(String macxz) {
		this.macxz = macxz;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getIpxz() {
		return ipxz;
	}

	public void setIpxz(String ipxz) {
		this.ipxz = ipxz;
	}

	/** default constructor */
	public CarryYh() {
	}

	public CarryYh(String yhbh, String dlmc, String mm, String yhmc, String bm,
			String zt, String bz, Date gxsj, String lxdh) {
		super();
		this.yhbh = yhbh;
		this.dlmc = dlmc;
		this.mm = mm;
		this.yhmc = yhmc;
		this.bm = bm;
		this.zt = zt;
		this.bz = bz;
		this.gxsj = gxsj;
		this.lxdh = lxdh;
	}

	// Property accessors

	public String getYhbh() {
		return this.yhbh;
	}

	public void setYhbh(String yhbh) {
		this.yhbh = yhbh;
	}

	public String getDlmc() {
		return this.dlmc;
	}

	public void setDlmc(String dlmc) {
		this.dlmc = dlmc;
	}

	public String getMm() {
		return this.mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getYhmc() {
		return this.yhmc;
	}

	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}

	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getGxsj() {
		return this.gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

}