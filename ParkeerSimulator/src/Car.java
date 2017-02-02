//package Parkeersimulator
//test
import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasParkPass;
    private boolean isReserved;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();

    public boolean hasParkPass() {
    	return hasParkPass;
    }
    
    public void setHasParkPass(boolean parkPass) {
    	hasParkPass = parkPass;
    }
    
    public boolean isReserved() {
    	return isReserved;
    }
    
    public void setIsReserved(boolean reserved) {
    	isReserved = reserved;
    }
}
