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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivityPel extends AppCompatActivity {

    ImageView upimage, back;
    Button savebtn;
    EditText inkeg, inlem, inperi, inskala;
    String imageurl;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpelatihan);

        upimage= findViewById(R.id.upload4);
        inkeg= findViewById(R.id.kegiatanpel);
        inlem= findViewById(R.id.lembagapel);
        inperi= findViewById(R.id.periodepel);
        inskala= findViewById(R.id.skalapel);
        back= findViewById(R.id.back4);
        savebtn= findViewById(R.id.btnsavepel);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivityPel.this, Tipe_Sertif.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent>activityResultLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            upimage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivityPel.this, "Gambar tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Sertifikat Pelatihan").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivityPel.this);
        builder.setCancelable(false);
        builder.setView(R.layout.saving_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlimage = uriTask.getResult();
                imageurl = urlimage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        String kegiatan = inkeg.getText().toString();
        String lembaga = inlem.getText().toString();
        String periode = inperi.getText().toString();
        String skala = inskala.getText().toString();

        DataClassPeng dataClass = new DataClassPeng(kegiatan,lembaga,periode,skala,imageurl);

        FirebaseDatabase.getInstance().getReference("Deskripsi Pelatihan").child(kegiatan).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UploadActivityPel.this, "saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivityPel.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}