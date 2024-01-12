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
                Socket sock = serverSocket.accept();
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
           Object input = ois.readObject();
           if(input instanceof Client){
                Client client = (Client) input;
                client.setSocketClient(socket);
                client.setDateConnexion(LocalDateTime.now());
               listClient.add(client);
               // AFAIRE: envoyé listClient a tout les client déja connecté

            } else if(input instanceof Trame){
               Trame trame = (Trame) input;
                if(trame.getForGroup()){
                    listClient.stream().filter(cl->cl.getSocketClient().isConnected()).
                            forEach(member->
                            {
                                try {
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(member.getSocketClient().getOutputStream());
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



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
