import java.util.*;


public class Ant {
SpacePosition position;
SpacePosition goal;
Space space;
ArrayList<SpacePosition> path = new ArrayList<SpacePosition>(1);
HashMap<String, SpacePosition> pathhash= new HashMap<String, SpacePosition>(10000);

public Ant(SpacePosition startposition, Space space, SpacePosition goal, boolean smelly)
		{
		position=startposition;
		this.space=space;
		this.goal=goal;
		}
	
private SpacePosition positionbyint(int d)
	{
		SpacePosition newposition=null;
		if (d==0) newposition=position.up();
		if (d==1) newposition=position.down();
		if (d==2) newposition=position.left();
		if (d==3) newposition=position.right();	
		return newposition;
	}
	
private boolean walkstep(int d)
	{
	SpacePosition newposition=positionbyint(d);
	if (newposition!=null)
		{
		position=newposition;
		}
	return (position==goal);
	}

private double[] smellPheromone()		//returns probabilities (0..1) for following position based on pheromone
	{
	double[] smell = new double[4];
	double sum=0;
	for (int i=0; i<4; i++)
		if (positionbyint(i)!=null)
			{
			smell[i]= positionbyint(i).getPheromone();
			sum+=smell[i];
			}
	for (int i=0; i<4; i++)
		smell[i]/=sum;
	return smell;
	}

private double[] donotsmell()		//returns probabilities (0..1) for following position based on pheromone
{
double[] smell = new double[4];
double sum=0;
for (int i=0; i<4; i++)
	if (positionbyint(i)!=null)
		{
		smell[i]= 1;
		sum+=1;
		}
for (int i=0; i<4; i++)
	smell[i]/=sum;
return smell;
}

private double[] accumulatedSum(double[] array)	//*transforms* probability array into accumulated sum
	{
	double sum=0;
	for (int i=0; i< array.length; i++)
		{
		sum+=array[i];
		array[i]=sum;
		}
	array[array.length-1]=1; //avoid rounding errors
	return array;
	}

private int chooseRandomIndex(double[] array)	//choses a index based on accumulated sum array
{
double r= Math.random();
int i=0;
while (array[i]<r) i++;	
return i;
}

public boolean randomstep()
	{
	if (pathhash.put(position.x+" "+position.y, position)==null)
		path.add(position);
	
	if (walkstep(chooseRandomIndex(accumulatedSum(smellPheromone())))) 
		{
		for (int i=0; i< path.size(); i++)
			path.get(i).addPheromone(1);
		path.clear();
		pathhash.clear();
		return true;
		}
	return false;
		

	}

}
