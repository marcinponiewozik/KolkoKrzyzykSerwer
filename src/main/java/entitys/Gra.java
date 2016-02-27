/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Gra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToMany
    @JoinTable(name = "Gra_Osoba")
    private List<Osoba> gracze;
    
    private boolean zakonczona;
    private boolean ruchGospodarza;
    private Long idZwyciescy;
    
    @ElementCollection
    private List<Wybor> wybory;
    

    public Long getId() {
        return id;
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
        if (!(object instanceof Gra)) {
            return false;
        }
        Gra other = (Gra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<Osoba> getGracze() {
        return gracze;
    }

    public void setGracze(List<Osoba> gracze) {
        this.gracze = gracze;
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

    public List<Wybor> getWybory() {
        return wybory;
    }

    public void setWybory(List<Wybor> wybory) {
        this.wybory = wybory;
    }

    public Long getIdZwyciescy() {
        return idZwyciescy;
    }

    public void setIdZwyciescy(Long idZwyciescy) {
        this.idZwyciescy = idZwyciescy;
    }
    

    

    @Override
    public String toString() {
        return "entitys.Gra[ id=" + id + " ]";
    }
    
}
