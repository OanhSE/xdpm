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
@Table(name = "KhachHang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KhachHang.findAll", query = "SELECT k FROM KhachHang k"),
    @NamedQuery(name = "KhachHang.findByKhMa", query = "SELECT k FROM KhachHang k WHERE k.khMa = :khMa"),
    @NamedQuery(name = "KhachHang.findByKhDiaChi", query = "SELECT k FROM KhachHang k WHERE k.khDiaChi = :khDiaChi"),
    @NamedQuery(name = "KhachHang.findByKhHoVaTen", query = "SELECT k FROM KhachHang k WHERE k.khHoVaTen = :khHoVaTen"),
    @NamedQuery(name = "KhachHang.findByKhSDT", query = "SELECT k FROM KhachHang k WHERE k.khSDT = :khSDT"),
    @NamedQuery(name = "KhachHang.findByKhTinhTrang", query = "SELECT k FROM KhachHang k WHERE k.khTinhTrang = :khTinhTrang")})
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kh_Ma")
    private String khMa;
    @Column(name = "kh_DiaChi")
    private String khDiaChi;
    @Column(name = "kh_HoVaTen")
    private String khHoVaTen;
    @Column(name = "kh_SDT")
    private String khSDT;
    @Column(name = "kh_TinhTrang")
    private Boolean khTinhTrang;
    @OneToMany(mappedBy = "khMa")
    private Collection<DatTruoc> datTruocCollection;
    @OneToMany(mappedBy = "khMa")
    private Collection<HoaDon> hoaDonCollection;

    public KhachHang() {
    }

    public KhachHang(String khMa, String khDiaChi, String khHoVaTen, String khSDT, Boolean khTinhTrang) {
        this.khMa = khMa;
        this.khDiaChi = khDiaChi;
        this.khHoVaTen = khHoVaTen;
        this.khSDT = khSDT;
        this.khTinhTrang = khTinhTrang;
    }
    

    public KhachHang(String khMa) {
        this.khMa = khMa;
    }

    public String getKhMa() {
        return khMa;
    }

    public void setKhMa(String khMa) {
        this.khMa = khMa;
    }

    public String getKhDiaChi() {
        return khDiaChi;
    }

    public void setKhDiaChi(String khDiaChi) {
        this.khDiaChi = khDiaChi;
    }

    public String getKhHoVaTen() {
        return khHoVaTen;
    }

    public void setKhHoVaTen(String khHoVaTen) {
        this.khHoVaTen = khHoVaTen;
    }

    public String getKhSDT() {
        return khSDT;
    }

    public void setKhSDT(String khSDT) {
        this.khSDT = khSDT;
    }

    public Boolean getKhTinhTrang() {
        return khTinhTrang;
    }

    public void setKhTinhTrang(Boolean khTinhTrang) {
        this.khTinhTrang = khTinhTrang;
    }

    @XmlTransient
    public Collection<DatTruoc> getDatTruocCollection() {
        return datTruocCollection;
    }

    public void setDatTruocCollection(Collection<DatTruoc> datTruocCollection) {
        this.datTruocCollection = datTruocCollection;
    }

    @XmlTransient
    public Collection<HoaDon> getHoaDonCollection() {
        return hoaDonCollection;
    }

    public void setHoaDonCollection(Collection<HoaDon> hoaDonCollection) {
        this.hoaDonCollection = hoaDonCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (khMa != null ? khMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KhachHang)) {
            return false;
        }
        KhachHang other = (KhachHang) object;
        if ((this.khMa == null && other.khMa != null) || (this.khMa != null && !this.khMa.equals(other.khMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KhachHang[ khMa=" + khMa + " ]";
    }
    
}
