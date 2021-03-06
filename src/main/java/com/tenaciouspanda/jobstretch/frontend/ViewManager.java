/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch.frontend;

import com.tenaciouspanda.jobstretch.Session;
import java.awt.CardLayout;
import javax.swing.JFrame;


public class ViewManager extends JFrame {

    private Session session;
    /**
     * Creates new form ViewManager
     */
    public ViewManager() {
        session = new Session();
        initComponents();
        setup();
    }

    private void setup(){
        DashboardPanel dashboard = new DashboardPanel(session, this);
        LoginPanel login = new LoginPanel(session, this);
        CompanyDetailPanel cd = new CompanyDetailPanel(session, this);
        CompanyProfilePanel cp = new CompanyProfilePanel(session, this);
        PersonDetailPanel pd = new PersonDetailPanel(session, this);
        RegistrationPanel rp = new RegistrationPanel(session, this);
        UserFoundPanel ufp = new UserFoundPanel(session, this);
        UserProfilePanel upp = new UserProfilePanel(session, this);
        
        cardPanel.add(dashboard, "DashboardPanel");
        cardPanel.add(login, "LoginPanel");
        cardPanel.add(cd, "CompanyDetailPanel");
        cardPanel.add(cp, "CompanyProfilePanel");
        cardPanel.add(pd, "PersonDetailPanel");
        cardPanel.add(rp, "RegistrationPanel");
        cardPanel.add(ufp, "UserFoundPanel");
        cardPanel.add(upp, "UserProfilePanel");
        add(cardPanel);
        displayView("LoginPanel");
        setStatus("Initialized");
    }
    void displayView(String viewTitle){
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, viewTitle);
        setVisible(true);
        pack();
    }
    
    void setStatus(String status){
        statusbar.setText(status);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        statusbar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cardPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);
        getContentPane().add(statusbar, java.awt.BorderLayout.PAGE_END);
        statusbar.getAccessibleContext().setAccessibleName("Status");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel statusbar;
    // End of variables declaration//GEN-END:variables
}
