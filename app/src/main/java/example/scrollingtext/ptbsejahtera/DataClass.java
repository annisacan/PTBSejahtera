package example.scrollingtext.ptbsejahtera;

import java.security.PrivateKey;

public class DataClass {
    private String dataKegiatan;
    private String dataTanggal;
    private String dataCapaian;
    private String dataSkala;
    private String dataImage;

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

    public DataClass(String dataKegiatan, String dataTanggal, String dataCapaian, String dataSkala, String dataImage) {
        this.dataKegiatan = dataKegiatan;
        this.dataTanggal = dataTanggal;
        this.dataCapaian = dataCapaian;
        this.dataSkala = dataSkala;
        this.dataImage = dataImage;
    }
}
