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

public class AdapterSertifPress extends RecyclerView.Adapter <SertifPressViewHolder> {
    private Context context;
    private List<DataClassPress>dataPress;

    public AdapterSertifPress(Context context, List<DataClassPress> dataPress) {
        this.context = context;
        this.dataPress = dataPress;
    }

    @NonNull
    @Override
    public SertifPressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sertif_layout_press, parent, false);
        return new SertifPressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SertifPressViewHolder holder, int position) {
        Glide.with(context).load(dataPress.get(position).getDataImage()).into(holder.recPressImg);
        holder.reckeg.setText(dataPress.get(position).getDataKegiatan());
        holder.rectgl.setText(dataPress.get(position).getDataTanggal());
        holder.reccap.setText(dataPress.get(position).getDataCapaian());
        holder.recskal.setText(dataPress.get(position).getDataSkala());

        holder.recCardPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Sertif_Press.class);
                intent.putExtra("ImagePress", dataPress.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("KegiatanPress", dataPress.get(holder.getAdapterPosition()).getDataKegiatan());
                intent.putExtra("TanggalPress", dataPress.get(holder.getAdapterPosition()).getDataTanggal());
                intent.putExtra("CapaianPress", dataPress.get(holder.getAdapterPosition()).getDataCapaian());
                intent.putExtra("SkalaPress", dataPress.get(holder.getAdapterPosition()).getDataSkala());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPress.size();
    }
}

class SertifPressViewHolder extends RecyclerView.ViewHolder{
    ImageView recPressImg;
    TextView reckeg, rectgl, reccap, recskal;
    CardView recCardPress;

    public SertifPressViewHolder(@NonNull View itemView) {
        super(itemView);

        recPressImg = itemView.findViewById(R.id.rcimagePress);
        recCardPress = itemView.findViewById(R.id.rcCardPress);
        reckeg = itemView.findViewById(R.id.rcJudulPress);
        rectgl = itemView.findViewById(R.id.rctanggalPress);
        reccap = itemView.findViewById(R.id.rcCapaianPress);
        recskal = itemView.findViewById(R.id.rcSkalaPress);
    }
}
