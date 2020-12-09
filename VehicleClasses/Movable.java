package VehicleClasses;

/**
 * An interface which models the behaviours of a movable vehicle
 */
public interface Movable {

	/**
	 * A method which should move the vehicle.
	 */
	public void move();

	/**
	 * A method which should rotate the vehicle to the left.
	 */
	public void turnLeft();

	/**
	 * A method which should rotate the vehicle to the right.
	 */
	public void turnRight();
}
