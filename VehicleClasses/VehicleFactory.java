package VehicleClasses;

public abstract class VehicleFactory {

	public static GeneralVehicle createSaab95() {
		return new Saab95();
	}

	public static GeneralVehicle createVolvo240() {
		return new Volvo240();
	}

	public static GeneralVehicle createScania() {
		return new Scania();
	}

	public static GeneralVehicle createByParameter(String typeOfVehicle) {

		if(typeOfVehicle.equals("Volvo240"))
			return createVolvo240();
		if(typeOfVehicle.equals("Saab95"))
			return createSaab95();
		if(typeOfVehicle.equals("Scania"))
			return createScania();
		return null;
	}
}
