package fi.jykamaki.mvp_demo;

import java.util.List;

/**
 * Created by YR on 12.11.2015.
 */
public class VenueListPresenter implements IVenueListPresenter {
    private IMainView view;
    private IModel model;

    public VenueListPresenter() {
    }

    @Override
    public void setView(IMainView view) {
        this.view = view;
    }

    @Override
    public void publishVenueList(List<VenueData> venueDataList) {
        if (view != null) {
            view.publishVenueList(venueDataList);
        }
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void setSearchString(String s) {
        if (model != null) {
            model.setSearchString(s);
        }
    }
}
