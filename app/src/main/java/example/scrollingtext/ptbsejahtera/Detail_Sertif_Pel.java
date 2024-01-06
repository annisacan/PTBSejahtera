package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_Sertif_Pel extends AppCompatActivity {
    ImageView detailImgPel, backDetailPel;
    TextView detailkegPel, detailLemPel, detailPerPel, detailSkalPel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_pel);

        detailImgPel = findViewById(R.id.viewdetailPel);
        detailkegPel= findViewById(R.id.data1Pel);
        detailLemPel = findViewById(R.id.data2Pel);
        detailPerPel = findViewById(R.id.data3Pel);
        detailSkalPel = findViewById(R.id.data4Pel);
        backDetailPel = findViewById(R.id.backDetailPel);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailkegPel.setText(bundle.getString("KegiatanPel"));
            detailLemPel.setText(bundle.getString("LembagaPel"));
            detailPerPel.setText(bundle.getString("PeriodePel"));
            detailSkalPel.setText(bundle.getString("SkalaPel"));
            Glide.with(this).load(bundle.getString("ImagePel")).into(detailImgPel);
        }

        backDetailPel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Pel.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}