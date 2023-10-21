/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import action_service.VehicleManagement;
import menuGUI.MainMenu;

/**
 *
 * @author ASUS
 */
public class MainProgram {

    public static void main(String[] args) {
        VehicleManagement vm = new VehicleManagement();
        MainMenu mn = new MainMenu("Show Room");
        boolean continues = true;

        mn.addOptions("Add new Vehicle");
        mn.addOptions("Check exists Vehicle");
        mn.addOptions("Update Vehicle");
        mn.addOptions("Delete Vehicle");
        mn.addOptions("Search Vehicle");
        mn.addOptions("Display Vehicle (in show room)");
        mn.addOptions("Save Vehicle to file (.dat)");
        mn.addOptions("Print Vehicle from the file");
        mn.addOptions("Exit Program");

        while (continues) {
            mn.printMenu();
            switch (mn.getOption()) {
                case 1:
                    vm.addVehicle();
                    break;
                case 2:
                    vm.checkExisted();
                    break;
                case 3:
                    vm.updateVehicle();
                    break;
                case 4:
                    vm.deleteVehicle();
                    break;
                case 5:
                    vm.search();
                    break;
                case 6:
                    vm.displayVehicle();
                    break;
                case 7:
                    vm.saveToFile("vehicle.dat");
                    break;
                case 8:
                    vm.printVehicle();
                    break;
                case 9:
                    System.out.println("Thank you for coming");
                    continues = false;
            }
        }
    }
}