/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretch;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Simon
 */
 public class MainClass {
    public static void main(String[] args){  
        final JFrame frame = new JFrame();

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        MapsPanel mvp = new MapsPanel();
        frame.add(mvp);
        mvp.addMarker(47.6, -122.3, "Marker");
        mvp.addMarker(34.6, -83, "Marker2");
        mvp.clear();
    }
 }
