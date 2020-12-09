package VehicleClasses;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LargeFerry <typeOfVehicle extends GeneralVehicle> implements GeneralVehicle {

	private List<FerryLane<typeOfVehicle>> listOfLanes;
	private Vehicle vehicleModel;

	protected LargeFerry(int nrOfLanes, int maxNrOfVehicles) {

		listOfLanes = new ArrayList<>();
		for(int i = 0; i < nrOfLanes; i++) {
			listOfLanes.add(new FerryLane<>(maxNrOfVehicles));
		}

		vehicleModel = new Vehicle(Color.GREEN, 40, "M/V Boaty McBigBoatface");
	}

	public void addUnit(int lane, typeOfVehicle vehicle) {
		if (getPosition().distance(vehicle.getPosition()) > 10)
			throw new RuntimeException("The vehicle is too far away from the ferry to be loaded. Max distance is 10 units");
		listOfLanes.get(lane).addUnit(vehicle);
	}

	public typeOfVehicle unloadUnit(int lane) {
		typeOfVehicle tempVehicle = listOfLanes.get(lane).unloadFirstUnit();
		Point tempPosition = getPosition();
		tempPosition.translate(5, 5);
		tempVehicle.setPosition(tempPosition);
		return tempVehicle;
	}

	public int getNrOfUnits() {
		int res = 0;
		for (FerryLane lane : listOfLanes)
			res += lane.getNrOfUnits();
		return res;
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
	 * Increases the speed of the vehicle.
	 * @param amount The factor of the increase. Must be in the interval [0, 1].
	 * @throws RuntimeException If the input is not within the interval [0, 1].
	 */
	@Override
	public void gas(double amount) throws RuntimeException{
		vehicleModel.gas(amount, speedFactor());
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
		for (FerryLane lane : listOfLanes)
			lane.updatePositions(this.getPosition());
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
