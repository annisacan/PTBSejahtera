package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import example.scrollingtext.ptbsejahtera.databinding.ActivityDetailSertifBinding;

public class Detail_Sertif extends AppCompatActivity {
    ImageView detailImgOrg;
    TextView detailOrg, detailPer, detailJab, detailDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif);

        detailImgOrg = findViewById(R.id.viewdetail);
        detailOrg = findViewById(R.id.data1);
        detailPer = findViewById(R.id.data2);
        detailJab = findViewById(R.id.data3);
        detailDiv = findViewById(R.id.data4);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailOrg.setText(bundle.getString("Organisasi"));
            detailPer.setText(bundle.getString("Periode"));
            detailJab.setText(bundle.getString("Jabatan"));
            detailDiv.setText(bundle.getString("Divisi"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImgOrg);
        }
    }
}