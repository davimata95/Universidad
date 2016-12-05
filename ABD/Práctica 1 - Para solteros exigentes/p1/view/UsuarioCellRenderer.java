/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abd.p1.view;

import abd.p1.model.Usuario;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Rodrigo y David
 */
@SuppressWarnings("serial")
public class UsuarioCellRenderer extends UserPanel implements ListCellRenderer<Usuario> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Usuario> list, 
            Usuario value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        
    	// Fijamos el nombre
        this.setNombre(value.getNombre());
        // Fijamos la edad (convirtiendo la fecha de nacimiento)
        if (value.getNacimiento() != null) {
        	this.setEdad(fecha2Edad(value.getNacimiento()));
        } else {
        	this.setEdad(-1);
        }
        // Fijamos el avatar
        if (value.getAvatar() != null) {
        	this.setIcon(new ImageIcon(value.getAvatar()));
        } else {
        	this.setIcon(null);
        }
        // La hacemos opaca
        this.setOpaque(true);
        this.setEditable(false);
        if (isSelected) {
            this.setBackground(Color.CYAN);
        } else {
            this.setBackground(Color.WHITE);
        }
        
        return this;
    }
    
	public int fecha2Edad (Date fechaNacimiento) {
		Date fechaHoy = new Date(System.currentTimeMillis());
		Calendar calendarHoy = Calendar.getInstance();
		calendarHoy.setTime(fechaHoy);
		Calendar calendarNacimiento = Calendar.getInstance();
		calendarNacimiento.setTime(fechaNacimiento);
		
		// Calculamos la diferencia entre la fecha de hoy y la fecha de nacimiento
		int day = calendarHoy.get(Calendar.DATE) - calendarNacimiento.get(Calendar.DATE);
		int month = calendarHoy.get(Calendar.MONTH) - calendarNacimiento.get(Calendar.MONTH);
		int year = calendarHoy.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR);

		if ((month == 0 && day < 0) || month < 0)
			year--;
		
		return year;
	}
    
}
