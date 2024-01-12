import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDateTime;

public class Chat implements Serializable {
    private String id;
    private List<Client> clients;

    public Chat(String id) {
        this.id = id;
        this.clients = new ArrayList<Client>(); 
    }
}
