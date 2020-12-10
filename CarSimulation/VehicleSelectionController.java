package CarSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.function.Consumer;

public class VehicleSelectionController extends JPanel implements MouseControllerListener {

	// A controller for adding or removing vehicles from a model.

	private final ModelControl model;
	private final int width;
	private final JPanel internalJPanel;
	private Map<String, JButton> buttonMap;
	private boolean mouseForInput = false;

	private Consumer<MouseEvent> vehicleAction = e -> {};

	public VehicleSelectionController(CarModel model, int width, String[] modelNames){
		this.width = width;
		this.model = model;

		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		internalJPanel = new JPanel();
		internalJPanel.setLayout(new GridLayout(1,modelNames.length));
		internalJPanel.setPreferredSize(new Dimension((int)(width*0.7), 200));
		fillInternalJPanel(modelNames);

		this.add(internalJPanel);
	}

	private void toggleMouse() {
		if(mouseForInput)
			disableMouseInput();
		else
			useMouseForInput();
	}

	public void useMouseForInput() {
		mouseForInput = true;
	}

	public void disableMouseInput() {
		mouseForInput = false;
		vehicleAction = e -> {}; // Clears the Consumer otherwise used by the action(MouseEvent) method.
		buttonMap.forEach((s, jb) -> jb.setBackground(null)); // Clears the graphical selection from all buttons.
	}

	private void addVehicle(String modelName) {
		if (mouseForInput) {
			vehicleAction = e -> model.addCar(modelName, e.getPoint()); // Sets the Consumer triggered by action(MouseEvent) to instruct the model to add a vehicle at the MouseEvent's location.
			markButton(modelName); // Shows graphically which action is currently selected.
		}
		else
			model.addCar(modelName); //Instructs the model to add the specified vehicle at no particular place.
	}

	private void markButton(String buttonKey) { // Shows graphically which action is currently selected and clears the marking of the rest.
		buttonMap.forEach((s, jb) -> jb.setBackground(null));
		buttonMap.get(buttonKey).setBackground(Color.RED);
	}

	private void removeVehicle() { // Instructs the model to remove a vehicle. Either closest to the MouseEvent or at its own discretion.
		if (mouseForInput) {
			vehicleAction = e -> model.removeCar(e.getPoint());
			markButton("removeButton");
		}
		else
			model.removeCar();
	}

	@Override
	public void action(MouseEvent e) { // Calls a Consumer function when the MouseControllerListener is triggered.
		vehicleAction.accept(e);
	}

	private void fillInternalJPanel(String[] modelNames) { // Sets upp the buttons and adds ActionListeners to them.

		buttonMap = new LinkedHashMap<>(); // The buttons are stored in a map so the graphics can be updated when selected.

		for (String modelName : modelNames) { // Adds buttons as specified in the parameters
			JButton tempButton = new JButton(modelName);
			tempButton.addActionListener(e -> addVehicle(modelName));
			buttonMap.put(modelName, tempButton);
		}

		JButton removeButton = new JButton("<html>Remove <br/> Vehicle</html>");
		removeButton.addActionListener(e -> removeVehicle());
		buttonMap.put("removeButton", removeButton);

		JButton mouseToggle = new JButton("<html>Toggle <br/> Mouse</html>");
		mouseToggle.addActionListener(e -> toggleMouse());
		buttonMap.put("mouseToggle", mouseToggle);

		buttonMap.forEach((s, jb) -> internalJPanel.add(jb)); // adds the buttons to the internal JPanel.
	}
}

