package smpt.proftaak.ggd;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

/**
 * Created by BartKneepkens on 25/06/15.
 */

public class settingsActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, ResultCallback<People.LoadPeopleResult> {

    boolean postcodeHidden = true;
    boolean isSignedIn;

    static User thisUser;

    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

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

        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        final EditText etNumber = (EditText) findViewById(R.id.etNumber);
        etNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!etNumber.getText().equals("")) {
                        if (etNumber.getText().length() == 10) {
                            saveToSharedPref(etNumber.getText().toString(), null);
                        }
                    }
                }
                return false;
            }
        });

        final EditText etPostcode = (EditText) findViewById(R.id.etPostcode);
        etPostcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(!etPostcode.getText().equals("")){
                        if(etPostcode.getText().toString().trim().length() == 6) {
                            //additional checks for input
                            saveToSharedPref(null, etPostcode.getText().toString().trim());
                        }
                    }
                }
                return false;
            }
        });

        if(sharedPrefPresent()) {
            loadFromSharedPref();
            initGUI();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        System.out.println("onConnected:" + bundle);
        mShouldResolve = false;
        isSignedIn = true;

        //set de button naar 'uitloggen'
        this.changePlusButton();
        Toast.makeText(getApplicationContext(), getString(R.string.ingelogd), Toast.LENGTH_SHORT).show();

        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);

        SharedPreferences prefs = this.getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);

        String personEmail =Plus.AccountApi.getAccountName(mGoogleApiClient);
        Person p = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        SharedPreferences.Editor editor = prefs.edit();
        if (p != null) {
            String personName = p.getDisplayName();

            editor.putString(getString(R.string.sharedpref_name), personName);
        }
        editor.putString(getString(R.string.sharedpref_email), personEmail);
        editor.commit();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

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
            //Toast.makeText(getApplicationContext(), getString(R.string.uitgelogd), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {

        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    System.out.println("Display name: " + personBuffer.get(i).getDisplayName());
                }
            } finally {
                personBuffer.close();
            }
        } else {
            System.out.println("Error requesting visible circles: " + peopleData.getStatus());
        }
    }

    public void onSignInClicked(){
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
    }

    public void onSignOutClicked() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
        isSignedIn = false;

        //set de button naar 'inloggen'
        this.changePlusButton();


    }

    private void changePlusButton() {
        SignInButton signInButton = (SignInButton) this.findViewById(R.id.sign_in_button);
            // Find the TextView that is inside of the SignInButton and set its text
            for (int i = 0; i < signInButton.getChildCount(); i++) {
                View v = signInButton.getChildAt(i);

                if (v instanceof TextView) {
                    TextView tv = (TextView) v;

                    if(this.isSignedIn)
                        tv.setText(getString(R.string.uitloggen));
                    else
                        tv.setText(getString(R.string.inloggen));
                }
            }
    }

    private void locatieInputChanged() {
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

    private boolean sharedPrefPresent() {
        SharedPreferences prefs = this.getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);

        String number = prefs.getString(getString(R.string.sharedpref_number), "");
        if(number.equals(""))
            return false;
        else
            return true;
    }

    private void loadFromSharedPref() {
        SharedPreferences prefs = this.getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);

        String email = prefs.getString(getString(R.string.sharedpref_email), "");
        String name = prefs.getString(getString(R.string.sharedpref_name), "");
        String postcode = prefs.getString(getString(R.string.sharedpref_postcode), "");
        String number = prefs.getString(getString(R.string.sharedpref_number), "");

        thisUser = new User(name, email, postcode, number);

    }

    private void saveToSharedPref(String number, String postcode){
        SharedPreferences prefs = this.getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(number != null)
        editor.putString(getString(R.string.sharedpref_number), number).apply();

        if(postcode != null)
        editor.putString(getString(R.string.sharedpref_postcode), postcode).apply();

        editor.commit();
    }

    private void initGUI() {
        if(thisUser !=null){
            EditText etNumber = (EditText) this.findViewById(R.id.etNumber);
            etNumber.setText(thisUser.getNumber());

            EditText etPostcode = (EditText) this.findViewById(R.id.etPostcode);
            etPostcode.setText(thisUser.getPostcode());
        }

        locatieInputChanged();
    }
}
