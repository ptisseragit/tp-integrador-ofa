/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frsf.ofa.cursojava.tp.integrador.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Pedido;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.PedidoService;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.RecetaService;

/**
 *
 * @author Cristian
 */
@SessionScoped
@Named("pedidoController")
public class PedidoController implements Serializable {

    @Inject
    RecetaService recetaSrv;

    @Inject
    PedidoService pedidoSrv; 
    
    private Pedido pedidoSeleccionado;
    private Receta recetaSeleccionada;
    private List<Pedido> listaPedidos;
    private DualListModel<Receta> recetasDisponibles;
    
    @PostConstruct
    public void init() {
        this.pedidoSeleccionado = null;
        this.listaPedidos = pedidoSrv.listar();
        List<Receta> origen = recetaSrv.listar();
        List<Receta> destino = new ArrayList<Receta>();
        this.recetasDisponibles = new DualListModel<>(origen, destino); 
    }
    
    public RecetaService getRecetaSrv() {
        return recetaSrv;
    }

    public void setRecetaSrv(RecetaService recetaSrv) {
        this.recetaSrv = recetaSrv;
    }

    public PedidoService getPedidoSrv() {
        return pedidoSrv;
    }

    public void setPedidoSrv(PedidoService pedidoSrv) {
        this.pedidoSrv = pedidoSrv;
    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
    }

    public Receta getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void setRecetaSeleccionada(Receta recetaSeleccionada) {
        this.recetaSeleccionada = recetaSeleccionada;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public DualListModel<Receta> getRecetasDisponibles() {
        return recetasDisponibles;
    }

    public void setRecetasDisponibles(DualListModel<Receta> recetasDisponibles) {
        this.recetasDisponibles = recetasDisponibles;
    }
    
    public String guardar() {
        pedidoSeleccionado.setRecetas(this.recetasDisponibles.getTarget());
        // TODO completar el metodo guardar
        // setear el autor de la receta seleccionada
        // invocar al metodo qeu guarda la receta
        
        Pedido p = this.pedidoSrv.guardar(pedidoSeleccionado);
        this.listaPedidos.add(p);        
        this.pedidoSeleccionado = null;
        return null;
    }

    public String nuevo() {
        this.pedidoSeleccionado = new Pedido();
        this.pedidoSeleccionado.setRecetas(new ArrayList<>());
        this.recetasDisponibles.setTarget(new ArrayList<Receta>());
        this.recetasDisponibles.setSource(recetaSrv.listar());
        return null;
    }
    
    
}
