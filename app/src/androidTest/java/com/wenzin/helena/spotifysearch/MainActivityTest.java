package com.wenzin.helena.spotifysearch;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Mock
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation()
                .getTargetContext().getCacheDir().toString());
        MockitoAnnotations.initMocks(this);

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MainActivity.class);
        startActivity(intent, null, null);
    }

    public void testView() throws Exception {
        MainActivity activity = getActivity();

        TextView targetTextViewOnTop = (TextView) activity
                .findViewById(R.id.textViewOnTop);
        assertEquals("Find Spotify tracks:", targetTextViewOnTop.getText());

        Button targetButton2 = (Button) activity.findViewById(R.id.buttonGoFind);
        assertEquals("Go find tracks!", targetButton2.getText());

        EditText targetEditText = (EditText) activity.findViewById(R.id.textSearch);
        assertEquals("", targetEditText.getText().toString());

        ImageView targetImage = (ImageView) activity.findViewById(R.id.imageSpotifyIcon);
        assertNotNull(targetImage);

    }

}
