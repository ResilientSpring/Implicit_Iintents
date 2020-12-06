package com.example.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // add a private variable at the top of the class to hold the EditText object for the web site URI.
    private EditText mWebsiteEditText;

    private EditText mLocationEditText;

    private EditText mShareTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In the onCreate() method for MainActivity, use findViewById() to get a reference
        // to the EditText instance and assign it to that private variable:
        mWebsiteEditText = findViewById(R.id.website_edittext);

        mLocationEditText = findViewById(R.id.location_edittext);

        mShareTextEditText = findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {

        // Add a statement to the new openWebsite() method that gets the string value of the EditText
        String url = mWebsiteEditText.getText().toString();

        // Encode and parse that string into a Uri object
        Uri webpage = Uri.parse(url);

        // Create a new Intent with Intent.ACTION_VIEW as the action and the URI as the data
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        /**This Intent constructor is different from the one you used to create an explicit Intent.
         * In the previous constructor, you specified the current context
         * and a specific component (Activity class) to send the Intent.
         *
         * In this constructor you specify an action and the data for that action.
         * Actions are defined by the Intent class and can include
         * ACTION_VIEW (to view the given data), ACTION_EDIT (to edit the given data),
         * or ACTION_DIAL (to dial a phone number).
         *
         * In this case the action is ACTION_VIEW because you want
         * to display the web page specified by the URI in the webpage variable.
         *
         * */

        // Use the resolveActivity() method and the Android package manager to find an Activity that
        // can handle your implicit Intent.
        if (intent.resolveActivity(getPackageManager()) != null) {

            // Inside the if statement, call startActivity() to send the Intent.
            startActivity(intent);
        }
        /**
         * This request that matches your Intent action and data with the Intent filters
         * for installed apps on the device.
         * You use it to make sure there is at least one Activity that can handle your requests.
         * */

        // Add an else block to print a Log message if the Intent could not be resolved.
        else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }


    }

    public void openLocation(View view) {

        String loc = mLocationEditText.getText().toString();

        /**
         * you implement the on-click handler method for the second button in the UI, Open Location.
         * This method is almost identical to the openWebsite() method.
         * The difference is the use of a geo URI to indicate a map location.
         * You can use a geo URI with latitude and longitude, or use a query string for a general location.
         * In this example we've used the latter.
         * */

        // Parse that string into a Uri object with a geo search query:
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        // Create a new Intent with Intent.ACTION_VIEW as the action and loc as the data.
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Resolve the Intent and check to make sure that the Intent resolved successfully.
        // If so, startActivity(), otherwise log an error message.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    /**
     * A share action is an easy way for users to share items in your app with social networks
     * and other apps.
     *
     * Although you could build a share action in your own app using an implicit Intent,
     * Android provides the ShareCompat.IntentBuilder helper class to make implementing sharing easy.
     * You can use ShareCompat.IntentBuilder to build an Intent
     * and launch a chooser to let the user choose the destination app for sharing.
     * */
    public void shareText(View view) {

        String txt = mShareTextEditText.getText().toString();

        // Define the mime type of the text to share
        String mimeType = "text/plain";

        // Call ShareCompat.IntentBuilder with these methods
        ShareCompat.IntentBuilder
                .from(this)        // The Activity that launches this share Intent (this).
                .setType(mimeType) // The MIME type of the item to be shared.
                .setChooserTitle("Share this text with: ")//The title that appears on the system app chooser.
                .setText(txt)     // The actual text to be shared
                .startChooser();  // Show the system app chooser and send the Intent.
    }
}