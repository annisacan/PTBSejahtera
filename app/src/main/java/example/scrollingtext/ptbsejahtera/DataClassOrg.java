package example.scrollingtext.ptbsejahtera;

public class DataClassOrg {
    private String dataOrganisasi;
    private String dataPeriode;
    private String dataJabatan;
    private String dataDivisi;
    private String dataImage;

    public String getDataOrganisasi() {
        return dataOrganisasi;
    }

    public String getDataPeriode() {
        return dataPeriode;
    }

    public String getDataJabatan() {
        return dataJabatan;
    }

    public String getDataDivisi() {
        return dataDivisi;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassOrg(String dataOrganisasi, String dataPeriode, String dataJabatan, String dataDivisi, String dataImage) {
        this.dataOrganisasi = dataOrganisasi;
        this.dataPeriode = dataPeriode;
        this.dataJabatan = dataJabatan;
        this.dataDivisi = dataDivisi;
        this.dataImage = dataImage;
    }
}
