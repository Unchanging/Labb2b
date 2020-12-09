package Trash;

import VehicleClasses.GeneralVehicle;
import VehicleClasses.Saab95;
import VehicleClasses.Scania;
import VehicleClasses.VehicleFactory;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */


public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private int delay = 50;
    // The timer is started with an listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    private CarView frame;
    // A list of cars, modify if needed
    private List<GeneralVehicle> cars;

    //methods:

    public static void main(String[] args) {

        // Instance of this class
        CarController cc = new CarController();

    }

    public CarController() {

        userSelectSimulationSpeed();
        cars = userSelectVehicles();

        // Start a new view and send a reference of self
        frame = new CarView("CarSim 1.0", this);

        // Start the timer
        timer.start();

    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (GeneralVehicle car : cars) {
                turnRandomly(car);
                detectVehicleCollision(car);
                car.move();
                turnAtEdgeCollision(car);
                // repaint() calls the paintComponent method of the panel
                frame.repaint();
            }
        }
    }

    private void userSelectSimulationSpeed() {
//        Integer[] speedOptions = {20, 50, 100};
//        int chosenSpeedOption = JOptionPane.showOptionDialog(null, "Choose the simulation speed", "Speed Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, speedOptions, speedOptions[0]);
//        timer.setDelay(speedOptions[chosenSpeedOption]);
        timer.setDelay(20);
    }

    private List<GeneralVehicle> userSelectVehicles() {
        List<GeneralVehicle> resListOfCars = new ArrayList<>();

        String[] vehicleOptions = {"Volvo240", "Saab95", "Scania"};

        int chosenVehicle = showVehicleChoiceDialog(vehicleOptions); // Lets the user make in initial choice of Vehicles
        int y = 0;

        while (chosenVehicle != -1) { //repeat the loop until the user dismisses the dialog
            GeneralVehicle vehicle = VehicleFactory.createByParameter(vehicleOptions[chosenVehicle]); // manufacture a vehicle according to the User input
            vehicle.setPosition(new Point(0, y)); // move the new Vehicle down from the starting position
            y += 100; //increase how far down the vehicle will be place for the next loop
            resListOfCars.add(vehicle); // add the new vehicle to the list which will be returned
            chosenVehicle = showVehicleChoiceDialog(vehicleOptions); // Let the User select another vehicle or cancel the loop by closing the dialog
        }
        return resListOfCars;
    }

    private int showVehicleChoiceDialog(String[] vehicleOptions) {
        return JOptionPane.showOptionDialog(null, "Choose a vehicle", "Vehicle Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, vehicleOptions, vehicleOptions[0]);
    }

    private void detectVehicleCollision(GeneralVehicle thisCar){

        Dimension thisCarDimension = frame.getImageDimension(thisCar);
        Rectangle2D thisCarRect = new Rectangle(thisCar.getPosition(), thisCarDimension);

        for (GeneralVehicle otherCar : cars.stream().filter(c -> !c.equals(thisCar)).collect(Collectors.toUnmodifiableList())) {
            Dimension otherCarDimension = frame.getImageDimension(otherCar);
            Rectangle2D otherCarRect = new Rectangle(otherCar.getPosition(), otherCarDimension);

            if (thisCarRect.intersects(otherCarRect)) {
                turnerHelper(thisCar, getDirectionAwayFrom(thisCar, otherCar));
                turnerHelper(otherCar, getDirectionAwayFrom(otherCar, thisCar));
            }
        }
    }

    private double getDirectionAwayFrom(GeneralVehicle firstCar, GeneralVehicle secondCar) {
        double deltaX = firstCar.getPosition().getX() - secondCar.getPosition().getX(); // calculated the difference in x between the cars
        double deltaY = firstCar.getPosition().getY() - secondCar.getPosition().getY(); // calculated the difference in x between the cars
        double res =  Math.atan2(deltaY, deltaX); //function which returns the angle corresponding to the change in x and y
        if (res < 0)
            res += 2 * Math.PI;
        return res;
    }

    private void turnAtEdgeCollision(GeneralVehicle car) {
        int X = (int) (frame.getDrawPanelDimension().getWidth() - frame.getImageDimension(car).getWidth());
        int Y = (int) (frame.getDrawPanelDimension().getHeight() - frame.getImageDimension(car).getHeight());
        if (car.getPosition().getX() < 0)
            turnerHelper(car, 0);
        else if (car.getPosition().getX() > X )
            turnerHelper(car, Math.PI);
        else if (car.getPosition().getY() < 0)
            turnerHelper(car, Math.PI*0.5);
        else if (car.getPosition().getY() > Y)
            turnerHelper(car, Math.PI*1.5);
    }

    private void turnRandomly(GeneralVehicle car) {
        if (Math.random() > 0.999)
            car.turnRight();
        else if (Math.random() > 0.999)
            car.turnLeft();
    }

    private void turnerHelper(GeneralVehicle car, double directionRadians) {
        while (Math.abs(car.getHeading() - directionRadians) > 0.25)
            car.turnRight();
    }

    public List<GeneralVehicle> getCars() {
        return cars;
    }

    // Calls the gas method for each car once
    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        cars.forEach(c -> c.gas(gas));
    }

    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        cars.forEach(c -> c.brake(brake));
    }

    public void turboOn() {
        cars.stream().filter(c -> c instanceof Saab95).map(Saab95.class::cast).forEach(Saab95::setTurboOn);
    }

    public void turboOff() {
        cars.stream().filter(c -> c instanceof Saab95).map(Saab95.class::cast).forEach(Saab95::setTurboOff);
    }

    public void startAllCars(){
        cars.forEach(GeneralVehicle::startEngine);
    }

    public void stopAllCars(){
        cars.forEach(GeneralVehicle::stopEngine);
    }

    public void liftAllScaniaBeds() {
        cars.stream().filter(c -> c instanceof Scania).map(Scania.class::cast).forEach(c -> c.raisePlatform(15));
    }

    public void lowerAllScaniaBeds() {
        cars.stream().filter(c -> c instanceof Scania).map(Scania.class::cast).forEach(c -> c.lowerPlatform(15));
    }
}
