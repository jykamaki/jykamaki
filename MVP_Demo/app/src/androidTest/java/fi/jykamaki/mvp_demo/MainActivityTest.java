package fi.jykamaki.mvp_demo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YR on 15.11.2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mainActivity;
    ListView viewVenueList;
    TextView viewHeader;
    EditText editText;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        viewHeader = (TextView) mainActivity.findViewById(R.id.textView);
        viewVenueList = (ListView) mainActivity.findViewById(R.id.listView);
        editText = (EditText) mainActivity.findViewById(R.id.editText);
    }

    @UiThreadTest
    public void testPublishVenueList() throws Exception {
        ListAdapter adapter =  viewVenueList.getAdapter();
        assertEquals(1, adapter.getCount());
        List<VenueData> venueDatas = new ArrayList<>();
        VenueData data = new VenueData();
        data.setVenueAddress("Oikotie");
        data.setVenueName("Koti");
        data.setVenueDistance("100");
        venueDatas.add(data);
        mainActivity.publishVenueList(venueDatas);
        assertEquals(2, adapter.getCount());
    }

    public void testHeaderDisplayed() throws Exception {
        View mainActivityDecorView = mainActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(mainActivityDecorView, viewHeader);
    }

    public void testEditDisplayed() throws Exception {
        View mainActivityDecorView = mainActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(mainActivityDecorView, editText);
    }

    public void testListDisplayed() throws Exception {
        View mainActivityDecorView = mainActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(mainActivityDecorView, viewVenueList);
    }

    public void testVenueListRetrieved() throws Exception {
        ListAdapter adapter =  viewVenueList.getAdapter();
        assertEquals(1, adapter.getCount());
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText.setText("a");
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Thread.sleep(30000);
        Log.v("Test", "list count = " + adapter.getCount());
        assertTrue(adapter.getCount() > 1);
    }
}