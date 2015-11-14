package fi.jykamaki.mvp_demo;

/**
 * Created by YR on 12.11.2015.
 */
public class VenueData {
    private String venueName;
    private String venueAddress;
    private String venueDistance;

    public String getVenueAddress() {
        return venueAddress;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueDistance() {
        return venueDistance;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public void setVenueDistance(String venueDistance) {
        this.venueDistance = venueDistance;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
