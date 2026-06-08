package vektra.View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import vektra.Dao.TicketDao;
import vektra.Model.Ticket;

/**
 * TicketsActivosPanel — muestra los tickets activos como cards visuales
 * tipo boleto de tren/metro real. Sin NetBeans Form Editor.
 */
public class TicketsActivosPanel extends JPanel {

    // ── Paleta de Vektra ──────────────────────────────────────────────────────
    private static final Color BG_PAGE      = new Color(248, 250, 255);
    private static final Color BG_CARD      = Color.WHITE;
    private static final Color NAVY         = new Color(15,  23,  42);
    private static final Color NAVY_LIGHT   = new Color(30,  41,  59);
    private static final Color BLUE_ACCENT  = new Color(37, 99, 235);
    private static final Color TEXT_PRIMARY = new Color(15,  23,  42);
    private static final Color TEXT_MUTED   = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);
    private static final Color GREEN_BG     = new Color(220, 252, 231);
    private static final Color GREEN_FG     = new Color(22,  101,  52);
    private static final Color DASHED_COLOR = new Color(203, 213, 225);

    // ── Líneas del metro (color por nombre) ──────────────────────────────────
    private static Color lineColor(String linea) {
        if (linea == null) return BLUE_ACCENT;
        String l = linea.toLowerCase();
        if (l.contains("roja")    || l.contains("red"))    return new Color(220, 38,  38);
        if (l.contains("verde")   || l.contains("green"))  return new Color(34,  197, 94);
        if (l.contains("azul")    || l.contains("blue"))   return new Color(37,  99, 235);
        if (l.contains("amarilla")|| l.contains("yellow")) return new Color(234, 179,  8);
        return BLUE_ACCENT;
    }

    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,   22);
    private static final Font FONT_SUB     = new Font("Segoe UI", Font.PLAIN,  13);
    private static final Font FONT_LABEL   = new Font("Segoe UI", Font.PLAIN,  10);
    private static final Font FONT_VALUE   = new Font("Segoe UI", Font.BOLD,   13);
    private static final Font FONT_TICKET  = new Font("Segoe UI", Font.BOLD,   11);
    private static final Font FONT_STATION = new Font("Segoe UI", Font.BOLD,   15);
    private static final Font FONT_EMPTY   = new Font("Segoe UI", Font.PLAIN,  14);
    private static final Font FONT_EMPTY_H = new Font("Segoe UI", Font.BOLD,   16);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JPanel ticketsContainer;
    private JScrollPane scrollPane;

    public TicketsActivosPanel() {
        initUI();
        vektra.Util.FontUtil.applyCustomFont(this);
        cargarTicketsActivos();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Construcción del layout principal
    // ─────────────────────────────────────────────────────────────────────────
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_PAGE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PAGE);
        header.setBorder(new EmptyBorder(24, 32, 12, 32));

        JLabel title = new JLabel("Tikects Activos");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_PRIMARY);

        JLabel sub = new JLabel("Tickets vigentes");
        sub.setFont(FONT_SUB);
        sub.setForeground(TEXT_MUTED);

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setBackground(BG_PAGE);
        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(sub);
        header.add(titleBlock, BorderLayout.CENTER);

        // Grid de cards — 2 columnas
        ticketsContainer = new JPanel(new java.awt.GridLayout(0, 2, 14, 14));
        ticketsContainer.setBackground(BG_PAGE);

        // Wrapper NORTH: impide que GridLayout estire las cards verticalmente
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(BG_PAGE);
        gridWrapper.setBorder(new EmptyBorder(0, 32, 24, 32));
        gridWrapper.add(ticketsContainer, BorderLayout.NORTH);

        // ScrollPane sobre el wrapper
        scrollPane = new JScrollPane(gridWrapper);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Carga de datos
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarTicketsActivos() {
        ticketsContainer.removeAll();

        try {
            TicketDao dao = new TicketDao();
            List<Ticket> tickets = dao.obtenerTicketsActivos();

            if (tickets.isEmpty()) {
                ticketsContainer.add(crearEstadoVacio());
            } else {
                for (Ticket t : tickets) {
                    ticketsContainer.add(crearCardTicket(t));
                }
            }
        } catch (Exception e) {
            ticketsContainer.add(crearEstadoError(e.getMessage()));
        }

        ticketsContainer.revalidate();
        ticketsContainer.repaint();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Card de ticket estilo boleto físico
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearCardTicket(Ticket t) {
        // Extraer datos del ticket
        String nombrePasajero = (t.getPasajero() != null) ? t.getPasajero().getNombre() : "N/A";
        String idPasajero     = (t.getPasajero() != null) ? String.valueOf(t.getPasajero().getId()) : "N/A";
        String origen         = (t.getEstacionOrigen()  != null) ? t.getEstacionOrigen().getNombre()  : "N/A";
        String destino        = (t.getEstacionDestino() != null) ? t.getEstacionDestino().getNombre() : "N/A";
        String ticketId       = (t.getCodigo() != null) ? t.getCodigo() : String.valueOf(t.getId());

        // Intentar obtener nombre de línea (si el modelo lo expone)
        String linea = obtenerLinea(t);
        Color  lc    = lineColor(linea);

        // ── Card exterior ─────────────────────────────────────────────────
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra suave
                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, 16, 16);
                // Fondo blanco
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 16, 16);
                // Borde
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setPreferredSize(new Dimension(0, 120));
        card.setBorder(new EmptyBorder(0, 0, 4, 4));

        // ── Franja izquierda de color de línea ────────────────────────────
        JPanel stripe = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(lc);
                // Solo esquinas izquierdas redondeadas
                g2.fillRoundRect(0, 0, getWidth() + 10, getHeight(), 16, 16);
                g2.dispose();
            }
        };
        stripe.setOpaque(false);
        stripe.setPreferredSize(new Dimension(10, 0));
        card.add(stripe, BorderLayout.WEST);

        // ── Cuerpo izquierdo (info principal) ─────────────────────────────
        JPanel left = new JPanel(new GridBagLayout());
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(10, 14, 10, 10));

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(0, 0, 4, 16);

        // Fila 0: PASAJERO
        g.gridx = 0; g.gridy = 0;
        left.add(crearLabelGroup("PASAJERO", nombrePasajero), g);

        // Fila 0: ID
        g.gridx = 1;
        left.add(crearLabelGroup("ID", idPasajero), g);

        // Fila 0: TICKET ID
        g.gridx = 2;
        left.add(crearLabelGroup("TICKET ID", ticketId), g);

        // Fila 1: ORIGEN → DESTINO
        g.gridx = 0; g.gridy = 1; g.gridwidth = 3; g.insets = new Insets(0, 0, 0, 0);
        left.add(crearRutaPanel(origen, destino, lc), g);

        card.add(left, BorderLayout.CENTER);

        // ── Separador dentado (estilo boleto físico) ───────────────────────
        card.add(crearSeparadorDentado(), BorderLayout.LINE_END);

        return card;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Grupo etiqueta + valor
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearLabelGroup(String label, String value) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);

        JLabel val = new JLabel(value);
        val.setFont(FONT_VALUE);
        val.setForeground(TEXT_PRIMARY);

        p.add(lbl);
        p.add(Box.createVerticalStrut(2));
        p.add(val);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Panel ORIGEN → DESTINO con flecha
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearRutaPanel(String origen, String destino, Color lc) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);

        JLabel lblOrigen = new JLabel(origen);
        lblOrigen.setFont(FONT_STATION);
        lblOrigen.setForeground(TEXT_PRIMARY);

        // Icono flecha con punto de origen/destino
        JPanel arrow = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth()  / 2;
                int cy = getHeight() / 2;
                // Línea
                g2.setColor(DASHED_COLOR);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND, 0, new float[]{4, 3}, 0));
                g2.drawLine(8, cy, getWidth() - 8, cy);
                // Punto origen
                g2.setColor(lc);
                g2.fillOval(4, cy - 4, 8, 8);
                // Punto destino
                g2.fillOval(getWidth() - 12, cy - 4, 8, 8);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(60, 22); }
            @Override public Dimension getMinimumSize()   { return getPreferredSize(); }
        };
        arrow.setOpaque(false);

        JLabel lblDestino = new JLabel(destino);
        lblDestino.setFont(FONT_STATION);
        lblDestino.setForeground(TEXT_PRIMARY);

        p.add(lblOrigen);
        p.add(Box.createHorizontalStrut(10));
        p.add(arrow);
        p.add(Box.createHorizontalStrut(10));
        p.add(lblDestino);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Separador dentado derecho (talón del boleto) con badge ACTIVO
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearSeparadorDentado() {
        JPanel talón = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                // Fondo gris muy suave del talón
                g2.setColor(new Color(248, 250, 252));
                g2.fillRoundRect(0, 0, w - 2, h - 2, 0, 0);

                // Línea discontinua vertical
                g2.setColor(DASHED_COLOR);
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND, 0, new float[]{4, 4}, 0));
                g2.drawLine(0, 10, 0, h - 10);

                // Semicírculos dentados (arriba y abajo)
                g2.setColor(BG_PAGE);
                g2.setStroke(new BasicStroke(1f));
                g2.fillOval(-8, -8, 16, 16);
                g2.fillOval(-8, h - 8, 16, 16);
                g2.setColor(BORDER_COLOR);
                g2.drawArc(-8, -8, 16, 16, -90, 180);
                g2.drawArc(-8, h - 8, 16, 16, 90, 180);

                g2.dispose();
            }
        };
        talón.setOpaque(false);
        talón.setPreferredSize(new Dimension(80, 0));
        talón.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Badge "ACTIVO"
        JLabel badge = new JLabel("● ACTIVO") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREEN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(GREEN_FG);
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(3, 7, 3, 7));

        talón.add(badge);
        return talón;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Estado vacío
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearEstadoVacio() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);

        JLabel icon = new JLabel("◻");
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        icon.setForeground(new Color(203, 213, 225));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("No hay tickets activos disponibles.");
        titulo.setFont(FONT_EMPTY_H);
        titulo.setForeground(TEXT_MUTED);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("Cuando compres un ticket, aparecerá aquí.");
        hint.setFont(FONT_EMPTY);
        hint.setForeground(new Color(148, 163, 184));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        inner.add(Box.createVerticalStrut(20));
        inner.add(icon);
        inner.add(Box.createVerticalStrut(12));
        inner.add(titulo);
        inner.add(Box.createVerticalStrut(6));
        inner.add(hint);
        inner.add(Box.createVerticalStrut(20));

        p.add(inner);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Estado de error
    // ─────────────────────────────────────────────────────────────────────────
    private JPanel crearEstadoError(String msg) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel lbl = new JLabel("Error al cargar tickets: " + msg);
        lbl.setFont(FONT_EMPTY);
        lbl.setForeground(new Color(185, 28, 28));
        p.add(lbl);
        return p;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Obtener nombre de línea del ticket (adaptable a tu modelo)
    // ─────────────────────────────────────────────────────────────────────────
    private String obtenerLinea(Ticket t) {
        // Tu modelo Estacion no expone getLinea() — retorna vacío
        // El color de la card quedará en azul por defecto (BLUE_ACCENT)
        // Si en el futuro agregas getLinea() a Estacion, activa el bloque de abajo:
        //
        // try {
        //     if (t.getEstacionOrigen() != null && t.getEstacionOrigen().getLinea() != null)
        //         return t.getEstacionOrigen().getLinea().getNombre();
        // } catch (Exception ignored) {}
        return "";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Método público para refrescar (útil si se llama desde otro panel)
    // ─────────────────────────────────────────────────────────────────────────
    public void refrescar() {
        cargarTicketsActivos();
    }
}