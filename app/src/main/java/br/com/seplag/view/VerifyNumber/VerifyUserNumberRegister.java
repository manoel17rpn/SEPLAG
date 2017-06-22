package br.com.seplag.view.VerifyNumber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import br.com.seplag.R;
import br.com.seplag.helper.UserSessionHelper;
import br.com.seplag.model.UserModel;
import br.com.seplag.view.LoginActivity;
import br.com.seplag.view.MainActivity;

public class VerifyUserNumberRegister extends AppCompatActivity {
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private Button bt_verify;
    private EditText code;
    private Button bt_sendcode;
    private EditText number;
    private String phone;
    private TextInputLayout textInputLayout;
    private TextInputLayout textInputLayout1;
    private TextView textView;
    private UserModel user;
    ProgressDialog progressDialog;
    private Button bt_sendagain;
    private TextView textProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user_number);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        phone = params.getString("phone");
        user = (UserModel) params.getSerializable("user");

        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout_verify);
        textInputLayout1 = (TextInputLayout) findViewById(R.id.textInputLayout1_verify);
        bt_verify = (Button) findViewById(R.id.verify_number);
        code = (EditText) findViewById(R.id.code_confirm);
        bt_sendcode = (Button) findViewById(R.id.send_code);
        textView = (TextView) findViewById(R.id.textConfirm);
        textProgress = (TextView) findViewById(R.id.textProgress);
        number = (EditText) findViewById(R.id.number_confirm);
        bt_sendagain = (Button) findViewById(R.id.send_again);
        number.setText(phone);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Verificar Número");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        authCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                updateUI(STATE_VERIFY_SUCCESS, phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                mVerificationId = verificationId;

                updateUI(STATE_CODE_SENT);
            }
        };

        bt_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+55" + phone,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        VerifyUserNumberRegister.this,
                        authCallback);

                Toast.makeText(VerifyUserNumberRegister.this, "Aguarde, você receberá o código em alguns segundos...",
                        Toast.LENGTH_LONG).show();

                textInputLayout.setVisibility(View.GONE);
                bt_sendcode.setVisibility(View.GONE);
                textInputLayout1.setVisibility(View.VISIBLE);
                bt_verify.setVisibility(View.VISIBLE);
                bt_sendagain.setVisibility(View.VISIBLE);
                textView.setText("Inserir Código");
                textProgress.setVisibility(View.VISIBLE);
            }
        });

        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(VerifyUserNumberRegister.this);
                progressDialog.setMessage("Verificando Código...");
                progressDialog.show();

                String code_confirm = code.getText().toString();
                verifyPhoneNumberWithCode(mVerificationId, code_confirm);
            }
        });

        bt_sendagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+55" + phone,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        VerifyUserNumberRegister.this,
                        authCallback);

                Toast.makeText(VerifyUserNumberRegister.this, "Aguarde, estamos enviando um novo código...",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:

                break;
            case STATE_CODE_SENT:

                break;
            case STATE_VERIFY_FAILED:

                break;
            case STATE_VERIFY_SUCCESS:


                break;
            case STATE_SIGNIN_FAILED:

                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {

        } else {
            // Signed in

        }
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (!phone.equals(null)) {
                                user.setUser_phone(phone);
                                UserSessionHelper user_session = new UserSessionHelper(VerifyUserNumberRegister.this);
                                user_session.createUserLoginSession(user.getUser_name(), user.getUser_phone(),
                                        user.getUser_neighborhood(), Integer.toString(user.getUser_score()), user.getUser_id(),
                                        user.getUser_office());
                                if (!user.getUser_sex().equals(null)) {
                                    user_session.UpdateRegisterUser(user.getUser_sex(), user.getUser_scholarity(), user.getNumbers_residents(),
                                            user.getUser_income());
                                } else {
                                    user_session.UpdateRegisterUser("null", "null", "null", "null");
                                }

                                FirebaseUser user = task.getResult().getUser();
                                // [START_EXCLUDE]
                                updateUI(STATE_SIGNIN_SUCCESS, user);
                                Intent intent = new Intent(VerifyUserNumberRegister.this, MainActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }

                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                // [END_EXCLUDE]
                            }
                            progressDialog.dismiss();
                            Toast.makeText(VerifyUserNumberRegister.this, "Código errado, insira novamente ou solicite " +
                                    "novo código!", Toast.LENGTH_LONG);
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyUserNumberRegister.this, LoginActivity.class));
    }
}