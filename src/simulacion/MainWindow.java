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

public class MainWindow extends JFrame implements ActionListener {
    private JComboBox<String> generatorSelector;
    private JComboBox<String> testSelector;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextArea resultArea;
    private JButton generateButton;
    private JButton testButton;
    private JButton clearButton;
    private JButton exitButton;
    private ArrayList<Double> data;

    public MainWindow() {
        super("Simulacion de numeros pseudoaleatorios");
        data = new ArrayList<Double>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        buildInterface();
    }

    private void buildInterface() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBorder(BorderFactory.createTitledBorder("Generacion"));
        top.add(new JLabel("Metodo:"));
        String[] generators = {
            "Congruencial aditivo",
            "Congruencial multiplicativo",
            "Congruencial cuadratico",
            "Productos medios",
            "Cuadrados medios",
            "Multiplicador constante",
            "Congruencial lineal"
        };
        generatorSelector = new JComboBox<String>(generators);
        top.add(generatorSelector);
        generateButton = new JButton("Generar");
        generateButton.addActionListener(this);
        top.add(generateButton);

        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        testPanel.setBorder(BorderFactory.createTitledBorder("Pruebas"));
        testPanel.add(new JLabel("Prueba:"));
        String[] tests = {
            "Prueba de medias",
            "Prueba de varianza",
            "Prueba Chi-cuadrada",
            "Kolmogorov-Smirnov",
            "Corridas arriba y abajo",
            "Corridas arriba y abajo de la media",
            "Prueba de Poker",
            "Prueba de huecos"
        };
        testSelector = new JComboBox<String>(tests);
        testPanel.add(testSelector);
        testButton = new JButton("Ejecutar prueba");
        testButton.addActionListener(this);
        testPanel.add(testButton);
        clearButton = new JButton("Limpiar");
        clearButton.addActionListener(this);
        testPanel.add(clearButton);
        exitButton = new JButton("Salir");
        exitButton.addActionListener(this);
        testPanel.add(exitButton);

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.NORTH);
        north.add(testPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Indice");
        tableModel.addColumn("Numero generado");
        dataTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        tableScroll.setPreferredSize(new Dimension(260, 0));
        tableScroll.setBorder(BorderFactory.createTitledBorder("Datos generados"));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Calculos y resultado"));

        JPanel center = new JPanel(new BorderLayout());
        center.add(tableScroll, BorderLayout.WEST);
        center.add(resultScroll, BorderLayout.CENTER);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == generateButton) {
            generateData();
        } else if (event.getSource() == testButton) {
            executeTest();
        } else if (event.getSource() == clearButton) {
            data.clear();
            tableModel.setRowCount(0);
            resultArea.setText("");
        } else if (event.getSource() == exitButton) {
            dispose();
        }
    }

    private void generateData() {
        NumberGenerator generator = null;
        int choice = generatorSelector.getSelectedIndex();
        if (choice == 0) generator = new AdditiveCongruentialGenerator();
        if (choice == 1) generator = new MultiplicativeCongruentialGenerator();
        if (choice == 2) generator = new QuadraticCongruentialGenerator();
        if (choice == 3) generator = new MidProductGenerator();
        if (choice == 4) generator = new MidSquareGenerator();
        if (choice == 5) generator = new ConstantMultiplierGenerator();
        if (choice == 6) generator = new LinearCongruentialGenerator();
        GenerationResult result = generator.generate(this);
        if (result == null) {
            return;
        }
        data = result.getValues();
        tableModel.setRowCount(0);
        for (int i = 0; i < data.size(); i++) {
            tableModel.addRow(new Object[]{i + 1, data.get(i)});
        }
        resultArea.setText("DATOS GENERADOS\n" + result.getReport());
    }

    private void executeTest() {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos generados.");
            return;
        }
        String report = null;
        int choice = testSelector.getSelectedIndex();
        if (choice == 0) report = MeanTest.execute(this, data);
        if (choice == 1) report = VarianceTest.execute(this, data);
        if (choice == 2) report = ChiSquareTest.execute(this, data);
        if (choice == 3) report = KolmogorovTest.execute(this, data);
        if (choice == 4) report = RunsTest.execute(this, data);
        if (choice == 5) report = MeanRunsTest.execute(this, data);
        if (choice == 6) report = PokerTest.execute(this, data);
        if (choice == 7) report = GapTest.execute(this, data);
        if (report != null) {
            resultArea.setText(report);
        }
    }
}
