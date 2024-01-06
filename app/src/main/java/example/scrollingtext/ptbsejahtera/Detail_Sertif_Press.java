package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_Sertif_Press extends AppCompatActivity {
    ImageView detailImgPress, backDetailPress;
    TextView detailkegPress, detailtglPress, detailCapPress, detailSkalPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertif_press);

        detailImgPress = findViewById(R.id.viewdetailPress);
        detailkegPress= findViewById(R.id.data1Press);
        detailtglPress = findViewById(R.id.data2Press);
        detailCapPress = findViewById(R.id.data3Press);
        detailSkalPress = findViewById(R.id.data4Press);
        backDetailPress = findViewById(R.id.backDetailPress);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailkegPress.setText(bundle.getString("KegiatanPress"));
            detailtglPress.setText(bundle.getString("TanggalPress"));
            detailCapPress.setText(bundle.getString("CapaianPress"));
            detailSkalPress.setText(bundle.getString("SkalaPress"));
            Glide.with(this).load(bundle.getString("ImagePress")).into(detailImgPress);
        }

        backDetailPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Press.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}