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

public class AdapterSertifOrg extends RecyclerView.Adapter<SertifViewHolder> {

    private Context context;
    private List<DataClassOrg>dataOrg;

    public AdapterSertifOrg(Context context, List<DataClassOrg> dataOrg) {
        this.context = context;
        this.dataOrg = dataOrg;
    }

    @NonNull
    @Override
    public SertifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sertif_layout_org, parent, false);
        return new SertifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifViewHolder holder, int position) {
        Glide.with(context).load(dataOrg.get(position).getDataImage()).into(holder.recOrgImg);
        holder.recorg.setText(dataOrg.get(position).getDataOrganisasi());
        holder.recper.setText(dataOrg.get(position).getDataPeriode());
        holder.recjab.setText(dataOrg.get(position).getDataJabatan());
        holder.recdiv.setText(dataOrg.get(position).getDataDivisi());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_SertifOrg.class);
                intent.putExtra("Image", dataOrg.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Organisasi", dataOrg.get(holder.getAdapterPosition()).getDataOrganisasi());
                intent.putExtra("Periode", dataOrg.get(holder.getAdapterPosition()).getDataPeriode());
                intent.putExtra("Jabatan", dataOrg.get(holder.getAdapterPosition()).getDataJabatan());
                intent.putExtra("Divisi", dataOrg.get(holder.getAdapterPosition()).getDataDivisi());
                intent.putExtra("Key", dataOrg.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataOrg.size();
    }
}
class SertifViewHolder extends RecyclerView.ViewHolder{
    ImageView recOrgImg;
    TextView recorg, recper, recjab, recdiv;
    CardView recCard;
    public SertifViewHolder(@NonNull View itemView) {
        super(itemView);

        recOrgImg = itemView.findViewById(R.id.rcimage_sertif);
        recCard = itemView.findViewById(R.id.rcCard);
        recorg = itemView.findViewById(R.id.rcJudul_sertif);
        recper = itemView.findViewById(R.id.rctanggal_sertif);
        recjab = itemView.findViewById(R.id.rcjab);
        recdiv = itemView.findViewById(R.id.rcdiv);
    }
}
