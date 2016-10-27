/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch;

import com.tenaciouspanda.jobstretch.frontend.DashboardPanel;
import com.tenaciouspanda.jobstretch.frontend.LoginPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
 import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainClass extends JFrame {

    private static JPanel cardPanel;
    private static CardLayout cardLayout = new CardLayout();

    public MainClass() {
        setTitle("Job Stretch");
        setSize(820, 490);
        DashboardPanel dashboard = new DashboardPanel();
        LoginPanel login = new LoginPanel();
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(dashboard, "dashboard");
        cardPanel.add(login, "login");
        cardLayout.show(cardPanel, "login");
        add(cardPanel);
        
    }
    
    public static void displayDashboard(){
        cardLayout.show(cardPanel, "dashboard");
    }
    
    public static void displayLogin(){
        cardLayout.show(cardPanel, "login");
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainClass frame = new MainClass();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}