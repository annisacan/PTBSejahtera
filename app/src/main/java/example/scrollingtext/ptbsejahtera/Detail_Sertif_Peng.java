package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_Sertif_Peng extends AppCompatActivity {
    ImageView detailImgPeng, backDetailPeng;
    TextView detailkegPeng, detailInsPeng, detailPerPeng, detailPosisiPeng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_peng);

        detailImgPeng = findViewById(R.id.viewdetailPeng);
        detailkegPeng = findViewById(R.id.data1Peng);
        detailInsPeng = findViewById(R.id.data2Peng);
        detailPerPeng = findViewById(R.id.data3Peng);
        detailPosisiPeng = findViewById(R.id.data4Peng);
        backDetailPeng = findViewById(R.id.backDetailPeng);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailkegPeng.setText(bundle.getString("KegiatanPeng"));
            detailInsPeng.setText(bundle.getString("InstansiPeng"));
            detailPerPeng.setText(bundle.getString("PeriodePeng"));
            detailPosisiPeng.setText(bundle.getString("PosisiPeng"));
            Glide.with(this).load(bundle.getString("ImagePeng")).into(detailImgPeng);
        }

        backDetailPeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Peng.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}