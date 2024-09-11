package pos.presentation.estadistica;

import pos.presentation.estadistica.Controller;
import pos.presentation.estadistica.Model;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class View {
    private JPanel panel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JButton button1;
    private JButton button2;
    private JTable table1;
    private JButton button3;
    private JButton button4;


    // MVC
    pos.presentation.estadistica.Model model;
    pos.presentation.estadistica.Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener((PropertyChangeListener) this);
    }

    public Component getPanel() {
        return panel;
    }
}
