
public class Team {
	
	/* PRIVATE */
	@SuppressWarnings("unused")
	private String name = "";
	@SuppressWarnings("unused")
	private int members = 0;
	private int lap = 0;
	private int lastLap = 0;
	private int lastTTime = 0;
	private int [] lTime = new int [100];
	private int [] tTime = new int [100];

	/* INIT */
	Team() {
		
	}
	
	/* METHODS */
	public void addLap (int time){
		if (lap>=1)
			lTime[lap] = time - tTime[lap-1];
		else
			lTime[lap] = time;
		tTime[lap] = time;
		
		lastLap = lTime[lap];
		lastTTime = tTime[lap];
		
		lap++;
		
		System.out.println(lap);
	}
	public void removeLap () {
		System.out.println("removeLap ();");
		
		lap--;
		
		if (lap != 0){
			lastLap = lTime [lap-1];
			lastTTime = tTime [lap-1];
		}
		else {
			lastLap = 0;
			lastTTime = 0;
		}
		lTime[lap] = 0;
		tTime[lap] = 0;
		
		System.out.println(lap);
	}
	
	/* GETTER */
	public int getLastLap (){
		return lastLap;
	}
	public int getLastTTime (){
		return lastTTime;
	}
	public int getLap (){
		return lap;
	}
}
