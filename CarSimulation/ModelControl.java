package CarSimulation;

//TODO change to interface

import java.awt.*;

public interface ModelControl {

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
