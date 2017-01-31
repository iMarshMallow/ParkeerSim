//package Parkeersimulator;

import java.util.Random;

public class Simulator {

	private static final double AD_HOC = 0.6;
	private static final double PASS = 0.3;
	private static final double RESVC = 0.1;
	
    private int aantalAutos = 0;
    private int aantalAdHocAutos = 0;
    private int aantalGereserveerdeAutos = 0;
    private int aantalParkingPassAutos = 0;
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    public static void main(String[] args){
    	Simulator mySimulator = new Simulator();
    	mySimulator.run();
    }
    
    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
        }
    }

    private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }
    
    Random random = new Random();

    // Get the average number of cars that arrive per hour.
    int averageNumberOfCarsPerHour = day < 5
            ? weekDayArrivals
            : weekendArrivals;

    // Calculate the number of cars that arrive this minute.
    double standardDeviation = averageNumberOfCarsPerHour * 0.1;
    double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
    int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);
    
    private void carsArriving(){
Random r = new Random();
        
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            if(r.nextDouble() <= AD_HOC) {
                Car car = new AdHocCar();
                entranceCarQueue.addCar(car);          	
            	aantalAutos++;
            	aantalAdHocAutos++;
            }
            
            else if(r.nextDouble() <= PASS) {
                Car car = new ParkingPassCar();
                entranceCarQueue.addCar(car);     
            	aantalAutos ++;
            	aantalParkingPassAutos ++;
            }
                else if(r.nextDouble() <= RESVC) {
                Car car = new ReservCar();
                entranceCarQueue.addCar(car);
            	aantalAutos++;
            	aantalGereserveerdeAutos ++;
            }}
        }

    private void carsEntering(CarQueue queue){
    	 for (int i = 0; i < enterSpeed; i++) {
             Car car = entranceCarQueue.removeCar();
             if (car == null) {
                 break;
             }
          // Find a space for this car.
 			Location freeLocation = simulatorView.getFirstFreeLocation(car);
 			if (freeLocation != null) {
                 simulatorView.setCarAt(freeLocation, car);
                 int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                 car.setMinutesLeft(stayMinutes); 
                 
                 if (car instanceof AdHocCar){
                 	int hours = car.getMinutesLeft()/60*4;
                 }  
                 
                 if (car instanceof ParkingPassCar){
                 	int hours = car.getMinutesLeft()/60*2;
                 }
                 	
                 if (car instanceof ReservCar){
                 	int hours = car.getMinutesLeft()/60*3;
                 }                            
             }}
         }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private void getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
            : weekend;   
    }
    
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
Random r = new Random();
        
			for (int i = 0; i < numberOfCarsPerMinute; i++) {
				if (random.nextInt(10) < 1) {
					Car car = new ReservCar();
					entranceCarQueue.addCar(car);
					aantalAutos++;
					aantalGereserveerdeAutos++;
					car.setIsReserved(true);
				} else if (random.nextInt(10) > 8) {
					Car car = new AdHocCar();
					car.setHasParkPass(true);
					entranceCarQueue.addCar(car);
					aantalAutos++;
				} else {
					Car car = new AdHocCar();
					entranceCarQueue.addCar(car);
					aantalAutos++;
					car.setIsReserved(false);
				}}
			}

        /*for (int i = 0; i < numberOfCarsPerMinute; i++) {
            if(r.nextDouble() <= AD_HOC) {
                Car car = new AdHocCar();
                entranceCarQueue.addCar(car);          	
            	aantalAutos++;
            	aantalAdHocAutos++;
            }
            
            else if(r.nextDouble() <= PASS) {
                Car car = new ParkingPassCar();
                entranceCarQueue.addCar(car);   
            	aantalAutos ++;
            	aantalParkingPassAutos ++;
            }
                else if(r.nextDouble() <= RESVC) {
                Car car = new ReservCar();
                entranceCarQueue.addCar(car); 
            	aantalAutos++;
            	aantalGereserveerdeAutos ++;
            }}
        }*/
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
