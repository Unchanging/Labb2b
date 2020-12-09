package VehicleClasses;

import java.awt.*;
import java.util.Iterator;

/**A class which models a ferry which can carry vehicles that implements the Application.GeneralVehicle interface.
 * Specific kind of vehicle can be set by generics to classes which implement Application.GeneralVehicle.
 * @param <typeOfVehicle> The kind of vehicle this ferry should carry.
 */
public class Ferry<typeOfVehicle extends GeneralVehicle> implements GeneralVehicle, Storer<typeOfVehicle> {

	private UnitStorage<typeOfVehicle> vehicleStorage;
	private Vehicle vehicleModel;

	/** Constructor for the ferry.
	 * @param maxNrOfVehicles Sets the number of vehicle which the ferry can store.
	 */
	protected Ferry(int maxNrOfVehicles) {
		vehicleStorage = new UnitStorage<>(maxNrOfVehicles);
		vehicleModel = new Vehicle(Color.GREEN, 40, "M/V Boaty McBoatface");

	}

	/**
	 * Increases the speed of the vehicle.
	 * @param amount The factor of the increase. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void gas(double amount) throws RuntimeException{
		vehicleModel.gas(amount, speedFactor());
	}

	/**Adds a vehicle to the ferry
	 * @param vehicle The VehicleClasses.Vehicle to be stored on the ferry
	 */
	@Override
	public void addUnit(typeOfVehicle vehicle) {
		if (getPosition().distance(vehicle.getPosition()) > 10)
			throw new RuntimeException("The vehicle is too far away from the ferry to be loaded. Max distance is 10 units");
		vehicleStorage.addUnit(vehicle);
	}

	/** Removes and returns the first loaded vehicle on the ferry.
	 * @return The offloaded vehicle
	 */
	@Override
	public typeOfVehicle unloadUnit() {
		typeOfVehicle tempVehicle = vehicleStorage.unloadFirstUnit();
		Point tempPosition = getPosition();
		tempPosition.translate(5, 5);
		tempVehicle.setPosition(tempPosition);
		return tempVehicle;
	}

	/** Returns an iterator of the vehicles on the ferry.
	 * The iterator is made unmodifiable and calls to .remove() will not work
	 * @return An iterator of the vehicles on the ferry
	 */
	@Override
	public Iterator<typeOfVehicle> getIterator() {
		return vehicleStorage.getIterator();
	}

	/**Returns the number of vehicles currently on the ferry.
	 * @return the number of vehicle currently on the ferry.
	 */
	@Override
	public int getNrOfUnits() {
		return vehicleStorage.getNrOfUnits();
	}

	@Override
	public double speedFactor() {
		return vehicleModel.getEnginePower() * 0.002;
	}

	/**Returns the engine power of the vehicle.
	 * @return The engine power of the vehicle.
	 */
	@Override
	public double getEnginePower(){
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
	public double getCurrentSpeed(){
		return vehicleModel.getCurrentSpeed();
	}


	/**Returns the color of the vehicle.
	 * @return The color of the vehicle.
	 */
	@Override
	public Color getColor(){
		return vehicleModel.getColor();
	}

	/**
	 * Sets the color of the vehicle.
	 * @param clr color which is to be set.
	 */
	@Override
	public void setColor(Color clr){
		vehicleModel.setColor(clr);
	}

	/**
	 * Starts the vehicle by setting the variable currentspeed to a positive value.
	 */
	@Override
	public void startEngine(){
		vehicleModel.startEngine(speedFactor());
	}

	/**
	 * Stops the vehicle by setting the variable currentspeed to zero.
	 */
	@Override
	public void stopEngine(){
		vehicleModel.stopEngine();
	}

	/**
	 * Increases the speed of the vehicle up to a maximum limit of the vehicle's engine power.
	 * @param amount The factor by which the vehicle will increase its speed.
	 */
	@Override
	public void incrementSpeed(double amount) {
		vehicleModel.incrementSpeed(amount, speedFactor());
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
	 * Decreases the speed of the vehicle.
	 * @param amount The factor of the decrease. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void brake(double amount) throws RuntimeException{
		vehicleModel.brake(amount, speedFactor());
	}

	/**Returns the vehicle's current position as a Point.
	 * @return The vehicle's current position as a Point.
	 */
	@Override
	public Point getPosition(){
		return vehicleModel.getPosition();
	}

	public void setPosition(Point position) {
		vehicleModel.setPosition(position);
	}

	/**Return the vehicle's current heading in radians.
	 * @return The vehicle's current heading in radians.
	 */
	@Override
	public double getHeading() {
		return vehicleModel.getHeading();
	}

	/**
	 * Implements the move method from the Application.Movable interface. Changes the vehicle's position to a position further along the current heading by a distance determined by the vehicle's current speed.
	 */
	@Override
	public void move() {
		vehicleModel.move();
		vehicleStorage.updatePositions(this.getPosition());
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
