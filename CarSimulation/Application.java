package CarSimulation;

import javax.swing.*;
import java.awt.*;

public class Application {
	private final CarModel model;
	private final UpdateTimer timer;

	public static void main(String[] args) {
		Application instance1 = new Application();
		instance1.initializeSimulation();
	}

	private void initializeSimulation() { //set parameters for and then start the simulation.
		timer.start(); // starts the update timer. Should be the last method call in the main function.
	}

	private Application() {
		int X = 800; // The size of the application window
		int Y = 800;

		JFrame frame = new JFrame();
		frame.setTitle("Improved Simulation");
		frame.setPreferredSize(new Dimension(X,Y));
		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		this.model = new CarModel(X, Y-440); // the initialization of the simulation model.
		CarViewer viewer = new CarViewer(model, X, Y-440); // initializes a graphical view for the cars in the model.
		frame.add(viewer); //adds the view to the application frame as a JPanel.

		VehicleActionController buttonController = new VehicleActionController(model, X); // initializes a row of buttons for controlling the cars in the model.
		frame.add(buttonController); // adds the buttons to the frame as a JPanel

		timer = new UpdateTimer(model); // initializes a timer which will update the model when started

		this.model.addVehicleListListeners(viewer); // The view listens to changes in the list of vehicles to generate or discard CarGraphics objects.
		this.model.addVehicleUpdateListeners(viewer); // The view listens to changes to the vehicles to update the displayed graphics.


		InfoPanel infoPanel = new InfoPanel(model, X); // initializes a text based view for the cars in the model.
		frame.add(infoPanel); // add the text view to the frame as a JLabel
		this.model.addVehicleUpdateListeners(infoPanel); // the text view listens to updates about the cars in the model.

		String[] availableCars = {"Volvo240", "Saab95", "Scania"}; // Labels and functions which will be used for the VehicleSelectionController. The strings must match the kinds of vehicles the model can create with the connected factory.
		VehicleSelectionController addCarButtons = new VehicleSelectionController(model, X, availableCars); // initializes buttons for adding or removing cars to or from the model.
		frame.add(addCarButtons); // add the buttons to the frame as a JPanel.

		MouseController mouseController = new MouseController(model); // initializes a controller for mouse interactions with the view.
		viewer.addMouseListener(mouseController); // adds the mouseController as a mouseListener to the graphical view.
		mouseController.addListeners(addCarButtons); // adds the vehicleSelectionController to the mouseController so that it can react to mouse actions.

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
