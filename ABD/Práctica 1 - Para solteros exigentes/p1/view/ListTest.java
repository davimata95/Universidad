/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abd.p1.view;

import abd.p1.model.Opcion;
import abd.p1.model.Pregunta;
import javax.swing.DefaultListModel;

/**
 *
 * @author Rodrigo y David
 */
@SuppressWarnings("serial")
public class ListTest extends javax.swing.JPanel {

    /**
     * Creates new form ListTest
     */
    public ListTest() {
        initComponents();
        Pregunta p1 = new Pregunta(1, "Pregunta 1", null);
        Pregunta p2 = new Pregunta(2, "Pregunta 2", null);
        p2.getOpciones().add(new Opcion());
        p2.getOpciones().add(new Opcion());
        Pregunta p3 = new Pregunta(3, "Pregunta 3", null);
        p3.getOpciones().add(new Opcion());
        p3.getOpciones().add(new Opcion());
        p3.getOpciones().add(new Opcion());
        p3.getOpciones().add(new Opcion());
        DefaultListModel<Pregunta> modelo = new DefaultListModel<>();
        modelo.addElement(p1);
        modelo.addElement(p2);
        modelo.addElement(p3);
        listaPreguntas.setModel(modelo);
        listaPreguntas.setCellRenderer(new PreguntaCellRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaPreguntas = new javax.swing.JList<>();

        jScrollPane1.setViewportView(listaPreguntas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Pregunta> listaPreguntas;
    // End of variables declaration//GEN-END:variables
}
