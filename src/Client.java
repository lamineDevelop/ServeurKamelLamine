import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client implements Serializable {
    private String login;
    private LocalDateTime dateConnexion;

    private Socket socketClient;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getDateConnexion() {
        return dateConnexion;
    }

    public void setDateConnexion(LocalDateTime dateConnexion) {
        this.dateConnexion = dateConnexion;
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public Client(String login, LocalDateTime dateConnexion, Socket socketClient) {
        this.login = login;
        this.dateConnexion = dateConnexion;
        this.socketClient = socketClient;
    }

    public Client() {
    }
}
