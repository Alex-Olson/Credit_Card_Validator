package com.alex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CCValidator extends JFrame {
    private JTextField creditCardNumberTextField;
    private JButton validateButton;
    private JButton quitButton;
    private JPanel rootPanel;
    private JLabel validMessageLabel;

    public CCValidator(){
        super("Credit Card Validator");
        setContentPane(rootPanel);
        pack();
        setSize(new Dimension(350,200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ccNumber = creditCardNumberTextField.getText();

                boolean valid = isVisaCreditCardNumberValid(ccNumber);

                if (valid){
                    validMessageLabel.setText("Visa card number is valid");
                } else {
                    validMessageLabel.setText("Visa card number is NOT valid");
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quit = JOptionPane.showConfirmDialog(CCValidator.this, "Are you sure you want to quit?",
                        "Quit", JOptionPane.OK_CANCEL_OPTION);
                if (quit == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
            }
        });
    }

    private static boolean isVisaCreditCardNumberValid(String cardNumber) {

        //the number string that was passed into the method will get split into single digits
        String[] cardDigitsString = cardNumber.split("");
        //parse the digits from strings to ints
        int[] cardDigits = new int[cardDigitsString.length];
        for (int i = 0; i < cardDigitsString.length; i++){
            cardDigits[i] = Integer.parseInt(cardDigitsString[i]);
        }
        //determine whether the card number input is correct or not

        //if the first number of the card isn't 4, or if the card number isn't 16 digits, return false
        if (cardDigits[0] != 4 || cardDigits.length != 16){
            return false;
        } else {
            /*if the basic tests are good, then use the formula described here: http://web.eecs.umich.edu/~bartlett/credit_card_number.html
            to determine if the card number is valid or not
             */

            int digitTotal = 0;
            /*
            every even ordered digit is added to a running total, every odd ordered digit is also added after being
            doubled. if a digit doubled is 10+, the sum of the two digits is added instead
             */

            for (int i = 0; i < cardDigits.length; i++){

                if ((i + 1) % 2 == 0){
                    digitTotal += cardDigits[i];
                } else {
                    int doubleDigit = cardDigits[i] * 2;
                    if (cardDigits[i] > 4){
                        digitTotal += (doubleDigit / 10) + (doubleDigit % 10);
                    } else {
                        digitTotal += doubleDigit;
                    }
                }
            }
            //if the running total is a multiple of 10 after every digit has been added, return true. if not, return false.
            if (digitTotal % 10 == 0){
                return true;
            } else {
                return false;
            }

        }

    }
}
