package fi.jykamaki.mvp_demo;

import java.util.List;

/**
 * Created by YR on 12.11.2015.
 */
public class VenueListPresenter implements IVenueListPresenter {
    private MainActivity view;
    private Model model;

    public VenueListPresenter() {
    }

    public void setView(MainActivity view) {
        this.view = view;
        if (view == null) {
            // we are finishing
            if (model != null) {
                model.finish();
                model = null;
            }
        } else {
            // create model
            if (model == null) {
                model = new Model(view, this);
            }
        }
    }

    public void publishVenueList(List<VenueData> venueDataList) {
        if (view != null) {
            view.publishVenueList(venueDataList);
        }
    }

    @Override
    public void setSearchString(String s) {
        model.setSearchString(s);
    }
}
