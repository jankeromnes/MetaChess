package metachess.library;

import metachess.model.ClockBehavior;

public class Clock extends Thread {
	
	private ClockBehavior sync;
	private long start;
	private long total;
	
	public Clock(ClockBehavior sync) {
		this.sync = sync;
		start = 0;
		total = 0;
	}
	
	public long getCurrentTime() {
		return System.currentTimeMillis()-start+total;
	}
	
	@Override
    public void run() {
		start = System.currentTimeMillis();
		while(true) {
			sync.setCurrentTimeMillis(getCurrentTime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
		total = getCurrentTime();
	}
	
	
}
