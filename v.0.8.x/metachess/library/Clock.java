package metachess.library;

import java.util.Vector;

import metachess.model.Synchron;

/** The Clock of MetaChess
 * @author Jan (7DD)
 * @version 0.8.7
 */
public class Clock extends Thread {
	
	/** 
	 * The unique instance of the Clock.
	 */
	private static Clock instance = new Clock();
	
	private static Vector<Synchron> subscribers;
	
	private Clock() {
		subscribers = new Vector<Synchron>();
	}
	
	public static Clock getInstance() {
		return instance;
	}
	
	public static void susbscribe(Synchron subscriber) {
		if(!subscribers.contains(subscriber)) subscribers.add(subscriber);
	}
	
	public static void unsubscribe(Synchron subscriber) {
		if(subscribers.contains(subscriber)) subscribers.remove(subscriber);
	}
	
	@Override
    public void run() {
		while(true) {
			for(Synchron s : subscribers) s.synchronize();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
	}
	
	
}
