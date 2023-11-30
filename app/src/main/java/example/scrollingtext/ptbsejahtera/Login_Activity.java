package example.scrollingtext.ptbsejahtera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginemail, loginpass;
    private Button loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth= FirebaseAuth.getInstance();
        loginemail= findViewById(R.id.inputusernamelogin);
        loginpass= findViewById(R.id.inputpasslogin);
        loginbtn= findViewById(R.id.masukTombol);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= loginemail.getText().toString();
                String pass= loginpass.getText().toString();

                if (email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.matches())){
                    if (!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Login_Activity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent());
                            }
                        });
                    }
                }
            }
        });
    }
}