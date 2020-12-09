package VehicleClasses;

import java.awt.*;
import java.util.Iterator;

/**
 * A class for a car transport truck
 */
public class CarTransport implements Truck, Storer<Car> {

	private Vehicle vehicleModel;
	private boolean rampIsDown;
	private UnitStorage<Car> vehicleStorage;


	/**
	 * Constructor for a car transport truck.
	 */
	protected CarTransport() {
		vehicleModel = new Vehicle(2, Color.RED, 90, "Iveco");
		rampIsDown = false;
		vehicleStorage = new UnitStorage<>(6);
	}

	/**
	 * Method which returns the speed factor of the car.
	 * @return the speed factor which determines how the car accelerates or decelerates.
	 */
	public double speedFactor() {
		return vehicleModel.getEnginePower() * 0.004;
	}

	/**
	 * Raises the ramp
	 */
	public void raiseRamp() {
		rampIsDown = false;
	}

	/**
	 * Lowers the ramp
	 */
	public void lowerRamp(){
		if (getCurrentSpeed() == 0)
			rampIsDown = true;
		else
			System.out.println("Can't lower the ramp while moving");
	}

	/**
	 * @return True if the ramp is down
	 */
	public boolean isRampIsDown() {
		return rampIsDown;
	}

	/** Places a car on the truck
	 * @param vehicle The car to be placed in on the truck
	 */
	public void addUnit(Car vehicle) {
		if (isRampIsDown() && this.getPosition().distance(vehicle.getPosition()) <= 10)
			vehicleStorage.addUnit(vehicle);
		else
			System.out.println("Can't add a car to the transport");

	}

	/** Unloads and returns the last added car
	 * @return The offloaded car.
	 * @throws IllegalStateException Shu
	 */
	public Car unloadUnit() throws IllegalStateException{
		if (rampIsDown) {
			Car tempCar = vehicleStorage.unloadLastUnit();
			Point tempPosition = getPosition();
			tempPosition.translate(5, 5);
			tempCar.setPosition(tempPosition);
			return tempCar;
		}
		else
			throw new IllegalStateException("Can not unload cars while the ramp is not down");
	}

	/** Return an unmodifiable iterator over the cars stored on the truck.
	 * @return An unmodifiable iterator over the cars stored on the truck.
	 */
	public Iterator<Car> getIterator() {
		return vehicleStorage.getIterator();
	}

	/** Return the number of cars on the truck.
	 * @return The numer of cars on the truck.
	 */
	public int getNrOfUnits() {
		return vehicleStorage.getNrOfUnits();
	}

	/**
	 * Increases the speed of the vehicle up to a maximum limit of the vehicle's engine power.
	 * @param amount The factor by which the vehicle will increase its speed.
	 */
	@Override
	public void incrementSpeed(double amount){
		if (isReadyToDrive()) {
			vehicleModel.setCurrentSpeed(Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()));
		}
		else
			System.out.println("Can't increment speed while the ramp is lowered");
	}

	/**
	 * Implements the move method from the Application.Movable interface. Changes the vehicle's position to a position further along the current heading by a distance determined by the vehicle's current speed.
	 * Also updates the transported cars location to match the truck.
	 */
	@Override
	public void move() {
		vehicleModel.move();
		vehicleStorage.updatePositions(this.getPosition());
	}

	/**Returns the number of doors of the vehicle.
	 * @return The number of doors of the vehicle.
	 */
	@Override
	public int getNrDoors() {
		return vehicleModel.getNrDoors();
	}


	/** Returns true if the vehicle can drive.
	 * @return True if the vehicle can drive.
	 */
	@Override
	public boolean isReadyToDrive() {
		return !isRampIsDown();
	}

	/**Returns the engine power of the vehicle.
	 * @return The engine power of the vehicle.
	 */
	@Override
	public double getEnginePower() {
		return vehicleModel.getEnginePower();
	}

	/** Returns the model name of the vehicle.
	 * @return The model name of the vehicle.
	 */
	@Override
	public String getModelName() {
		return vehicleModel.getModelName();
	}

	/**Returns the current speed of the vehicle.
	 * @return The current speed of the vehicle.
	 */
	@Override
	public double getCurrentSpeed() {
		return vehicleModel.getCurrentSpeed();
	}

	/**Returns the color of the vehicle.
	 * @return The color of the vehicle.
	 */
	@Override
	public Color getColor() {
		return vehicleModel.getColor();
	}

	/** Sets the position of the vehicle
	 * @param position The position to be set.
	 */
	@Override
	public void setPosition(Point position) {
		vehicleModel.setPosition(position);
	}

	/**Returns the vehicle's current position as a Point.
	 * @return The vehicle's current position as a Point.
	 */
	@Override
	public Point getPosition() {
		return vehicleModel.getPosition();
	}

	/**Return the vehicle's current heading in radians.
	 * @return The vehicle's current heading in radians.
	 */
	@Override
	public double getHeading() {
		return vehicleModel.getHeading();
	}

	/**
	 * Sets the color of the vehicle.
	 * @param color color which is to be set.
	 */
	@Override
	public void setColor(Color color) {
		vehicleModel.setColor(color);
	}

	/**
	 * Decreases the speed of the vehicle down to a minimum of zero.
	 * @param amount The factor by which the vehicle will decrease its speed.
	 */
	@Override
	public void decrementSpeed(double amount) {
		vehicleModel.decrementSpeed(amount, speedFactor());
	}

	/**
	 * Increases the speed of the vehicle.
	 * @param amount The factor of the increase. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void gas(double amount) throws RuntimeException{
		if (isReadyToDrive()) {
			vehicleModel.gas(amount, speedFactor());
		}
		else
			System.out.println("Can't increment speed while the ramp is lowered");
	}

	/**
	 * Decreases the speed of the vehicle.
	 * @param amount The factor of the decrease. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void brake(double amount) throws RuntimeException{
		vehicleModel.brake(amount, speedFactor());
	}

	/**
	 * Starts the vehicle by setting the variable currentspeed to a positive value.
	 */
	@Override
	public void startEngine() {
		if (isReadyToDrive()) {
			vehicleModel.startEngine(speedFactor());
		}
		else
			System.out.println("Can't increment speed while the ramp is lowered");
	}

	/**
	 * Stops the vehicle by setting the variable currentspeed to zero.
	 */
	@Override
	public void stopEngine() {
		vehicleModel.stopEngine();
	}

	/**
	 * Implements the turnLeft method from the Application.Movable interface. Changes the vehicle's heading by half a radian to the left.
	 */
	@Override
	public void turnLeft() {
		vehicleModel.turnLeft();
	}

	/**
	 * Implements the turnRight method from the Application.Movable interface. Changes the vehicle's heading by half a radian to the right.
	 */
	@Override
	public void turnRight() {
		vehicleModel.turnRight();
	}

	/**
	 * @return The projected destination after move() is called again.
	 */
	public Point getMovementProjection() {
		return vehicleModel.getMovementProjection();
	}

	@Override
	public String toString() {
		return vehicleModel.toString();
	}
}
