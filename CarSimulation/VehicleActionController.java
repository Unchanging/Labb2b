package CarSimulation;
import javax.swing.*;
import java.awt.*;

public class VehicleActionController extends JPanel{ // A controller for instructing the model of actions to take with the current vehicles.

	private final ModelControl modelControl;
	private final JPanel controlPanel = new JPanel();
	private final JPanel gasPanel = new JPanel();
	private int gasAmount = 50;
	private final JLabel gasLabel = new JLabel("Amount of gas");

	private final JButton gasButton = new JButton("Gas");
	private final JButton brakeButton = new JButton("Brake");
	private final JButton turboOnButton = new JButton("Saab Turbo on");
	private final JButton turboOffButton = new JButton("Saab Turbo off");
	private final JButton liftBedButton = new JButton("Scania Lift Bed");
	private final JButton lowerBedButton = new JButton("Scania Lower Bed");

	private final JButton startButton = new JButton("Start all cars");
	private final JButton stopButton = new JButton("Stop all cars");

	// Constructor
	public VehicleActionController(ModelControl modelControl, int width){
		this.modelControl = modelControl;

		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		initComponents(width); // Fixes the graphical aspect of the buttons.
		addListenersToButtons(); // Connect the buttons to actionListeners.
	}

	// Sets everything in place and fits everything
	private void initComponents(int X) {

		SpinnerModel spinnerModel =
				new SpinnerNumberModel(gasAmount, //initial value
						0, //min
						100, //max
						1);//step
		JSpinner gasSpinner = new JSpinner(spinnerModel);
		gasSpinner.addChangeListener(e -> gasAmount = (int) ((JSpinner)e.getSource()).getValue());

		gasPanel.setLayout(new BorderLayout());
		gasPanel.add(gasLabel, BorderLayout.PAGE_START);
		gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

		this.add(gasPanel);

		controlPanel.setLayout(new GridLayout(2,4));

		controlPanel.add(gasButton, 0);
		controlPanel.add(turboOnButton, 1);
		controlPanel.add(liftBedButton, 2);
		controlPanel.add(brakeButton, 3);
		controlPanel.add(turboOffButton, 4);
		controlPanel.add(lowerBedButton, 5);
		controlPanel.setPreferredSize(new Dimension((X/2)+4, 200));
		this.add(controlPanel);
		controlPanel.setBackground(Color.CYAN);


		startButton.setBackground(Color.blue);
		startButton.setForeground(Color.green);
		startButton.setPreferredSize(new Dimension(X/5-15,200));
		this.add(startButton);


		stopButton.setBackground(Color.red);
		stopButton.setForeground(Color.black);
		stopButton.setPreferredSize(new Dimension(X/5-15,200));
		this.add(stopButton);
	}

	private void addListenersToButtons() {
		gasButton.addActionListener(e -> modelControl.gas(gasAmount));
		brakeButton.addActionListener(e -> modelControl.brake(gasAmount));
		turboOnButton.addActionListener(e -> modelControl.turboOn());
		turboOffButton.addActionListener(e -> modelControl.turboOff());
		stopButton.addActionListener(c -> modelControl.stopAllCars());
		startButton.addActionListener(e -> modelControl.startAllCars());
		liftBedButton.addActionListener(e -> modelControl.liftAllScaniaBeds());
		lowerBedButton.addActionListener(e -> modelControl.lowerAllScaniaBeds());
	}
}
