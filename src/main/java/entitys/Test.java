/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Test implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Osoba gospodarz;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Osoba przeciwnik;
    
    
    private boolean zakonczona;
    private boolean ruchGospodarza;
    private Long idZwyciescy;

    @ElementCollection
    private List<Wybor> wybory;

    public List<Wybor> getWybory() {
        return wybory;
    }

    public void setWybory(List<Wybor> wybory) {
        this.wybory = wybory;
    }
    
    
    public boolean isZakonczona() {
        return zakonczona;
    }

    public void setZakonczona(boolean zakonczona) {
        this.zakonczona = zakonczona;
    }

    public boolean isRuchGospodarza() {
        return ruchGospodarza;
    }

    public void setRuchGospodarza(boolean ruchGospodarza) {
        this.ruchGospodarza = ruchGospodarza;
    }

    public Long getIdZwyciescy() {
        return idZwyciescy;
    }

    public void setIdZwyciescy(Long idZwyciescy) {
        this.idZwyciescy = idZwyciescy;
    }
    
    

    public Osoba getPrzeciwnik() {
        return przeciwnik;
    }

    public void setPrzeciwnik(Osoba przeciwnik) {
        this.przeciwnik = przeciwnik;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public Osoba getGospodarz() {
        return gospodarz;
    }

    public void setGospodarz(Osoba gospodarz) {
        this.gospodarz = gospodarz;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Test[ id=" + id + " ]";
    }
    
}
