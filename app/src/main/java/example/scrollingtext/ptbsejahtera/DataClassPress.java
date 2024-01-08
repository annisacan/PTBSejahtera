package example.scrollingtext.ptbsejahtera;

public class DataClassPress {
    private String dataKegiatan;
    private String dataTanggal;
    private String dataCapaian;
    private String dataSkala;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataKegiatan() {
        return dataKegiatan;
    }

    public String getDataTanggal() {
        return dataTanggal;
    }

    public String getDataCapaian() {
        return dataCapaian;
    }

    public String getDataSkala() {
        return dataSkala;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassPress(String dataKegiatan, String dataTanggal, String dataCapaian, String dataSkala, String dataImage) {
        this.dataKegiatan = dataKegiatan;
        this.dataTanggal = dataTanggal;
        this.dataCapaian = dataCapaian;
        this.dataSkala = dataSkala;
        this.dataImage = dataImage;
    }
    public DataClassPress(){

    }
}
