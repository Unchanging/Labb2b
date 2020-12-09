package VehicleClasses;

import java.util.Iterator;

public interface Storer<typeOfUnit extends GeneralVehicle> {
	void addUnit(typeOfUnit unit);
	typeOfUnit unloadUnit();
	Iterator<typeOfUnit> getIterator();
	int getNrOfUnits();

}
