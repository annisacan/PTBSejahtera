package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detail_Sertif_Peng extends AppCompatActivity {
    ImageView detailImgPeng, backDetailPeng;
    TextView detailkegPeng, detailInsPeng, detailPerPeng, detailPosisiPeng;
    Button detailDelete;
    String key = "";
    String imgurl = "";

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
        detailDelete = findViewById(R.id.deletePeng);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailkegPeng.setText(bundle.getString("KegiatanPeng"));
            detailInsPeng.setText(bundle.getString("InstansiPeng"));
            detailPerPeng.setText(bundle.getString("PeriodePeng"));
            detailPosisiPeng.setText(bundle.getString("PosisiPeng"));
            key = bundle.getString("Key");
            imgurl = bundle.getString("ImagePeng");
            Glide.with(this).load(bundle.getString("ImagePeng")).into(detailImgPeng);
        }

        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pengalaman");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imgurl);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Detail_Sertif_Peng.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

        backDetailPeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Peng.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}