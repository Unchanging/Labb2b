package VehicleClasses;

import java.awt.*;
import java.util.Iterator;


public class FerryLane<typeOfUnit extends GeneralVehicle> implements Transporter<typeOfUnit> {

	private UnitStorage<typeOfUnit> vehicleStorage;

	public FerryLane(int maxNrOfVehicles) {
		vehicleStorage = new UnitStorage<>(maxNrOfVehicles);
	}

	@Override
	public void addUnit(typeOfUnit unit) {
		vehicleStorage.addUnit(unit);
	}

	@Override
	public void updatePositions(Point newPosition) {
		vehicleStorage.updatePositions(newPosition);
	}

	@Override
	public typeOfUnit unloadLastUnit() {
		return vehicleStorage.unloadLastUnit();
	}

	@Override
	public typeOfUnit unloadFirstUnit() {
		return vehicleStorage.unloadFirstUnit();
	}

	@Override
	public Iterator<typeOfUnit> getIterator() {
		return vehicleStorage.getIterator();
	}

	@Override
	public int getNrOfUnits() {
		return vehicleStorage.getNrOfUnits();
	}
}
