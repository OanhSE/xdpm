/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "HoaDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoaDon.findAll", query = "SELECT h FROM HoaDon h"),
    @NamedQuery(name = "HoaDon.findByHdMa", query = "SELECT h FROM HoaDon h WHERE h.hdMa = :hdMa"),
    @NamedQuery(name = "HoaDon.findByHdNgayThue", query = "SELECT h FROM HoaDon h WHERE h.hdNgayThue = :hdNgayThue"),
    @NamedQuery(name = "HoaDon.findByHdNgayTra", query = "SELECT h FROM HoaDon h WHERE h.hdNgayTra = :hdNgayTra"),
    @NamedQuery(name = "HoaDon.findByHdSoLuong", query = "SELECT h FROM HoaDon h WHERE h.hdSoLuong = :hdSoLuong"),
    @NamedQuery(name = "HoaDon.findByHdTinhTrang", query = "SELECT h FROM HoaDon h WHERE h.hdTinhTrang = :hdTinhTrang")})
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "hd_Ma")
    private String hdMa;
    @Column(name = "hd_NgayThue")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hdNgayThue;
    @Column(name = "hd_NgayTra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hdNgayTra;
    @Column(name = "hd_SoLuong")
    private Integer hdSoLuong;
    @Column(name = "hd_TinhTrang")
    private Boolean hdTinhTrang;
    @JoinColumn(name = "dvd_Ma", referencedColumnName = "dvd_Ma")
    @ManyToOne
    private Dvd dvdMa;
    @JoinColumn(name = "kh_Ma", referencedColumnName = "kh_Ma")
    @ManyToOne
    private KhachHang khMa;

    public HoaDon() {
    }

    public HoaDon(String hdMa, Date hdNgayThue, Date hdNgayTra, Integer hdSoLuong, Boolean hdTinhTrang, Dvd dvdMa, KhachHang khMa) {
        this.hdMa = hdMa;
        this.hdNgayThue = hdNgayThue;
        this.hdNgayTra = hdNgayTra;
        this.hdSoLuong = hdSoLuong;
        this.hdTinhTrang = hdTinhTrang;
        this.dvdMa = dvdMa;
        this.khMa = khMa;
    }

    public HoaDon(String hdMa) {
        this.hdMa = hdMa;
    }

    public String getHdMa() {
        return hdMa;
    }

    public void setHdMa(String hdMa) {
        this.hdMa = hdMa;
    }

    public Date getHdNgayThue() {
        return hdNgayThue;
    }

    public void setHdNgayThue(Date hdNgayThue) {
        this.hdNgayThue = hdNgayThue;
    }

    public Date getHdNgayTra() {
        return hdNgayTra;
    }

    public void setHdNgayTra(Date hdNgayTra) {
        this.hdNgayTra = hdNgayTra;
    }

    public Integer getHdSoLuong() {
        return hdSoLuong;
    }

    public void setHdSoLuong(Integer hdSoLuong) {
        this.hdSoLuong = hdSoLuong;
    }

    public Boolean getHdTinhTrang() {
        return hdTinhTrang;
    }

    public void setHdTinhTrang(Boolean hdTinhTrang) {
        this.hdTinhTrang = hdTinhTrang;
    }

    public Dvd getDvdMa() {
        return dvdMa;
    }

    public void setDvdMa(Dvd dvdMa) {
        this.dvdMa = dvdMa;
    }

    public KhachHang getKhMa() {
        return khMa;
    }

    public void setKhMa(KhachHang khMa) {
        this.khMa = khMa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hdMa != null ? hdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoaDon)) {
            return false;
        }
        HoaDon other = (HoaDon) object;
        if ((this.hdMa == null && other.hdMa != null) || (this.hdMa != null && !this.hdMa.equals(other.hdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HoaDon[ hdMa=" + hdMa + " ]";
    }
    
}
