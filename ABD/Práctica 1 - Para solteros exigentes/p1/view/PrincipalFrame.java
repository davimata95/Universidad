/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abd.p1.view;

import abd.p1.controller.ListPreguntaController;
import abd.p1.controller.ListUserController;

/**
 *
 * @author Rodrigo y David
 */
@SuppressWarnings("serial")
public class PrincipalFrame extends javax.swing.JDialog {

	private ListUserController controller;
	private ListPreguntaController controller2;
	
    /**
     * Creates new form PrincipalFrame
     */
    
    public PrincipalFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

	public PrincipalFrame(ListUserController controller, ListPreguntaController controller2) {
		this(null, true);
		this.controller = controller;
		listUserPanel1.setController(this.controller);
		listUserPanel1.inicializarListaUsuarios();
		this.controller2 = controller2;
		listPreguntaPanel1.setController(this.controller2);
		listPreguntaPanel1.inicializarListaPreguntas();
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        listUserPanel1 = new abd.p1.view.ListUserPanel();
        listPreguntaPanel1 = new abd.p1.view.ListPreguntaPanel();
        listMensajePanel1 = new abd.p1.view.ListMensajePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.addTab("Usuarios", listUserPanel1);
        jTabbedPane1.addTab("Preguntas", listPreguntaPanel1);
        jTabbedPane1.addTab("Mensajes no leídos", listMensajePanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new PrincipalFrame(new javax.swing.JFrame(), true).setVisible(true);;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    private abd.p1.view.ListMensajePanel listMensajePanel1;
    private abd.p1.view.ListPreguntaPanel listPreguntaPanel1;
    private abd.p1.view.ListUserPanel listUserPanel1;
    // End of variables declaration//GEN-END:variables
}
