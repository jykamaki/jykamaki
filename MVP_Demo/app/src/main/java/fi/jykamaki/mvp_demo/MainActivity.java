package fi.jykamaki.mvp_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {

    private VenueListPresenter venueListPresenter;
    private EditText editText;
    private VenueDataAdapter listAdapter;
    private List<VenueData> venueDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView;
        venueDatas = new ArrayList();
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);
        View header = (View)getLayoutInflater().inflate(R.layout.header_row, null);
        listView.addHeaderView(header);
        listAdapter = new VenueDataAdapter(this, R.layout.venue_row, venueDatas);
        listView.setAdapter(listAdapter);
        venueListPresenter = new VenueListPresenter();
        venueListPresenter.setView(this);
        addTextWatcher();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        venueListPresenter.setView(null);
        if (isFinishing()) {
            venueListPresenter = null;
        }
    }

    @Override
    public void publishVenueList(List<VenueData> venueDataList) {
        venueDatas = venueDataList;
        listAdapter.setData(venueDatas);
        listAdapter.notifyDataSetChanged();
    }


    private void addTextWatcher() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do here
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Nothing to do here
            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                if (venueListPresenter != null) {
                    venueListPresenter.setSearchString(enteredText);
                }
            }
        });

    }
}
