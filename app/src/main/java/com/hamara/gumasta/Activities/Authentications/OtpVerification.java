package com.hamara.gumasta.Activities.Authentications;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.Util;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {


    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    //firebase auth object
    private FirebaseAuth mAuth;
    AlertDialog dialog;
    EditText firstDigit;
    EditText secondDigit;
    EditText thirdDigit;
    EditText fourthDigit;
    EditText fivthDigit;
    EditText sixthDigit;


    TextView textResendCode;

    TextView txtphonenum;
    String Phone;
    boolean isActivityRunning=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        Phone=getIntent().getExtras().getString("Phone","");


        firstDigit=findViewById(R.id.firstDigit);
        secondDigit=findViewById(R.id.secondDigit);
        thirdDigit=findViewById(R.id.thirdDigit);
        fourthDigit=findViewById(R.id.fourthDigit);
        fivthDigit=findViewById(R.id.fivthDigit);
        sixthDigit=findViewById(R.id.sixthDigit);

        txtphonenum=findViewById(R.id.txtphonenum);

        txtphonenum.setText(Phone);

        setTextWatcher();



        mAuth = FirebaseAuth.getInstance();

        textResendCode=findViewById(R.id.textResendCode);

        dialog= ProgressDialogManager.getProgressDialog(this);




        dialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


        textResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(Phone,mResendToken);
                dialog.setTitle("Re-Sending..");
                dialog.show();

            }
        });


    }

    private void setTextWatcher()
    {



        firstDigit.addTextChangedListener(new GenericTextWatcher(firstDigit));
        secondDigit.addTextChangedListener(new GenericTextWatcher(secondDigit));
        thirdDigit.addTextChangedListener(new GenericTextWatcher(thirdDigit));
        fourthDigit.addTextChangedListener(new GenericTextWatcher(fourthDigit));
        fivthDigit.addTextChangedListener(new GenericTextWatcher(fivthDigit));
        sixthDigit.addTextChangedListener(new GenericTextWatcher(sixthDigit));


    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            if( dialog.isShowing())
                dialog.dismiss();

            String code = phoneAuthCredential.getSmsCode();


            if (code != null)
            {



                firstDigit.setText(String.valueOf(code.charAt(0)));
                secondDigit.setText(String.valueOf(code.charAt(1)));
                thirdDigit.setText(String.valueOf(code.charAt(2)));
                fourthDigit.setText(String.valueOf(code.charAt(3)));
                fivthDigit.setText(String.valueOf(code.charAt(4)));
                sixthDigit.setText(String.valueOf(code.charAt(5)));
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            dialog.dismiss();
            if(e.getMessage().contains("The sms code has expired"))
            {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
            }
            else
            {
                Toast.makeText(OtpVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
            }
            finish();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            dialog.dismiss();

            Toast.makeText(OtpVerification.this,"Code Sent to : "+Phone, Toast.LENGTH_LONG).show();
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)// OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyVerificationCode(String otp) {

        if(!isActivityRunning)
            return;

        dialog.setTitle("Verifying..");
        dialog.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpVerification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if (task.isSuccessful())
                        {

                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);

                        }
                        else
                        {

                            System.out.println("ERROR : _ "+task.getException().getMessage());

                            if(task.getException().getMessage().contains("the sms code has expired"))
                            {
                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_OK, returnIntent);
                            }
                            else
                            {
                                Util.showSnackBar(OtpVerification.this,task.getException().getMessage());
                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_CANCELED, returnIntent);
                            }


                        }
                        finish();
                    }
                });
    }

    public void onClickVerify(View view)
    {

        String code="";
        for(EditText editText:new EditText[]{firstDigit,secondDigit,thirdDigit,fourthDigit,fivthDigit,sixthDigit})
        {

            if(TextUtils.isEmpty(editText.getText().toString()))
            {
                editText.setError("Fill");
                editText.requestFocus();
                return;
            }
            code=code+editText.getText().toString();

        }

        verifyVerificationCode(code);

    }

    public void onClickBack(View view) {
        onBackPressed();
    }


    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.firstDigit:
                    if(text.length()==1)
                        secondDigit.requestFocus();
                    break;
                case R.id.secondDigit:
                    if(text.length()==1)
                        thirdDigit.requestFocus();
                    else if(text.length()==0)
                        firstDigit.requestFocus();
                    break;
                case R.id.thirdDigit:
                    if(text.length()==1)
                        fourthDigit.requestFocus();
                    else if(text.length()==0)
                        secondDigit.requestFocus();
                    break;
                case R.id.fourthDigit:
                    if(text.length()==1)
                        fivthDigit.requestFocus();
                    else if(text.length()==0)
                        thirdDigit.requestFocus();
                    break;
                case R.id.fivthDigit:
                    if(text.length()==1)
                        sixthDigit.requestFocus();
                    else if(text.length()==0)
                        fourthDigit.requestFocus();
                    break;
                case R.id.sixthDigit:
                    if(text.length()==0)
                        fivthDigit.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityRunning=false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}