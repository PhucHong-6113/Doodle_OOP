/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import action_service.WarehouseManagement;
import menuGUI.MainMenu;

/**
 *
 * @author ASUS
 */
public class MainProgram {

    public static void main(String[] args) {
        WarehouseManagement wm = new WarehouseManagement();

        MainMenu mn = new MainMenu();
        mn.addOptions("Manage Product");
        mn.addOptions("Manage Warehouse");
        mn.addOptions("Report");
        mn.addOptions("Store/Load data (.dat)");
        mn.addOptions("End Program");

        boolean continues = true;

        while (continues) {
            mn.printMenu();
            switch (mn.getOption()) {
                case 1:
                    MainMenu mn1 = new MainMenu();
                    mn1.addOptions("Add Product");
                    mn1.addOptions("Update Product");
                    mn1.addOptions("Delete Product");
                    mn1.addOptions("Show All Product");
                    mn1.addOptions("Exit to main menu");

                    boolean continues1 = true;

                    while (continues1) {
                        mn1.printMenu();
                        switch (mn1.getOption()) {
                            case 1:
                                wm.pm.addProduct();
                                break;
                            case 2:
                                wm.pm.updateProduct();
                                break;
                            case 3:
                                wm.pm.deleteProduct();
                                break;
                            case 4:
                                wm.pm.showAll();
                                break;
                            case 5:
                                continues1 = false;
                                break;
                        }
                    }
                    break;
                case 2:
                    MainMenu mn2 = new MainMenu();
                    mn2.addOptions("Create Import Receipt");
                    mn2.addOptions("Create Export Receipt");
                    mn2.addOptions("Return to Main menu");

                    boolean continues2 = true;

                    while (continues2) {
                        mn2.printMenu();
                        switch (mn2.getOption()) {
                            case 1:
                                wm.createImportReceipt();
                                break;
                            case 2:
                                wm.createExportReceipt();
                                break;
                            case 3:
                                continues2 = false;
                                break;
                        }
                    }
                    break;
                case 3:
                    MainMenu mn3 = new MainMenu();
                    mn3.addOptions("List products that have expired");
                    mn3.addOptions("List products that the store is selling");
                    mn3.addOptions("List products that are running out of stock");
                    mn3.addOptions("List product import/export receipt");
                    mn3.addOptions("Return to main menu");

                    boolean continues3 = true;

                    while (continues3) {
                        mn3.printMenu();
                        switch (mn3.getOption()) {
                            case 1:
                                wm.pm.expiredProduct();
                                break;
                            case 2:
                                wm.pm.sellingProduct();
                                break;
                            case 3:
                                wm.pm.outOfStockProduct();
                                break;
                            case 4:
                                wm.printProductReceipt();
                                break;
                            case 5:
                                continues3 = false;
                                break;
                        }
                    }
                    break;
                case 4:
                    MainMenu mn4 = new MainMenu();
                    mn4.addOptions("Save data to file");
                    mn4.addOptions("Load data from file");

                    mn4.printMenu();
                    switch (mn4.getOption()) {
                        case 1:
                            wm.pm.saveProductDataToFile("product.dat");
                            wm.saveWarehouseDataToDAT();
                            break;
                        case 2:
                            wm.pm.loadProductDataFromFile("product.dat");
                            break;
                    }

                    break;
                case 5:
                    System.out.println("Ending Program, goodbye!!");
                    continues = false;
                    break;
            }
        }
    }
}
