/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frsf.ofa.cursojava.tp.integrador.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import utn.frsf.ofa.cursojava.tp.integrador.logica.RecetaLogica;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

/**
 *
 * @author mdominguez
 */
@Stateless
public class RecetaService {
    @PersistenceContext(unitName = "RECETAS_PU")
    private EntityManager em;
    
    @Inject
    private RecetaLogica logica;
    
    public Receta guardar(Receta r){        
        //if(!logica.autorPuedeCrearReceta(r)) throw new RuntimeException("La receta no puede ser creada. El autor creo el maximo de recetas. Verifique los datos"); 
        //if(!logica.puedeAgregarIngredientes(r)) throw new RuntimeException("La receta no puede ser creada. Excede la cantidad maxima de ingredientes"); 
        //if(!logica.costoIngredientesValido(r)) throw new RuntimeException("La receta no puede ser creada. El monto de los ingredientes supera el maximo"); 
        if(r.getId()!=null && r.getId()>0) {
                return em.merge(r);
        }
        em.persist(r);
        em.flush();
        em.refresh(r);
        return r;
    }  
    
    public List<Receta> listar(){
        return em.createQuery("SELECT r FROM Receta r").getResultList();
    }
    
    public List<Ingrediente> ingredientesPorIdReceta(Integer id){
        return em.createQuery("SELECT i FROM Receta r JOIN r.ingredientes i WHERE r.id = :P_ID_RECETA")
                .setParameter("P_ID_RECETA", id)
                .getResultList();
    }
    
    public List<Receta> busquedaAvanzada(int tipoBusqueda, Autor aut, Ingrediente ing, Double precioMin, Double precioMax,Date fMin,Date fMax){        
    //public List<Receta> busquedaAvanzada(AutorService a, Ingrediente i, Double precioMin, Double precioMax,Date fMin,Date fMax){        
        
        //a. Precio mínimo y máximo
        //b. Fecha inicial y final de creación de receta
        //c. Autor
        //d. Ingrediente
        List<Receta> listaRecetas;
        
        switch (tipoBusqueda){

            case 1: {
                listaRecetas=em.createQuery("SELECT r FROM Receta r WHERE r.precio > :PMINIMO AND r.precio < :PMAXIMO")
                        .setParameter("PMINIMO", precioMin)
                        .setParameter("PMAXIMO",precioMax).getResultList();
                break;
            }
            case 2: {
                listaRecetas= em.createQuery("SELECT r FROM Receta r JOIN r.ingredientes i WHERE i.id = :INGREDIENTE ")
                .setParameter("INGREDIENTE", ing.getId()).getResultList();
                break;
            }
            case 3: {
                listaRecetas= em.createQuery("SELECT r FROM Receta r JOIN r.autor a WHERE a.id = :AUTOR ")
                .setParameter("AUTOR", aut.getId()).getResultList();
                break;
            }
            case 4:{
                
                System.out.println("Consulta:" + em.createQuery("SELECT r FROM Receta r WHERE r.fechaCreacion BETWEEN :FMINIMO AND :FMAXIMO")
                        .setParameter("FMINIMO", fMin)
                        .setParameter("FMAXIMO", fMax).toString());
                listaRecetas= (List<Receta>)em.createQuery("SELECT r FROM Receta r WHERE r.fechaCreacion BETWEEN :FMINIMO AND :FMAXIMO")
                        .setParameter("FMINIMO", fMin)
                        .setParameter("FMAXIMO", fMax).getResultList();
                break;
            }
            default: {
                listaRecetas= em.createQuery("SELECT r FROM Receta r JOIN r.ingredientes i JOIN r.autor a WHERE r.precio > :PMINIMO AND r.precio<:PMAXIMO AND a.id = :AUTOR AND i.id = :INGREDIENTE ")
                .setParameter("PMINIMO", precioMin)
                .setParameter("PMAXIMO",precioMax)
                .setParameter("AUTOR", aut.getId())
                .setParameter("INGREDIENTE", ing.getId()).getResultList();
            }
            
        }
  
        //return em.createQuery("SELECT r FROM Receta r WHERE r.precio > :PMINIMO ")
        //    .setParameter("PMINIMO", precioMin).getResultList();
        
        //System.out.println("Aquí deberia realizar la busqueda y devolver lista ...");
        return listaRecetas;
    }

}
