package com.wenzin.helena.spotifysearch;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ListView;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DisplayTracksActivityTest extends ActivityUnitTestCase<DisplayTracksActivity> {

    public DisplayTracksActivityTest() {
        super(DisplayTracksActivity.class);
    }

    @Mock
    Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation()
                .getTargetContext().getCacheDir().toString());
        MockitoAnnotations.initMocks(this);

        //Intent intent = new Intent(getInstrumentation().getTargetContext(), DisplayTracksActivity.class);
        Intent intent = MainActivity.getDisplayTracksIntent(getInstrumentation().getTargetContext(), "love", "some-access-token");

        startActivity(intent, null, null);
    }

    public void testView() throws Exception {
        DisplayTracksActivity displayTracksActivity = getActivity();
        ListView targetListView = (ListView) displayTracksActivity.findViewById(R.id.list);
        assertNotNull(targetListView);
        assertEquals(100, targetListView.getCount());
    }
}
