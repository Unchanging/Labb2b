package CarSimulation;

import VehicleClasses.VehiclePresentation;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class InfoPanel extends JLabel implements VehicleUpdateListener{

	private final ModelPresenter model;

	public InfoPanel(ModelPresenter model, int X) {
		super();
		this.model = model;
		this.setPreferredSize(new Dimension(X/5 - 15, 200));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}

	@Override
	public void vehicleUpdate() {
		Iterator<? extends VehiclePresentation> iterator = model.getVehicleIterator();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		while (iterator.hasNext()) {
			VehiclePresentation vehicle = iterator.next();
			builder.append(String.format("%s: %s <br/>", vehicle.getModelName(), vehicle.getCurrentSpeed()));
		}
		builder.append("</html>");
		this.setText(builder.toString());
	}
}