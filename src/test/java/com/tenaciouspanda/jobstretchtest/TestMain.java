/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenaciouspanda.jobstretchtest;

/**
 *
 * @author Simon
 */
public class TestMain {
    public static void main(String[] args) {
        (new GeocoderTester()).testAll();
        //(new SessionTester()).testAll();
        (new DBTester()).testAll();
        System.out.println("All tests successful");
    }
}
