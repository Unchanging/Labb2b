package VehicleClasses;

import java.awt.*;
import java.util.Iterator;

public interface Transporter <typeOfUnit extends Transportable> {
	void addUnit(typeOfUnit unit);
	void updatePositions(Point newPosition);
	typeOfUnit unloadLastUnit();
	typeOfUnit unloadFirstUnit();
	Iterator<typeOfUnit> getIterator();
	int getNrOfUnits();
}
