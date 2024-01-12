import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MyServer implements Runnable {
    ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        new Thread(new MyServer()).start();
    }

    public MyServer() throws IOException {
        serverSocket = new ServerSocket(4000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("....... ******** ........");
                System.out.println(".......  SERVEUR ........");
                System.out.println("....... ******** ........");
                Socket sock = serverSocket.accept();
                System.out.println("Nouvelle connexion ...");


                new Thread(new Conversation(sock)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Conversation implements Runnable {
    Socket socket;
   public static  List<Client> listClient;
    Conversation(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
           ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
           ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

           if(ois.readObject()  instanceof Client){
                Client client = (Client) ois.readObject();
                client.setSocketClient(socket);
                client.setDateConnexion(LocalDateTime.now());
               listClient.add(client);
            } else if(ois.readObject()  instanceof Trame){
               Trame trame = (Trame) ois.readObject();
                if(trame.getForGroup()){
                    listClient.stream().filter(cl->cl.getSocketClient().isConnected()).
                            forEach(res->
                            {
                                try {
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(res.getSocketClient().getOutputStream());
                                    objectOutputStream.writeObject(trame);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            );
                }else{
                    //Envoi vers un seul client
                    Client destinataire = listClient.stream().filter(cl->cl.getLogin().equals(trame.getDestinataire())).findFirst().get();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(destinataire.getSocketClient().getOutputStream());
                    objectOutputStream.writeObject(trame);

                }
           }








            InputStream is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(isr);

            OutputStream os = this.socket.getOutputStream();
            OutputStreamWriter isw = new OutputStreamWriter(os);
            PrintWriter pw = new PrintWriter(isw, true);
            pw.write("Donner un chiffre");
            String reponse = bf.readLine();
            System.out.println("le client a répondu " + reponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
