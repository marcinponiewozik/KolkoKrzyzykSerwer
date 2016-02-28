/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
public class Osoba implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
    private String haslo;
    private String email;
    private int pkt;
    
    @Lob
    private byte[] logo; 
    
    private int liczbaRozegranychGier;
    private int liczbaWygranych;
    private int liczbaRemisow;
    private int liczbaPorazek;
    private int liczbaSkonczonychGier;

    public Osoba() {
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }  
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPkt() {
        return pkt;
    }

    public void setPkt(int pkt) {
        this.pkt = pkt;
    }

    public int getLiczbaRozegranychGier() {
        return liczbaRozegranychGier;
    }

    public void setLiczbaRozegranychGier(int liczbaRozegranychGier) {
        this.liczbaRozegranychGier = liczbaRozegranychGier;
    }

    public int getLiczbaWygranych() {
        return liczbaWygranych;
    }

    public void setLiczbaWygranych(int liczbaWygranych) {
        this.liczbaWygranych = liczbaWygranych;
    }

    public int getLiczbaRemisow() {
        return liczbaRemisow;
    }

    public void setLiczbaRemisow(int liczbaRemisow) {
        this.liczbaRemisow = liczbaRemisow;
    }

    public int getLiczbaPorazek() {
        return liczbaPorazek;
    }

    public void setLiczbaPorazek(int liczbaPorazek) {
        this.liczbaPorazek = liczbaPorazek;
    }

    public int getLiczbaSkonczonychGier() {
        return liczbaSkonczonychGier;
    }

    public void setLiczbaSkonczonychGier(int liczbaSkonczonychGier) {
        this.liczbaSkonczonychGier = liczbaSkonczonychGier;
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
        if (!(object instanceof Osoba)) {
            return false;
        }
        Osoba other = (Osoba) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + "\"id\":" + id + ",\"login\":" + login + ",\"haslo\":" + haslo + ",\"email\":" + email + ",\"pkt\":" + pkt + ",\"logo\":" + logo + ",\"liczbaRozegranychGier\":" + liczbaRozegranychGier + ",\"liczbaWygranych\":" + liczbaWygranych + ",\"liczbaRemisow\":" + liczbaRemisow + ",\"liczbaPorazek\":" + liczbaPorazek + ",\"liczbaSkonczonychGier\":" + liczbaSkonczonychGier + '}';
    }   
}