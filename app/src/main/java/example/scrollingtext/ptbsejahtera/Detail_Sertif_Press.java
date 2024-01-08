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

public class Detail_Sertif_Press extends AppCompatActivity {
    ImageView detailImgPress, backDetailPress;
    TextView detailkegPress, detailtglPress, detailCapPress, detailSkalPress;
    Button detailDelete;
    String key = "";
    String imgurl = "";

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
        detailDelete = findViewById(R.id.deletePress);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailkegPress.setText(bundle.getString("KegiatanPress"));
            detailtglPress.setText(bundle.getString("TanggalPress"));
            detailCapPress.setText(bundle.getString("CapaianPress"));
            detailSkalPress.setText(bundle.getString("SkalaPress"));
            key = bundle.getString("Key");
            imgurl = bundle.getString("ImagePress");
            Glide.with(this).load(bundle.getString("ImagePress")).into(detailImgPress);
        }

        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Prestasi");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imgurl);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(Detail_Sertif_Press.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

        backDetailPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Sertif_Press.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}