package CarSimulation;

import VehicleClasses.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CarModel implements ModelControl, ModelPresenter{

	private final List<GeneralVehicle> cars;
	private Dimension modelArea; // Used for calculating when vehicles collide with the "walls".
	private final List<VehicleListListener> vehicleListListeners = new ArrayList<>();
	private final List<VehicleUpdateListener> updateListeners = new ArrayList<>();
	private final Map<VehiclePresentation, Dimension> vehicleModelSizes = new HashMap<>(); //Clients can specify the dimensions of the simulated Vehicles. Optional
	private int y; // vertical offset between each vehicle. Supposed to be incremented with each new car added without a specified location.

	public CarModel(int X, int Y) {
		cars = new ArrayList<>();
		modelArea = new Dimension(X, Y);
	}

	@Override
	public void updateModel() {
		for (GeneralVehicle car : cars) {
			detectVehicleCollision(car); // Detects if two vehicles are going to intersect when they move next time and turns them away from each other.
			turnAtEdgeCollision(car); // Turns the vehicle towards an appropriate direction if the projected movement would have placed it outside of the modelArea.
			car.move(); // advances the vehicle along its trajectory.
		}
		callUpdateListeners();
	}

	@Override
	public void addCar(String modelName, Point point) { // creates and adds a car at a specified location. Will do nothing if there are 10 or more cars in the model.
		if (cars.size() < 10) {
			GeneralVehicle car = VehicleFactory.createByParameter(modelName);
			car.setPosition(point);
			cars.add(car);
			callVehicleListListeners();
		}
	}

	@Override
	public void addCar(String modelName) { // overloaded method which adds a Point as an argument and call the method addCar(String, Point).
		addCar(modelName, new Point(0, y));
		y += 100; // advances the vertical offset between added cars.
	}

	@Override
	public void removeCar(Point point) { // Removes the vehicle closest to the specified point in th model. Does not do anything if the model has 0 vehicles.

		cars.stream() // creates a stream of all the vehicles in the model
				.sorted((thisCar, otherCar) -> (int)(thisCar.getPosition().distance(point) - otherCar.getPosition().distance(point))) // sorts them according to the proximity to the specified point
				.findFirst() //find the closest vehicle to the point
				.ifPresent(c -> cars.remove(c)); // if a vehicle is found then remove it.
		callVehicleListListeners();
	}

	@Override
	public void removeCar() { // overloaded method which find the location of the earliest added vehicle and calls the removeCar(Point) with that location.
		if (cars.size() > 0)
			removeCar(cars.get(0).getPosition());
	}

	private void turnAtEdgeCollision(GeneralVehicle car) {
		int X = (int) (modelArea.getWidth() - getCarDimension(car).getWidth()); // create boundaries by subtracting the size of the car from the modelArea.
		int Y = (int) (modelArea.getHeight() - getCarDimension(car).getHeight());
		if (car.getMovementProjection().getX() < 0) //turn toward an appropriate direction if the next movement would have placed the vehicle outside the boundaries
			turnerHelper(car, 0);
		else if (car.getMovementProjection().getX() > X )
			turnerHelper(car, Math.PI);
		else if (car.getMovementProjection().getY() < 0)
			turnerHelper(car, Math.PI*0.5);
		else if (car.getMovementProjection().getY() > Y)
			turnerHelper(car, Math.PI*1.5);
	}

	private void detectVehicleCollision(GeneralVehicle thisCar){
		Dimension thisCarDimension = getCarDimension(thisCar);
		Rectangle2D thisCarRect = new Rectangle(thisCar.getMovementProjection(), thisCarDimension); // create a rectangle object which models the vehicle for collisions

		for (GeneralVehicle otherCar : cars.stream().filter(c -> !c.equals(thisCar)).collect(Collectors.toUnmodifiableList())) { // find all other vehicles in the model and loop through them
			Dimension otherCarDimension = getCarDimension(otherCar);
			Rectangle2D otherCarRect = new Rectangle(otherCar.getMovementProjection(), otherCarDimension); // create a rectangle object to model the other vehicle

			if (thisCarRect.intersects(otherCarRect)) { // if the two rectangles intersect turn each vehicle away from the other.
				turnerHelper(thisCar, getDirectionAwayFrom(thisCar, otherCar));
				turnerHelper(otherCar, getDirectionAwayFrom(otherCar, thisCar));
			}
		}
	}

	private double getDirectionAwayFrom(GeneralVehicle firstCar, GeneralVehicle secondCar) {
		double deltaX = firstCar.getPosition().getX() - secondCar.getPosition().getX(); // calculated the difference in x between the cars
		double deltaY = firstCar.getPosition().getY() - secondCar.getPosition().getY(); // calculated the difference in y between the cars
		double res =  Math.atan2(deltaY, deltaX); //function which returns the angle corresponding to the change in x and y

		return res < 0 ? res + 2 * Math.PI : res; // return the direction as a positive radian.
	}

	private void turnerHelper(GeneralVehicle car, double directionRadians) { //turns the car around until the car faces the desired direction with a small tolerance
		while (Math.abs(car.getHeading() - directionRadians) > Math.PI/24)
			car.turnRight();
	}

	@Override
	public Iterator<? extends VehiclePresentation> getVehicleIterator() {
		return new ArrayList<>(cars).iterator(); // TODO: Inefficient. Redo once we understand thread safety and concurrency issues
	}

	@Override
	public void setVehicleDimension(VehiclePresentation car, Dimension dimension) { // Used by clients if they want to specify how the dimensions of the vehicles.
		vehicleModelSizes.putIfAbsent(car, dimension);
	}

	private Dimension getCarDimension(GeneralVehicle car) { //the collision with walls and other cars assumes the car got a default size, but it can be specified. Optional
		if (vehicleModelSizes.containsKey(car))
			return vehicleModelSizes.get(car);
		return new Dimension(100, 50);
	}

	public void addVehicleListListeners(VehicleListListener listener) {
		vehicleListListeners.add(listener);
	}

	private void callVehicleListListeners() {
		vehicleListListeners.forEach(VehicleListListener::VehicleListUpdate);
	}

	public void addVehicleUpdateListeners(VehicleUpdateListener listener) {
		updateListeners.add(listener);
	}

	private void callUpdateListeners() {
		updateListeners.forEach(VehicleUpdateListener::vehicleUpdate);
	}

	@Override
	public void setModelArea(Dimension dimension) { // The size of the model area is set in the constructor but this method can be used to update it. Optional
		modelArea = dimension;
	}

	@Override
	public void gas(int amount) {
		double gas = ((double) amount) / 100;
		cars.forEach(c -> c.gas(gas));
	}

	@Override
	public void brake(int amount) {
		double brake = ((double) amount) / 100;
		cars.forEach(c -> c.brake(brake));
	}

	@Override
	public void turboOn() {
		cars.stream().filter(c -> c instanceof TurboControl).map(TurboControl.class::cast).forEach(TurboControl::setTurboOn);
	}

	@Override
	public void turboOff() {
		cars.stream().filter(c -> c instanceof TurboControl).map(TurboControl.class::cast).forEach(TurboControl::setTurboOff);
	}

	@Override
	public void startAllCars(){
		cars.forEach(GeneralVehicle::startEngine);
	}

	@Override
	public void stopAllCars(){
		cars.forEach(GeneralVehicle::stopEngine);
	}

	@Override
	public void liftAllScaniaBeds() {
		cars.stream().filter(c -> c instanceof PlatformControl).map(PlatformControl.class::cast).forEach(c -> c.raisePlatform(15));
	}

	@Override
	public void lowerAllScaniaBeds() {
		cars.stream().filter(c -> c instanceof PlatformControl).map(PlatformControl.class::cast).forEach(c -> c.lowerPlatform(15));
	}
}
