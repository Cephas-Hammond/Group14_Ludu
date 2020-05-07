package sample.Server;

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
    private Socket s;
    boolean isloggedin;

    // constructor
    public ClientHandler(Socket s) throws IOException {
        this.s = s;
        this.isloggedin=true;
        this.name="null";
        // obtain input and output streams
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;
        while (!s.isClosed())//true)
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

                //sets name if name has not been set
                if(name.equals("null")){
                    name = sender;
                    System.out.println("Username Acquired as: "+name);
					continue;
                }


                if(MsgToSend.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler client : Server.clients)
                {
                    // if the recipient is found, write on its
                    // output stream
                    if (!client.name.equals(sender) && client.isloggedin)
                    {
                        client.dos.writeUTF(received);
                        //break;
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
                this.isloggedin = false;
                Server.clients.remove(this);
                break;
            }
        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

