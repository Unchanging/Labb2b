package VehicleClasses;

import java.awt.*;

/**
 *  A class for a VehicleClasses.Saab95
 */
public class Saab95 implements Car, TurboControl{
	private boolean turboOn;
	private Vehicle vehicleModel;

	/**
	 * Constructor for the Saab 95.
	 */
	protected Saab95(){
		vehicleModel = new Vehicle(2, Color.RED, 125, "Saab95");

		turboOn = false;
	}

	/**
	 * Turns the turbo on.
	 */
	public void setTurboOn(){
		turboOn = true;
	}

	/**
	 * Turns the turbo off.
	 */
	public void setTurboOff(){
		turboOn = false;
	}

	/**
	 * Method which returns the speed factor of the car.
	 * @return the speed factor which determines how the car accelerates or decelerates.
	 */
	@Override
	public double speedFactor(){
		double turbo = 1;
		if(turboOn) turbo = 1.3;
		return vehicleModel.getEnginePower() * 0.01 * turbo;
	}

	/**Returns the number of doors of the vehicle.
	 * @return The number of doors of the vehicle.
	 */
	@Override
	public int getNrDoors() {
		return vehicleModel.getNrDoors();
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

	/** Sets the position of the vehicle
	 * @param position The position to be set.
	 */
	@Override
	public void setPosition(Point position) {
		vehicleModel.setPosition(position);
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
	 * Increases the speed of the vehicle.
	 * @param amount The factor of the increase. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void gas(double amount) throws RuntimeException{
		vehicleModel.gas(amount, speedFactor());
	}

	/**
	 * Decreases the speed of the vehicle.
	 * @param amount The factor of the decrease. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void brake(double amount)throws RuntimeException{
		vehicleModel.brake(amount, speedFactor());
	}

	/**
	 * Starts the vehicle by setting the variable currentspeed to a positive value.
	 */
	@Override
	public void startEngine() {
		vehicleModel.startEngine(speedFactor());
	}

	/**
	 * Stops the vehicle by setting the variable currentspeed to zero.
	 */
	@Override
	public void stopEngine() {
		vehicleModel.stopEngine();
	}

	/**
	 * Implements the move method from the Application.Movable interface. Changes the vehicle's position to a position further along the current heading by a distance determined by the vehicle's current speed.
	 */
	@Override
	public void move() {
		vehicleModel.move();
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

