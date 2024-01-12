import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Trame implements Serializable {

    private String message;
    private String nomEmeteur;
    private List<String> destinataire;
    private LocalDateTime dateEnvoi;
    private Boolean ForGroup;

    public String getNomEmeteur() {
        return nomEmeteur;
    }

    public void setNomEmeteur(String nomEmeteur) {
        this.nomEmeteur = nomEmeteur;
    }

    public List<String> getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(List<String> destinataire) {
        this.destinataire = destinataire;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Boolean getForGroup() {
        return ForGroup;
    }

    public void setForGroup(Boolean forGroup) {
        ForGroup = forGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Trame(String message, String nomEmeteur, List<String> destinataire, Boolean forGroup) {
        this.message = message;
        this.nomEmeteur = nomEmeteur;
        this.destinataire = destinataire;
        this.dateEnvoi = LocalDateTime.now();
        ForGroup = forGroup;
    }

    public Trame() {
    }
}
