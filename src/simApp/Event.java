package simApp;

public abstract class Event {

	protected boolean active = true;
	
	public boolean getActive(){
		return active;
	}
	public abstract Event event();
}
