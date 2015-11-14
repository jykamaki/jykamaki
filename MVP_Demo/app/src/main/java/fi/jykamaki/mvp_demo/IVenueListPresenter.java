package fi.jykamaki.mvp_demo;

import java.util.List;

/**
 * Created by YR on 12.11.2015.
 */
public interface IVenueListPresenter {
    void setSearchString(String s);
    void publishVenueList(List<VenueData> venueDataList);
}
