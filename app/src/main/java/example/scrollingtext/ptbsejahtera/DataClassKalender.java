package example.scrollingtext.ptbsejahtera;

public class DataClassKalender {
    private String dataTanggalKalender;
    private String dataLokasiKalender;
    private String dataAgendaKalender;


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
