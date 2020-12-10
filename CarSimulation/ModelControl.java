package CarSimulation;

import java.awt.*;

public interface ModelControl {

	//An interface implemented by the CarModel and used by classes which want to control the model.

	void updateModel();
	void setModelArea(Dimension dimension);
	void addCar(String modelName, Point point);
	void addCar(String modelName);
	void removeCar(Point point);
	void removeCar();

	void gas(int amount);
	void brake(int amount);
	void turboOn();
	void turboOff();
	void startAllCars();
	void stopAllCars();
	void liftAllScaniaBeds();
	void lowerAllScaniaBeds();
}
