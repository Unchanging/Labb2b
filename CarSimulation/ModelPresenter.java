package CarSimulation;

import VehicleClasses.VehiclePresentation;

import java.awt.*;
import java.util.Iterator;

// An interface implemented by the CarModel and used by classes to access information about the model and report back information about the size of rendered vehicles if appropriate.

public interface ModelPresenter {
	Iterator<? extends VehiclePresentation> getVehicleIterator();
	void setVehicleDimension(VehiclePresentation car, Dimension dimension);
}
