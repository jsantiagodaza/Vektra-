package vektra.View;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import vektra.Dao.ConductorDao;
import vektra.Model.Conductor;

/**
 * ConductoresPanel — tabla estilizada con colores Vektra, filas alternas,
 * header custom y botones rediseñados. Swing puro, sin NetBeans Form Editor.
 */
public class ConductoresPanel extends JPanel {

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color background_Pagina      = new Color(248, 250, 255);
    private static final Color NAVY         = new Color(15,  23,  42);
    private static final Color AzulAcentuado  = new Color(37,  99, 235);
    private static final Color AzulClaro   = new Color(219, 234, 254);
    private static final Color textoPrincipal = new Color(15,  23,  42);
    private static final Color TEXT_MUTED   = new Color(100, 116, 139);
    private static final Color colorBorde = new Color(226, 232, 240);
    private static final Color filaAlt      = new Color(241, 245, 255);   // azul muy suave
    private static final Color FilaHover    = new Color(219, 234, 254);
    private static final Color filaSelect   = new Color(191, 219, 254);
    private static final Color HDR_BG       = NAVY;
    private static final Color HDR_FG       = Color.WHITE;
    private static final Color BTN_ADD_BG   = AzulAcentuado;
    private static final Color BTN_ADD_FG   = Color.WHITE;
    private static final Color BTN_DEL_BG   = new Color(254, 242, 242);
    private static final Color BTN_DEL_FG   = new Color(185,  28,  28);
    private static final Color BTN_DEL_BRD  = new Color(254, 202, 202);

    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_SUB    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_HDR    = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_CELL   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_BADGE  = new Font("Segoe UI", Font.BOLD,  11);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JTable           tabla;
    private DefaultTableModel modelo;
    private JScrollPane       scroll;
    private MainFrameView     mainFrame;
    private JLabel            lblConteo;

    // ── Columnas ──────────────────────────────────────────────────────────────
    private static final String[] COLS = {
        "ID", "Nombre", "Apellido", "Cédula", "Teléfono", "Licencia", "Ruta asignada"
    };
    private static final int[] COL_W = { 50, 140, 140, 110, 130, 120, 220 };

    // ─────────────────────────────────────────────────────────────────────────
    public ConductoresPanel(MainFrameView frame) {
        this.mainFrame = frame;
        inicializarUI();
        vektra.Util.FontUtil.applyCustomFont(this);
        cargarConductores();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Layout principal
    // ─────────────────────────────────────────────────────────────────────────
    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBackground(background_Pagina);

        add(crearHeader(),  BorderLayout.NORTH);
        add(crearCuerpo(),  BorderLayout.CENTER);
        add(crearFooter(),  BorderLayout.SOUTH);
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(background_Pagina);
        p.setBorder(new EmptyBorder(24, 32, 12, 32));

        // Título + subtítulo
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(background_Pagina);

        JLabel title = new JLabel("Conductores");
        title.setFont(FONT_TITLE);
        title.setForeground(textoPrincipal);

        JLabel sub = new JLabel("Gestión del personal de conducción");
        sub.setFont(FONT_SUB);
        sub.setForeground(TEXT_MUTED);

        left.add(title);
        left.add(Box.createVerticalStrut(3));
        left.add(sub);

        // Badge contador (se actualiza en cargarConductores)
        lblConteo = new JLabel("0 registros") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AzulClaro);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblConteo.setFont(FONT_BADGE);
        lblConteo.setForeground(AzulAcentuado);
        lblConteo.setBorder(new EmptyBorder(4, 12, 4, 12));
        lblConteo.setOpaque(false);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        right.setBackground(background_Pagina);
        right.add(lblConteo);

        p.add(left,  BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);
        return p;
    }

    // ── Cuerpo (tabla) ────────────────────────────────────────────────────────
    private JPanel crearCuerpo() {
        modelo = new DefaultTableModel(COLS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modelo) {
            // Filas alternas + hover
            private int hoverRow = -1;
            {
                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override public void mouseMoved(MouseEvent e) {
                        int r = rowAtPoint(e.getPoint());
                        if (r != hoverRow) { hoverRow = r; repaint(); }
                    }
                });
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseExited(MouseEvent e) {
                        hoverRow = -1; repaint();
                    }
                });
            }
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(filaSelect);
                } else if (row == hoverRow) {
                    c.setBackground(FilaHover);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : filaAlt);
                }
                c.setForeground(textoPrincipal);
                return c;
            }
        };

        // Estilo general de la tabla
        tabla.setFont(FONT_CELL);
        tabla.setRowHeight(36);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(colorBorde);
        tabla.setSelectionBackground(filaSelect);
        tabla.setSelectionForeground(textoPrincipal);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBorder(null);

        // Header personalizado
        JTableHeader header = tabla.getTableHeader();
        header.setFont(FONT_HDR);
        header.setBackground(HDR_BG);
        header.setForeground(HDR_FG);
        header.setPreferredSize(new Dimension(0, 42));
        header.setReorderingAllowed(false);
        header.setBorder(null);

        // Renderer del header
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                lbl.setBackground(HDR_BG);
                lbl.setForeground(HDR_FG);
                lbl.setFont(FONT_HDR);
                lbl.setBorder(new EmptyBorder(0, 10, 0, 10));
                lbl.setHorizontalAlignment(c == 0 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return lbl;
            }
        });

        // Renderer de celdas con padding
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setHorizontalAlignment(c == 0 ? SwingConstants.CENTER : SwingConstants.LEFT);
                setFont(FONT_CELL);
                return this;
            }
        };
        for (int i = 0; i < COLS.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            tabla.getColumnModel().getColumn(i).setPreferredWidth(COL_W[i]);
        }

        // Scroll
        scroll = new JScrollPane(tabla);
        scroll.setBorder(crearBordeRedondeado());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(background_Pagina);
        wrapper.setBorder(new EmptyBorder(0, 32, 16, 32));
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    // ── Footer (botones) ──────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        p.setBackground(background_Pagina);
        p.setBorder(new EmptyBorder(0, 32, 24, 32));

        JButton btnAdd = crearBoton(
            "Añadir Conductor",
            "/vektra/View/Imagenes/ICONS CANVA/ADD.png",
            BTN_ADD_BG, BTN_ADD_FG, null
        );
        btnAdd.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarPanel(new AnadirConductorPanel());
        });

        JButton btnDel = crearBoton(
            "Borrar Conductor",
            "/vektra/View/Imagenes/ICONS CANVA/DELETE.png",
            BTN_DEL_BG, BTN_DEL_FG, BTN_DEL_BRD
        );
        btnDel.addActionListener(e -> {
            if (mainFrame != null) mainFrame.mostrarPanel(new BorrarConductorPanel());
        });

        p.add(btnAdd);
        p.add(btnDel);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Carga de datos
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarConductores() {
        try {
            ConductorDao dao = new ConductorDao();
            List<Conductor> lista = dao.obtenerTodos();
            modelo.setRowCount(0);
            for (Conductor c : lista) {
                modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getCedula(),
                    c.getTelefono(),
                    c.getLicencia(),
                    c.getRutaAsignada()
                });
            }
            lblConteo.setText(lista.size() + " registros");
        } catch (Exception e) {
            System.out.println("Error cargando conductores: " + e.getMessage());
        }
    }

    public void refrescar() { cargarConductores(); }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers visuales
    // ─────────────────────────────────────────────────────────────────────────

    /** Botón con esquinas redondeadas y hover suave */
    private JButton crearBoton(String texto, String iconPath,
                                Color bg, Color fg, Color borde) {
        JButton btn = new JButton(texto) {
            private boolean hovering = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovering = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovering = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color bgActual = hovering
                    ? (borde == null
                        ? BTN_ADD_BG.darker()
                        : new Color(254, 226, 226))
                    : bg;
                g2.setColor(bgActual);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                if (borde != null) {
                    g2.setColor(borde);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() { return false; }
        };

        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setPreferredSize(new Dimension(210, 46));
        btn.setOpaque(false);

        // Ícono
        try {
            java.net.URL url = getClass().getResource(iconPath);
            if (url != null) {
                ImageIcon raw = new ImageIcon(url);
                Image scaled = raw.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaled));
                btn.setIconTextGap(8);
            }
        } catch (Exception ignored) {}

        return btn;
    }

    /** Borde redondeado para el scroll de la tabla */
    private Border crearBordeRedondeado() {
        return new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorBorde);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(x, y, w - 1, h - 1, 10, 10);
                g2.dispose();
            }
            @Override
            public Insets getBorderInsets(Component c) { return new Insets(1, 1, 1, 1); }
        };
    }
}