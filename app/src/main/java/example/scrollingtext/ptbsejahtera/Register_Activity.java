package example.scrollingtext.ptbsejahtera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText signusername, signemail, signpass, signkonpass, signinst, signnohp;
    Button dafbtn, linbtn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");

        signusername= findViewById(R.id.inputusernamereg);
        signemail= findViewById(R.id.inputemailreg);
        signpass= findViewById(R.id.inputpassreg);
        signkonpass= findViewById(R.id.inputconfirpassreg);
        signinst= findViewById(R.id.inputinstitusi);
        signnohp= findViewById(R.id.inputnomorhp);
        dafbtn = findViewById(R.id.DaftarTombol);
        linbtn  = findViewById(R.id.belumPunyaAkunButton);

        dafbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( signusername.getText().toString().trim().isEmpty() ||
                signemail.getText().toString().trim().isEmpty() ||
                signpass.getText().toString().trim().isEmpty() ||
                signkonpass.getText().toString().trim().isEmpty() ||
                signinst.getText().toString().trim().isEmpty() ||
                signnohp.getText().toString().trim().isEmpty()){
                    Toast.makeText(Register_Activity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    String email = signemail.getText().toString().trim();
                    String pass = signpass.getText().toString().trim();
                    String konpass = signkonpass.getText().toString().trim();
                    
                    if (pass.length() < 6){
                        Toast.makeText(Register_Activity.this, "Minimal Password 6 Character", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(konpass)) {
                        Toast.makeText(Register_Activity.this, "Konfirmasi Password tidak sesuai", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    String userId = auth.getCurrentUser().getUid();
                                    DataUserClass user = new DataUserClass(
                                            signusername.getText().toString().trim(),
                                            signemail.getText().toString().trim(),
                                            signinst.getText().toString().trim(),
                                            signnohp.getText().toString().trim()
                                    );

                                    reference.child(userId).setValue(user);

                                    Toast.makeText(Register_Activity.this, "Akun Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                                }else {
                                    Toast.makeText(Register_Activity.this, "Akun Gagal Terdaftar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
        linbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}