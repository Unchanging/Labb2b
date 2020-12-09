package CarSimulation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MouseController implements MouseListener {
	private ModelControl model;
	private int state;
	private List<MouseControllerListener> listeners = new ArrayList<>();

	public MouseController(ModelControl model) {
		this.model = model;
	}

	public void addListeners(MouseControllerListener listener) {
		listeners.add(listener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		listeners.forEach(l -> l.action(e));
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
