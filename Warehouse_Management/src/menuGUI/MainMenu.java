/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menuGUI;

import tool.Inputter;

/**
 *
 * @author RLappc.com
 */
public class MainMenu {

    public String title;
    public int count = 1;
    public String[] optionList = new String[20];

    public MainMenu() {
        title = "Menu";
    }

    public MainMenu(String title) {
        this.title = title;
    }

    public void addOptions(String options) {
        optionList[count] = options;
        this.count++;
    }

//    public void printMenu(){
//        
//    }
//    
//    public void printSide(){
//        
//    }
    public void printMenu() {
        System.out.println("------------$ " + title + " $------------");
        for (int i = 1; i <= count - 1; i++) {
            System.out.println(i + ". " + optionList[i]);
        }
    }

    public int getOption() {
        return Inputter.getInteger("Please input your choice :", "The value should be between 1 and " + (count), 1, count);
    }
}
