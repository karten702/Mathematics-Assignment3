package math;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import math.RSA.Constants;
import math.RSA.Decryptor;
import math.RSA.Encryptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class App {
    private JButton Step1;
    private JPanel FirstPanel;
    private JTextField textField1;
    private JButton step2Button;
    private JTextField textField2;
    private JButton step3Button;
    private JTextField textField3;
    private JTextField textField4;
    private JButton step1Button;
    private JTextField textField5;
    private JButton step2Button1;

    private Encryptor encrypt;
    private Decryptor decrypt;

    public App() {
        Step1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Constants.tryParseLong(textField1.getText())) {
                    long number = Long.parseLong(textField1.getText());
                    if (number >= 300) {
                        encrypt = new Encryptor(number);
                        long[] results = encrypt.CalculatePQ();
                        JOptionPane.showMessageDialog(null, "p is " + results[0] + " \n" +
                                "q is " + results[1] + " \n " +
                                "Amount of time busy to find p and q: " + results[2] + " milliseconds");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "N needs to be higher than 300");
                }
                else
                    JOptionPane.showMessageDialog(null, "The number you tried to parse is not a valid number");
            }
        });
        step2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (encrypt != null) {
                    long e = encrypt.CalculateE();
                    JOptionPane.showMessageDialog(null, "e is " + e);
                }
                else
                    JOptionPane.showMessageDialog(null, "Could not find a valid encryption object, please perform encryption step 1 first");
            }
        });
        step3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (encrypt != null){
                    if (textField2.getText().isEmpty())
                        JOptionPane.showMessageDialog(null, "Please enter a message to encrypt first");
                    else {
                        String encryptedMessage = encrypt.encryptedMessage(textField2.getText());
                        JTextArea textArea = new JTextArea(10,50);
                        textArea.setText("Message after encryption is: \n" + encryptedMessage);
                        textArea.setWrapStyleWord(true);
                        textArea.setLineWrap(true);
                        textArea.setCaretPosition(0);
                        textArea.setEditable(false);
                        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Could not find a valid encryption object, please perform encryption step 1 and 2 first");
            }
        });
        step1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Constants.tryParseLong(textField3.getText()) && Constants.tryParseLong(textField4.getText())) {
                    long N = Long.parseLong(textField3.getText());
                    long e = Long.parseLong(textField4.getText());
                    if (N >= 300 ) {
                        if (BigInteger.valueOf(e).isProbablePrime(3)) {
                            decrypt = new Decryptor(N, e);
                            long d = decrypt.calculateD();
                            JOptionPane.showMessageDialog(null, "d is " + d);
                        }
                        else
                            JOptionPane.showMessageDialog(null, "e is not a prime number");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "N needs to be higher than 300");
                }
                else
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers in the fields for N and E");
            }
        });
        step2Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (decrypt != null) {
                    if (!textField5.getText().isEmpty()) {
                        String inputMessage = textField5.getText();
                        String[] splitMessage = inputMessage.split(",");
                        try {
                            long[] encryptedCodes = Constants.generateLongArrayFromString(splitMessage);
                            String decryptedMessage = decrypt.DecryptMessage(encryptedCodes);
                            JOptionPane.showMessageDialog(null, "Message after decryption is: " + decryptedMessage);
                        }
                        catch (NumberFormatException e){
                            JOptionPane.showMessageDialog(null, "Error parsing: " + e.getMessage());
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Please enter a message first");
                }
                else
                    JOptionPane.showMessageDialog(null, "Please perform decryption step 1 first");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Assignment 3");
        frame.setContentPane(new App().FirstPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
