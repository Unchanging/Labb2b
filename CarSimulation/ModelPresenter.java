package CarSimulation;

import VehicleClasses.VehiclePresentation;

import java.awt.*;
import java.util.Iterator;

public interface ModelPresenter {
	Iterator<? extends VehiclePresentation> getVehicleIterator();
	void setVehicleDimension(VehiclePresentation car, Dimension dimension);
}
