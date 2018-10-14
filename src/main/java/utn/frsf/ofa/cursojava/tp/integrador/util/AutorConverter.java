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
/**
 *
 * @author mdominguez
 */
@FacesConverter("autorConverter")
public class AutorConverter  implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        String[] datos = string.split(";");
        Autor i = new Autor();
        i.setId(Integer.valueOf(datos[0]));
        i.setNombre(datos[1]);
        System.out.println("en objeto "+i.toString()+ " < "+string+">");
        return i;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        //System.out.println("CONVERTIR autor "+t.toString());
        Autor a = (Autor) t;
        return a.getId()+";"+a.getNombre();
    }
    
}
