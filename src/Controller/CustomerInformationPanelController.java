package Controller;

import Model.Account;
import Model.AccountList;
import Model.Session;
import View.CutomerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by asaifbutt on 4/19/17.
 */
public class CustomerInformationPanelController {

    private AccountList database = AccountList.getInstance();
    private  Session currentUserSession;
    private JTextField firstNameField;
    private final Account currentUserInfo;
    private JTextField lastNameField;
    private JTextField userNameField;
    private JTextField passwordField;
    private JTextField creditCardField;
    private JTextField ccvField;
    private JTextField expirationDateField;
    private JLabel titleField;
    private boolean edit = false;


    /**
     * Creates a CustomerInformationPanelController object
     * @param currentUser the current user logged in
     */
    public CustomerInformationPanelController(Session currentUser, JTextField firstName, JTextField lastName, JTextField userName, JTextField password, JTextField creditCard, JTextField ccv, JTextField expirationDate, JLabel title) {
        currentUserSession = currentUser;
        currentUserInfo = database.retrieve(currentUser.getUserInSession());
        firstNameField = firstName;
        lastNameField = lastName;
        userNameField = userName;
        passwordField = password;
        creditCardField = creditCard;
        ccvField = ccv;
        expirationDateField = expirationDate;
        titleField = title;
        getAccountInfo();
        setTitle();
    }

    /**
     * A method to set the Title of the Account page by retrieving the first name of the current user from the database
     */
    private void setTitle()
    {
        titleField.setText(currentUserInfo.getFirstName().toUpperCase() + "'S ACCOUNT");
    }

    /**
     * Retrieves the information for the user currently logged in
     */
    private void getAccountInfo()
    {
        firstNameField.setText(currentUserInfo.getFirstName());
        lastNameField.setText(currentUserInfo.getLastName());
        userNameField.setText(currentUserInfo.getUserName());
        passwordField.setText(currentUserInfo.getPassword());
        creditCardField.setText(currentUserInfo.getCreditCard());
        ccvField.setText(currentUserInfo.getCCV());
        expirationDateField.setText(currentUserInfo.getExpDate());


    }

    /**
     * Update Account Action for the CustomerInformationPanel View which reacts to Update Account Button
     * @param e ActionEvent object
     * @param frame The frame where the action takes place
     */
    public void updateAccountButtonActionPerformed(ActionEvent e, JFrame frame, JButton updateAccountButton, JButton backButton) {
        if (!edit) {
            edit = true;
            backButton.setEnabled(false);
            updateAccountButton.setText("Save Changes");
            userNameField.setEditable(false);
            firstNameField.setEditable(true);
            lastNameField.setEditable(true);
            passwordField.setEditable(true);
            creditCardField.setEditable(true);
            ccvField.setEditable(true);
        }
        else if (edit)
        {
            String[] userInput = new String[5];
            userInput[0] = firstNameField.getText().trim();
            userInput[1] = lastNameField.getText().trim();
            userInput[2] = passwordField.getText().trim();
            userInput[3] = creditCardField.getText().trim();
            userInput[4] = ccvField.getText().trim();

            if(userInput[0].length() < 1)
            {
                JOptionPane.showMessageDialog(null, "Cannot leave first name field blank", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(userInput[1].length() < 1)
            {
                JOptionPane.showMessageDialog(null, "Cannot leave last name field blank", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(userInput[2].length() < 6)
            {
                JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(userInput[3].length() < 16 || userInput[3].length() >= 17) {
                JOptionPane.showMessageDialog(null, "Incorrect Credit Card Length", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            else {
                for(int i = 0; i < userInput[3].length(); i++) {
                    if(Character.isDigit(userInput[3].charAt(i))) {
                        // do nothing
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "credit card number cannot contains characters or symbols",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            if(userInput[4].length() < 3 || userInput[4].length() >= 4) {
                JOptionPane.showMessageDialog(null, "CCV number has incorrect length.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            edit = false;
            currentUserInfo.setFirstName(userInput[0]);
            currentUserInfo.setLastName(userInput[1]);
            currentUserInfo.setPassword(userInput[2]);
            currentUserInfo.setCreditCard(userInput[3]);
            currentUserInfo.setCCV(userInput[4]);

            updateAccountButton.setText("Update Account");
            userNameField.setEditable(false);
            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            passwordField.setEditable(false);
            creditCardField.setEditable(false);
            ccvField.setEditable(false);
            backButton.setEnabled(true);

            currentUserSession.setUserInSession(currentUserInfo.getUserName());

            JOptionPane.showMessageDialog(null, "Account Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);


        }

    }

    /**
     * * Back Action for the CustomerInformationPanel View which reacts to BackButton
     * @param e ActionEvent object
     * @param frame The frame where the action takes place
     */
    public void backButtonActionPerformed(ActionEvent e, JFrame frame) {
        frame.dispose();
        CutomerPanel backToProductPanel = new CutomerPanel(currentUserSession);
    }
}
