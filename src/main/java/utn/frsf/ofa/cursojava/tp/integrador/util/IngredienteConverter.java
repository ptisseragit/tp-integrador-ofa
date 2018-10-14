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
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
/**
 *
 * @author mdominguez
 */
@FacesConverter("ingredienteConverter")
public class IngredienteConverter  implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String[] datos = string.split(";");
        Ingrediente i = new Ingrediente();
        i.setId(Integer.valueOf(datos[0]));
        i.setDescripcion(datos[1]);
        i.setCosto(Double.valueOf(datos[2]));
        System.out.println("en objeto "+i.toString()+ " < "+string+">");
        return i;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        //System.out.println("CONVERTIR ingrediente "+t.toString());
        Ingrediente i = (Ingrediente) t;
        return i.getId()+";"+i.getDescripcion()+";"+i.getCosto();
        //return t.toString();
    }
    
}
