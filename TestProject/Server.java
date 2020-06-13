//package sample.server;
// Java implementation of Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// Server class 
public class Server 
{ 

	// Vector to store active clients 
	static Vector<ClientHandler> clients = new Vector<>();
	static int turnIndex=0;

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(1234);
		final int N_USERS = 6;

		System.out.println("Server started.....");
		
		Socket s; 
		
		//RUNNING LOOP FOR GETTING CLIENT CLIENT REQUESTS
		for(int i=0;i<N_USERS;i++)
		{ 
			// Accept the incoming request 
			s = ss.accept(); 

			System.out.println("New client request received : " + s);
			
			System.out.println("Creating a new handler for this client..."); 

			// Create a new handler object for handling this request. 
			ClientHandler client = new ClientHandler(s);

			// Create a new Thread with this object. 
			Thread t = new Thread(client);
			
			System.out.println("Adding this client to active client list");
			// add this client to active clients list
			clients.add(client);

			// start the thread. 
			t.start();
		} 
	} 
}