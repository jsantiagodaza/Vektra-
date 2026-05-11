package vektra.View;

public class Dashboard extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(Dashboard.class.getName());
    private javax.swing.JPanel panelContenido;

    public Dashboard() {
        initComponents();
        configurarVentana();
        cargarPanelInicio();
    }

    private void configurarVentana() {
        setTitle("Vektra — Sistema de Metro");
        setSize(1200, 750);
        setLocationRelativeTo(null);

        // Hacemos que el panel principal ocupe todo el JFrame
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        panelContenido = new javax.swing.JPanel();
        panelContenido.setBackground(new java.awt.Color(18, 41, 71));
        panelContenido.setLayout(new java.awt.BorderLayout());

        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel1.add(panelContenido, java.awt.BorderLayout.CENTER);
    }

    // ── PANEL INICIO ─────────────────────────────────────────────
    private void cargarPanelInicio() {
        panelContenido.removeAll();

        javax.swing.JPanel panelTop = new javax.swing.JPanel(new java.awt.BorderLayout());
        panelTop.setOpaque(false);
        panelTop.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 10, 30));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Bienvenido a Vektra");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        lblTitulo.setForeground(java.awt.Color.WHITE);

        // Barras de líneas decorativas
        javax.swing.JPanel barras = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));
        barras.setOpaque(false);
        barras.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        String[] coloresBarras = { "E50822", "F39C12", "27AE60", "2980B9" };
        int[] anchos = { 90, 70, 60, 55 };
        for (int i = 0; i < coloresBarras.length; i++) {
            javax.swing.JPanel barra = new javax.swing.JPanel();
            barra.setBackground(java.awt.Color.decode("#" + coloresBarras[i]));
            barra.setPreferredSize(new java.awt.Dimension(anchos[i], 5));
            barras.add(barra);
        }

        panelTop.add(lblTitulo, java.awt.BorderLayout.NORTH);
        panelTop.add(barras, java.awt.BorderLayout.CENTER);

        // Cards estadísticas
        javax.swing.JPanel panelCards = new javax.swing.JPanel(new java.awt.GridLayout(1, 3, 15, 0));
        panelCards.setOpaque(false);
        panelCards.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelCards.add(crearCard("0", "Estaciones", "ACTIVAS...", new java.awt.Color(229, 8, 34)));
        panelCards.add(crearCard("0", "Tickets", "Hoy", new java.awt.Color(41, 128, 185)));
        panelCards.add(crearCard("0", "Pasajeros", "En Circulación...", new java.awt.Color(39, 174, 96)));

        // Panel inferior
        javax.swing.JPanel panelInferior = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 15, 0));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 30, 30, 30));
        panelInferior.add(crearPanelLineas());
        panelInferior.add(crearPanelTickets());

        panelContenido.add(panelTop, java.awt.BorderLayout.NORTH);
        panelContenido.add(panelCards, java.awt.BorderLayout.CENTER);
        panelContenido.add(panelInferior, java.awt.BorderLayout.SOUTH);

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private javax.swing.JPanel crearCard(String numero, String titulo, String subtitulo, java.awt.Color colorBorde) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout());
        card.setBackground(new java.awt.Color(25, 50, 90));
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, colorBorde),
                javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        javax.swing.JLabel lblNum = new javax.swing.JLabel(numero, javax.swing.SwingConstants.CENTER);
        lblNum.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 44));
        lblNum.setForeground(java.awt.Color.WHITE);

        javax.swing.JLabel lblTit = new javax.swing.JLabel(titulo, javax.swing.SwingConstants.CENTER);
        lblTit.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTit.setForeground(java.awt.Color.WHITE);

        javax.swing.JLabel lblSub = new javax.swing.JLabel(subtitulo, javax.swing.SwingConstants.CENTER);
        lblSub.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        lblSub.setForeground(new java.awt.Color(150, 180, 210));

        javax.swing.JPanel centro = new javax.swing.JPanel(new java.awt.GridLayout(3, 1, 0, 4));
        centro.setOpaque(false);
        centro.add(lblNum);
        centro.add(lblTit);
        centro.add(lblSub);

        card.add(centro, java.awt.BorderLayout.CENTER);
        return card;
    }

    private javax.swing.JPanel crearPanelLineas() {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBackground(new java.awt.Color(25, 50, 90));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 18, 15, 18));

        javax.swing.JLabel titulo = new javax.swing.JLabel("Líneas Activas");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        titulo.setForeground(java.awt.Color.WHITE);
        titulo.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(javax.swing.Box.createVerticalStrut(12));

        String[][] lineas = {
                { "Línea Roja Activa...", "E50822" },
                { "Línea Amarilla Activa...", "F39C12" },
                { "Línea Verde Activa...", "27AE60" },
                { "Línea Azul Activa...", "2980B9" }
        };

        for (String[] linea : lineas) {
            javax.swing.JPanel fila = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 3));
            fila.setOpaque(false);
            fila.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 32));
            fila.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

            javax.swing.JLabel dot = new javax.swing.JLabel("●");
            dot.setForeground(java.awt.Color.decode("#" + linea[1]));
            dot.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));

            javax.swing.JLabel nombre = new javax.swing.JLabel(linea[0]);
            nombre.setForeground(java.awt.Color.WHITE);
            nombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

            fila.add(dot);
            fila.add(nombre);
            panel.add(fila);
        }
        return panel;
    }

    private javax.swing.JPanel crearPanelTickets() {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBackground(new java.awt.Color(25, 50, 90));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 18, 15, 18));

        javax.swing.JLabel titulo = new javax.swing.JLabel("Tickets Vendidos Hoy");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        titulo.setForeground(java.awt.Color.WHITE);
        titulo.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        panel.add(titulo);
        panel.add(javax.swing.Box.createVerticalStrut(12));

        javax.swing.JLabel sinDatos = new javax.swing.JLabel("Esperando conexión...");
        sinDatos.setForeground(new java.awt.Color(150, 180, 210));
        sinDatos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 13));
        panel.add(sinDatos);

        return panel;
    }

    // ── GENERADO POR NETBEANS — NO TOCAR ─────────────────────────
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 41, 71));

        jPanel2.setBackground(new java.awt.Color(242, 242, 253));

        jButton1.setBackground(new java.awt.Color(229, 0, 47));
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/VIKTOR.png")));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(27, 169, 68));
        jButton2.setForeground(new java.awt.Color(0, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/TICKET.png")));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 203, 48));
        jButton3.setForeground(new java.awt.Color(255, 102, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/MAP.png")));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(66, 199, 255));
        jButton4.setForeground(new java.awt.Color(0, 105, 239));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/TEAM.png")));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 80, 169));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/ASIGNAR.png")));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 153));
        jButton6.setForeground(new java.awt.Color(0, 65, 178));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/MAP.png")));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 95,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 89,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 879, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        cargarPanelInicio();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        // cargarPanelTickets(); — próxima pantalla
    }// GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        // cargarPanelMapa(); — próxima pantalla
    }// GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        // cargarPanelEquipo(); — próxima pantalla
    }// GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        // cargarPanelConductores(); — próxima pantalla
    }// GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
        // cargarPanelUbicacion(); — próxima pantalla
    }// GEN-LAST:event_jButton6ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}