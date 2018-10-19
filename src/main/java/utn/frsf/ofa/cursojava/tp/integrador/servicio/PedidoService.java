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
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Pedido;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

/**
 *
 * @author Cristian
 */

@Stateless
public class PedidoService {
    @PersistenceContext(unitName = "RECETAS_PU")
    private EntityManager em;
    
    public Pedido guardar(Pedido p){        
        
        if(p.getId()!=null && p.getId()>0) {
                return em.merge(p);
        }
        em.persist(p);
        em.flush();
        em.refresh(p);
        return p;
    }  
    
    public List<Pedido> listar(){
        return em.createQuery("SELECT p FROM Pedido p").getResultList();
    }
    
    public List<Receta> recetasPorIdPedido(Integer id){
        return em.createQuery("SELECT r FROM Pedido p JOIN p.recetas r WHERE p.id = :PEDIDO")
                .setParameter("PEDIDO", id)
                .getResultList();
    }
}
