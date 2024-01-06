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

public class AdapterSertifPeng extends RecyclerView.Adapter<SertifPengViewHolder> {
    private Context context;
    private List<DataClassPeng>dataPeng;

    public AdapterSertifPeng(Context context, List<DataClassPeng> dataPeng) {
        this.context = context;
        this.dataPeng = dataPeng;
    }

    @NonNull
    @Override
    public SertifPengViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sertif_layout_peng, parent, false);
        return new SertifPengViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifPengViewHolder holder, int position) {
        Glide.with(context).load(dataPeng.get(position).getDataImage()).into(holder.recPengImg);
        holder.reckeg.setText(dataPeng.get(position).getDataKegiatan());
        holder.recper.setText(dataPeng.get(position).getDataPeriode());
        holder.recIns.setText(dataPeng.get(position).getDataInstansi());
        holder.recpos.setText(dataPeng.get(position).getDataPosisi());

        holder.recCardPeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Sertif_Peng.class);
                intent.putExtra("ImagePeng", dataPeng.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("KegiatanPeng", dataPeng.get(holder.getAdapterPosition()).getDataKegiatan());
                intent.putExtra("PeriodePeng", dataPeng.get(holder.getAdapterPosition()).getDataPeriode());
                intent.putExtra("InstansiPeng", dataPeng.get(holder.getAdapterPosition()).getDataInstansi());
                intent.putExtra("PosisiPeng", dataPeng.get(holder.getAdapterPosition()).getDataPosisi());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPeng.size();
    }
}
class SertifPengViewHolder extends RecyclerView.ViewHolder{
    ImageView recPengImg;
    TextView reckeg, recIns, recper, recpos;
    CardView recCardPeng;

    public SertifPengViewHolder(@NonNull View itemView) {
        super(itemView);
        recPengImg = itemView.findViewById(R.id.rcimagePeng);
        recCardPeng = itemView.findViewById(R.id.rcCardPeng);
        reckeg = itemView.findViewById(R.id.rcJudulPeng);
        recper = itemView.findViewById(R.id.rctanggalPeng);
        recIns = itemView.findViewById(R.id.rcInstansiPeng);
        recpos = itemView.findViewById(R.id.rcPosisiPeng);
    }
}
