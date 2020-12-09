package VehicleClasses;

import java.awt.*;
import java.awt.geom.Line2D;

public class Vehicle {

	private double enginePower; // Engine power of the vehicle
	private double currentSpeed; // The current speed of the vehicle
	private Color color; // Color of the vehicle
	private Point position; // current position
	private double heading; // current heading in radians
	private int nrDoors; // Number of doors on the vehicle

	private String modelName; // The vehicle model name

	/**A constructor used by the constructor of specific vehicles to model the behaviours of those vehicles
	 * @param color The color of the vehicle
	 * @param enginePower The power of the vehicle's engine
	 * @param modelName The model name of the vehicle
	 */
	public Vehicle(Color color, double enginePower, String modelName) {

		this.color = color;
		this.enginePower = enginePower;
		this.modelName = modelName;

		position = new Point();
		heading = 0;
	}

	/**A constructor used by the constructor of specific vehicles with doors to model the behaviours of those vehicles
	 * @param nrDoors The number of doors
	 * @param color The color of the vehicle
	 * @param enginePower The power of the vehicle's engine
	 * @param modelName The model name of the vehicle
	 */
	public Vehicle(int nrDoors, Color color, double enginePower, String modelName) {

		this(color, enginePower, modelName);
		this.nrDoors = nrDoors;
	}

	/**Returns the number of doors of the vehicle.
	 * @return The number of doors of the vehicle.
	 */
	public int getNrDoors(){
		return nrDoors;
	}

	/**Returns the engine power of the vehicle.
	 * @return The engine power of the vehicle.
	 */
	public double getEnginePower(){
		return enginePower;
	}

	/** Returns the model name of the vehicle.
	 * @return The model name of the vehicle.
	 */
	public String getModelName() {
		return modelName;
	}

	/**Returns the current speed of the vehicle.
	 * @return The current speed of the vehicle.
	 */
	public double getCurrentSpeed(){
		return currentSpeed;
	}

	/**Returns the color of the vehicle.
	 * @return The color of the vehicle.
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Sets the color of the vehicle.
	 * @param clr color which is to be set.
	 */
	public void setColor(Color clr){
		color = clr;
	}

	/** Sets the speed of the vehicle
	 * @param speed The speed of the vehicle
	 */
	public void setCurrentSpeed(double speed) {
		currentSpeed = speed;
	}

	/**
	 * Starts the vehicle by setting the variable currentspeed to a positive value.
	 */
	public void startEngine(double speedFactor){
		if (currentSpeed == 0)
			incrementSpeed(0.1, speedFactor);
	}

	/**
	 * Stops the vehicle by setting the variable currentspeed to zero.
	 */
	public void stopEngine(){
		currentSpeed = 0;
	}

	/**
	 * Increases the speed of the vehicle up to a maximum limit of the vehicle's engine power.
	 * @param amount The factor by which the vehicle will increase its speed.
	 */
	public void incrementSpeed(double amount, double speedFactor) {
		currentSpeed = Math.min(getCurrentSpeed() + speedFactor * amount, enginePower);
	}

	/**
	 * Decreases the speed of the vehicle down to a minimum of zero.
	 * @param amount The factor by which the vehicle will decrease its speed.
	 */
	public void decrementSpeed(double amount,double speedFactor) {
		currentSpeed = Math.max(getCurrentSpeed() - speedFactor * amount, 0);
	}


	/**
	 * Increases the speed of the vehicle.
	 * @param amount The factor of the increase. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	public void gas(double amount, double speedFactor) throws RuntimeException{
		if (amount <= 1 && amount >= 0)
			incrementSpeed(amount, speedFactor);
		else
			throw new RuntimeException("input was not in interval [0, 1]");
	}

	/**
	 * Decreases the speed of the vehicle.
	 * @param amount The factor of the decrease. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	public void brake(double amount, double speedFactor) throws RuntimeException{
		if (amount <= 1 && amount >= 0)
			decrementSpeed(amount, speedFactor);
		else
			throw new RuntimeException("input was not in interval [0, 1]");
	}

	/**Returns the vehicle's current position as a Point.
	 * @return The vehicle's current position as a Point.
	 */
	public Point getPosition(){
		return position.getLocation();
	}

	public void setPosition(Point position) {
		this.position = position.getLocation();
	}

	/**Return the vehicle's current heading in radians.
	 * @return The vehicle's current heading in radians.
	 */
	public double getHeading() {
		return heading % (2*Math.PI);
	}

	/**
	 * Implements the move method from the Application.Movable interface. Changes the vehicle's position to a position further along the current heading by a distance determined by the vehicle's current speed.
	 */
	public void move() {
		position.translate((int)(Math.cos(heading)*getCurrentSpeed()), (int)(Math.sin(heading)*getCurrentSpeed()));
	}


	/**
	 * Implements the turnLeft method from the Application.Movable interface. Changes the vehicle's heading by half a radian to the left.
	 */
	public void turnLeft() {
		heading -= Math.PI/16;
	}

	/**
	 * Implements the turnRight method from the Application.Movable interface. Changes the vehicle's heading by half a radian to the right.
	 */
	public void turnRight() {
		heading += Math.PI/16;
	}

	/**
	 * @return The projected destination after move() is called again.
	 */
	public Point getMovementProjection() {
		Point nextPosition = position.getLocation();
		nextPosition.translate((int)(Math.cos(heading)*getCurrentSpeed()), (int)(Math.sin(heading)*getCurrentSpeed()));
		return nextPosition;
	}

	public String toString() {
		return this.getModelName() + " at X=" + this.getPosition().getX() + ",Y=" + this.getPosition().getY()+ " with velocity " + this.getCurrentSpeed();
	}

}
