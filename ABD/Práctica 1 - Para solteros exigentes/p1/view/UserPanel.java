/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abd.p1.view;

import javax.swing.ImageIcon;

import abd.p1.controller.EditarPerfilController;
import abd.p1.model.Usuario;

/**
 *
 * @author Rodrigo y David
 */

@SuppressWarnings({ "serial", "unused" })
public class UserPanel extends javax.swing.JPanel {
    private String nombre = "Nombre";
    private int edad = -1;
    private boolean editable = true;
    private EditarPerfilController controller;
    private ImageIcon icon;
    
    // Constructor sin parámetros
    public UserPanel() {
        initComponents();
        labelNombre.setText(nombre);
        labelEdad.setText("Edad");
    }

    // Geters and setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        labelNombre.setText(nombre);
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
        if (edad != -1) {
        	labelEdad.setText(edad + " años");
        } else {
        	labelEdad.setText("Edad no expecificada");
        }
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        buttonNombre.setVisible(editable);
        buttonFechaNacimiento.setVisible(editable);
    }
    
    public void setIcon(ImageIcon icon) {
    	this.icon = icon;
    	avatarPanel1.setIcon(icon);
    }

    public EditarPerfilController getController() {
		return controller;
	}

	public void setController(EditarPerfilController controller) {
		this.controller = controller;
	}

	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        avatarPanel1 = new abd.p1.view.AvatarPanel();
        labelNombre = new javax.swing.JLabel();
        labelEdad = new javax.swing.JLabel();
        buttonNombre = new javax.swing.JButton();
        buttonFechaNacimiento = new javax.swing.JButton();

        avatarPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout avatarPanel1Layout = new javax.swing.GroupLayout(avatarPanel1);
        avatarPanel1.setLayout(avatarPanel1Layout);
        avatarPanel1Layout.setHorizontalGroup(
            avatarPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );
        avatarPanel1Layout.setVerticalGroup(
            avatarPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        labelNombre.setText("Nombre:");

        labelEdad.setText("Edad:");

        buttonNombre.setText("Cambiar nombre");
        buttonNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNombreActionPerformed(evt);
            }
        });

        buttonFechaNacimiento.setText("Cambiar fecha de nacimiento");
        buttonFechaNacimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFechaNacimientoActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEdad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNombre)
                            .addComponent(buttonNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelEdad)
                            .addComponent(buttonFechaNacimiento)))
                    .addComponent(avatarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNombreActionPerformed
    	controller.cambiarNombre(this);
    }//GEN-LAST:event_buttonNombreActionPerformed
    
    private void buttonFechaNacimientoActionPerformed(java.awt.event.ActionEvent evt) {                                                      
    	controller.cambiarFechaNacimiento(this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private abd.p1.view.AvatarPanel avatarPanel1;
    private javax.swing.JButton buttonFechaNacimiento;
    private javax.swing.JButton buttonNombre;
    private javax.swing.JLabel labelEdad;
    private javax.swing.JLabel labelNombre;
    // End of variables declaration//GEN-END:variables

    // Getters and setters
	public void setTextLabelNombre(String statement) {
		this.labelNombre.setText(statement);
	}

	public void rellenarVentana(Usuario usuario, boolean editable) {
		setNombre(usuario.getNombre());
		if (usuario.getNacimiento() != null) {
			setEdad(controller.fecha2Edad(usuario.getNacimiento()));
		} else {
			setEdad(-1);
		}
		setEditable(editable);
		if (usuario.getAvatar() != null) {
			avatarPanel1.setIcon(new ImageIcon(usuario.getAvatar()));
		}
	}
}
