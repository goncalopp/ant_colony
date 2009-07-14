
public class Space {
SpacePosition[][] space;
SpacePosition maxpheromone;
Double pheromoneDissipationConstant;
long time;

public Space(int x, int y, double pheromoneDissipationConstant)
	{
	this.pheromoneDissipationConstant=pheromoneDissipationConstant;
	space=new SpacePosition[y][x];
	for (int x0=0; x0<x; x0++)
		for (int y0=0; y0< y; y0++)
			space[y0][x0]= new SpacePosition(x0,y0, this);
	maxpheromone=space[0][0];
	}

public SpacePosition position(int x, int y)
	{
	if ((x>=0)&&(y>=0)&&(x<space[0].length)&&(y<space.length)  )
		return space[y][x];
	else
		return null;
	}

public String toString(){
	String tmp="";
	for (int y=0; y<space.length; y++)
		{
		for (int x=0; x<space[0].length; x++)
			tmp+=space[y][x].toString()+' ';
		tmp+="\n";
		}
	return tmp;
	}
}
