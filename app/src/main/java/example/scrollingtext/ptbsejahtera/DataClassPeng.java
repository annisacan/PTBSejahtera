package example.scrollingtext.ptbsejahtera;

public class DataClassPeng {
    private String dataKegiatan;
    private String dataInstansi;
    private String dataPeriode;
    private String dataPosisi;
    private String dataImage;

    public String getDataKegiatan() {
        return dataKegiatan;
    }

    public String getDataInstansi() {
        return dataInstansi;
    }

    public String getDataPeriode() {
        return dataPeriode;
    }

    public String getDataPosisi() {
        return dataPosisi;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassPeng(String dataKegiatan, String dataInstansi, String dataPeriode, String dataPosisi, String dataImage) {
        this.dataKegiatan = dataKegiatan;
        this.dataInstansi = dataInstansi;
        this.dataPeriode = dataPeriode;
        this.dataPosisi = dataPosisi;
        this.dataImage = dataImage;
    }
}