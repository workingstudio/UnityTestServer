package UnityServer;

import java.util.ArrayList;
import java.util.List;

public class Game implements UnityClientConnectionListener {
	
	private List<UnityClientConnection> mActivePlayers;
	
	public Game () {
		mActivePlayers = new ArrayList<UnityClientConnection>();
	}
	
	@Override
	public synchronized void onConnect(UnityClientConnection connection) {
		mActivePlayers.add(connection);
		System.out.println("Connection|"+connection.getID());
		for (UnityClientConnection player : mActivePlayers) {
			if (player==connection) {
				player.sendMessage("Connect");
			}
		}
	}

	@Override
	public synchronized void onMessage(UnityClientConnection connection, String msg) {
		System.out.println(msg);
		for (UnityClientConnection player : mActivePlayers) {
			if (player==connection) {
				player.sendMessage("Action|"+msg);
			}
		}
	}

	@Override
	public synchronized void onDisconnect(UnityClientConnection connection) {
		mActivePlayers.remove(connection);
		System.out.println("DC|"+connection.getID());
		for (UnityClientConnection player : mActivePlayers) {
			if (player==connection) {
				//player.sendMessage("DC");
			}
		}
	}

}
