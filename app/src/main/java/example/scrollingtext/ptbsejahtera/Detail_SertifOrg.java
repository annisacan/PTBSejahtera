package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class Detail_SertifOrg extends AppCompatActivity {
    ImageView detailImgOrg, backDetail;
    TextView detailOrg, detailPer, detailJab, detailDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_org);

        detailImgOrg = findViewById(R.id.viewdetail);
        detailOrg = findViewById(R.id.data1);
        detailPer = findViewById(R.id.data2);
        detailJab = findViewById(R.id.data3);
        detailDiv = findViewById(R.id.data4);
        backDetail = findViewById(R.id.backDetail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailOrg.setText(bundle.getString("Organisasi"));
            detailPer.setText(bundle.getString("Periode"));
            detailJab.setText(bundle.getString("Jabatan"));
            detailDiv.setText(bundle.getString("Divisi"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImgOrg);
        }

        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_SertifOrg.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}