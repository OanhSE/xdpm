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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "DVD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dvd.findAll", query = "SELECT d FROM Dvd d"),
    @NamedQuery(name = "Dvd.findByDvdMa", query = "SELECT d FROM Dvd d WHERE d.dvdMa = :dvdMa"),
    @NamedQuery(name = "Dvd.findByDvdTrangThai", query = "SELECT d FROM Dvd d WHERE d.dvdTrangThai = :dvdTrangThai")})
public class Dvd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dvd_Ma")
    private String dvdMa;
    @Column(name = "dvd_TrangThai")
    private Integer dvdTrangThai;
    @JoinColumn(name = "td_Ma", referencedColumnName = "td_Ma")
    @ManyToOne
    private TieuDe tdMa;
    @OneToMany(mappedBy = "dvdMa")
    private Collection<HoaDon> hoaDonCollection;

    public Dvd() {
    }

    public Dvd(String dvdMa, Integer dvdTrangThai, TieuDe tdMa) {
        this.dvdMa = dvdMa;
        this.dvdTrangThai = dvdTrangThai;
        this.tdMa = tdMa;
    }

    public Dvd(String dvdMa) {
        this.dvdMa = dvdMa;
    }

    public String getDvdMa() {
        return dvdMa;
    }

    public void setDvdMa(String dvdMa) {
        this.dvdMa = dvdMa;
    }

    public Integer getDvdTrangThai() {
        return dvdTrangThai;
    }

    public void setDvdTrangThai(Integer dvdTrangThai) {
        this.dvdTrangThai = dvdTrangThai;
    }

    public TieuDe getTdMa() {
        return tdMa;
    }

    public void setTdMa(TieuDe tdMa) {
        this.tdMa = tdMa;
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
        hash += (dvdMa != null ? dvdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dvd)) {
            return false;
        }
        Dvd other = (Dvd) object;
        if ((this.dvdMa == null && other.dvdMa != null) || (this.dvdMa != null && !this.dvdMa.equals(other.dvdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dvd[ dvdMa=" + dvdMa + " ]";
    }
    
}
