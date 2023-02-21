package DB;

public class exemple {
    private int id;
    private String cont;

    public exemple() {
    }

    public exemple(int id, String cont) {
        this.id = id;
        this.cont = cont;
    }

    public exemple(String cont) {
        this.cont = cont;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
