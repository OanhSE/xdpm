/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "DatTruoc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatTruoc.findAll", query = "SELECT d FROM DatTruoc d"),
    @NamedQuery(name = "DatTruoc.findByDtMa", query = "SELECT d FROM DatTruoc d WHERE d.dtMa = :dtMa"),
    @NamedQuery(name = "DatTruoc.findByDtSoLuong", query = "SELECT d FROM DatTruoc d WHERE d.dtSoLuong = :dtSoLuong")})
public class DatTruoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dt_Ma")
    private String dtMa;
    @Column(name = "dt_SoLuong")
    private Integer dtSoLuong;
    @JoinColumn(name = "kh_Ma", referencedColumnName = "kh_Ma")
    @ManyToOne
    private KhachHang khMa;
    @JoinColumn(name = "td_Ma", referencedColumnName = "td_Ma")
    @ManyToOne
    private TieuDe tdMa;

    public DatTruoc() {
    }

    public DatTruoc(String dtMa) {
        this.dtMa = dtMa;
    }

    public String getDtMa() {
        return dtMa;
    }

    public void setDtMa(String dtMa) {
        this.dtMa = dtMa;
    }

    public Integer getDtSoLuong() {
        return dtSoLuong;
    }

    public void setDtSoLuong(Integer dtSoLuong) {
        this.dtSoLuong = dtSoLuong;
    }

    public KhachHang getKhMa() {
        return khMa;
    }

    public void setKhMa(KhachHang khMa) {
        this.khMa = khMa;
    }

    public TieuDe getTdMa() {
        return tdMa;
    }

    public void setTdMa(TieuDe tdMa) {
        this.tdMa = tdMa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtMa != null ? dtMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatTruoc)) {
            return false;
        }
        DatTruoc other = (DatTruoc) object;
        if ((this.dtMa == null && other.dtMa != null) || (this.dtMa != null && !this.dtMa.equals(other.dtMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DatTruoc[ dtMa=" + dtMa + " ]";
    }
    
}
