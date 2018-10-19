/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frsf.ofa.cursojava.tp.integrador.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

/**
 *
 * @author Cristian
 */
@FacesConverter("recetaConverter")
public class RecetaConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String[] datos = string.split(";");
        Receta r = new Receta();
        r.setId(Integer.valueOf(datos[0]));
        r.setDescripcion(datos[1]);
        System.out.println("en objeto "+r.toString()+ " < "+string+">");
        return r;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        //System.out.println("CONVERTIR autor "+t.toString());
        Receta r = (Receta) t;
        return r.getId()+";"+r.getDescripcion();
    }
    
}
