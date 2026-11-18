package simulacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener {
    private JComboBox<String> selectorGenerador;
    private JComboBox<String> selectorPrueba;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaResultados;
    private JButton botonGenerar;
    private JButton botonPrueba;
    private JButton botonLimpiar;
    private JButton botonSalir;
    private ArrayList<Double> datos;

    public VentanaPrincipal() {
        super("Simulacion de numeros pseudoaleatorios");
        datos = new ArrayList<Double>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.setBorder(BorderFactory.createTitledBorder("Generacion"));
        superior.add(new JLabel("Metodo:"));
        String[] generadores = {
            "Congruencial aditivo",
            "Congruencial multiplicativo",
            "Congruencial cuadratico",
            "Productos medios",
            "Cuadrados medios",
            "Multiplicador constante",
            "Congruencial lineal"
        };
        selectorGenerador = new JComboBox<String>(generadores);
        superior.add(selectorGenerador);
        botonGenerar = new JButton("Generar");
        botonGenerar.addActionListener(this);
        superior.add(botonGenerar);

        JPanel panelPrueba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPrueba.setBorder(BorderFactory.createTitledBorder("Pruebas"));
        panelPrueba.add(new JLabel("Prueba:"));
        String[] pruebas = {
            "Prueba de medias",
            "Prueba de varianza",
            "Prueba Chi-cuadrada",
            "Kolmogorov-Smirnov",
            "Corridas arriba y abajo",
            "Corridas arriba y abajo de la media",
            "Prueba de Poker",
            "Prueba de huecos"
        };
        selectorPrueba = new JComboBox<String>(pruebas);
        panelPrueba.add(selectorPrueba);
        botonPrueba = new JButton("Ejecutar prueba");
        botonPrueba.addActionListener(this);
        panelPrueba.add(botonPrueba);
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.addActionListener(this);
        panelPrueba.add(botonLimpiar);
        botonSalir = new JButton("Salir");
        botonSalir.addActionListener(this);
        panelPrueba.add(botonSalir);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(superior, BorderLayout.NORTH);
        norte.add(panelPrueba, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Indice");
        modeloTabla.addColumn("Numero generado");
        tablaDatos = new JTable(modeloTabla);
        JScrollPane desplazamientoTabla = new JScrollPane(tablaDatos);
        desplazamientoTabla.setPreferredSize(new Dimension(260, 0));
        desplazamientoTabla.setBorder(BorderFactory.createTitledBorder("Datos generados"));

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(false);
        JScrollPane desplazamientoResultado = new JScrollPane(areaResultados);
        desplazamientoResultado.setBorder(BorderFactory.createTitledBorder("Calculos y resultado"));

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(desplazamientoTabla, BorderLayout.WEST);
        centro.add(desplazamientoResultado, BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botonGenerar) {
            generarDatos();
        } else if (evento.getSource() == botonPrueba) {
            ejecutarPrueba();
        } else if (evento.getSource() == botonLimpiar) {
            datos.clear();
            modeloTabla.setRowCount(0);
            areaResultados.setText("");
        } else if (evento.getSource() == botonSalir) {
            dispose();
        }
    }

    private void generarDatos() {
        GeneradorNumeros generador = null;
        int opcion = selectorGenerador.getSelectedIndex();
        if (opcion == 0) generador = new GeneradorCongruencialAditivo();
        if (opcion == 1) generador = new GeneradorCongruencialMultiplicativo();
        if (opcion == 2) generador = new GeneradorCongruencialCuadratico();
        if (opcion == 3) generador = new GeneradorProductosMedios();
        if (opcion == 4) generador = new GeneradorCuadradosMedios();
        if (opcion == 5) generador = new GeneradorMultiplicadorConstante();
        if (opcion == 6) generador = new GeneradorCongruencialLineal();
        ResultadoGeneracion resultado = generador.generar(this);
        if (resultado == null) {
            return;
        }
        datos = resultado.obtenerValores();
        modeloTabla.setRowCount(0);
        for (int i = 0; i < datos.size(); i++) {
            modeloTabla.addRow(new Object[]{i + 1, datos.get(i)});
        }
        areaResultados.setText("DATOS GENERADOS\n" + resultado.obtenerReporte());
    }

    private void ejecutarPrueba() {
        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos generados.");
            return;
        }
        String reporte = null;
        int opcion = selectorPrueba.getSelectedIndex();
        if (opcion == 0) reporte = PruebaMedias.ejecutar(this, datos);
        if (opcion == 1) reporte = PruebaVarianza.ejecutar(this, datos);
        if (opcion == 2) reporte = PruebaChiCuadrada.ejecutar(this, datos);
        if (opcion == 3) reporte = PruebaKolmogorovSmirnov.ejecutar(this, datos);
        if (opcion == 4) reporte = PruebaCorridas.ejecutar(this, datos);
        if (opcion == 5) reporte = PruebaCorridasMedia.ejecutar(this, datos);
        if (opcion == 6) reporte = PruebaPoker.ejecutar(this, datos);
        if (opcion == 7) reporte = PruebaHuecos.ejecutar(this, datos);
        if (reporte != null) {
            areaResultados.setText(reporte);
        }
    }
}


