package edu.thepower.desarrollointerfaces.formulario;

/**
 *  Crear un formulario con Swing que pida Nombre, Apellidos, Dirección y Teléfono
 *  - Todos los campos son obligatorios
 *  - Botones: Aceptar y Limpiar
 *  - Validación con mensajes de error y marcado visual
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/** Formulario con estilo Neomórfico pastel */

public class NeumorphicForm extends JFrame {
    /**
     * // Colores base (Azul)
     *     private static final Color BASE = new Color(0xEAF0F5);        // fondo pastel
     *     private static final Color ACCENT = new Color(0xBBD4E7);      // acento suave
     *     private static final Color TEXT_DARK = new Color(0x364355);   // texto
     *     private static final Color SHADOW_DARK = new Color(0xB4C3D0); // sombra
     *     private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);// luz
     */
    /**
     *     // Colores base (rosa pastel)
     *  private static final Color BASE = new Color(0xFAE8E8);        // Fondo principal (rosa empolvado claro)
     *     private static final Color ACCENT = new Color(0xF3C6C6);      // Acento suave para botones o sombras interiores
     *     private static final Color TEXT_DARK = new Color(0x5A4A4A);   // Texto gris rosado oscuro (contraste)
     *     private static final Color SHADOW_DARK = new Color(0xE2BABA); // Sombra inferior (tono rosado más profundo)
     *     private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);// Luz superior (blanco suave)
     */


    // Colores base (rosa melocotón pastel)
    private static final Color BASE = new Color(0xFFE9E3);
    private static final Color ACCENT = new Color(0xF9BEBE);
    private static final Color TEXT_DARK = new Color(0x5A4A4A);
    private static final Color SHADOW_DARK = new Color(0xE0B3B3);
    private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);

    // Campos
    private RoundedField txtNombre;
    private RoundedField txtApellidos;
    private RoundedField txtDireccion;
    private RoundedField txtTelefono;
    private RoundedArea txtNotas; // Nuevo campo de texto multilínea

    // Botones
    private NeoButton btnAceptar;
    private NeoButton btnLimpiar;

    // Borde por defecto para restaurar validación
    private final javax.swing.border.Border defaultFieldBorder = new EmptyBorder(8, 12, 8, 12);

    public NeumorphicForm() {
        setTitle("Formulario de Registro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(540, 500);
        setLocationRelativeTo(null);

        // Look & Feel y fuente
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));

        // Panel raíz con fondo pastel
        NeoPanel root = new NeoPanel(BASE, 18);
        root.setLayout(new BorderLayout(0, 16));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));
        setContentPane(root);

        // Título
        JLabel title = new JLabel("FORMULARIO", SwingConstants.CENTER);
        title.setForeground(TEXT_DARK);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        root.add(title, BorderLayout.NORTH);

        // Panel del formulario
        NeoPanel form = new NeoPanel(ACCENT, 22);
        form.setLayout(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 22, 20, 22));
        root.add(form, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        // Etiquetas y campos
        int row = 0;
        txtNombre = addRow(form, c, row++, "Nombre :");
        txtApellidos = addRow(form, c, row++, "Apellidos :");
        txtDireccion = addRow(form, c, row++, "Dirección :");
        txtTelefono = addRow(form, c, row++, "Teléfono :");

        //  Campo de texto multilínea para notas
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        JLabel lblNotas = new JLabel("Notas :");
        lblNotas.setForeground(TEXT_DARK);
        form.add(lblNotas, c);

        c.gridx = 1;
        c.gridy = row;
        c.weightx = 1;
        txtNotas = new RoundedArea(5, BASE);
        txtNotas.setToolTipText("Campo opcional para observaciones o comentarios.");
        txtNotas.setPreferredSize(new Dimension(200, 70));
        form.add(txtNotas, c);

        // Botones
        NeoPanel actions = new NeoPanel(ACCENT, 18);
        actions.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnLimpiar = new NeoButton("Limpiar");
        btnAceptar = new NeoButton("Aceptar");
        actions.add(btnLimpiar);
        actions.add(btnAceptar);
        root.add(actions, BorderLayout.SOUTH);

        // Enter => Aceptar
        getRootPane().setDefaultButton(btnAceptar);

        // Acciones
        btnAceptar.addActionListener(this::onAceptar);
        btnLimpiar.addActionListener(e -> limpiar());

        // Colores de texto
        for (RoundedField f : new RoundedField[]{txtNombre, txtApellidos, txtDireccion, txtTelefono}) {
            f.setForeground(TEXT_DARK);
            f.setBorder(defaultFieldBorder);
            f.setBackground(BASE);
        }
        txtNotas.setForeground(TEXT_DARK);
    }

    private RoundedField addRow(JPanel form, GridBagConstraints c, int row, String label) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        JLabel l = new JLabel(label);
        l.setForeground(TEXT_DARK);
        form.add(l, c);

        c.gridx = 1;
        c.gridy = row;
        c.weightx = 1;
        RoundedField field = new RoundedField(14, BASE);
        form.add(field, c);
        return field;
    }

    private void onAceptar(ActionEvent e) {
        resetBorders();

        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String notas = txtNotas.getText().trim();

        StringBuilder errs = new StringBuilder();

        if (nombre.isEmpty()) {
            errs.append("• El campo Nombre es obligatorio.\n");
            markError(txtNombre);
        }
        if (apellidos.isEmpty()) {
            errs.append("• El campo Apellidos es obligatorio.\n");
            markError(txtApellidos);
        }
        if (direccion.isEmpty()) {
            errs.append("• El campo Dirección es obligatorio.\n");
            markError(txtDireccion);
        }
        if (telefono.isEmpty()) {
            errs.append("• El campo Teléfono es obligatorio.\n");
            markError(txtTelefono);
        } else if (!telefono.matches("^\\+?\\d{7,15}$")) {
            errs.append("• Teléfono no válido (7–15 dígitos, opcional '+').\n");
            markError(txtTelefono);
        }

        if (errs.length() > 0) {
            JOptionPane.showMessageDialog(this, errs.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Formulario registrado correctamente:\n" +
                        "Nombre: " + nombre + "\n" +
                        "Apellidos: " + apellidos + "\n" +
                        "Dirección: " + direccion + "\n" +
                        "Teléfono: " + telefono + "\n" +
                        "Notas: " + (notas.isEmpty() ? "(Sin notas)" : notas),
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );

        limpiar();
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNotas.setText("");
        resetBorders();
        txtNombre.requestFocus();
    }

    private void markError(JTextField f) {
        f.setBorder(new LineBorder(new Color(0xE57373), 1));
    }

    private void resetBorders() {
        txtNombre.setBorder(defaultFieldBorder);
        txtApellidos.setBorder(defaultFieldBorder);
        txtDireccion.setBorder(defaultFieldBorder);
        txtTelefono.setBorder(defaultFieldBorder);
    }

    // ---------------------- Componentes personalizados ----------------------

    static class NeoPanel extends JPanel {
        private final Color base;
        private final int radius;

        NeoPanel(Color base, int radius) {
            this.base = base;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(255, 255, 255, 180));
            g2.fillRoundRect(4, 4, w - 8, h - 8, radius, radius);
            g2.setColor(new Color(180, 190, 200, 120));
            g2.fillRoundRect(6, 6, w - 12, h - 12, radius, radius);
            g2.setColor(base);
            g2.fillRoundRect(6, 6, w - 12, h - 12, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class NeoButton extends JButton {
        NeoButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(TEXT_DARK);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setPreferredSize(new Dimension(110, 36));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), r = 18;
            boolean pressed = getModel().isArmed() && getModel().isPressed();
            Color top = pressed ? new Color(0xE0B3B3) : SHADOW_LIGHT;
            Color bottom = pressed ? SHADOW_LIGHT : SHADOW_DARK;
            g2.setColor(top);
            g2.fillRoundRect(2, 2, w - 4, h - 4, r, r);
            g2.setColor(bottom);
            g2.fillRoundRect(3, 3, w - 6, h - 6, r, r);
            g2.setColor(BASE);
            g2.fillRoundRect(3, 3, w - 6, h - 6, r, r);
            g2.setColor(TEXT_DARK);
            FontMetrics fm = g2.getFontMetrics();
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), tx, ty);
            g2.dispose();
        }
    }

    static class RoundedField extends JTextField {
        private final int arc;
        private final Color base;

        RoundedField(int arc, Color base) {
            this.arc = arc;
            this.base = base;
            setOpaque(false);
            setBorder(new EmptyBorder(8, 12, 8, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(SHADOW_LIGHT);
            g2.fillRoundRect(1, 1, w - 2, h - 2, arc, arc);
            g2.setColor(SHADOW_DARK);
            g2.fillRoundRect(2, 2, w - 4, h - 4, arc, arc);
            g2.setColor(base);
            g2.fillRoundRect(2, 2, w - 4, h - 4, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**  Campo multilínea para “Notas” con mismo estilo redondeado */
    static class RoundedArea extends JTextArea {
        private final int arc;
        private final Color base;

        RoundedArea(int arc, Color base) {
            this.arc = arc;
            this.base = base;
            setOpaque(false);
            setBorder(new EmptyBorder(10, 12, 10, 12));
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(SHADOW_LIGHT);
            g2.fillRoundRect(1, 1, w - 2, h - 2, arc, arc);
            g2.setColor(SHADOW_DARK);
            g2.fillRoundRect(2, 2, w - 4, h - 4, arc, arc);
            g2.setColor(base);
            g2.fillRoundRect(2, 2, w - 4, h - 4, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NeumorphicForm().setVisible(true));
    }
}