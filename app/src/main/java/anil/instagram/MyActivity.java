package anil.instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import anil.instagram.instagram.Instagram;
import anil.instagram.instagram.InstagramSession;
import anil.instagram.instagram.InstagramUser;


public class MyActivity extends Activity {

    private InstagramSession instagramSession;
    private Instagram instagram;

    private static final String CLIENT_ID = "f96e2c9c0a294943a87faccf6086b330";
    private static final String CLIENT_SECRET = "ec1434ab6e9a46649064cd4ddf610496";
    private static final String REDIRECT = "http://syllogismos.github.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        TextView textView = (TextView) findViewById(R.id.textView);
        instagram = new Instagram(this, CLIENT_ID, CLIENT_SECRET, REDIRECT);
        instagramSession = instagram.getSession();

        if (instagramSession.isActive()){
            textView.setText(instagramSession.getUser().username);
        } else {
            Instagram.InstagramAuthListener instagramListener = new Instagram.InstagramAuthListener() {
                @Override
                public void onSuccess(InstagramUser instagramUser) {
                    finish();
                    startActivity(new Intent(MyActivity.this, MyActivity.class));
                }

                @Override
                public void onError(String s) {
                    showToast(s);
                }

                @Override
                public void onCancel() {
                    showToast("OK maybe laters");
                }
            };
            instagram.authorize(instagramListener);
        }
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
