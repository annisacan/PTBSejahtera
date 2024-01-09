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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class Edit_Pel extends AppCompatActivity {
    ImageView editImg;
    TextView editKeg, editLem, editPer, editSkal;
    Button saveEditBtn;
    String Kegiatan, Lembaga, Periode, Skala;
    String imageUrl;
    String key, oldImgUrl;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pel);

        saveEditBtn = findViewById(R.id.editBtnSavePel);
        editKeg = findViewById(R.id.editKegiatanPel);
        editLem = findViewById(R.id.editLembagaPel);
        editPer = findViewById(R.id.editPeriodePel);
        editSkal = findViewById(R.id.editSkalaPel);
        editImg = findViewById(R.id.editImgPel);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            editImg.setImageURI(uri);
                        } else {
                            Toast.makeText(Edit_Pel.this, "Tidak ada Gambar yang dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(Edit_Pel.this).load(bundle.getString("ImagePel")).into(editImg);
            editKeg.setText(bundle.getString("KegiatanPel"));
            editLem.setText(bundle.getString("LembagaPel"));
            editPer.setText(bundle.getString("PeriodePel"));
            editSkal.setText(bundle.getString("SkalaPel"));
            key = bundle.getString("Key");
            oldImgUrl = bundle.getString("ImagePel");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Data Sertifikat User").child(uid).child("Sertifikat Pelatihan").child(key);

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(Edit_Pel.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Pelatihan").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Pel.this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        Kegiatan = editKeg.getText().toString().trim();
        Lembaga = editLem.getText().toString().trim();
        Periode = editPer.getText().toString().trim();
        Skala = editSkal.getText().toString().trim();

        DataClassPel dataPel = new DataClassPel(Kegiatan, Lembaga, Periode, Skala, imageUrl);

        databaseReference.setValue(dataPel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImgUrl);
                    reference.delete();
                    Toast.makeText(Edit_Pel.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Edit_Pel.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}