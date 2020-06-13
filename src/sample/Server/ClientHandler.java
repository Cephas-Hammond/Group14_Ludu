package sample.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

// ClientHandler class
class ClientHandler implements Runnable
{
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    private final Socket s;
    boolean isLoggedIn;
    boolean isReady;
    String color = null;

    // constructor
    public ClientHandler(Socket s) throws IOException {
        this.s = s;
        this.isLoggedIn =true;
        this.isReady = false;
        this.name="null";
        //OBTAIN INPUT AND OUTPUT STREAMS
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

    }

    @Override
    public void run() {

        String received;
        while (!s.isClosed())
        {
            try
            {
                // receive the string
                received = dis.readUTF();

                //received message is displayed
                System.out.println(received);

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String sender = st.nextToken();

                //SETS NAME OF NEW PLAYER
                if(name.equals("null")){
                    name = sender;
                    System.out.println("Username Acquired as: "+name);
                }
                else if(MsgToSend.equals("findPlayers")){
                    System.out.println("Searching for Players");
                    alertAllPlayers();
                }
                else if(MsgToSend.equals("ready")){
                    System.out.println("Checking Readiness...");
                    this.isReady = true;
                    checkReady();
                }
                else if(MsgToSend.contains("color")){
                    color = MsgToSend.split(":")[1];
                    for (ClientHandler client : Server.clients) {
                        if (!client.name.equals(sender) && client.isLoggedIn) {
                            client.dos.writeUTF(received);
                        }
                    }
                }
                // SEND TO ALL CLIENTS EXCEPT SENDER
                else{
                    for (ClientHandler client : Server.clients) {
                        if (!client.name.equals(sender) && client.isLoggedIn) {
                            client.dos.writeUTF(received);
                        }
                    }

                    if (!MsgToSend.equals("6")) {
                        Server.turnIndex = (Server.turnIndex + 1) % Server.clients.size();
                    }
                    Server.clients.get(Server.turnIndex).dos.writeUTF("yourTurn#"+sender);
                }
            } catch (IOException e) {
                //I/O STREAM TO PLAYER IS HITS DEAD END (PLAYER NOT RESPONDING)
                //e.printStackTrace();
                this.isLoggedIn = false;
                Server.clients.remove(this);
                break;
            }
        }
        try
        {
            // CLOSING RESOURCES
            this.dis.close();
            this.dos.close();
            System.out.println(this.name + " has disconnected.");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void alertAllPlayers() {
        //GET NAMES OF ALL USERS
        StringBuilder playerNames = new StringBuilder();
        int size = Server.clients.size();
        for (int i=0;i<size;i++) {
            playerNames.append(Server.clients.get(i).name +"]"+ Server.clients.get(i).color);
            if(i<(size-1)) playerNames.append(",");
        }
        //SEND NAMES TO ALL PLAYERS
        for (ClientHandler client : Server.clients) {
            try {
                client.dos.writeUTF(playerNames+"#"+this.name);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void checkReady() {
        boolean ready = true;
        for (ClientHandler client : Server.clients) {
            if(!client.isReady){
                ready = false;
                break;
            }
        }
        try {
            if (ready) {
                //SEND START TO ALL PLAYERS
                for (ClientHandler client : Server.clients) {

                    client.dos.writeUTF("start#" + this.name);
                }
                Server.clients.get(Server.turnIndex).dos.writeUTF("yourTurn#"+this.name);
            }
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

