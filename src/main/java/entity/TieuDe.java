/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "TieuDe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TieuDe.findAll", query = "SELECT t FROM TieuDe t"),
    @NamedQuery(name = "TieuDe.findByTdMa", query = "SELECT t FROM TieuDe t WHERE t.tdMa = :tdMa"),
    @NamedQuery(name = "TieuDe.findByGia", query = "SELECT t FROM TieuDe t WHERE t.gia = :gia"),
    @NamedQuery(name = "TieuDe.findByLoaiMa", query = "SELECT t FROM TieuDe t WHERE t.loaiMa = :loaiMa"),
    @NamedQuery(name = "TieuDe.findByPhiTre", query = "SELECT t FROM TieuDe t WHERE t.phiTre = :phiTre"),
    @NamedQuery(name = "TieuDe.findByTdImages", query = "SELECT t FROM TieuDe t WHERE t.tdImages = :tdImages"),
    @NamedQuery(name = "TieuDe.findByTdNgay", query = "SELECT t FROM TieuDe t WHERE t.tdNgay = :tdNgay"),
    @NamedQuery(name = "TieuDe.findByTdNhaSX", query = "SELECT t FROM TieuDe t WHERE t.tdNhaSX = :tdNhaSX"),
    @NamedQuery(name = "TieuDe.findByTdTenTD", query = "SELECT t FROM TieuDe t WHERE t.tdTenTD = :tdTenTD"),
    @NamedQuery(name = "TieuDe.findByTdTinhTrang", query = "SELECT t FROM TieuDe t WHERE t.tdTinhTrang = :tdTinhTrang")})
public class TieuDe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "td_Ma")
    private String tdMa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gia")
    private Double gia;
    @Column(name = "loai_Ma")
    private String loaiMa;
    @Column(name = "phiTre")
    private Double phiTre;
    @Column(name = "td_Images")
    private String tdImages;
    @Column(name = "td_Ngay")
    private Integer tdNgay;
    @Column(name = "td_NhaSX")
    private String tdNhaSX;
    @Column(name = "td_TenTD")
    private String tdTenTD;
    @Column(name = "td_TinhTrang")
    private Boolean tdTinhTrang;
    @OneToMany(mappedBy = "tdMa")
    private Collection<Dvd> dvdCollection;
    @OneToMany(mappedBy = "tdMa")
    private Collection<DatTruoc> datTruocCollection;

    public TieuDe() {
    }

    public TieuDe(String tdMa, Double gia, String loaiMa, Double phiTre, String tdImages, Integer tdNgay, String tdNhaSX, String tdTenTD, Boolean tdTinhTrang) {
        this.tdMa = tdMa;
        this.gia = gia;
        this.loaiMa = loaiMa;
        this.phiTre = phiTre;
        this.tdImages = tdImages;
        this.tdNgay = tdNgay;
        this.tdNhaSX = tdNhaSX;
        this.tdTenTD = tdTenTD;
        this.tdTinhTrang = tdTinhTrang;
    }

    public TieuDe(String tdMa) {
        this.tdMa = tdMa;
    }

    public String getTdMa() {
        return tdMa;
    }

    public void setTdMa(String tdMa) {
        this.tdMa = tdMa;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public String getLoaiMa() {
        return loaiMa;
    }

    public void setLoaiMa(String loaiMa) {
        this.loaiMa = loaiMa;
    }

    public Double getPhiTre() {
        return phiTre;
    }

    public void setPhiTre(Double phiTre) {
        this.phiTre = phiTre;
    }

    public String getTdImages() {
        return tdImages;
    }

    public void setTdImages(String tdImages) {
        this.tdImages = tdImages;
    }

    public Integer getTdNgay() {
        return tdNgay;
    }

    public void setTdNgay(Integer tdNgay) {
        this.tdNgay = tdNgay;
    }

    public String getTdNhaSX() {
        return tdNhaSX;
    }

    public void setTdNhaSX(String tdNhaSX) {
        this.tdNhaSX = tdNhaSX;
    }

    public String getTdTenTD() {
        return tdTenTD;
    }

    public void setTdTenTD(String tdTenTD) {
        this.tdTenTD = tdTenTD;
    }

    public Boolean getTdTinhTrang() {
        return tdTinhTrang;
    }

    public void setTdTinhTrang(Boolean tdTinhTrang) {
        this.tdTinhTrang = tdTinhTrang;
    }

    @XmlTransient
    public Collection<Dvd> getDvdCollection() {
        return dvdCollection;
    }

    public void setDvdCollection(Collection<Dvd> dvdCollection) {
        this.dvdCollection = dvdCollection;
    }

    @XmlTransient
    public Collection<DatTruoc> getDatTruocCollection() {
        return datTruocCollection;
    }

    public void setDatTruocCollection(Collection<DatTruoc> datTruocCollection) {
        this.datTruocCollection = datTruocCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdMa != null ? tdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TieuDe)) {
            return false;
        }
        TieuDe other = (TieuDe) object;
        if ((this.tdMa == null && other.tdMa != null) || (this.tdMa != null && !this.tdMa.equals(other.tdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TieuDe[ tdMa=" + tdMa + " ]";
    }
    
}
