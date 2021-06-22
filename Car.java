import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private CyclicBarrier preparationСyclicBarrier;
    private CyclicBarrier finishСyclicBarrier;
    private Semaphore tunnelSemaphore;
   

       private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Semaphore getTunnelSemaphore() {
        return tunnelSemaphore;
    }

    public Car(Race race, CyclicBarrier preparationСyclicBarrier, Semaphore tunnelSemaphore, CyclicBarrier finishСyclicBarrier, int speed) {
        this.race = race;
        this.preparationСyclicBarrier = preparationСyclicBarrier;
        this.finishСyclicBarrier = finishСyclicBarrier;
        this.tunnelSemaphore = tunnelSemaphore;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            
            System.out.println(this.name + " готов");
            preparationСyclicBarrier.await();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        try {
            finishСyclicBarrier.await();
        } catch (Exception e) {
            
            e.printStackTrace();
        } 
    }
}