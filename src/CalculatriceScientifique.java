import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngineManager;

import javax.script.ScriptEngine;

import javax.script.ScriptException;


public class CalculatriceScientifique extends JFrame implements ActionListener {

    private JTextField inputField;

    public CalculatriceScientifique() {
        setTitle("Calculatrice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4));
        String[] buttonLabels = {
        		"ln⁡(x)", "√", "e^x", "x^2",
        	    "7", "8", "9", "+",
        	    "4", "5", "6", "-",
        	    "1", "2", "3", "/",
        	    ".", "0", "=", "*",
        	    "Effacer"  
        	};


        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("=")) {
            String expression = inputField.getText().trim();
            if (!expression.isEmpty()) {
                try {
                    double result = eval(expression);
                    inputField.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    inputField.setText("Erreur");
                }
            }
        } else if (action.equals("ln⁡(x)")) {
                double value = Double.parseDouble(inputField.getText());
                double result = Math.log(value);
                inputField.setText(Double.toString(result));
            } else if (action.equals("√")) {
                double value = Double.parseDouble(inputField.getText());
                double result = Math.sqrt(value);
                inputField.setText(Double.toString(result));
            } else if (action.equals("e^x")) {
                double value = Double.parseDouble(inputField.getText());
                double result = Math.exp(value);
                inputField.setText(Double.toString(result));
            } else if (action.equals("x^2")) {
                double value = Double.parseDouble(inputField.getText());
                double result = Math.pow(value, 2);
                inputField.setText(Double.toString(result));
            }  else if (action.equals("Effacer")) {
            inputField.setText("");
        } else {
            inputField.setText(inputField.getText() + action);
        }
    }

    private double eval(String expression) {
        double result = 0;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object evalResult = engine.eval(expression);
            if (evalResult instanceof Integer) {
                result = ((Integer) evalResult).doubleValue();
            } else {
                result = (double) evalResult;
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculatriceScientifique();
            }
        });
    }
}
