package UnityServer;

import java.util.ArrayList;
import java.util.List;

public class UnityServerDebug implements UnityClientConnectionListener {

	private List<UnityClientConnection> mPlayers;
	
	
	public UnityServerDebug () {
		mPlayers = new ArrayList<UnityClientConnection>();		
	}
	
	
	@Override
	public void onConnect(UnityClientConnection connection) {
		mPlayers.add(connection);
		System.out.println("New Connection!");
		connection.sendMessage("Hello");
	}

	@Override
	public void onMessage(UnityClientConnection connection, String msg) {
		System.out.println("Messgae: "+msg);
	}

	@Override
	public void onDisconnect(UnityClientConnection connection) {
		System.out.println("DC!");
		mPlayers.remove(connection);
	}

}
