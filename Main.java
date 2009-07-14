
public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Ant[] ants= new Ant[500];
		SpacePosition[] food= new SpacePosition[1];
		
		Space space= new Space(300,300, 0.000007);
		food[0]=space.position(10,20);
		SpacePosition ninho=space.position(260, 200);
		
		
		for (int i=0; i< ants.length; i++)
			ants[i]= new Ant(ninho, space, food[0], false);
		
		boolean running=true;
		
		OpenglClass.start(300, 300, ants, food, space);
		Thread.sleep(1000);
		
		while (running)
			{
			for (int i=0; i< ants.length; i++)
				{
				if (ants[i].randomstep())
					{
					if (ants[i].goal==ninho)
						ants[i].goal=food[0];
					else
						ants[i].goal=ninho;
					
					}
					
				}	

			space.time++;
				
			if (space.time%1==0)
				{
				OpenglClass.draw();
				System.out.println(space.maxpheromone.getPheromone());
				}
			//Thread.sleep(100);
			
			//System.out.println(space.maxpheromone.getPheromone());
			}
	}

}
