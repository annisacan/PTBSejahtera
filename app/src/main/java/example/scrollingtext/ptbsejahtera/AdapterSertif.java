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

public class AdapterSertif extends RecyclerView.Adapter<SertifViewHolder> {

    private Context context;
    private List<DataClassOrg>dataOrg;

    public AdapterSertif(Context context, List<DataClassOrg> dataOrg) {
        this.context = context;
        this.dataOrg = dataOrg;
    }

    @NonNull
    @Override
    public SertifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sertif_layout, parent, false);
        return new SertifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifViewHolder holder, int position) {
        Glide.with(context).load(dataOrg.get(position).getDataImage()).into(holder.recOrgImg);
        holder.recorg.setText(dataOrg.get(position).getDataOrganisasi());
        holder.recper.setText(dataOrg.get(position).getDataPeriode());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Sertif.class);
                intent.putExtra("Image", dataOrg.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Organisasi", dataOrg.get(holder.getAdapterPosition()).getDataOrganisasi());
                intent.putExtra("Periode", dataOrg.get(holder.getAdapterPosition()).getDataPeriode());

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
    TextView recorg, recper;
    CardView recCard;
    public SertifViewHolder(@NonNull View itemView) {
        super(itemView);

        recOrgImg = itemView.findViewById(R.id.rcimage_sertif);
        recCard = itemView.findViewById(R.id.rcCard);
        recorg = itemView.findViewById(R.id.rcJudul_sertif);
        recper = itemView.findViewById(R.id.rctanggal_sertif);
    }
}
