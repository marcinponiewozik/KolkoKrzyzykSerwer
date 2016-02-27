/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requests;

import entitys.Gra;
import entitys.Osoba;
import entitys.Wybor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marcin
 */
@Stateless
public class GraRequest {

    @PersistenceContext
    private EntityManager manager;

    public void dodaj(Gra gra) {
        manager.persist(gra);
    }

    public Gra znajdz(Long id) {
        return manager.find(Gra.class, id);
    }
    public Gra znajdzPoIdOsoba(Long idOsoba){
        Query q = manager.createQuery("SELECT g FROM Gra g WHERE g.gospodarz.id=:idOsoba", Gra.class);
        q.setParameter("idOsoba", idOsoba);
        Gra gra = new Gra();
        if(q.getResultList().isEmpty()){
            return null;
        }else{
            return (Gra) q.getResultList().get(q.getResultList().size()-1);
        }
    }
    public void zamien(Long idGra, Gra gra) {
        gra.setId(idGra);
        manager.merge(gra);
    }

    public void dodajPrzeciwnika(Gra gra, Osoba przeciwnik) {
        gra.getGracze().add(przeciwnik);
//        gra.setPrzeciwnik(przeciwnik);
        manager.merge(gra);
    }

    public void dodajRuch(Gra gra, Wybor w) {
        gra.getWybory().add(w);
        manager.merge(gra);
    }

    public List<Wybor> listaRuchow(Long idGra) {
        Query q = manager.createQuery("SELECT g FROM Gra g WHERE g.id=:idGra", Gra.class);
        q.setParameter("idGra", idGra);
        List<Wybor> list = new ArrayList<Wybor>();
        list = q.getResultList();
        return list;
    }
    
    public List<Gra> wszystkieDostepneGry(Long idOsoba){
        Query q= manager.createQuery("SELECT g FROM Gra g WHERE g.przeciwnik IS NULL AND g.gospodarz.id <> :idOsoba", Gra.class);
        q.setParameter("idOsoba", idOsoba);
        return q.getResultList();
    }
    public List<Gra> gryGracza(String login){
        Query q= manager.createQuery("SELECT g FROM Gra g WHERE g.przeciwnik IS NULL AND g.gospodarz.login=:login", Gra.class);
        q.setParameter("login", login);
        return q.getResultList();
    }
    
    public boolean brakPrzeciwnika(Long idGra){
//        Query q = manager.createQuery("SELECT g.przeciwnik FROM Gra g WHERE g.id =:idGra", Osoba.class);
//        q.setParameter("idGra", idGra);
//        Osoba o = (Osoba) q.getSingleResult();
        Gra gra = new Gra();
        gra = znajdz(idGra);
        
        return gra.getGracze().size()==1;
    }
    
    private final String[] wygrana = {"123", "456", "789", "147", "258", "369", "159", "357"};
    public boolean sprawdzStanGr(Long idGra){
        Gra gra = manager.find(Gra.class, idGra);
        if(gra.getWybory().size()<5)
            return false;
        List<Wybor> gospodarz = new ArrayList<Wybor>();
        List<Wybor> przeciwnik = new ArrayList<Wybor>();
        
        for (Wybor w : gra.getWybory()) {
            if (Objects.equals(w.getIdOsoba(), gra.getGracze().get(0).getId())) {//gracz 1
                gospodarz.add(w);
            }
            else{
                przeciwnik.add(w);
            }
        }        
        //gracz1
        String wyboryGospodarzString= null;
        for (Wybor gospodarz1 : gospodarz) {
            wyboryGospodarzString += String.valueOf(gospodarz1.getPole());            
        }
        //gracz2
        String wyboryPrzeciwnikaString= null;
        for (Wybor przeciwnik1 : przeciwnik) {
            wyboryPrzeciwnikaString += String.valueOf(przeciwnik1.getPole());               
        }
        
        //sprawdz wygrana
        for (String wygrana1 : wygrana) {
            if (sprawdzWygrana(wygrana1, wyboryGospodarzString)) { 
                gra.setIdZwyciescy(gra.getGracze().get(0).getId());
//                gra.setZakonczona(true);
                zamien(idGra, gra);
                return true;                
            }
            if (sprawdzWygrana(wygrana1, wyboryPrzeciwnikaString)) {
                gra.setIdZwyciescy(gra.getGracze().get(1).getId());
//                gra.setZakonczona(true);
                zamien(idGra, gra);
                return true;
            }
        }        
        return false;        
    }
    
    
    public boolean sprawdzWygrana(String wzor, String wyboryGracza){
        int licznik=0;
        for (int i = 0; i < wyboryGracza.length(); i++) {
            if(wzor.indexOf(wyboryGracza.charAt(i))!=-1){
                licznik++;
            }            
        }        
        if(licznik==3)
            return true;
        else
            return false;
    }
}
