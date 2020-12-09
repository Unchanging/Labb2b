package VehicleClasses;

import java.util.Iterator;

public class RepairShop<typeOfVehicles extends GeneralVehicle> implements Storer<typeOfVehicles> {

	private UnitStorage<typeOfVehicles> vehicleStorage;

	public RepairShop(int maxNrOfVehicles) {
		vehicleStorage = new UnitStorage<>(maxNrOfVehicles);
	}

	public typeOfVehicles retrieveVehicle(String modelName){
		return vehicleStorage.retrieveUnit(modelName);
	}

	@Override
	public void addUnit(typeOfVehicles vehicle) {
		vehicleStorage.addUnit(vehicle);
	}

	@Override
	public typeOfVehicles unloadUnit() {
		return vehicleStorage.unloadFirstUnit();
	}

	@Override
	public Iterator<typeOfVehicles> getIterator() {
		return vehicleStorage.getIterator();
	}

	@Override
	public int getNrOfUnits() {
		return vehicleStorage.getNrOfUnits();
	}
}
