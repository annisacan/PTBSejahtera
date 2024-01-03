package example.scrollingtext.ptbsejahtera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivityPeng extends AppCompatActivity {

    ImageView upimage, back;
    Button savebtn;
    EditText inkeg, inins, inper, inpos;
    String imageurl;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpengalaman);

        upimage= findViewById(R.id.upload2);
        inkeg= findViewById(R.id.kegiatanpeng);
        inins= findViewById(R.id.instansipeng);
        inper= findViewById(R.id.periodepeng);
        inpos= findViewById(R.id.posisipeng);
        back= findViewById(R.id.back2);
        savebtn= findViewById(R.id.btnsavepeng);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivityPeng.this, Tipe_Sertif.class);
                startActivity(intent);
            }
        });

        upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void pickImage() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        upimage.setImageURI(uri);
                    } else {
                        Toast.makeText(UploadActivityPeng.this, "Gambar tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public void saveData() {
        if (uri == null) {
            Toast.makeText(this, "Tolong masukkan gambar", Toast.LENGTH_SHORT).show();
            return;
        }
        String kegiatan = inkeg.getText().toString();
        String instansi = inins.getText().toString();
        String periode = inper.getText().toString();
        String posisi = inpos.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Pengalaman").child(uid).child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri urlimage) {
                        imageurl = urlimage.toString();
                        uploadData(kegiatan, instansi, periode, posisi, uid);
                        dialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(UploadActivityPeng.this, "Gagal mengupload gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadData(String kegiatan, String instansi, String periode, String posisi, String uid) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid);
        DatabaseReference dataReference = userReference.child("Deskripsi Pengalaman").child(kegiatan);

        DataClassPeng dataClassPeng = new DataClassPeng(kegiatan, instansi, periode, posisi, imageurl);

        dataReference.setValue(dataClassPeng).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadActivityPeng.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UploadActivityPeng.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}