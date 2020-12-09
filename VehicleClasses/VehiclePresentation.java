package VehicleClasses;

import java.awt.*;

public interface VehiclePresentation {

	Point getMovementProjection();

	/**A method which should return the vehicle's current position in 2D space.
	 * @return The vehicle's position.
	 */
	Point getPosition();

	/**A method which should return the vehicle's current heading in radians.
	 * @return The vehicle's current heading in radians.
	 */
	double getHeading();

	/**A method which should return the vehicle's modelName
	 * @return The model name of the vehicle
	 */
	String getModelName();

	/**A method which should return the vehicle's current speed
	 * @return The vehicle's current speed
	 */
	double getCurrentSpeed();
}
