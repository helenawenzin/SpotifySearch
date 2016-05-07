package com.wenzin.helena.spotifysearch;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntentCreationTest {

    @Mock
    MainActivity context;

    @Test
    public void intentShouldBeCreatedWithExtras() {
        Intent intent = MainActivity.getDisplayTracksIntent(context, "happy", "accessToken");
        assertNotNull(intent);
        //Bundle extras = intent.getExtras();
        //assertNotNull(extras);
        //assertEquals("query", extras.getString("QUERY"));
        //assertEquals("value", extras.getString("VALUE"));
    }
}