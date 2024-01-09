package example.scrollingtext.ptbsejahtera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterKalender extends RecyclerView.Adapter<KalenderViewHolder> {

    private Context context;
    private List<DataClassKalender>dataKal;

    public AdapterKalender(Context context, List<DataClassKalender> dataKalender) {
        this.context = context;
        this.dataKal = dataKalender;
    }

    @NonNull
    @Override
    public KalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kalender_layout, parent, false);
        return new KalenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KalenderViewHolder holder, int position) {

        holder.rectanggal.setText(dataKal.get(position).getDataTanggalKalender());
        holder.reclokasi.setText(dataKal.get(position).getDataLokasiKalender());
        holder.recagenda.setText(dataKal.get(position).getDataAgendaKalender());

        holder.reccardAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan logika untuk menangani klik pada tombol detail kalender di sini
                // Anda bisa memulai aktivitas baru atau menampilkan detail kalender di sini
                // Misalnya:
                Intent intent = new Intent(context, Detail_Kalender.class);
                intent.putExtra("Tanggal", dataKal.get(holder.getAdapterPosition()).getDataTanggalKalender());
                intent.putExtra("Lokasi", dataKal.get(holder.getAdapterPosition()).getDataLokasiKalender());
                intent.putExtra("Agenda", dataKal.get(holder.getAdapterPosition()).getDataAgendaKalender());
                intent.putExtra("Key", dataKal.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataKal.size();
    }
}
class KalenderViewHolder extends RecyclerView.ViewHolder{
    TextView rectanggal, reclokasi, recagenda;
    CardView reccardAgenda;
    public KalenderViewHolder(@NonNull View itemView) {
        super(itemView);

        rectanggal = itemView.findViewById(R.id.tanggalkalender);
        reclokasi = itemView.findViewById(R.id.lokasikalender);
        recagenda = itemView.findViewById(R.id.agendakalender);
        reccardAgenda = itemView.findViewById(R.id.cardAgenda);

    }
}