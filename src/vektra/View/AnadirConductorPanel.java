package vektra.View;

import javax.swing.JOptionPane;
import vektra.Dao.ConductorDao;
import vektra.Model.Conductor;

/**
 *
 * @author santi
 */
public class AnadirConductorPanel extends javax.swing.JPanel {

    private static final java.awt.Color COLOR_CAMPO = new java.awt.Color(51, 51, 51);
    private static final java.awt.Color COLOR_ERROR = new java.awt.Color(200, 50, 50);
    private static final java.awt.Color COLOR_OK = new java.awt.Color(39, 174, 96);
    private static final java.awt.Color COLOR_TEXTO = new java.awt.Color(204, 204, 204);

    public AnadirConductorPanel() {
        initComponents();
        vektra.Util.FontUtil.applyCustomFont(this);
        initPlaceholders();
        cargarRutas();
        initValidaciones();
    }

    // ── PLACEHOLDERS ──────────────────────────────────────────────
    private void initPlaceholders() {
        configurarPlaceholder(nombreConductortxt, "Ej. Marco Javier");
        configurarPlaceholder(apellidosConductortxt, "Ej. Torres Piña");
        configurarPlaceholder(cedulaConductortxt, "Ej. 12345678");
        configurarPlaceholder(telefonoConductortxt, "Ej. +57 300 000 0000");
        configurarPlaceholder(correoConductortxt, "Ej. correo@email.com");
        configurarPlaceholder(licenciaConductortxt, "Ej. LIC-2025-9293839");
    }

    private void cargarRutas() {
        try {
            vektra.Dao.RutaDao dao = new vektra.Dao.RutaDao();
            java.util.List<vektra.Model.Ruta> rutas = dao.obtenerTodasLasRutas();
            cmbRutas.removeAllItems();
            cmbRutas.addItem("Seleccionar ruta...");
            for (vektra.Model.Ruta r : rutas) {
                cmbRutas.addItem(r.formatoUI());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar rutas: " + e.getMessage());
        }
    }

    private void configurarPlaceholder(javax.swing.JTextField campo, String placeholder) {
        campo.setText(placeholder);
        campo.setForeground(new java.awt.Color(120, 120, 120));
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new java.awt.Color(120, 120, 120));
                    marcarCampo(campo, false);
                }
            }
        });
    }

    // ── VALIDACIONES ──────────────────────────────────────────────
    private void initValidaciones() {
        nombreConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = nombreConductortxt.getText().trim();
            marcarCampo(nombreConductortxt,
                    !v.isEmpty() && !v.equals("Ej. Marco Javier") && v.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        }));

        apellidosConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = apellidosConductortxt.getText().trim();
            marcarCampo(apellidosConductortxt,
                    !v.isEmpty() && !v.equals("Ej. Torres Piña") && v.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        }));

        cedulaConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = cedulaConductortxt.getText().trim();
            marcarCampo(cedulaConductortxt,
                    !v.isEmpty() && !v.equals("Ej. 12345678") && v.matches("\\d{6,12}"));
        }));

        telefonoConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = telefonoConductortxt.getText().trim().replaceAll("\\s", "");
            marcarCampo(telefonoConductortxt,
                    !v.isEmpty() && !v.equals("Ej.+57300000000") && v.matches("\\+57\\d{10}"));
        }));

        correoConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = correoConductortxt.getText().trim();
            marcarCampo(correoConductortxt,
                    !v.isEmpty() && !v.equals("Ej. correo@email.com") && v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        }));

        licenciaConductortxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = licenciaConductortxt.getText().trim();
            marcarCampo(licenciaConductortxt,
                    !v.isEmpty() && !v.equals("Ej. LIC-2025-9293839") && v.matches("[A-Za-z0-9\\-]+") && v.length() >= 5);
        }));
    }

    // ── HELPERS ───────────────────────────────────────────────────
    private void marcarCampo(javax.swing.JTextField campo, boolean valido) {
        java.awt.Color color = valido ? COLOR_OK : COLOR_ERROR;
        campo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(color, 2, true),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        campo.putClientProperty("valido", valido);
    }

    private boolean todosValidos() {
        javax.swing.JTextField[] campos = {
            nombreConductortxt, apellidosConductortxt, cedulaConductortxt,
            telefonoConductortxt, correoConductortxt, licenciaConductortxt
        };
        for (javax.swing.JTextField c : campos) {
            Object tag = c.getClientProperty("valido");
            if (tag == null || !(Boolean) tag) {
                return false;
            }
        }
        return true;
    }

    private static class SimpleDocListener implements javax.swing.event.DocumentListener {

        private final Runnable accion;

        SimpleDocListener(Runnable accion) {
            this.accion = accion;
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            accion.run();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            accion.run();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            accion.run();
        }
    }

    // ── GEN — NO TOCAR ────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nombreConductortxt = new javax.swing.JTextField();
        cedulaConductortxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        telefonoConductortxt = new javax.swing.JTextField();
        correoConductortxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        licenciaConductortxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnAnadirConductor = new javax.swing.JButton();
        apellidosConductortxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbRutas = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Añadir Conductor");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Registra un nuevo conductor en el sistema");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Nombres:");
        nombreConductortxt.setBackground(COLOR_CAMPO);
        nombreConductortxt.setForeground(COLOR_TEXTO);
        cedulaConductortxt.setBackground(COLOR_CAMPO);
        cedulaConductortxt.setForeground(COLOR_TEXTO);
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Cédula:");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Apellidos:");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Correo Electronico:");
        telefonoConductortxt.setBackground(COLOR_CAMPO);
        telefonoConductortxt.setForeground(COLOR_TEXTO);
        correoConductortxt.setBackground(COLOR_CAMPO);
        correoConductortxt.setForeground(COLOR_TEXTO);
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Número de Licencia:");
        licenciaConductortxt.setBackground(COLOR_CAMPO);
        licenciaConductortxt.setForeground(COLOR_TEXTO);
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Teléfono:");
        btnAnadirConductor.setBackground(new java.awt.Color(51, 153, 255));
        btnAnadirConductor.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnAnadirConductor.setForeground(java.awt.Color.WHITE);
        btnAnadirConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vektra/View/Imagenes/PLUS (1) (1).png")));
        btnAnadirConductor.setText("Añadir Conductor");
        btnAnadirConductor.addActionListener(e -> btnAnadirConductorActionPerformed(e));
        apellidosConductortxt.setBackground(COLOR_CAMPO);
        apellidosConductortxt.setForeground(COLOR_TEXTO);
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Ruta Asignada:");
        cmbRutas.setBackground(COLOR_CAMPO);
        cmbRutas.setForeground(COLOR_TEXTO);
        cmbRutas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Seleccionar ruta...", "Item 1", "Item 2"}));
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("*Todos los campos son obligatorios");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel10)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel3)
                                                        .addComponent(nombreConductortxt, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(apellidosConductortxt)
                                                        .addComponent(jLabel4)
                                                        .addComponent(cedulaConductortxt)
                                                        .addComponent(jLabel8)
                                                        .addComponent(telefonoConductortxt)
                                                        .addComponent(jLabel7)
                                                        .addComponent(licenciaConductortxt))
                                                .addGap(18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel6)
                                                        .addComponent(correoConductortxt, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(cmbRutas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnAnadirConductor, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(2)
                                .addComponent(jLabel10)
                                .addGap(10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3).addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nombreConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(correoConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5).addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(apellidosConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbRutas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cedulaConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAnadirConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telefonoConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(licenciaConductortxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    }// </editor-fold>
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void btnAnadirConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirConductorActionPerformed
        if (!todosValidos()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor corrige los campos marcados en rojo.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cmbRutas.getSelectedItem() == null
                || cmbRutas.getSelectedItem().toString().startsWith("Item")
                || cmbRutas.getSelectedItem().toString().equals("Seleccionar ruta...")) {

            JOptionPane.showMessageDialog(this,
                    "Selecciona una ruta para el conductor.",
                    "Ruta requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            Conductor c = new Conductor();

            c.setNombre(nombreConductortxt.getText());
            c.setApellido(apellidosConductortxt.getText());
            c.setCedula(cedulaConductortxt.getText());
            c.setTelefono(telefonoConductortxt.getText());
            c.setLicencia(licenciaConductortxt.getText());

            // Si tu modelo tiene correo:
            // c.setCorreo(correoConductortxt.getText());
            c.setRutaAsignada(cmbRutas.getSelectedItem().toString());

            ConductorDao dao = new ConductorDao();

            boolean registrado = dao.agregarConductor(c);

            if (registrado) {

                JOptionPane.showMessageDialog(this,
                        "Conductor registrado correctamente");

                nombreConductortxt.setText("");
                apellidosConductortxt.setText("");
                cedulaConductortxt.setText("");
                telefonoConductortxt.setText("");
                licenciaConductortxt.setText("");
                correoConductortxt.setText("");

                cmbRutas.setSelectedIndex(0);

            } else {

                JOptionPane.showMessageDialog(this,
                        "No fue posible registrar el conductor");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAnadirConductorActionPerformed

    // Variables declaration - do not modify                     
    // Variables declaration - do not modify
    private javax.swing.JTextField apellidosConductortxt;
    private javax.swing.JButton btnAnadirConductor;
    private javax.swing.JTextField cedulaConductortxt;
    private javax.swing.JComboBox<String> cmbRutas;
    private javax.swing.JTextField correoConductortxt;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5;
    private javax.swing.JLabel jLabel6, jLabel7, jLabel8, jLabel9, jLabel10;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField licenciaConductortxt;
    private javax.swing.JTextField nombreConductortxt;
    private javax.swing.JTextField telefonoConductortxt;
    // End of variables declaration
}
