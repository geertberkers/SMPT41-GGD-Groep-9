package smpt.proftaak.ggd;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

/**
 * Created by BartKneepkens on 25/06/15.
 */
public class settingsActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener
{
    boolean postcodeHidden = true;
    boolean isSignedIn;

    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.oceaanblauw)));

        Switch gpsSwitch = (Switch) this.findViewById(R.id.gpsSwitch);
        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    postcodeHidden = true;
                } else {
                    postcodeHidden = false;
                }
                locatieInputChanged();
            }
        });

        locatieInputChanged();


        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }

    private void locatieInputChanged()
    {
        TextView postcode = (TextView) this.findViewById(R.id.tvPostcode);
        View cv = this.findViewById(R.id.cvPostcode);
        EditText ed = (EditText) this.findViewById(R.id.etPostcode);
        TextView noGps = (TextView) this.findViewById(R.id.tvNoGps);

        if(postcodeHidden)
        {
            postcode.setVisibility(View.GONE);
            cv.setVisibility(View.GONE);
            ed.setVisibility(View.GONE);
            noGps.setVisibility(View.VISIBLE);
        }
        else
        {
            postcode.setVisibility(View.VISIBLE);
            cv.setVisibility(View.VISIBLE);
            ed.setVisibility(View.VISIBLE);
            noGps.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        System.out.println("onConnected:" + bundle);
        mShouldResolve = false;

        // Show the signed-in UI
        //showSignedInUI();
        Toast.makeText(getApplicationContext(), "signed in!!!! 1337", Toast.LENGTH_SHORT).show();

        isSignedIn = true;

        //set de button naar 'uitloggen'
        SignInButton signInButton = (SignInButton) this.findViewById(R.id.sign_in_button);
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText("uitloggen");
                return;
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        System.out.println("onConnectionFailed:" + connectionResult.toString());

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    System.out.println("Could not resolve ConnectionResult." +  e.toString());
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            }
            else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                //showErrorDialog(connectionResult);
                Toast.makeText(getApplicationContext(), connectionResult.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show the signed-out UI
            //showSignedOutUI();
            Toast.makeText(getApplicationContext(), "uitgelogd", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onClick(View v) {

        if (!isSignedIn) {
            onSignInClicked();
        }

        if(isSignedIn)
        {
            onSignOutClicked();
        }


    }

    public void onSignInClicked(){
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
        //mStatusTextView.setText(R.string.signing_in);
        //Toast.makeText(getApplicationContext(), "signing in", Toast.LENGTH_SHORT).show();
    }

    public void onSignOutClicked()
    {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }


        //Toast.makeText(getApplicationContext(), "signed out ", Toast.LENGTH_SHORT).show();
        isSignedIn = false;


        //set de button naar 'uitloggen'
        SignInButton signInButton = (SignInButton) this.findViewById(R.id.sign_in_button);
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText("inloggen");
                return;
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }
}
