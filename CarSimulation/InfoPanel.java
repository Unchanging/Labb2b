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
	public void vehicleUpdate() { // When changes in the model trigger the updateListener this method will renew the rendred text.
		Iterator<? extends VehiclePresentation> iterator = model.getVehicleIterator();
		StringBuilder builder = new StringBuilder();
		builder.append("<html>"); // HTML tag is used to more fluently flow the text
		while (iterator.hasNext()) {
			VehiclePresentation vehicle = iterator.next();
			builder.append(String.format("%s: %s <br/>", vehicle.getModelName(), vehicle.getCurrentSpeed())); // See the interface VehiclePresentation for the information you can display here.
		}
		builder.append("</html>");
		this.setText(builder.toString());
	}
}