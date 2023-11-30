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

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signusername, signemail, signpass, signkonpass, signinst, signnohp;
    private Button dafbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth= FirebaseAuth.getInstance();
        signusername= findViewById(R.id.inputusernamereg);
        signemail= findViewById(R.id.inputemailreg);
        signpass= findViewById(R.id.inputpassreg);
        signkonpass= findViewById(R.id.inputconfirpassreg);
        signinst= findViewById(R.id.inputinstitusi);
        signnohp= findViewById(R.id.inputnomorhp);

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
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Register_Activity.this, "Akun Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                            }else {
                                Toast.makeText(Register_Activity.this, "Akun Gagal Terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}