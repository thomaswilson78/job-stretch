/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch.frontend;
import com.tenaciouspanda.jobstretch.Session;
import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ViewManager extends JFrame {

    private static JPanel cardPanel;
    private static CardLayout cardLayout = new CardLayout();
    private Session session;

    public ViewManager() {
        session = new Session();
        setFrameAttrs();
        addViews();
        displayView("LoginPanel");
    }
    private void setFrameAttrs(){
        setTitle("Job Stretch");
        setSize(820, 490);
    }
    private void addViews(){
        DashboardPanel dashboard = new DashboardPanel(session, this);
        LoginPanel login = new LoginPanel(session, this);
        CompanyDetailPanel cd = new CompanyDetailPanel(session, this);
        CompanyProfilePanel cp = new CompanyProfilePanel(session, this);
        PersonDetailPanel pd = new PersonDetailPanel(session, this);
        RegistrationPanel rp = new RegistrationPanel(session, this);
        UserFoundPanel ufp = new UserFoundPanel(session, this);
        UserProfilePanel upp = new UserProfilePanel(session, this);
        
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(dashboard, "DashboardPanel");
        cardPanel.add(login, "LoginPanel");
        cardPanel.add(cd, "CompanyDetailPanel");
        cardPanel.add(cp, "CompanyProfilePanel");
        cardPanel.add(pd, "PersonDetailPanel");
        cardPanel.add(rp, "RegistrationPanel");
        cardPanel.add(ufp, "UserFoundPanel");
        cardPanel.add(upp, "UserProfilePanel");
        add(cardPanel);
    }
    
    void displayView(String viewTitle){
        cardLayout.show(cardPanel, viewTitle);
        setVisible(true);
    }  
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ViewManager frame = new ViewManager();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}