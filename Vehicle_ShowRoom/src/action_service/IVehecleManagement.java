package action_service;

import java.util.ArrayList;

public interface IVehecleManagement{
    public void addVehicle();

    public void checkExisted();

    public void updateVehicle();

    public void deleteVehicle();

    public void search();

    public void showAll();

    public void displayVehicle();
    
    public void printVehicle();
    
    public void saveToFile(String fname);
    
    public ArrayList<Vehicle> loadFromFile(String filename);
}
