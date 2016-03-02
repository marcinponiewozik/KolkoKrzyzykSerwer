/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Requests.GraRequest;
import Requests.OsobaRequest;
import entitys.Gra;
import entitys.Osoba;
import entitys.Wybor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcin
 */
@Stateless
@Path("gra")
public class GraFacadeREST extends AbstractFacade<Gra> {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @EJB
    private GraRequest graRequest;
    @EJB
    private OsobaRequest osobaRequest;

    public GraFacadeREST() {
        super(Gra.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Gra entity) {
        Osoba osoba = osobaRequest.wezOsobaPoId(entity.getGospodarz().getId());
        osoba.setLiczbaRozegranychGier(osoba.getLiczbaRozegranychGier()+1);
        osobaRequest.zamien(osoba);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Wybor wbor) {
        Gra gra = graRequest.znajdz(id);
        List<Wybor> list = new ArrayList<Wybor>();
        list = gra.getWybory();
        list.add(wbor);
        gra.setWybory(list);
        gra.setRuchGospodarza(!gra.isRuchGospodarza());
        super.edit(gra);
    }

    @PUT
    @Path("/dolacz/{id}/{idOsoba}")
    @Consumes({"application/json"})
    public Response dolacz(@PathParam("id") Long id, @PathParam("idOsoba") Long idOsoba, Gra entity) {
        System.out.println("1");
        Osoba przeciwnik = new Osoba();
        System.out.println("2");
        przeciwnik = osobaRequest.wezOsobaPoId(idOsoba);
        System.out.println("3");
        Gra graDB = new Gra();
        graDB = graRequest.znajdz(id);
        System.out.println("4");
        if (graRequest.brakPrzeciwnika(id)) {
            System.out.println("5");
            graRequest.dodajPrzeciwnika(entity, przeciwnik);
            System.out.println("6");
            przeciwnik.setLiczbaRozegranychGier(przeciwnik.getLiczbaRozegranychGier()+1);
            System.out.println("7");
            osobaRequest.zamien(przeciwnik);
            System.out.println("8");
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Gra find(@PathParam("id") Long id) {
        if (graRequest.sprawdzStanGr(id)) {
            Gra gra = new Gra();
            gra = graRequest.znajdz(id);
            
            if (!gra.isZakonczona()) {
                if (!gra.getIdZwyciescy().equals(null)) {
                    Osoba zwyciesca = osobaRequest.wezOsobaPoId(gra.getIdZwyciescy());                    
                    zwyciesca.setPkt(zwyciesca.getPkt() + 100);
                    zwyciesca.setLiczbaWygranych(zwyciesca.getLiczbaWygranych()+1);
                    zwyciesca.setLiczbaSkonczonychGier(zwyciesca.getLiczbaSkonczonychGier()+1);
                    osobaRequest.zamien(zwyciesca);

                    Osoba przegrany;
                    if (gra.getGospodarz().getId().equals(gra.getIdZwyciescy())) {
                        przegrany = osobaRequest.wezOsobaPoId(gra.getPrzeciwnik().getId());
                    } else {
                        przegrany = osobaRequest.wezOsobaPoId(gra.getGospodarz().getId());
                    }

                    przegrany.setLiczbaPorazek(przegrany.getLiczbaPorazek()+1);
                    przegrany.setLiczbaSkonczonychGier(przegrany.getLiczbaSkonczonychGier()+1);
                    if (przegrany.getPkt() < 80) {
                        przegrany.setPkt(0);
                    } else {
                        przegrany.setPkt(przegrany.getPkt() - 80);
                    }
                    osobaRequest.zamien(przegrany);
                }else{
                    Osoba gospodarz = gra.getGospodarz();
                    Osoba przeciwnik = gra.getPrzeciwnik();
                    gospodarz.setLiczbaRemisow(gospodarz.getLiczbaRemisow()+1);
                    gospodarz.setLiczbaSkonczonychGier(gospodarz.getLiczbaSkonczonychGier()+1);
                    przeciwnik.setLiczbaRemisow(przeciwnik.getLiczbaRemisow()+1);
                    przeciwnik.setLiczbaSkonczonychGier(przeciwnik.getLiczbaSkonczonychGier()+1);  
                    osobaRequest.zamien(gospodarz);
                    osobaRequest.zamien(przeciwnik);                    
                }
                gra.setZakonczona(true);
                graRequest.zamien(gra.getId(), gra);
            }
        }
        return super.find(id);
    }

    @GET
    @Path("/ostatniaGra/{idGracza}")
    @Produces("text/plain")
    public String ostatniaGra(@PathParam("idGracza") Long idOsoba) {
        Gra gra = graRequest.znajdzPoIdOsoba(idOsoba);
        if (gra == null) {
            return String.valueOf(0L);
        } else {
            return String.valueOf(gra.getId());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Gra> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("/dlagracza/{id}")
    @Produces({"application/json"})
    public List<Gra> dlaGracza(@PathParam("id") Long idOsoba) {
        return graRequest.wszystkieDostepneGry(idOsoba);
    }
    @GET
    @Path("/gracz/{login}")
    @Produces({"application/json"})
    public List<Gra> gryGracza(@PathParam("login") String login) {
        return graRequest.gryGracza(login);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
