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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadActivityOrg extends AppCompatActivity {

    ImageView upimage, back;
    Button savebtn;
    EditText inorg, inperiod, injab, indiv;
    String imageurl;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadorganisasi);

        upimage= findViewById(R.id.upload3);
        inorg= findViewById(R.id.org);
        inperiod= findViewById(R.id.periodorg);
        injab= findViewById(R.id.jabatanorg);
        indiv= findViewById(R.id.divorg);
        back= findViewById(R.id.back3);
        savebtn= findViewById(R.id.btnsaveorg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivityOrg.this, Tipe_Sertif.class);
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
                        Toast.makeText(UploadActivityOrg.this, "Gambar tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public void saveData() {
        if (uri == null) {
            Toast.makeText(this, "Tolong masukkan gambar", Toast.LENGTH_SHORT).show();
            return;
        }
        String organisasi = inorg.getText().toString();
        String periode = inperiod.getText().toString();
        String jabatan = injab.getText().toString();
        String divisi = indiv.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Organisasi").child(uid).child(uri.getLastPathSegment());

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
                        uploadData(organisasi, periode, jabatan, divisi, uid);
                        dialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(UploadActivityOrg.this, "Gagal mengupload gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadData(String organisasi, String periode, String jabatan, String divisi, String uid) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid);
        DatabaseReference dataReference = userReference.child("Sertifikat Organisasi").child(currentDate);

        DataClassOrg dataClassOrg = new DataClassOrg(organisasi, periode, jabatan, divisi, imageurl);

        dataReference.setValue(dataClassOrg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadActivityOrg.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UploadActivityOrg.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}