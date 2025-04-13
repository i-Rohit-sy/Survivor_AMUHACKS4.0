import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server(){
        connections = new ArrayList<>();
        done = false;
    }
    @Override
    public void run(){
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            ShutDown();
        }
    }


    public void Broadcast(String message){
        for(ConnectionHandler ch:connections){
            if(ch != null){
                ch.SendMessage(message);
            }
        }
    }

    public void ShutDown(){
        try {
            done = true;
            pool.shutdown();
            if(!server.isClosed()){
                server.close();
            }
            for(ConnectionHandler ch:connections){
                ch.ShutDown();
            }
        }catch (IOException e){
            //ignore
        }

    }
    class ConnectionHandler implements Runnable{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String NickName;

        public ConnectionHandler(Socket client){
            this.client = client;
        }
        /**
         * Runs this operation.
         */
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please Enter Your name: ");
                NickName = in.readLine();
                System.out.println(NickName + " Connected!!");
                Broadcast(NickName+ " Joined the Chat!");
                String message;
                while ((message = in.readLine()) != null){
                    if(message.startsWith("/nick")){
                        String[] messageSplit = message.split(" ",2);
                        if(messageSplit.length == 2){
                            Broadcast(NickName + " Updated to "+ messageSplit[1]);
                            System.out.println(NickName+ "Updated to "+ messageSplit[1]);
                            NickName = messageSplit[1];
                            out.println("Successfully changed to "+NickName);
                        }
                        else{
                            out.println("No NickName Provided");
                        }
                    } else if (message.startsWith("/quit")) {
                        Broadcast(NickName + " left the Chat!");
                        ShutDown();
                    }
                    else{
                        Broadcast(NickName+ " : "+ message);
                    }
                }
            } catch (Exception e) {
                ShutDown();
            }
        }

        public void SendMessage (String message){
            out.println(message);
        }
        public void ShutDown(){
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            }catch (IOException e){
                //ignore
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
