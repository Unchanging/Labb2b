package VehicleClasses;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class UnitStorage<typeOfUnit extends Transportable> implements Transporter<typeOfUnit> {

	private LinkedList<typeOfUnit> listOfUnits;
	private int maxNrOfUnits;

	public UnitStorage(int maxNrOfUnits) {
		this.maxNrOfUnits = maxNrOfUnits;
		listOfUnits = new LinkedList<>();
	}

	/**Stores a unit if there is space left.
	 * @param unit The unit which is to be stored.
	 */
	public void addUnit(typeOfUnit unit) {
		if (listOfUnits.size() < maxNrOfUnits)
			listOfUnits.add(unit);
		else
			throw new RuntimeException("The storage is full and can not add another Unit");
	}

	/**Removes and returns the last loaded unit from storage.
	 * @return The unit which was loaded last
	 */
	public typeOfUnit unloadLastUnit(){
		typeOfUnit unit = listOfUnits.getLast();
		listOfUnits.remove(unit);
		return unit;
	}

	/**Removes and returns the first loaded unit from storage.
	 * @return The unit which was loaded first
	 */
	public typeOfUnit unloadFirstUnit(){
		typeOfUnit unit = listOfUnits.getFirst();
		listOfUnits.remove(unit);
		return unit;
	}

	public typeOfUnit retrieveUnit(String modelName) {
		typeOfUnit foundUnit = null;
		for(typeOfUnit unit : listOfUnits) {
			if (unit.getIdentifier().equals(modelName))
				foundUnit = unit;
		}
		listOfUnits.remove(foundUnit);
		return foundUnit;
	}

	public void updatePositions(Point newPosition) {
		for (typeOfUnit unit : listOfUnits) {
			unit.setPosition(newPosition);
		}
	}

	public Iterator<typeOfUnit> getIterator() {
		return Collections.unmodifiableList(listOfUnits).iterator();
	}

	public int getNrOfUnits() {
		return listOfUnits.size();
	}
}
