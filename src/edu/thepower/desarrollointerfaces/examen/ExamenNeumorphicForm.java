package edu.thepower.desarrollointerfaces.examen;

/**
 * EXAMEN PRÁCTICO – Formulario Swing
 * - Campos: Nombre, Apellidos, Teléfono, DNI, Dirección, Email
 * - Botones: Guardar (valida y guarda en TXT) y Limpiar
 * - Validaciones extra: Teléfono (9 dígitos), DNI (8 números + 1 letra), Email con @ y '.'
 * - Guarda en usuarios.txt (formato legible) y muestra confirmación
 * - Contador de usuarios guardados
 * - (Opcional) Botón "Ver usuarios" para mostrar el contenido del TXT
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class ExamenNeumorphicForm extends JFrame {

    // --------- Colores (neumórfico pastel) ----------
    private static final Color BASE = new Color(0xFFE9E3);
    private static final Color ACCENT = new Color(0xF9BEBE);
    private static final Color TEXT_DARK = new Color(0x5A4A4A);
    private static final Color SHADOW_DARK = new Color(0xE0B3B3);
    private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);

    /**
     * // Colores base (Azul)
     *     private static final Color BASE = new Color(0xEAF0F5);        // fondo pastel
     *     private static final Color ACCENT = new Color(0xBBD4E7);      // acento suave
     *     private static final Color TEXT_DARK = new Color(0x364355);   // texto
     *     private static final Color SHADOW_DARK = new Color(0xB4C3D0); // sombra
     *     private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);// luz

     // Colores base (rosa pastel)
     private static final Color BASE = new Color(0xFAE8E8);        // Fondo principal (rosa empolvado claro)
     private static final Color ACCENT = new Color(0xF3C6C6);      // Acento suave para botones o sombras interiores
     private static final Color TEXT_DARK = new Color(0x5A4A4A);   // Texto gris rosado oscuro (contraste)
     private static final Color SHADOW_DARK = new Color(0xE2BABA); // Sombra inferior (tono rosado más profundo)
     private static final Color SHADOW_LIGHT = new Color(0xFFFFFF);// Luz superior (blanco suave)
    */

    // --------- Campos ----------
    private RoundedField txtNombre;
    private RoundedField txtApellidos;
    private RoundedField txtTelefono;
    private RoundedField txtDni;
    private RoundedField txtDireccion;
    private RoundedField txtEmail;

    // --------- Botones ----------
    private NeoButton btnGuardar;
    private NeoButton btnLimpiar;
    private NeoButton btnVerUsuarios;
    private NeoButton btnTxtToXml;

    // --------- Archivos ----------
    private static final String ARCHIVO =
            "C:\\Users\\AlumnoAfternoon\\IdeaProjects\\Neomorphic-Form\\resources\\usuarios_registrados.txt";
    private static final String ARCHIVO_XML =
            "C:\\Users\\AlumnoAfternoon\\IdeaProjects\\Neomorphic-Form\\resources\\usuarios_desde_txt.xml";
    private static final File FILETXT = new File(ARCHIVO);

    // --------- Bordes ----------
    private final javax.swing.border.Border DEFAULT_BORDER = new EmptyBorder(8, 12, 8, 12);

    public ExamenNeumorphicForm() {
        setTitle("Formulario de Registro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 580);
        setLocationRelativeTo(null);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));

        NeoPanel root = new NeoPanel(BASE, 18);
        root.setLayout(new BorderLayout(0, 16));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));
        setContentPane(root);

        JLabel title = new JLabel("FORMULARIO", SwingConstants.CENTER);
        title.setForeground(TEXT_DARK);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        root.add(title, BorderLayout.NORTH);

        NeoPanel form = new NeoPanel(ACCENT, 22);
        form.setLayout(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 22, 20, 22));
        root.add(form, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        int row = 0;
        txtNombre    = addRow(form, c, row++, "Nombre :");
        txtApellidos = addRow(form, c, row++, "Apellidos :");
        txtTelefono  = addRow(form, c, row++, "Teléfono :");
        txtDni       = addRow(form, c, row++, "DNI :");
        txtDireccion = addRow(form, c, row++, "Dirección :");
        txtEmail     = addRow(form, c, row++, "Email :");

        NeoPanel actions = new NeoPanel(ACCENT, 18);
        actions.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnTxtToXml    = new NeoButton("TXT → XML");  // NUEVO
        btnVerUsuarios = new NeoButton("Ver usuarios");
        btnLimpiar     = new NeoButton("Limpiar");
        btnGuardar     = new NeoButton("Guardar");
        actions.add(btnTxtToXml);
        actions.add(btnVerUsuarios);
        actions.add(btnLimpiar);
        actions.add(btnGuardar);
        root.add(actions, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnGuardar);

        // Acciones
        btnGuardar.addActionListener(this::onGuardar);
        btnLimpiar.addActionListener(e -> limpiar());
        btnVerUsuarios.addActionListener(e -> mostrarUsuarios());
        btnTxtToXml.addActionListener(e -> convertirTxtAXml()); // NUEVO

        // Estética campos
        for (RoundedField f : new RoundedField[]{txtNombre, txtApellidos, txtTelefono, txtDni, txtDireccion, txtEmail}) {
            f.setForeground(TEXT_DARK);
            f.setBackground(BASE);
            f.setBorder(DEFAULT_BORDER);
        }
    }

    private RoundedField addRow(JPanel form, GridBagConstraints c, int row, String label) {
        c.gridx = 0; c.gridy = row; c.weightx = 0;
        JLabel l = new JLabel(label);
        l.setForeground(TEXT_DARK);
        form.add(l, c);

        c.gridx = 1; c.gridy = row; c.weightx = 1;
        RoundedField field = new RoundedField(14, BASE);
        form.add(field, c);
        return field;
    }

    // --------- Guardar en TXT ----------
    private void onGuardar(ActionEvent e) {
        resetBorders();

        String nombre    = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono  = txtTelefono.getText().trim();
        String dni       = txtDni.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String email     = txtEmail.getText().trim();

        StringBuilder errs = new StringBuilder();

        // Requeridos
        if (nombre.isEmpty())    { errs.append("• El campo Nombre es obligatorio.\n");    markError(txtNombre); }
        if (apellidos.isEmpty()) { errs.append("• El campo Apellidos es obligatorio.\n"); markError(txtApellidos); }
        if (telefono.isEmpty())  { errs.append("• El campo Teléfono es obligatorio.\n");  markError(txtTelefono); }
        if (dni.isEmpty())       { errs.append("• El campo DNI es obligatorio.\n");       markError(txtDni); }
        if (direccion.isEmpty()) { errs.append("• El campo Dirección es obligatorio.\n"); markError(txtDireccion); }
        if (email.isEmpty())     { errs.append("• El campo Email es obligatorio.\n");     markError(txtEmail); }

        // Teléfono 9 dígitos
        if (!telefono.isEmpty() && !telefono.matches("^\\d{9}$")) {
            errs.append("• Teléfono debe tener exactamente 9 dígitos.\n");
            markError(txtTelefono);
        }

        // DNI español 8 números + 1 letra
        if (!dni.isEmpty() && !dni.matches("^\\d{8}[A-Za-z]$")) {
            errs.append("• DNI debe tener 8 números y 1 letra (ej.: 12345678A).\n");
            markError(txtDni);
        }

        // Email básico
        if (!email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$")) {
            errs.append("• Email no válido (debe contener '@' y un dominio válido).\n");
            markError(txtEmail);
        }

        if (errs.length() > 0) {
            JOptionPane.showMessageDialog(this, errs.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardado en TXT
        try (FileWriter writer = new FileWriter(FILETXT, true)) {
            writer.write("Nombre: " + nombre + "\n");
            writer.write("Apellidos: " + apellidos + "\n");
            writer.write("Teléfono: " + telefono + "\n");
            writer.write("DNI: " + dni + "\n");
            writer.write("Dirección: " + direccion + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("--------------------------\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int total = contarUsuarios(FILETXT);
        JOptionPane.showMessageDialog(
                this,
                "Usuario guardado correctamente en:\n" + ARCHIVO + "\n" +
                        "Usuarios almacenados: " + total,
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );
        limpiar();
    }

    private int contarUsuarios(File file) {
        if (!file.exists()) return 0;
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Nombre:")) count++;
            }
        } catch (IOException ignored) {}
        return count;
    }

    // --------- Ver usuarios (en JTextArea) ----------
    private void mostrarUsuarios() {
        if (!FILETXT.exists()) {
            JOptionPane.showMessageDialog(this, "Aún no hay usuarios guardados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILETXT))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error leyendo el archivo:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextArea area = new JTextArea(sb.toString(), 20, 60);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane sp = new JScrollPane(area);

        JOptionPane.showMessageDialog(this, sp, "Usuarios guardados (" + contarUsuarios(FILETXT) + ")", JOptionPane.PLAIN_MESSAGE);
    }

    // ======== Conversión TXT → XML ========
    private void convertirTxtAXml() {
        if (!FILETXT.exists()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el TXT:\n" + FILETXT.getAbsolutePath(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 1) Construir DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("usuarios");
            doc.appendChild(root);

            // 2) Parsear TXT agrupando por separador
            String nombre = "", apellidos = "", telefono = "", dni = "", direccion = "", email = "";
            try (BufferedReader br = new BufferedReader(new FileReader(FILETXT))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();

                    if      (line.startsWith("Nombre:"))    nombre    = line.substring("Nombre:".length()).trim();
                    else if (line.startsWith("Apellidos:")) apellidos = line.substring("Apellidos:".length()).trim();
                    else if (line.startsWith("Teléfono:"))  telefono  = line.substring("Teléfono:".length()).trim();
                    else if (line.startsWith("DNI:"))       dni       = line.substring("DNI:".length()).trim();
                    else if (line.startsWith("Dirección:")) direccion = line.substring("Dirección:".length()).trim();
                    else if (line.startsWith("Email:"))     email     = line.substring("Email:".length()).trim();
                    else if (line.startsWith("---")) {
                        // Fin de registro
                        appendUsuarioXml(doc, root, nombre, apellidos, telefono, dni, direccion, email);
                        nombre = apellidos = telefono = dni = direccion = email = "";
                    }
                }
            }

            // Si el archivo no termina con separador, guarda el último bloque
            if (!nombre.isEmpty() || !apellidos.isEmpty() || !telefono.isEmpty() ||
                    !dni.isEmpty() || !direccion.isEmpty() || !email.isEmpty()) {
                appendUsuarioXml(doc, root, nombre, apellidos, telefono, dni, direccion, email);
            }

            // 3) Escribir XML con indentación
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.transform(new DOMSource(doc), new StreamResult(new File(ARCHIVO_XML)));

            JOptionPane.showMessageDialog(this,
                    "Conversión completada.\nXML generado en:\n" + ARCHIVO_XML,
                    "TXT → XML",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al convertir a XML:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void appendUsuarioXml(Document doc, Element root,
                                         String nombre, String apellidos, String telefono,
                                         String dni, String direccion, String email) {
        Element usuario = doc.createElement("usuario");
        createChild(doc, usuario, "nombre", nombre);
        createChild(doc, usuario, "apellidos", apellidos);
        createChild(doc, usuario, "telefono", telefono);
        createChild(doc, usuario, "dni", dni);
        createChild(doc, usuario, "direccion", direccion);
        createChild(doc, usuario, "email", email);
        root.appendChild(usuario);
    }

    private static void createChild(Document doc, Element parent, String tag, String value) {
        Element e = doc.createElement(tag);
        e.setTextContent(value == null ? "" : value);
        parent.appendChild(e);
    }

    // --------- Utilidades UI ----------
    private void limpiar() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtDni.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        resetBorders();
        txtNombre.requestFocus();
    }

    private void markError(JTextField f) { f.setBorder(new LineBorder(new Color(0xE57373), 1)); }

    private void resetBorders() {
        txtNombre.setBorder(DEFAULT_BORDER);
        txtApellidos.setBorder(DEFAULT_BORDER);
        txtTelefono.setBorder(DEFAULT_BORDER);
        txtDni.setBorder(DEFAULT_BORDER);
        txtDireccion.setBorder(DEFAULT_BORDER);
        txtEmail.setBorder(DEFAULT_BORDER);
    }

    // --------- Componentes Neumórficos ----------
    static class NeoPanel extends JPanel {
        private final Color base; private final int radius;
        NeoPanel(Color base, int radius) { this.base = base; this.radius = radius; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(255, 255, 255, 180)); g2.fillRoundRect(4, 4, w - 8, h - 8, radius, radius);
            g2.setColor(new Color(180, 190, 200, 120)); g2.fillRoundRect(6, 6, w - 12, h - 12, radius, radius);
            g2.setColor(base); g2.fillRoundRect(6, 6, w - 12, h - 12, radius, radius);
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
            setPreferredSize(new Dimension(150, 36));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), r = 18;
            boolean pressed = getModel().isArmed() && getModel().isPressed();
            Color top = pressed ? new Color(0xE0B3B3) : SHADOW_LIGHT;
            Color bottom = pressed ? SHADOW_LIGHT : SHADOW_DARK;
            g2.setColor(top);    g2.fillRoundRect(2, 2, w - 4, h - 4, r, r);
            g2.setColor(bottom); g2.fillRoundRect(3, 3, w - 6, h - 6, r, r);
            g2.setColor(BASE);   g2.fillRoundRect(3, 3, w - 6, h - 6, r, r);
            g2.setColor(TEXT_DARK);
            FontMetrics fm = g2.getFontMetrics();
            int tx = (w - fm.stringWidth(getText())) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), tx, ty);
            g2.dispose();
        }
    }

    static class RoundedField extends JTextField {
        private final int arc; private final Color base;
        RoundedField(int arc, Color base) { this.arc = arc; this.base = base; setOpaque(false); setBorder(new EmptyBorder(8,12,8,12)); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(SHADOW_LIGHT); g2.fillRoundRect(1,1,w-2,h-2,arc,arc);
            g2.setColor(SHADOW_DARK);  g2.fillRoundRect(2,2,w-4,h-4,arc,arc);
            g2.setColor(base);         g2.fillRoundRect(2,2,w-4,h-4,arc,arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamenNeumorphicForm().setVisible(true));
    }
}