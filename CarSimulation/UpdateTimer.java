package CarSimulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateTimer implements ActionListener {

	private ModelControl model;
	private Timer timer = new Timer(20, this);

	public UpdateTimer(ModelControl model) {
		this.model = model;
		timer.start();
	}

	public void setDelay(int milliSeconds) {
		timer.setDelay(milliSeconds);
	}

	public void start() {
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.updateModel();
	}
}
