package launcher;

import builders.GameHandlerBuilder;
import games_handler.GH_Manager;

import java.io.IOException;

public class  LaunchServer {

	public static void main(String[] args) throws IOException {
		int nbJoueur = (args.length > 0 ? Integer.parseInt(args[0]) : 1);
		System.out.println("Server initializing...");
		GameHandlerBuilder manager = new GameHandlerBuilder();
		manager.setInputPort(5757);
		manager.setOutputPort(5656);
		manager.setServerName("Snakes Server");
		manager.setBroadcastTimeInterval(2000);
		manager.setNbp(nbJoueur);
		GH_Manager gameHandler = manager.build();
		Thread GH=new Thread(gameHandler);
		GH.start();
	}

}
