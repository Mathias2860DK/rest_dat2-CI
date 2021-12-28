/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Employee;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade employeeFacade = EmployeeFacade.getEmployeeFacade(emf);
        employeeFacade.create(new Employee("testName","testAdress",999));
        employeeFacade.create(new Employee("testName","testAdress123",999));
        employeeFacade.create(new Employee("testName2","testAdress2",999));
        employeeFacade.create(new Employee("testName3","testAdress3",999));
//test
        //test 2
        
    }
    
    public static void main(String[] args) {
        populate();
    }
}
