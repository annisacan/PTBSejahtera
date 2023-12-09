package example.scrollingtext.ptbsejahtera;

public class DataUserClass {
    String username, email, institusi, nomor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitusi() {
        return institusi;
    }

    public void setInstitusi(String institusi) {
        this.institusi = institusi;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public DataUserClass(String username, String email, String institusi, String nomor) {
        this.username = username;
        this.email = email;
        this.institusi = institusi;
        this.nomor = nomor;
    }

    public DataUserClass() {
    }
}
