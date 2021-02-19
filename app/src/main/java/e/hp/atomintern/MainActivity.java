package e.hp.atomintern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button b1;
    private FirebaseAuth auth;
    private TextInputEditText eemail,epassword;
    private ProgressBar progressBar;
    private TextView tv;
    private String key,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            finish();
            Intent i = new Intent(MainActivity.this, HomePage.class);
            startActivity(i);
        }
        else{
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        b1 = (Button) findViewById(R.id.login);
        tv = (TextView) findViewById(R.id.register);
        eemail = (TextInputEditText) findViewById(R.id.mail);
        epassword = (TextInputEditText) findViewById(R.id.pwd);
        auth = FirebaseAuth.getInstance();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eemail.getText().toString().trim();
                String password = epassword.getText().toString().trim();
                if(email.isEmpty()||password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter details", Toast.LENGTH_LONG).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent i = new Intent(MainActivity.this, HomePage.class);
                                startActivity(i);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        }

    }
}