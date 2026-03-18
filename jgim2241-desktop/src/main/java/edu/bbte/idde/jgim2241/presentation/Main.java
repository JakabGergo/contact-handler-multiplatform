package edu.bbte.idde.jgim2241.presentation;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.DaoFactory;
import edu.bbte.idde.jgim2241.service.ServiceException;
import edu.bbte.idde.jgim2241.service.impl.ContactServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;
import java.util.Date;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        var daoFactory = DaoFactory.getInstance();
        var contactService = new ContactServiceImpl();
        contactService.setContactDao(daoFactory.getContactDao());

        JFrame frame = new JFrame("Contact app");

        // create table
        String[] column = {"ID", "NAME", "EMAIL", "PHONE", "BIRTHDATE", "ADRESS"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane;
        scrollPane = new JScrollPane(table);
        Runnable refreshTable = () -> {
            tableModel.setRowCount(0);
            try {
                Collection<Contact> contacts = contactService.findAll();
                for (Contact contact : contacts) {
                    Object[] rowData = {
                            contact.getId(),
                            contact.getName(),
                            contact.getEmail(),
                            contact.getPhoneNumber(),
                            contact.getBirthdate(),
                            contact.getAddress()
                    };
                    tableModel.addRow(rowData);
                }
            } catch (ServiceException e) {
                log.error(e.getMessage());
                JOptionPane.showMessageDialog(frame, "Somthing went wrong",
                        "Creation failed", JOptionPane.ERROR_MESSAGE);
            }
        };
        refreshTable.run();

        JPanel createPanel = getCreatePanel(contactService, frame, refreshTable);

        // update form
        JPanel updatePanel = getUpdatePanel(contactService, frame, refreshTable);

        // delete form
        JPanel deletePanel = getDeletePanel(contactService, frame, refreshTable);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(createPanel);
        bottomPanel.add(updatePanel);
        bottomPanel.add(deletePanel);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(350, 100, 700, 500);
        frame.setVisible(true);
    }

    private static JPanel getDeletePanel(ContactServiceImpl contactService,
                                         JFrame frame, Runnable refreshTable) {
        JTextField deleteIdField = new JTextField(3);
        JButton deleteButton = new JButton("Delete by ID");
        deleteButton.addActionListener(e -> {
            try {
                Long deleteID = Long.parseLong(deleteIdField.getText());
                contactService.delete(deleteID);
                refreshTable.run();
            } catch (NumberFormatException ex) {
                log.warn(ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for ID.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                log.error(ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Cant delete", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel deletePanel = new JPanel();
        deletePanel.add(new JLabel("Delete ID:"));
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteButton);
        return deletePanel;
    }

    private static JPanel getUpdatePanel(ContactServiceImpl contactService,
                                         JFrame frame, Runnable refreshTable) {
        JTextField updateIdField = new JTextField(3);
        JTextField updateNameField = new JTextField("Name", 10);
        JTextField updateEmailField = new JTextField("Email", 15);
        JTextField updatePhoneNumberField = new JTextField("Phone number", 10);
        JTextField updateAddressField = new JTextField("Adress", 15);
        JButton updateButton = new JButton("Update contact");
        updateButton.addActionListener(e -> {
            try {
                Long updateID = Long.parseLong(updateIdField.getText());
                Contact updatedContact = new Contact(updateID, updateNameField.getText(), updateEmailField.getText(),
                        updatePhoneNumberField.getText(), new Date(), updateAddressField.getText());
                contactService.update(updatedContact);
                refreshTable.run();
            } catch (NumberFormatException ex) {
                log.warn(ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for ID.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                log.error(ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Cannot update contact.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel updatePanel = new JPanel();
        updatePanel.add(new JLabel("Update ID:"));
        updatePanel.add(updateIdField);
        updatePanel.add(updateNameField);
        updatePanel.add(updateEmailField);
        updatePanel.add(updatePhoneNumberField);
        updatePanel.add(updateAddressField);
        updatePanel.add(updateButton);
        return updatePanel;
    }

    private static JPanel getCreatePanel(ContactServiceImpl contactService,
                                         JFrame frame, Runnable refreshTable) {
        // create add new form
        JTextField nameField = new JTextField("Name", 10);
        JTextField emailField = new JTextField("Email", 15);
        JTextField phoneNumberField = new JTextField("Phone number", 10);
        JTextField addressField = new JTextField("Adress", 15);
        JButton addButton = new JButton("Add contact");
        addButton.addActionListener(e -> {
            Contact newContact = new Contact(nameField.getText(), emailField.getText(),
                    phoneNumberField.getText(), new Date(), addressField.getText());
            try {
                contactService.create(newContact);
                refreshTable.run();
            } catch (ServiceException ex) {
                log.error(ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Please enter a valid values.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        JPanel createPanel = new JPanel();
        createPanel.add(nameField);
        createPanel.add(emailField);
        createPanel.add(phoneNumberField);
        createPanel.add(addressField);
        createPanel.add(addButton);
        return createPanel;
    }
}