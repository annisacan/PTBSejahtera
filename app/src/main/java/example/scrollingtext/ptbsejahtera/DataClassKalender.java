package example.scrollingtext.ptbsejahtera;

public class DataClassKalender {
    private String dataTanggalKalender;
    private String dataLokasiKalender;
    private String dataAgendaKalender;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTanggalKalender() {
        return dataTanggalKalender;
    }

    public String getDataLokasiKalender() { return dataLokasiKalender; }

    public String getDataAgendaKalender() {
        return dataAgendaKalender;
    }


    public DataClassKalender(String dataTanggalKalender, String dataLokasiKalender, String dataAgendaKalender) {
        this.dataTanggalKalender = dataTanggalKalender;
        this.dataLokasiKalender = dataLokasiKalender;
        this.dataAgendaKalender = dataAgendaKalender;
    }

    public DataClassKalender(){

    }

}
