package com.example.mobioticstest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    Button Login;

    Context context;

    SignInButton googlelogin;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context=this;
        // login=findViewById(R.id.login);
        googlelogin = (SignInButton) findViewById(R.id.googlelogin);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });


    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull com.google.android.gms.common.ConnectionResult connectionResult)
    {

    }
    @Override
    protected void onStart() {
        super.onStart();
        // make sure to initiate connection
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // disconnect api if it is connected
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin with google
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            //Calling a new function to handle signin
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess())
        {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            ApiInterface retrofitApi=ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserData>> call=retrofitApi.getAllData();
            call.enqueue(new Callback<List<UserData>>() {
                @Override
                public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response)
                {
                    Toast.makeText(getApplicationContext(),"You have been Login Successfully !!",Toast.LENGTH_LONG).show();


                    Global.firebaseDataArrayList= (ArrayList<UserData>) response.body();

                    Intent in=new Intent(LoginActivity.this,VideoListActivity.class);
                    startActivity(in);
                    finish();


                }

                @Override
                public void onFailure(Call<List<UserData>> call, Throwable t)
                {
                    Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            });






        } else
        {
            //If login fails

            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


}
