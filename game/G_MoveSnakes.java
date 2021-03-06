package game;

import utilities.GameOptions;
import utilities.Job;
import utilities.Snake;

import java.util.LinkedList;

public class G_MoveSnakes implements Runnable {
	private Game thisGame;

	public G_MoveSnakes(Game g) {
		thisGame = g;
	}

	@Override
	public void run() {
		LinkedList<Snake> newLoosers = new LinkedList<Snake>();
		int counter = 0;
		while (!thisGame.manager.gameOver) {
			synchronized (thisGame.snakes) {
				for (Snake s : thisGame.snakes.values()) {
					Snake killer = s.isInCollision(thisGame.snakes);
					if (killer != null) {
						
						newLoosers.add(s);
						if (s != killer)
							killer.score += 1000;
					} else {
						if (s.isInCollision(thisGame.food.getPoint())) {
							thisGame.drink.effect(s);
//							s.grow();
							s.score += thisGame.food.getFoodSize();
							thisGame.foodChange += 1;
							thisGame.drinkChange += 1;
							thisGame.resetFood();
							counter = 0;
						}
						else if (s.isInCollision(thisGame.badFood.getPoint())) {
							thisGame.badDrink.effect(s);
//							s.grow();
							s.score += thisGame.badFood.getFoodSize();
							thisGame.foodChange += 1;
							thisGame.drinkChange += 1;
							thisGame.resetFood();
							counter = 0;
						}
						else if (s.isInCollisionWithWall(thisGame.obstacle.getWall())){
							newLoosers.add(s);
						}
						else {
							s.move();
							s.score+=1;
						}
					}

				}
			}
			synchronized (thisGame.snakes) {
				while (!newLoosers.isEmpty()) {
					
					thisGame.snakes.remove(newLoosers.poll().id & 255);
					
					if((thisGame.snakesAtStart.size()>1 && thisGame.snakes.size()<2) || thisGame.snakes.size()<1){
						System.out.println("Game Over");
						synchronized(thisGame.manager){
							thisGame.manager.gameToBeOver=true;
							try {
								thisGame.manager.in_communicator.put(new Job(Job.Type.UNKNOWN));
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("Game to be over set to true");
						}
						
					}else System.out.println("game is still on");
				}
			}
			if (counter % GameOptions.foodLifeTime == 0)
				thisGame.resetFood();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;

		}

	}
}
