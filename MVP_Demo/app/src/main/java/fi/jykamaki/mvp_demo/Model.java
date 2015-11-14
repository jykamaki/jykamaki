package fi.jykamaki.mvp_demo;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by YR on 12.11.2015.
 */
public class Model implements IModel, LocationListener {
    private static final int LOCATION_UPDATE_INTERVAL = 2000; // 2 seconds
    private static final int LOCATION_UPDATE_DISTANCE = 100;  // 100 meters

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";

    private static final String QUERY_URL = "https://api.foursquare.com/v2/venues/search";
    private static final String QUERY_CLIENT_ID = "?client_id=";
    private static final String QUERY_CLIENT_SECRET = "&client_secret=";
    private static final String QUERY_PHANTOM = "&v=20130815";
    private static final String QUERY_LL = "&ll=";
    private static final String QUERY = "&query=";

    private HttpURLConnection conn;
    private VenueListPresenter venueListPresenter;
    private LocationManager locationManager;
    private String latitude = "0";
    private String longitude = "0";
    private String searchString;

    public Model(Context context, VenueListPresenter presenter) {
        venueListPresenter = presenter;
        // Get the location manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use default
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, LOCATION_UPDATE_INTERVAL,
                LOCATION_UPDATE_DISTANCE, this);
    }

    @Override
    public void finish() {
        // stop ongoing activities
        venueListPresenter = null;
        locationManager.removeUpdates(this);
        locationManager = null;
    }

    @Override
    public void setSearchString(String s) {
        searchString = s;
        startQuery();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
        longitude = Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
        startQuery();
    }

    private String createRequestURL() {
        return QUERY_URL + QUERY_CLIENT_ID + CLIENT_ID + QUERY_CLIENT_SECRET + CLIENT_SECRET +
                QUERY_PHANTOM + QUERY_LL + latitude + "," + longitude + QUERY + searchString;
    }

    private void startQuery() {
        if (searchString != null && !searchString.isEmpty()) {
            new getVenueListTask().execute(createRequestURL());
        }
    }

    // Async task to access network
    private class getVenueListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // make Call to the url
            try {
                return loadFromNetwork(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // got data from network
                // parse venues search result
                ArrayList venuesList = (ArrayList) parseFoursquare(result);
                if (venueListPresenter != null) {
                    venueListPresenter.publishVenueList(venuesList);
                }
            }
        }
    }

    // Get the data from network
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            stream = downloadUrl(urlString);
            str = readStream(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
            conn.disconnect();
            conn = null;
        }
        return str;
    }

    // Query data from network
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    // Stream reader
    private String readStream(InputStream stream) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }

    // JSON parser
    private ArrayList parseFoursquare(final String response) {
        ArrayList foundVenues = new ArrayList();
        try {
            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);
            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject venue = jsonArray.getJSONObject(i);
                        String name = venue.getString("name");
                        JSONArray formattedAddress = venue.getJSONObject("location").getJSONArray("formattedAddress");
                        String address = "";
                        for (int j = 0; j < formattedAddress.length(); j++) {
                            address = address + formattedAddress.getString(j) + "\n";
                        }
                        Integer dist = venue.getJSONObject("location").getInt("distance");
                        String distance = dist.toString();
                        VenueData found = new VenueData();
                        found.setVenueAddress(address);
                        found.setVenueDistance(distance);
                        found.setVenueName(name);
                        foundVenues.add(found);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foundVenues;
    }
}
