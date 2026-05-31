/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vektra.View;

/**
 *
 * @author santi
 */
public class MainFrameView extends javax.swing.JFrame {

    /**
     * Creates new form MainFrameView
     */
    public MainFrameView() {
        this(true);
    }

    public MainFrameView(boolean isAdmin) {
        initComponents();
        vektra.Util.FontUtil.applyCustomFont(this);
        iniciarEstilos(); // Llama al método para aplicar los estilos minimalistas
        iniciarContenido();
        
        if (!isAdmin) {
            btnEditarConductoresdelaEmpresa.setVisible(false);
            btnAnadirVehiculo.setVisible(false);
        }
    }
    private javax.swing.JPanel panelContenido;

    public void mostrarPanel(javax.swing.JPanel panel) {
        panelContenido.removeAll();
        panelContenido.add(panel, java.awt.BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void iniciarContenido() {
        btnEditarConductoresdelaEmpresa.addActionListener(e -> mostrarPanel(new ConductoresPanel(this)));

        // Panel contenedor que ocupa todo el espacio derecho
        panelContenido = new javax.swing.JPanel(new java.awt.BorderLayout());
        panelContenido.setBackground(new java.awt.Color(255, 255, 255));

        
        // Agregar al lado derecho del sidebar
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel1.add(panelContenido, java.awt.BorderLayout.CENTER);

        // Cargar dashboard por defecto
        
        mostrarPanel(new DashboardPanel());
        btnDashboardInicial.addActionListener(e -> mostrarPanel(new DashboardPanel()));
        btnGenerarticket.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mostrarPanel(new ComprarTicketPanel());
            }
        });
        btnTustickets.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mostrarPanel(new TicketsActivosPanel());
            }
        });
        btnGestionRutas.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mostrarPanel(new MapaPanel());
            }
        });
        btnAnadirVehiculo.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mostrarPanel(new AnadirVehiculo());
            }
        });
        
        btnCerrar.addActionListener(e -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro que deseas salir?",
                    "Cerrar Vektra",
                    javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void iniciarEstilos() {
        // Color oscuro cuando el botón es seleccionado
        java.awt.Color colorSeleccionado = new java.awt.Color(40, 44, 52); // Negro/gris oscuro
        java.awt.Color colorHover = new java.awt.Color(240, 240, 240); // Gris claro

        // Lista de todos los botones del sidebar
        javax.swing.JButton[] botones = {
            btnDashboardInicial, btnGenerarticket, btnTustickets,
            btnEditarConductoresdelaEmpresa,
            btnGestionRutas, btnCerrar, btnAnadirVehiculo
        };

        for (javax.swing.JButton btn : botones) {
            // Quitar bordes y fondo por defecto
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            btn.setOpaque(false); // Debe ser false para dibujar nuestros bordes redondeados
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn.putClientProperty("seleccionado", false);

            // Reemplazar la forma de dibujarse del botón para lograr bordes redondeados
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                    // Antialiasing para que los bordes redondeados se vean suaves y no pixelados
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                    Boolean seleccionado = (Boolean) c.getClientProperty("seleccionado");
                    javax.swing.ButtonModel modelo = ((javax.swing.JButton) c).getModel();

                    // Dibujar el fondo redondeado según el estado
                    if (seleccionado != null && seleccionado) {
                        g2.setColor(colorSeleccionado);
                        // fillRoundRect(x, y, width, height, arcWidth, arcHeight)
                        // Dejamos 10px de margen horizontal y 5px vertical para que no ocupe todo el ancho
                        g2.fillRoundRect(10, 5, c.getWidth() - 20, c.getHeight() - 10, 15, 15);
                    } else if (modelo.isRollover()) {
                        g2.setColor(colorHover);
                        g2.fillRoundRect(10, 5, c.getWidth() - 20, c.getHeight() - 10, 15, 15);
                    }

                    super.paint(g2, c); // Dibuja el icono encima
                    g2.dispose();
                }
            });

            // Evento para cambiar el estado al hacer clic
            btn.addActionListener(e -> {
                for (javax.swing.JButton b : botones) {
                    b.putClientProperty("seleccionado", false);
                    b.repaint();
                }
                btn.putClientProperty("seleccionado", true);
                btn.repaint();
            });
        }

        // Seleccionar el primer botón por defecto
        btnDashboardInicial.putClientProperty("seleccionado", true);
        btnDashboardInicial.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnDashboardInicial = new javax.swing.JButton();
        btnGenerarticket = new javax.swing.JButton();
        btnEditarConductoresdelaEmpresa = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnTustickets = new javax.swing.JButton();
        btnGestionRutas = new javax.swing.JButton();
        btnAnadirVehiculo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 253));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnDashboardInicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/HUBico.png"))); // NOI18N

        btnGenerarticket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/ticketsico (1) (1).png"))); // NOI18N
        btnGenerarticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarticketActionPerformed(evt);
            }
        });

        btnEditarConductoresdelaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/USUARIOico.png"))); // NOI18N

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/salirico.png"))); // NOI18N

        btnTustickets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/TUSTICKETSico.png"))); // NOI18N

        btnGestionRutas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/RUTASico.png"))); // NOI18N
        btnGestionRutas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionRutasActionPerformed(evt);
            }
        });

        btnAnadirVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ICONS CANVA/vehiculos (2) (1) (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarConductoresdelaEmpresa)
                    .addComponent(btnGenerarticket)
                    .addComponent(btnCerrar)
                    .addComponent(btnDashboardInicial)
                    .addComponent(btnTustickets)
                    .addComponent(btnGestionRutas)
                    .addComponent(btnAnadirVehiculo))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnDashboardInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerarticket, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionRutas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnTustickets, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditarConductoresdelaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnAnadirVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 937, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarticketActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerarticketActionPerformed

    private void btnGestionRutasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionRutasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGestionRutasActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            //logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrameView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirVehiculo;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnDashboardInicial;
    private javax.swing.JButton btnEditarConductoresdelaEmpresa;
    private javax.swing.JButton btnGenerarticket;
    private javax.swing.JButton btnGestionRutas;
    private javax.swing.JButton btnTustickets;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
