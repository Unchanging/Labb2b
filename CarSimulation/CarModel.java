package CarSimulation;

import VehicleClasses.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CarModel implements ModelControl, ModelPresenter{

	private final List<GeneralVehicle> cars;
	private Dimension modelArea;
	private final List<VehicleListListener> vehicleListListeners = new ArrayList<>();
	private final List<VehicleUpdateListener> updateListeners = new ArrayList<>();
	private final Map<VehiclePresentation, Dimension> vehicleModelSizes = new HashMap<>(); //Clients can specify the dimensions of the simulated Vehicles. Optional
	private int y; // vertical offset between each vehicle. Supposed to be incremented with each new car added.

	public CarModel(int X, int Y) {
		cars = new ArrayList<>();
		modelArea = new Dimension(X, Y);
	}

	@Override
	public void updateModel() {
		synchronized (cars) { //TODO fix thread safety
			for (GeneralVehicle car : cars) {
				detectVehicleCollision(car);
				car.move();
				turnAtEdgeCollision(car);
			}
			callUpdateListeners();
		}
	}

	@Override
	public void addCar(String modelName, Point point) {
		if (cars.size() < 10) {
			GeneralVehicle car = VehicleFactory.createByParameter(modelName);
			car.setPosition(point);
			cars.add(car);
			callVehicleListListeners();
		}
	}

	@Override
	public void addCar(String modelName) {
		addCar(modelName, new Point(0, y));
		y += 100;
	}

	@Override
	public void removeCar(Point point) {
		if (cars.size() > 0)
			cars.stream().sorted((thisCar, otherCar) -> (int)(thisCar.getPosition().distance(point) - otherCar.getPosition().distance(point)))
					.findFirst()
					.ifPresent(c -> cars.remove(c));
		callVehicleListListeners();
	}

	@Override
	public void removeCar() {
		if (cars.size() > 0)
			removeCar(cars.get(0).getPosition());
	}

	private void turnAtEdgeCollision(GeneralVehicle car) {
		int X = (int) (modelArea.getWidth() - getCarDimension(car).getWidth());
		int Y = (int) (modelArea.getHeight() - getCarDimension(car).getHeight());
		if (car.getMovementProjection().getX() < 0)
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
		Rectangle2D thisCarRect = new Rectangle(thisCar.getPosition(), thisCarDimension);

		for (GeneralVehicle otherCar : cars.stream().filter(c -> !c.equals(thisCar)).collect(Collectors.toUnmodifiableList())) {
			Dimension otherCarDimension = getCarDimension(otherCar);
			Rectangle2D otherCarRect = new Rectangle(otherCar.getPosition(), otherCarDimension);

			if (thisCarRect.intersects(otherCarRect)) {
				turnerHelper(thisCar, getDirectionAwayFrom(thisCar, otherCar));
				turnerHelper(otherCar, getDirectionAwayFrom(otherCar, thisCar));
			}
		}
	}

	private double getDirectionAwayFrom(GeneralVehicle firstCar, GeneralVehicle secondCar) {
		double deltaX = firstCar.getPosition().getX() - secondCar.getPosition().getX(); // calculated the difference in x between the cars
		double deltaY = firstCar.getPosition().getY() - secondCar.getPosition().getY(); // calculated the difference in y between the cars
		double res =  Math.atan2(deltaY, deltaX); //function which returns the angle corresponding to the change in x and y
		if (res < 0)
			res += 2 * Math.PI;
		return res;
	}

	private void turnerHelper(GeneralVehicle car, double directionRadians) { //turns the car around until the car faces the desired direction with a small tolerance
		while (Math.abs(car.getHeading() - directionRadians) > Math.PI/24)
			car.turnRight();
	}

	@Override
	public Iterator<? extends VehiclePresentation> getVehicleIterator() {
//		synchronized (cars) {
//			return cars.stream().iterator(); // TODO Fix thread safety
//		}
//
		return new ArrayList<>(cars).iterator();
	}

	@Override
	public void setVehicleDimension(VehiclePresentation car, Dimension dimension) {
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
