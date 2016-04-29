package UnityServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class UnityClientConnection implements Runnable {
	private static final String THREAD_TAG = "UnityClientConnection";
	private Socket mConnection;
	private UnityClientConnectionListener mCallback;
	private Thread mThread;
	private volatile boolean mRunning;
	private int mID;

	public UnityClientConnection(int id,Socket connection, UnityClientConnectionListener callback) {
		mConnection = connection;
		mCallback = callback;
		mRunning = true;
		mID = id;
		mThread = new Thread(null, this, THREAD_TAG);
		mThread.start();
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
			mCallback.onConnect(this);
			while (mRunning) {
				String msg = in.readLine();
				if (msg == null) {
					mCallback.onDisconnect(this);
					mRunning = false;
					break;
				}
				mCallback.onMessage(this, msg);
			}
			mConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getID() {
		return mID;
	}

	public void sendMessage(String msg) {
		try {
			PrintWriter out = new PrintWriter(mConnection.getOutputStream(), true);
			out.println(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void closeConnection() {
		mRunning = false;
		try {
			mThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
