package example.scrollingtext.ptbsejahtera;

public class DataClassPel {
    private String dataKegiatan;
    private String dataLembaga;
    private String dataPeriode;
    private String dataSkala;
    private String dataImage;

    public String getDataKegiatan() {
        return dataKegiatan;
    }

    public String getDataLembaga() {
        return dataLembaga;
    }

    public String getDataPeriode() {
        return dataPeriode;
    }

    public String getDataSkala() {
        return dataSkala;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassPel(String dataKegiatan, String dataLembaga, String dataPeriode, String dataSkala, String dataImage) {
        this.dataKegiatan = dataKegiatan;
        this.dataLembaga = dataLembaga;
        this.dataPeriode = dataPeriode;
        this.dataSkala = dataSkala;
        this.dataImage = dataImage;
    }
    public DataClassPel(){

    }
}
