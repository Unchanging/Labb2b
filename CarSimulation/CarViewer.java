package CarSimulation;

import VehicleClasses.VehiclePresentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CarViewer extends JPanel implements VehicleListListener, VehicleUpdateListener{

	private Map<VehiclePresentation, CarGraphics> carGraphicsMap;
	private final ModelPresenter model;

	// Initializes the panel and reads the images
	public CarViewer(ModelPresenter model, int x, int y) {

		this.model = model;
		carGraphicsMap = new HashMap<>();
		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(x, y));
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Draws the viewing area.
		carGraphicsMap.forEach((key, value) -> value.drawYourself(g, key)); // call upon all vehicles to redraw themselves.
	}

	private Map<VehiclePresentation, CarGraphics> generateCarGraphicsMap() {

		Iterator<? extends VehiclePresentation> vehicleIterator = model.getVehicleIterator(); // gets an iterator from the model
		Map<VehiclePresentation, CarGraphics> newMap = new HashMap<>(); //an empty map

		while (vehicleIterator.hasNext()) {
			VehiclePresentation currentCar = vehicleIterator.next();
			CarGraphics tempCarGraphics = new CarGraphics(currentCar);//Generate new graphics object for each car
			newMap.put(currentCar,tempCarGraphics); // add those new graphics to the map
			model.setVehicleDimension(currentCar, tempCarGraphics.getDimension()); // suggest how large the model should consider the cars to be. Optional.
		}
		return newMap; // Replace the old map
	}

	@Override
	public void VehicleListUpdate() { //When the model updates its list of vehicle the view should update its graphical objects
		carGraphicsMap = generateCarGraphicsMap();
	}

	@Override
	public void vehicleUpdate() {//calls the repaint method even the update observer is triggered.
		repaint();
	}

	/**
	 * A class which represents the graphical characteristics of a car.
	 */
	private static class CarGraphics {
		private BufferedImage carImage;

		public CarGraphics(VehiclePresentation car) {
			try {
				carImage = ImageIO.read(CarViewer.class.getResourceAsStream("../pics/" + car.getModelName() +".jpg"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		public void drawYourself(Graphics g, VehiclePresentation car) {
			g.drawImage(carImage, car.getPosition().x, car.getPosition().y, null);
		}

		public Dimension getDimension() {
			return new Dimension(carImage.getWidth(), carImage.getHeight());
		}
	}
}
