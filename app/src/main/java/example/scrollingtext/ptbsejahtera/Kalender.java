package example.scrollingtext.ptbsejahtera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

public class Kalender extends AppCompatActivity {
    private FirebaseAuth auth;
    ImageView backkalender;
    Button btnsavekalender;
    EditText tanggalkalender, lokasikalender, agendakalender;
    FirebaseDatabase database;
    DatabaseReference reference;

    private DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);

        backkalender = findViewById(R.id.backkalender);
        tanggalkalender = findViewById(R.id.tanggalkalender);
        lokasikalender = findViewById(R.id.lokasikalender);
        agendakalender = findViewById(R.id.agendakalender);
        btnsavekalender = findViewById(R.id.btnsavekalender);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("agenda").child(uid);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");

        tanggalkalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        backkalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kalender.this, KalenderFragment.class);
                startActivity(intent);
            }
        });
        lokasikalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        agendakalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnsavekalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveKalender();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    tanggalkalender.setText(selectedDate);
                },
                2022, 0, 1);

        datePickerDialog.show();
    }


    public void saveKalender() {

        String TanggalKalender = tanggalkalender.getText().toString();
        String LokasiKalender = lokasikalender.getText().toString();
        String AgendaKalender = agendakalender.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TanggalKalender.isEmpty() || LokasiKalender.isEmpty() || AgendaKalender.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Agenda").child(uid);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.activity_kalender);
        AlertDialog dialog = builder.create();
        dialog.show();

        DatabaseReference newEntryRef = databaseReference.push();
        String newEntryKey = newEntryRef.getKey(); // Mendapatkan ID unik yang dibuat oleh Firebase

        DataClassKalender dataKalender = new DataClassKalender(TanggalKalender, LokasiKalender, AgendaKalender);

        newEntryRef.setValue(dataKalender)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Kalender.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Kalender.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}