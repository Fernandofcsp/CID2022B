package agenteemisor;

import jade.core.AID;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class arranqueGui extends JFrame {
    private AgenteReceptor miAgente;
    private JTextField x1;

    arranqueGui(AgenteReceptor a) {
        miAgente = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.add(new JLabel("Valor de X:"));
        x1 = new JTextField(15);
        p.add(x1);
        getContentPane().add(p, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Predecir Y(Sales)");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String var1 = x1.getText().trim();
                    miAgente.main(Float.parseFloat(var1));;
                    x1.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(arranqueGui.this, "Invalid values. " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                miAgente.doDelete();
            }
        });

        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}