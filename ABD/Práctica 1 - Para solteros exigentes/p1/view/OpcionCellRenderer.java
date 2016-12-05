/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abd.p1.view;

import abd.p1.model.Opcion;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Rodrigo y David
 */
@SuppressWarnings("serial")
public class OpcionCellRenderer extends JLabel implements ListCellRenderer<Opcion> {
    
    public OpcionCellRenderer() {
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Opcion> list, 
            Opcion value, int index, 
            boolean isSelected, boolean cellHasFocus) {

        String texto = value.getTexto();
        int orden = value.getNumeroOrden();
        this.setText(orden + ". " + texto);
        if (isSelected) {
            this.setBackground(Color.CYAN);
        } else {
            this.setBackground(Color.CYAN);
        }
        
        return this;
    }
    
}
