/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requests;

import entitys.Osoba;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marcin
 */
@Stateless
public class OsobaRequest {

    @PersistenceContext
    private EntityManager manager;

    public Osoba wezOsobaPoId(Long id) {
        return manager.find(Osoba.class, id);
    }

    public void dodaj(Osoba osoba) {
        osoba.setLiczbaPorazek(0);
        osoba.setLiczbaRemisow(0);
        osoba.setLiczbaWygranych(0);
        osoba.setLiczbaRozegranychGier(0);
        osoba.setLiczbaSkonczonychGier(0);
        manager.persist(osoba);
    }

    public boolean uzytkownikIstnieje(String login, String haslo) {
        Query q = manager.createQuery("SELECT o FROM Osoba o WHERE o.login=:login AND o.haslo=:haslo", Osoba.class);
        q.setParameter("login", login);
        q.setParameter("haslo", haslo);
        return !q.getResultList().isEmpty();
    }

    public Osoba getOsoba(String login, String haslo) {
        Query q = manager.createQuery("SELECT o FROM Osoba o WHERE o.login=:login AND o.haslo=:haslo", Osoba.class);
        q.setParameter("login", login);
        q.setParameter("haslo", haslo);
        return (Osoba) q.getResultList().get(0);
    }

    public void zamien(Osoba osoba) {
        manager.merge(osoba);
    }

    public List<Osoba> ranking(Integer przedzial) {
        Query q = manager.createQuery("SELECT o FROM Osoba o ORDER BY O.pkt DESC", null);
        q.setMaxResults(150);
        List<Osoba> listaGraczy = new ArrayList<Osoba>();
        listaGraczy = q.getResultList();
        switch (przedzial) {
            case 0:
                if (listaGraczy.size() > 10) {
                    return listaGraczy.subList(0, 9);
                } else {
                    return listaGraczy;
                }
            case 1:
                if (listaGraczy.size() < 10) {
                    return new ArrayList<Osoba>();
                } else if (listaGraczy.size() > 50) {
                    return listaGraczy.subList(10, 49);
                } else {
                    return listaGraczy;
                }

            case 2:
                if (listaGraczy.size() < 50) {
                    return new ArrayList<Osoba>();
                } else if (listaGraczy.size() > 150) {
                    return listaGraczy.subList(50, 149);
                } else {
                    return listaGraczy;
                }
        }
        return q.getResultList();
    }
}
