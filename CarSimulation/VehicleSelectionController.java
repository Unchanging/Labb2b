package CarSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class VehicleSelectionController extends JPanel implements MouseControllerListener {
	private ModelControl model;
	private int width;
	private final JPanel internalJPanel;
	private boolean mouseForInput = false;

	private Consumer<MouseEvent> vehicleAction = e -> {};

	public VehicleSelectionController(CarModel model, int width, String[] modelNames){
		this.width = width;
		this.model = model;

		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		internalJPanel = new JPanel();
		internalJPanel.setLayout(new GridLayout(1,modelNames.length));
		internalJPanel.setPreferredSize(new Dimension((width/2)+4, 200));
		fillInternalJPanel(modelNames);

		this.add(internalJPanel);
	}

	public void useMouseForInput() {
		mouseForInput = true;
	}

	public void disableMouseInput() {
		mouseForInput = false;
	}

	private void addVehicle(String modelName) {
		if (mouseForInput)
			vehicleAction = e -> model.addCar(modelName, e.getPoint());
		else
			model.addCar(modelName);
	}

	private void removeVehicle() {
		if (mouseForInput)
			vehicleAction = e -> model.removeCar(e.getPoint());
		else
			model.removeCar();
	}

	@Override
	public void action(MouseEvent e) {
		vehicleAction.accept(e);
	}

	private void fillInternalJPanel(String[] modelNames) {

		for (String modelName : modelNames) {
			JButton tempButton = new JButton(modelName);
			tempButton.addActionListener(e -> addVehicle(modelName));
			internalJPanel.add(tempButton);
		}

		JButton removeButton = new JButton("Remove Vehicle");
		removeButton.addActionListener(e -> removeVehicle());
		internalJPanel.add(removeButton);
	}
}
