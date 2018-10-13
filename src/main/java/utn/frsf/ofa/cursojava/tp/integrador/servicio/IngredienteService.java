/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frsf.ofa.cursojava.tp.integrador.servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;

/**
 *
 * @author mdominguez
 */
@Stateless
public class IngredienteService {

    @PersistenceContext(unitName = "RECETAS_PU")
    private EntityManager em;

    public Ingrediente guardar(Ingrediente i) {
        // TODO: completar metodo
        // si el ingrediente tiene seteado un ID realiza merge
        // caso contrario realiza un persist
        if(i.getId()!=null && i.getId()>0) {
            return em.merge(i);
        }
        em.persist(i);
        em.flush();
        em.refresh(i);
        return i;
    }

    public List<Ingrediente> listar() {
        return em.createQuery("SELECT i FROM Ingrediente i").getResultList();
    }
}
