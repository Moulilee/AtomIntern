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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView tv;
    private Button register;
    public TextInputEditText namei,mobi,emaili,pwdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        tv = (TextView) findViewById(R.id.login);
        namei=(TextInputEditText)findViewById(R.id.name);
        mobi=(TextInputEditText)findViewById(R.id.mob);
        emaili=(TextInputEditText)findViewById(R.id.email);
        pwdi=(TextInputEditText)findViewById(R.id.pwd);
        register=(Button)findViewById(R.id.register);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(RegistrationPage.this, MainActivity.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nmobile=mobi.getText().toString().trim();
                final String nname=namei.getText().toString().trim();
                final String nmail=emaili.getText().toString().trim();
                final String npwd=pwdi.getText().toString().trim();
                if(nmail.isEmpty()||nname.isEmpty()||nmobile.isEmpty()||npwd.isEmpty()){
                    Toast.makeText(RegistrationPage.this, "Please fill all details", Toast.LENGTH_LONG).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(nmail, npwd).addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                db = FirebaseDatabase.getInstance().getReference("Users");
                                User user = new User(nname, nmobile, nmail);
                                db.child(currentUser.getUid()).setValue(user).addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Intent i1 = new Intent(RegistrationPage.this, HomePage.class);
                                            finish();
                                            startActivity(i1);
                                        } else {
                                            //display a failure message
                                            Toast.makeText(RegistrationPage.this, "Failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }
}