package CarSimulation;

import javax.swing.*;
import java.awt.*;

public class Application {

	//TODO Implement a mouseController with state pattern
	//TODO resolve the thread issues
	//TODO Defensive copying


	private final CarModel model;
	private final UpdateTimer timer;

	public static void main(String[] args) {
		Application instance1 = new Application();
		instance1.initializeSimulation();
	}

	private void initializeSimulation() { //set parameters for simulation.
		timer.start();
	}

	private Application() {
		int X = 800; // The size of the application window
		int Y = 800;

		JFrame frame = new JFrame();
		frame.setTitle("Improved Simulation");
		frame.setPreferredSize(new Dimension(X,Y));
		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		this.model = new CarModel(X, Y-440);
		CarViewer viewer = new CarViewer(model, X, Y-440);
		frame.add(viewer);

		VehicleActionController buttonController = new VehicleActionController(model, X);
		frame.add(buttonController);

//		KeyController keyController = new KeyController();
		timer = new UpdateTimer(model);

		this.model.addVehicleListListeners(viewer); // The view listens to changes in the list of vehicles to generate or discard CarGraphics objects.
		this.model.addVehicleUpdateListeners(viewer); // The view listens to changes to the vehicles to update the displayed graphics.


		InfoPanel infoPanel = new InfoPanel(model, X);
		frame.add(infoPanel);

		String[] availableCars = {"Volvo240", "Saab95", "Scania"};
		VehicleSelectionController addCarButtons = new VehicleSelectionController(model, X, availableCars);
		frame.add(addCarButtons);

//		JFrame tempFrame = new JFrame();
//		tempFrame.add(infoPanel);
//		tempFrame.setVisible(true);
//		tempFrame.pack();

		this.model.addVehicleUpdateListeners(infoPanel);


		MouseController mouseController = new MouseController(model);
		viewer.addMouseListener(mouseController);
		mouseController.addListeners(addCarButtons);
		addCarButtons.useMouseForInput();

		frame.pack();
		// Get the computer screen resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Center the frame
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		// Make the frame visible
		frame.setVisible(true);
		// Make sure the frame exits when "x" is pressed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
