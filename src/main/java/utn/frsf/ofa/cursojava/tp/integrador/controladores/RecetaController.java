package utn.frsf.ofa.cursojava.tp.integrador.controladores;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.IngredienteService;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.RecetaService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mdominguez
 */

// TODO: definir anotacion de ambito 
@SessionScoped
@Named("recetaController")
public class RecetaController implements Serializable {

    @Inject
    RecetaService recetaSrv;

    @Inject
    IngredienteService ingredienteSrv;

    private Receta recetaSeleccionada;
    private Autor autorSeleccionado;
    private List<Receta> listaRecetas;
    private DualListModel<Ingrediente> ingredientesDisponibles;
    
    //VARIABLES PARA LA BUSQUEDA
    private Double precioMinimo;
    private Double precioMaximo;
    private Autor autorBuscado;
    private Ingrediente ingredienteBuscado;
    private Date fechaDesde;
    private Date fechaHasta;
    private int tipoBusqueda;

    public Receta getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void setRecetaSeleccionada(Receta recetaSeleccionada) {
        this.recetaSeleccionada = recetaSeleccionada;
        this.recetaSeleccionada .setIngredientes(recetaSrv.ingredientesPorIdReceta(recetaSeleccionada.getId()));
        this.ingredientesDisponibles.setTarget(recetaSeleccionada.getIngredientes());       
    }

    public List<Receta> getListaRecetas() {
        return listaRecetas;
    }

    public void setListaRecetas(List<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    @PostConstruct
    public void init() {
        try {
            this.recetaSeleccionada = null;
            //this.recetaSeleccionada=new Receta();
            this.listaRecetas = recetaSrv.listar();
            List<Ingrediente> origen = ingredienteSrv.listar();
            List<Ingrediente> destino = new ArrayList<Ingrediente>();
            this.ingredientesDisponibles = new DualListModel<>(origen, destino);
            precioMinimo=0.0;
            precioMaximo=150.0;
            SimpleDateFormat formaFecha = new SimpleDateFormat("yyyy-MM-dd");
            String strFecha = "2010-01-30";
            fechaDesde=formaFecha.parse(strFecha);
            fechaHasta=new Date();
        } catch (ParseException ex) {
            //Logger.getLogger(RecetaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DualListModel<Ingrediente> getIngredientesDisponibles() {
        return ingredientesDisponibles;
    }

    public void setIngredientesDisponibles(DualListModel<Ingrediente> ingredientesDisponibles) {
        this.ingredientesDisponibles = ingredientesDisponibles;
    }

    public String guardar() {
        recetaSeleccionada.setIngredientes(this.ingredientesDisponibles.getTarget());
        // TODO completar el metodo guardar
        // setear el autor de la receta seleccionada
        // invocar al metodo qeu guarda la receta
        
        recetaSeleccionada.setAutor(this.autorSeleccionado);
        Receta rec = this.recetaSrv.guardar(recetaSeleccionada);
        
        //this.listaRecetas.add(rec);         
        //QUEDAN RECTAS REPETIDAS EN LA VISTA AL MOMENTO DE HACER UNA MODIFICACION SOBRE UNA RECTA
        this.listaRecetas = recetaSrv.listar();
        this.recetaSeleccionada = null;
        
        return null;
    }

    public String nuevo() {
        this.recetaSeleccionada = new Receta();
        this.recetaSeleccionada.setIngredientes(new ArrayList<>());
        this.ingredientesDisponibles.setTarget(new ArrayList<Ingrediente>());
        
        this.ingredientesDisponibles.setSource(ingredienteSrv.listar());
        return null;
    }

    //Setters y Getters para las variables de busqueda
    public void setIngredienteBuscado(Ingrediente ingredienteBuscado) {    
        this.ingredienteBuscado = ingredienteBuscado;
    }
    
    public Ingrediente getIngredienteBuscado() {
        return ingredienteBuscado;
    }
    
    public Autor getAutorSeleccionado() {
        return autorSeleccionado;
    }

    public void setAutorSeleccionado(Autor autorSeleccionado) {
        this.autorSeleccionado = autorSeleccionado;
    }
    
    public Double getPrecioMinimo(){    
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Autor getAutorBuscado() {
        return autorBuscado;
    }

    public void setAutorBuscado(Autor autorBuscado) {
        this.autorBuscado = autorBuscado;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public int getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(int tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }
    
    
    //FIN etters y Getters para las variables de busqueda
 
    public String buscarRecetas() {
            System.out.println("Buscando Recetas .............");
            System.out.println("Valor Minimo:" + this.precioMinimo.toString());
            System.out.println("Valor Maximo:" + this.precioMaximo.toString());
            System.out.println("Autor:" + this.autorBuscado.getId().toString() +"-"+ this.autorBuscado.getNombre());
            System.out.println("Fecha Minimo:" + this.fechaDesde.toString());
            System.out.println("Fecha Maximo:" + this.fechaHasta.toString());
            System.out.println("Tipo busqueda" + this.tipoBusqueda);
  
            this.listaRecetas=this.recetaSrv.busquedaAvanzada(tipoBusqueda, autorBuscado, ingredienteBuscado, precioMinimo, precioMaximo,fechaDesde, fechaHasta);
            return "buscarReceta";
    }

}
