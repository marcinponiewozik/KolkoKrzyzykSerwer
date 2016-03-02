/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Requests.OsobaRequest;
import entitys.Osoba;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcin
 */
@Stateless
@Path("osoba")
public class OsobaFacadeREST extends AbstractFacade<Osoba> {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @EJB
    private OsobaRequest osobaRequest;

    public OsobaFacadeREST() {
        super(Osoba.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Osoba entity) {
        super.create(entity);
    }

    @POST
    @Path("/rejestracja")
    @Consumes({"application/json"})
    public Response rejestracja(Osoba entity) {
        
        if (osobaRequest.uzytkownikIstnieje(entity.getLogin(), entity.getHaslo())) {
            return Response.status(Response.Status.CONFLICT).build();
        }        
        osobaRequest.dodaj(entity);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Osoba entity) {
        entity.setId(id);
        osobaRequest.zamien(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{login}/{haslo}")
    @Produces({"application/json"})
    public Response logowanie(@PathParam("login") String login, @PathParam("haslo") String haslo) {
        Osoba osoba;
        if (osobaRequest.uzytkownikIstnieje(login, haslo)) {
            osoba = new Osoba();
            osoba = osobaRequest.getOsoba(login, haslo);
            return Response.ok(osoba, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Osoba find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Osoba> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("/ranking/przedzial/{przedzial}")
    @Produces({"application/json"})
    public List<Osoba> ranking(@PathParam("przedzial")Integer przedzial) {
        return osobaRequest.ranking(przedzial);
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
