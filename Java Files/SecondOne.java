package com.app.glogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class SecondOne extends AppCompatActivity {

    TextView detailName;
    TextView detailsEmail;
    TextView detailsPersonid;
    ImageView profPic;

    Button logoutBtn;
    //Firebase Auth mAuth;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_one);

        logoutBtn = (Button) findViewById(R.id.btnLogout);
        detailName = findViewById(R.id.tvName);
        detailsEmail = findViewById(R.id.tvEmail);
        detailsPersonid = findViewById(R.id.tvPersonid);
        profPic = findViewById(R.id.ivProfpic);

///////////////////////////////////////////////////////////////////////
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            detailName.setText("Name: "+personName  );
            detailsEmail.setText("Email: "+personEmail);
            detailsPersonid.setText("PersonId: "+personId);

            Picasso.get()
                    .load(personPhoto)
                    .fit()
                    .placeholder(R.drawable.profpicholder)
                    .into(profPic);


        }





////////////////////////////////////////////////////



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth.getInstance().signOut();
                ////////////////////////////////////////////////////////
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                        });

                ////////////////////////////////////////////////////////
/*
                finish();
                startActivity(new Intent(SecondOne.this, MainActivity.class));*/


            }
        });

    }


    public Context getActivity() {
        return SecondOne.this;
    }
}
