//package Parkeersimulator;

import java.util.Random;
import java.awt.*;

public class ReservCar extends Car {
	private static final Color COLOR=Color.green;
	
  public ReservCar() {
  	Random random = new Random();
  	int stayMinutes = (int) (15 + random.nextFloat() * 60 * 3);
      this.setMinutesLeft(stayMinutes);
      this.setHasToPay(true);
  }
  
  public Color getColor(){
  	return COLOR;
  }
}