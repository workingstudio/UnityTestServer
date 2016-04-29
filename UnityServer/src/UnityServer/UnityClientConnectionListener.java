package UnityServer;

public interface UnityClientConnectionListener {
	public void onConnect (UnityClientConnection connection);
	
	public void onMessage(UnityClientConnection connection,String msg);
	
	public void onDisconnect (UnityClientConnection connection);
}
