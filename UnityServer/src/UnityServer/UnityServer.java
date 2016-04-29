package UnityServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UnityServer {
	
	public static final int PORT = 4444;
	private static final int MAX_PLAYERS = 12;
	private ServerSocket mServer;
	private Stack<Integer> mIDs;
	private List<UnityClientConnection> mConnections;
	
	public static void main(String[] args) {
		new UnityServer();
	}
	
	public UnityServer () {
		Game game = new Game();
		mIDs = new Stack<Integer>();
		for (int i = 0 ; i < MAX_PLAYERS;i++) {
			mIDs.push(i);
		}
		mConnections = new ArrayList<UnityClientConnection>(MAX_PLAYERS);
		try {
			mServer = new ServerSocket(PORT);
			System.out.println("Server Starting");
			while (true) {
				Socket socket = mServer.accept();
				int id = mIDs.pop();
				mConnections.add(new UnityClientConnection(id,socket,game));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
