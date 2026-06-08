package vektra.View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import vektra.Dao.ConductorDao;

/**
 * BorrarConductorPanel — rediseñado con estética Vektra.
 * Swing puro, sin NetBeans Form Editor.
 */
public class BorrarConductorPanel extends JPanel {

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color BG_PAGE    = new Color(248, 250, 255);
    private static final Color TEXT_PRI   = new Color(15,  23,  42);
    private static final Color TEXT_MUT   = new Color(100, 116, 139);
    private static final Color TEXT_LABEL = new Color(71,  85, 105);
    private static final Color BORDER_COL = new Color(226, 232, 240);
    private static final Color FIELD_BG   = new Color(249, 250, 251);
    private static final Color FIELD_PH   = new Color(148, 163, 184);
    private static final Color FIELD_DIS  = new Color(241, 245, 249);
    private static final Color COLOR_OK   = new Color(34,  197,  94);
    private static final Color COLOR_ERR  = new Color(239,  68,  68);
    private static final Color BLUE_LIGHT = new Color(239, 246, 255);
    private static final Color BLUE_ACC   = new Color(37,  99, 235);
    // Danger
    private static final Color WARN_BG    = new Color(255, 241, 242);
    private static final Color WARN_BRD   = new Color(254, 202, 202);
    private static final Color WARN_FG    = new Color(159,  18,  57);
    private static final Color WARN_ICON  = new Color(220,  38,  38);
    private static final Color BTN_BG     = new Color(220,  38,  38);
    private static final Color BTN_HOV    = new Color(185,  28,  28);
    private static final Color BTN_DIS    = new Color(252, 165, 165);

    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font F_SUB     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_SECTION = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font F_LABEL   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font F_FIELD   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_BTN     = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font F_WARN    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font F_ID      = new Font("Segoe UI", Font.BOLD,  20);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JComboBox<String> cmbConductores;
    private JTextField        txtConfirmaId;
    private JTextField        txtIdMostrada;   // read-only, muestra el ID del combo
    private JButton           btnBorrar;

    // ─────────────────────────────────────────────────────────────────────────
    public BorrarConductorPanel() {
        initUI();
        vektra.Util.FontUtil.applyCustomFont(this);
        cargarConductores();
        initPlaceholders();
        initValidaciones();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Layout
    // ─────────────────────────────────────────────────────────────────────────
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_PAGE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_PAGE);
        content.setBorder(new EmptyBorder(28, 36, 32, 36));

        content.add(crearHeader());
        content.add(Box.createVerticalStrut(16));
        content.add(crearBannerPeligro());
        content.add(Box.createVerticalStrut(28));
        content.add(crearSeccion("Selección", crearFilaSeleccion()));
        content.add(Box.createVerticalStrut(24));
        content.add(crearSeccion("Confirmación", crearFilaConfirmacion()));
        content.add(Box.createVerticalStrut(32));
        content.add(crearBotonBorrar());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Borrar Conductor");
        title.setFont(F_TITLE);
        title.setForeground(TEXT_PRI);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Esta acción es permanente y no se puede deshacer");
        sub.setFont(F_SUB);
        sub.setForeground(TEXT_MUT);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(title);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        return p;
    }

    // ── Banner de peligro ─────────────────────────────────────────────────────
    private JPanel crearBannerPeligro() {
        JPanel p = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WARN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // borde izquierdo grueso rojo
                g2.setColor(WARN_ICON);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                // borde exterior
                g2.setColor(WARN_BRD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setBorder(new EmptyBorder(12, 16, 12, 16));

        // Ícono ⚠ pintado
        JLabel icon = new JLabel("⚠") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WARN_ICON);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                g2.drawString("*", 0, 20);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(26, 26); }
        };
        icon.setOpaque(false);

        JLabel msg = new JLabel(
            "<html>Borrar un conductor elimina también sus asignaciones de vehículos.<br>" +
            "Asegúrate de reasignar sus rutas antes de continuar.</html>");
        msg.setFont(F_WARN);
        msg.setForeground(WARN_FG);

        p.add(icon, BorderLayout.WEST);
        p.add(msg,  BorderLayout.CENTER);
        return p;
    }

    // ── Sección con separador ─────────────────────────────────────────────────
    private JPanel crearSeccion(String titulo, JPanel contenido) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerRow = new JPanel(new BorderLayout(10, 0));
        headerRow.setOpaque(false);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(F_SECTION);
        lbl.setForeground(TEXT_LABEL);

        JSeparator sep = new JSeparator() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BORDER_COL);
                g.fillRect(0, getHeight()/2, getWidth(), 1);
            }
        };
        headerRow.add(lbl, BorderLayout.WEST);
        headerRow.add(sep, BorderLayout.CENTER);

        contenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(headerRow);
        p.add(Box.createVerticalStrut(14));
        p.add(contenido);
        return p;
    }

    // ── Fila Selección: Combo + Card ID ───────────────────────────────────────
    private JPanel crearFilaSeleccion() {
        JPanel row = new JPanel(new GridLayout(1, 2, 20, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Combo conductor
        cmbConductores = new JComboBox<>();
        cmbConductores.setFont(F_FIELD);
        cmbConductores.setBackground(FIELD_BG);
        cmbConductores.setForeground(TEXT_PRI);
        cmbConductores.setBorder(new RoundedBorder(8, BORDER_COL, 1));
        cmbConductores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cmbConductores.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> list, Object v, int i, boolean sel, boolean foc) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list,v,i,sel,foc);
                l.setBorder(new EmptyBorder(5,10,5,10));
                l.setFont(F_FIELD);
                if (sel) { l.setBackground(BLUE_LIGHT); l.setForeground(BLUE_ACC); }
                else     { l.setBackground(Color.WHITE); l.setForeground(TEXT_PRI); }
                return l;
            }
        });

        // Card que muestra el ID seleccionado
        txtIdMostrada = new JTextField("—");
        txtIdMostrada.setEditable(false);
        txtIdMostrada.setFont(F_ID);
        txtIdMostrada.setForeground(new Color(148, 163, 184));
        txtIdMostrada.setBackground(FIELD_DIS);
        txtIdMostrada.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COL, 1),
            new EmptyBorder(4, 14, 4, 14)
        ));
        txtIdMostrada.setHorizontalAlignment(JTextField.CENTER);

        row.add(crearCampoCombo("Seleccionar conductor a borrar:", cmbConductores));
        row.add(crearCampoTexto("ID del conductor seleccionado:", txtIdMostrada));
        return row;
    }

    // ── Fila Confirmación: input + botón ─────────────────────────────────────
    private JPanel crearFilaConfirmacion() {
        JPanel row = new JPanel(new GridLayout(1, 2, 20, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtConfirmaId = new JTextField();
        txtConfirmaId.setFont(F_FIELD);
        txtConfirmaId.setForeground(TEXT_PRI);
        txtConfirmaId.setBackground(FIELD_BG);
        txtConfirmaId.setCaretColor(TEXT_PRI);
        txtConfirmaId.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COL, 1),
            new EmptyBorder(7, 12, 7, 12)
        ));

        row.add(crearCampoTexto(
            "Confirma escribiendo el ID del conductor:", txtConfirmaId));
        row.add(new JPanel() {{ setOpaque(false); }}); // espacio vacío derecho
        return row;
    }

    // ── Botón Borrar ─────────────────────────────────────────────────────────
    private JPanel crearBotonBorrar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnBorrar = new JButton("Borrar Conductor") {
            private boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                @Override public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = !isEnabled() ? BTN_DIS : (hov ? BTN_HOV : BTN_BG);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };
        btnBorrar.setFont(F_BTN);
        btnBorrar.setForeground(Color.WHITE);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setBorderPainted(false);
        btnBorrar.setFocusPainted(false);
        btnBorrar.setEnabled(false);   // deshabilitado hasta validar
        btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBorrar.setBorder(new EmptyBorder(11, 28, 11, 28));
        btnBorrar.setPreferredSize(new Dimension(220, 44));
        btnBorrar.setOpaque(false);

        try {
            java.net.URL url = getClass().getResource(
                "/vektra/View/Imagenes/ICONS CANVA/WARNING(1)(1).png");
            if (url != null) {
                Image img = new ImageIcon(url).getImage()
                        .getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                btnBorrar.setIcon(new ImageIcon(img));
                btnBorrar.setIconTextGap(8);
            }
        } catch (Exception ignored) {}

        btnBorrar.addActionListener(e -> btnBorrarActionPerformed());
        p.add(btnBorrar);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers de campo
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearCampoTexto(String etiqueta, JTextField campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(TEXT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(campo);
        return p;
    }

    private JPanel crearCampoCombo(String etiqueta, JComboBox<?> combo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(F_LABEL);
        lbl.setForeground(TEXT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(5));
        p.add(combo);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lógica preservada del original
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarConductores() {
        try {
            ConductorDao dao = new ConductorDao();
            java.util.List<vektra.Model.Conductor> lista = dao.obtenerTodos();
            cmbConductores.removeAllItems();
            cmbConductores.addItem("Seleccionar conductor...");
            for (vektra.Model.Conductor c : lista) {
                cmbConductores.addItem(c.getId() + " - " + c.getNombre() + " " + c.getApellido());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar conductores: " + e.getMessage());
        }
    }

    private void initPlaceholders() {
        txtConfirmaId.setText("Digite la ID del conductor...");
        txtConfirmaId.setForeground(FIELD_PH);
        txtConfirmaId.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (txtConfirmaId.getText().equals("Digite la ID del conductor...")) {
                    txtConfirmaId.setText("");
                    txtConfirmaId.setForeground(TEXT_PRI);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (txtConfirmaId.getText().trim().isEmpty()) {
                    txtConfirmaId.setText("Digite la ID del conductor...");
                    txtConfirmaId.setForeground(FIELD_PH);
                    marcarCampo(txtConfirmaId, false);
                }
            }
        });
    }

    private void initValidaciones() {
        cmbConductores.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) return;
            actualizarIdMostrada();
            validarCampos();
        });
        txtConfirmaId.getDocument().addDocumentListener(
            new SimpleDocListener(this::validarCampos));
        actualizarIdMostrada();
        validarCampos();
    }

    private void actualizarIdMostrada() {
        String sel = cmbConductores.getSelectedItem() == null
                ? "" : cmbConductores.getSelectedItem().toString();
        if (sel.isEmpty() || sel.equals("Seleccionar conductor...")) {
            txtIdMostrada.setText("—");
            txtIdMostrada.setForeground(new Color(148, 163, 184));
            marcarCampo(cmbConductores, false);
        } else {
            String id = sel.split(" - ")[0];
            txtIdMostrada.setText(id);
            txtIdMostrada.setForeground(WARN_ICON);
            marcarCampo(cmbConductores, true);
        }
    }

    private void validarCampos() {
        boolean comboOk = cmbConductores.getSelectedItem() != null
                && !cmbConductores.getSelectedItem().toString()
                        .equals("Seleccionar conductor...");
        boolean textoOk = !txtConfirmaId.getText().trim().isEmpty()
                && !txtConfirmaId.getText().equals("Digite la ID del conductor...");
        boolean coincide = textoOk && comboOk
                && txtConfirmaId.getText().trim()
                        .equals(txtIdMostrada.getText().trim());

        marcarCampo(cmbConductores, comboOk);
        marcarCampo(txtConfirmaId,  coincide);

        boolean todo = comboOk && coincide;
        btnBorrar.setEnabled(todo);
        btnBorrar.repaint();
    }

    private void btnBorrarActionPerformed() {
        validarCampos();
        if (!todosValidos()) { new ERRORview().setVisible(true); return; }

        String idConductor = txtConfirmaId.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas borrar al conductor con ID: "
                + idConductor + "?\nEsta acción no se puede deshacer.",
            "Confirmar Borrado",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            ConductorDao dao = new ConductorDao();
            boolean borrado = dao.eliminarConductor(idConductor);
            if (borrado) {
                new Confirmacion().setVisible(true);
                cmbConductores.setSelectedIndex(0);
                txtConfirmaId.setText("Digite la ID del conductor...");
                txtConfirmaId.setForeground(FIELD_PH);
                marcarCampo(txtConfirmaId, false);
                validarCampos();
            } else {
                new ERRORview().setVisible(true);
            }
        }
    }

    private void marcarCampo(JComponent campo, boolean valido) {
        Color c = valido ? COLOR_OK : COLOR_ERR;
        if (campo instanceof JTextField tf) {
            tf.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, c, 2),
                new EmptyBorder(7, 12, 7, 12)
            ));
        } else if (campo instanceof JComboBox) {
            campo.setBorder(new RoundedBorder(8, c, 2));
        }
        campo.putClientProperty("valido", valido);
    }

    private boolean todosValidos() {
        for (JComponent c : new JComponent[]{cmbConductores, txtConfirmaId}) {
            Object v = c.getClientProperty("valido");
            if (!(v instanceof Boolean b) || !b) return false;
        }
        return true;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Borde redondeado reutilizable
    // ─────────────────────────────────────────────────────────────────────────
    private static class RoundedBorder extends AbstractBorder {
        private final int radius; private final Color color; private final float stroke;
        RoundedBorder(int r, Color c, float s) { radius=r; color=c; stroke=s; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(stroke));
            g2.drawRoundRect(x, y, w-1, h-1, radius, radius);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) {
            int i = (int)stroke+1;
            return new Insets(i,i,i,i);
        }
    }

    private static class SimpleDocListener implements DocumentListener {
        private final Runnable accion;
        SimpleDocListener(Runnable a) { accion = a; }
        @Override public void insertUpdate (DocumentEvent e) { accion.run(); }
        @Override public void removeUpdate (DocumentEvent e) { accion.run(); }
        @Override public void changedUpdate(DocumentEvent e) { accion.run(); }
    }
}