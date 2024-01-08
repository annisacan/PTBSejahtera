package example.scrollingtext.ptbsejahtera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterSertifPel extends RecyclerView.Adapter<SertifPelViewHolder>{
    private Context context;
    private List<DataClassPel>dataPel;

    public AdapterSertifPel(Context context, List<DataClassPel> dataPel) {
        this.context = context;
        this.dataPel = dataPel;
    }

    @NonNull
    @Override
    public SertifPelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sertif_layout_pel, parent, false);
        return new SertifPelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifPelViewHolder holder, int position) {
        Glide.with(context).load(dataPel.get(position).getDataImage()).into(holder.recPelImg);
        holder.reckeg.setText(dataPel.get(position).getDataKegiatan());
        holder.recper.setText(dataPel.get(position).getDataPeriode());
        holder.reclem.setText(dataPel.get(position).getDataLembaga());
        holder.recskal.setText(dataPel.get(position).getDataSkala());

        holder.recCardPel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Sertif_Pel.class);
                intent.putExtra("ImagePel", dataPel.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("KegiatanPel", dataPel.get(holder.getAdapterPosition()).getDataKegiatan());
                intent.putExtra("PeriodePel", dataPel.get(holder.getAdapterPosition()).getDataPeriode());
                intent.putExtra("LembagaPel", dataPel.get(holder.getAdapterPosition()).getDataLembaga());
                intent.putExtra("SkalaPel", dataPel.get(holder.getAdapterPosition()).getDataSkala());
                intent.putExtra("Key", dataPel.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataPel.size();
    }
}

class SertifPelViewHolder extends RecyclerView.ViewHolder{
    ImageView recPelImg;
    TextView reckeg, reclem, recper, recskal;
    CardView recCardPel;

    public SertifPelViewHolder(@NonNull View itemView) {
        super(itemView);
        recPelImg = itemView.findViewById(R.id.rcimagePel);
        recCardPel = itemView.findViewById(R.id.rcCardPel);
        reckeg = itemView.findViewById(R.id.rcJudulPel);
        recper = itemView.findViewById(R.id.rctanggalPel);
        reclem = itemView.findViewById(R.id.rclembagaPel);
        recskal = itemView.findViewById(R.id.rcskalaPel);
    }
}
