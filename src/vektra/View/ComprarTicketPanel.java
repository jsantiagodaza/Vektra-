/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vektra.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import vektra.Dao.RutaDao;
import vektra.Dao.PasajeroDao;
import vektra.Model.Estacion;
import vektra.Model.Pasajero;
import vektra.Model.Ruta;
import vektra.Service.TicketService;

/**
 *
 * @author santi
 */
public class ComprarTicketPanel extends javax.swing.JPanel {

    /**
     * Creates new form ComprarTicketPanel
     */
    
    public ComprarTicketPanel() {
        initComponents();
        vektra.Util.FontUtil.applyCustomFont(this);
        // Colores y comportamiento
        initColors();
        initPlaceholders();
        cargarEstacionesDesdeRutas();
        initValidaciones();
        btnGenerarTicket.addActionListener(e -> btnGenerarTicketActionPerformed(e));
    }
 
    private static final java.awt.Color COLOR_CAMPO = new java.awt.Color(51, 51, 51);
    private static final java.awt.Color COLOR_ERROR = new java.awt.Color(200, 50, 50);
    private static final java.awt.Color COLOR_OK    = new java.awt.Color(39, 174, 96);
    private static final java.awt.Color COLOR_TEXTO = new java.awt.Color(204, 204, 204);

    private List<Estacion> estaciones;

    private static class EstacionRenderer extends DefaultListCellRenderer {
        @Override
        public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Estacion) {
                Estacion estacion = (Estacion) value;
                value = estacion.getNombre();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    private void initColors() {
        nombreClientetxt.setBackground(COLOR_CAMPO);
        nombreClientetxt.setForeground(COLOR_TEXTO);
        idClientetxt.setBackground(COLOR_CAMPO);
        idClientetxt.setForeground(COLOR_TEXTO);
        emailClientetxt.setBackground(COLOR_CAMPO);
        emailClientetxt.setForeground(COLOR_TEXTO);
        EdadporFechadeNacimientoClientetxt.setBackground(COLOR_CAMPO);
        EdadporFechadeNacimientoClientetxt.setForeground(COLOR_TEXTO);
        btnGenerarTicket.setBackground(new java.awt.Color(51, 153, 255));
        btnGenerarTicket.setForeground(java.awt.Color.WHITE);
    }

    // PLACEHOLDERS
    private void initPlaceholders() {
        configurarPlaceholder(nombreClientetxt, "Ej. Juan Pérez");
        configurarPlaceholder(idClientetxt, "Ej. 1234567");
        configurarPlaceholder(emailClientetxt, "tuincreiblecorreo123@ejemplo.com");
        configurarPlaceholder(EdadporFechadeNacimientoClientetxt, "DD/MM/AAAA");
        // Añadir filtro para formatear la fecha automáticamente a dd/MM/aaaa
        try {
            javax.swing.text.Document d = EdadporFechadeNacimientoClientetxt.getDocument();
            if (d instanceof javax.swing.text.AbstractDocument) {
                ((javax.swing.text.AbstractDocument) d).setDocumentFilter(new DateDocumentFilter());
            }
        } catch (Exception ex) {
            // no crítico
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

    // VALIDACIONES
    private void initValidaciones() {
        nombreClientetxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = nombreClientetxt.getText().trim();
            marcarCampo(nombreClientetxt,
                !v.isEmpty() && !v.equals("Ej. Juan Pérez") && v.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        }));

        idClientetxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = idClientetxt.getText().trim();
            marcarCampo(idClientetxt,
                !v.isEmpty() && !v.equals("Ej. 1234567") && v.matches("\\d{6,12}"));
        }));

        emailClientetxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = emailClientetxt.getText().trim();
            marcarCampo(emailClientetxt,
                !v.isEmpty() && !v.equals("tuincreiblecorreo123@ejemplo.com") && v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        }));

        EdadporFechadeNacimientoClientetxt.getDocument().addDocumentListener(new SimpleDocListener(() -> {
            String v = EdadporFechadeNacimientoClientetxt.getText().trim();
            if (v.isEmpty() || v.equals("DD/MM/AAAA")) { marcarCampo(EdadporFechadeNacimientoClientetxt, false); return; }
            // Extraer solo dígitos
            String digits = v.replaceAll("\\D", "");
            if (digits.length() != 8) { marcarCampo(EdadporFechadeNacimientoClientetxt, false); return; }
            try {
                int day = Integer.parseInt(digits.substring(0, 2));
                int month = Integer.parseInt(digits.substring(2, 4));
                int year = Integer.parseInt(digits.substring(4, 8));
                java.time.LocalDate dob = java.time.LocalDate.of(year, month, day);
                java.time.LocalDate today = java.time.LocalDate.now();
                int edad = java.time.Period.between(dob, today).getYears();
                boolean valido = !dob.isAfter(today) && edad >= 0 && edad < 150;
                marcarCampo(EdadporFechadeNacimientoClientetxt, valido);
                if (valido) EdadporFechadeNacimientoClientetxt.putClientProperty("edad", edad);
            } catch (Exception ex) {
                marcarCampo(EdadporFechadeNacimientoClientetxt, false);
            }
        }));
    }

    private void btnGenerarTicketActionPerformed(java.awt.event.ActionEvent evt) {
        if (!todosValidos()) {
            new ERRORview().setVisible(true);
            return;
        }

        try {
            // Obtener datos del formulario
            String nombre = nombreClientetxt.getText().trim();
            String id = idClientetxt.getText().trim();
            String email = emailClientetxt.getText().trim();
            
            Object origenObj = cmbEstacion_origen.getSelectedItem();
            Object destinoObj = cmbEstacion_destino.getSelectedItem();
            
            if (!(origenObj instanceof Estacion) || !(destinoObj instanceof Estacion)) {
                new ERRORview().setVisible(true);
                return;
            }
            
            Estacion origen = (Estacion) origenObj;
            Estacion destino = (Estacion) destinoObj;
            
            if (origen.getId() == null || origen.getId().isEmpty() ||
                destino.getId() == null || destino.getId().isEmpty()) {
                new ERRORview().setVisible(true);
                return;
            }
            
            // Crear o obtener pasajero
            PasajeroDao pasajeroDao = new PasajeroDao();
            Pasajero pasajero = new Pasajero();
            pasajero.setId(id);
            pasajero.setNombre(nombre);
            pasajero.setEmail(email);
            pasajero.setFechaRegistro(java.time.LocalDateTime.now());
            
            // Guardar pasajero si es nuevo
            try {
                pasajeroDao.registrarPasajero(pasajero);
            } catch (Exception e) {
                System.out.println("Pasajero puede ya existir: " + e.getMessage());
            }
            
            // Crear ticket con precio base (se puede actualizar con lógica de tarifa)
            double precio = 25000; // Precio base por defecto
            TicketService ticketService = new TicketService();
            ticketService.crearTicket(id, pasajero, origen, destino, precio);
            
            // Mostrar confirmación
            new Confirmacion().setVisible(true);

            // Limpiar campos
            javax.swing.JTextField[] campos = { nombreClientetxt, idClientetxt, emailClientetxt, EdadporFechadeNacimientoClientetxt };
            for (javax.swing.JTextField c : campos) {
                c.setText("");
                c.putClientProperty("valido", false);
                c.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 80, 80), 1, true));
            }
            // Restaurar placeholders
            initPlaceholders();
            cargarEstacionesDesdeRutas();
            
        } catch (Exception e) {
            System.err.println("Error al generar ticket: " + e.getMessage());
            e.printStackTrace();
            new ERRORview().setVisible(true);
        }
    }

    // HELPERS
    private void marcarCampo(javax.swing.JTextField campo, boolean valido) {
        java.awt.Color color = valido ? COLOR_OK : COLOR_ERROR;
        campo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(color, 2, true),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        campo.putClientProperty("valido", valido);
    }

    private boolean todosValidos() {
        javax.swing.JTextField[] campos = { nombreClientetxt, idClientetxt, emailClientetxt, EdadporFechadeNacimientoClientetxt };
        for (javax.swing.JTextField c : campos) {
            Object tag = c.getClientProperty("valido");
            if (tag == null || !(Boolean) tag) return false;
        }
        return true;
    }

    private static class SimpleDocListener implements javax.swing.event.DocumentListener {
        private final Runnable accion;
        SimpleDocListener(Runnable accion) { this.accion = accion; }
        @Override public void insertUpdate(javax.swing.event.DocumentEvent e)  { accion.run(); }
        @Override public void removeUpdate(javax.swing.event.DocumentEvent e)  { accion.run(); }
        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { accion.run(); }
    }

    // DocumentFilter para formatear la fecha a dd/MM/aaaa mientras se escribe
    private static class DateDocumentFilter extends javax.swing.text.DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws javax.swing.text.BadLocationException {
            javax.swing.text.Document doc = fb.getDocument();
            String current = doc.getText(0, doc.getLength());
            // eliminar y reformat
            StringBuilder sb = new StringBuilder(current);
            sb.delete(offset, offset + length);
            String digits = sb.toString().replaceAll("\\D", "");
            String formatted = formatDigits(digits);
            fb.remove(0, doc.getLength());
            if (!formatted.isEmpty()) fb.insertString(0, formatted, null);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            javax.swing.text.Document doc = fb.getDocument();
            String current = doc.getText(0, doc.getLength());
            // construir nuevo texto
            StringBuilder sb = new StringBuilder(current);
            // tratar cuando placeholder está presente (letras)
            if (sb.toString().matches(".*[A-Za-z].*")) {
                sb = new StringBuilder();
            }
            if (length > 0) {
                int end = Math.min(offset + length, sb.length());
                if (end > offset) sb.delete(offset, end);
            }
            if (text != null) sb.insert(offset, text);
            String digits = sb.toString().replaceAll("\\D", "");
            if (digits.length() > 8) digits = digits.substring(0, 8);
            String formatted = formatDigits(digits);
            fb.remove(0, doc.getLength());
            if (!formatted.isEmpty()) fb.insertString(0, formatted, attrs);
        }

        private static String formatDigits(String d) {
            StringBuilder out = new StringBuilder();
            int len = d.length();
            for (int i = 0; i < len; i++) {
                out.append(d.charAt(i));
                if (i == 1 && len > 2) out.append('/');
                if (i == 3 && len > 4) out.append('/');
            }
            return out.toString();
        }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nombreClientetxt = new javax.swing.JTextField();
        idClientetxt = new javax.swing.JTextField();
        emailClientetxt = new javax.swing.JTextField();
        EdadporFechadeNacimientoClientetxt = new javax.swing.JTextField();
        btnGenerarTicket = new javax.swing.JButton();
        cmbEstacion_origen = new javax.swing.JComboBox<Estacion>();
        cmbEstacion_destino = new javax.swing.JComboBox<Estacion>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Comprar un Ticket");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Completa tus datos para generar tu ticket");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Nombre Completo:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Identificación (C.C / C.E / T.I):");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Correo Electrónico:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Fecha de Nacimiento:");

        nombreClientetxt.setBackground(new java.awt.Color(51, 51, 51));
        nombreClientetxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nombreClientetxt.setForeground(new java.awt.Color(153, 153, 153));
        nombreClientetxt.setText("Ej. Juan Pérez");
        nombreClientetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreClientetxtActionPerformed(evt);
            }
        });

        idClientetxt.setBackground(new java.awt.Color(51, 51, 51));
        idClientetxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idClientetxt.setForeground(new java.awt.Color(204, 204, 204));
        idClientetxt.setText("Ej. 1234567");

        emailClientetxt.setBackground(new java.awt.Color(51, 51, 51));
        emailClientetxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailClientetxt.setForeground(new java.awt.Color(153, 153, 153));
        emailClientetxt.setText("tuincreiblecorreo123@ejemplo.com");

        EdadporFechadeNacimientoClientetxt.setBackground(new java.awt.Color(51, 51, 51));
        EdadporFechadeNacimientoClientetxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        EdadporFechadeNacimientoClientetxt.setForeground(new java.awt.Color(153, 153, 153));
        EdadporFechadeNacimientoClientetxt.setText("DD/MM/AAAA");

        btnGenerarTicket.setBackground(new java.awt.Color(51, 51, 51));
        btnGenerarTicket.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGenerarTicket.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarTicket.setText("Generar Ticket");
        btnGenerarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarTicketActionPerformed(evt);
            }
        });

        cmbEstacion_origen.setBackground(new java.awt.Color(51, 51, 51));
        cmbEstacion_origen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbEstacion_origen.setForeground(new java.awt.Color(255, 255, 255));
        cmbEstacion_origen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstacion_origenActionPerformed(evt);
            }
        });

        cmbEstacion_destino.setBackground(new java.awt.Color(51, 51, 51));
        cmbEstacion_destino.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("¿Desde que estación Partes?:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("¿A donde quieres ir?:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreClientetxt)
                        .addComponent(idClientetxt)
                        .addComponent(emailClientetxt, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addComponent(EdadporFechadeNacimientoClientetxt))
                    .addComponent(btnGenerarTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEstacion_origen, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEstacion_destino, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreClientetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idClientetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailClientetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EdadporFechadeNacimientoClientetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEstacion_origen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstacion_destino, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnGenerarTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nombreClientetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreClientetxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreClientetxtActionPerformed

    private void cmbEstacion_origenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstacion_origenActionPerformed
        actualizarEstacionesDestino();
    }//GEN-LAST:event_cmbEstacion_origenActionPerformed

    private void cargarEstacionesDesdeRutas() {
        try {
            RutaDao dao = new RutaDao();
            List<Ruta> rutas = dao.obtenerTodasLasRutas();
            Map<String, Estacion> mapa = new LinkedHashMap<>();
            for (Ruta ruta : rutas) {
                if (ruta.getOrigen() != null && ruta.getOrigen().getId() != null) {
                    mapa.put(ruta.getOrigen().getId(), ruta.getOrigen());
                }
                if (ruta.getDestino() != null && ruta.getDestino().getId() != null) {
                    mapa.put(ruta.getDestino().getId(), ruta.getDestino());
                }
            }
            estaciones = new ArrayList<>(mapa.values());
            cmbEstacion_origen.removeAllItems();
            cmbEstacion_origen.setRenderer(new EstacionRenderer());
            cmbEstacion_origen.addItem(new Estacion("", "Seleccionar estación origen"));
            for (Estacion estacion : estaciones) {
                cmbEstacion_origen.addItem(estacion);
            }
            actualizarEstacionesDestino();
        } catch (Exception e) {
            System.out.println("Error al cargar estaciones: " + e.getMessage());
        }
    }

    private void actualizarEstacionesDestino() {
        Estacion seleccionado = null;
        Object item = cmbEstacion_origen.getSelectedItem();
        if (item instanceof Estacion) {
            Estacion estacion = (Estacion) item;
            if (estacion.getId() != null && !estacion.getId().isEmpty()) {
                seleccionado = estacion;
            }
        }
        cmbEstacion_destino.removeAllItems();
        cmbEstacion_destino.setRenderer(new EstacionRenderer());
        cmbEstacion_destino.addItem(new Estacion("", "Seleccionar estación destino"));
        for (Estacion estacion : estaciones) {
            if (seleccionado == null || !estacion.getId().equals(seleccionado.getId())) {
                cmbEstacion_destino.addItem(estacion);
            }
        }
        cmbEstacion_destino.setSelectedIndex(0);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EdadporFechadeNacimientoClientetxt;
    private javax.swing.JButton btnGenerarTicket;
    private javax.swing.JComboBox<Estacion> cmbEstacion_destino;
    private javax.swing.JComboBox<Estacion> cmbEstacion_origen;
    private javax.swing.JTextField emailClientetxt;
    private javax.swing.JTextField idClientetxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nombreClientetxt;
    // End of variables declaration//GEN-END:variables
}
