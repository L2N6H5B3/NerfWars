public class PlayerModule {
	String name;
	int deaths;
	int respawnTime;
	int currentTimeRemaining;
	boolean tickListenerUnassigned;
	
	
	public PlayerModule(String name, int time) {
		this.name = name;
		this.deaths = 0;
		this.respawnTime = time++;
		this.currentTimeRemaining = 0;
		this.tickListenerUnassigned = true;
	}
	
	
	public String getName()  {
		return this.name;
	}
	
	
	public void addDeath()  {
		this.deaths++;
	}
	
	public void resetDeaths()  {
		this.deaths = 0;
	}
	public void decrementTime() {
		currentTimeRemaining--;
	}
	
	public int getTimeRemaining() {
		return currentTimeRemaining;
	}
	
	public void resetTimer() {
		this.currentTimeRemaining = respawnTime;
	}
	
	public void changeRespawnTime(int time) {
		this.respawnTime = time++;
	}
	
	public void tickListenerAssigned() {
		this.tickListenerUnassigned = false;
	}
	
	public boolean getTickListenerAssignedStatus() {
		return this.tickListenerUnassigned;
	}
	
	public String toString() {
	return("["+this.currentTimeRemaining+"]"+"("+this.deaths+") "+this.name);
	}

}