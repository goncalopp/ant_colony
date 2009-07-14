
public class SpacePosition {
private double pheromone;
public int x;
public int y;
public long lasttime;
public Space space;

public SpacePosition(int x, int y, Space space)
	{
	this.x=x;
	this.y=y;
	pheromone=1;
	this.space=space;
	}

public SpacePosition up() {return space.position(x, y-1);}
public SpacePosition down() {return space.position(x, y+1);}
public SpacePosition left() {return space.position(x-1, y);}
public SpacePosition right() {return space.position(x+1, y);}

public String toString()
	{
	String tmp= Integer.toString((int)Math.floor(pheromone));
	while (tmp.length()<4)
		tmp+=' ';
	return tmp;
	}

public double getPheromone() {
	double tmp=pheromone-((double)space.time-lasttime)*space.pheromoneDissipationConstant;
	if (tmp<1)
		return 1;
	return tmp;
}

public void addPheromone(double pheromone) {
	this.pheromone = getPheromone()+pheromone*2;
	lasttime=space.time;
	if (space.maxpheromone.getPheromone()<getPheromone())
		space.maxpheromone=this;
}
}
