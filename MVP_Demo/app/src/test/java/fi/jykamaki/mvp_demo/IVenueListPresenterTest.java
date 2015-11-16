package fi.jykamaki.mvp_demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by YR on 14.11.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class IVenueListPresenterTest {

    @Mock
    private IMainView view;

    @Mock
    private IModel model;

    private IVenueListPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new VenueListPresenter();
        presenter.setModel(model);
        presenter.setView(view);
    }

    @After
    public void tearDown() {
        presenter.setView(null);
        presenter.setModel(null);
    }

    @org.junit.Test
    public void testPresenter() {
        presenter.setSearchString("jas");
        Mockito.verify(model, Mockito.times(1)).setSearchString("jas");
    }

    @org.junit.Test
    public void testPresenter2() {
        List<VenueData> venueDatas = new ArrayList<>();
        VenueData data = new VenueData();
        data.setVenueAddress("Oikotie");
        data.setVenueName("Koti");
        data.setVenueDistance("100");
        venueDatas.add(data);
        presenter.publishVenueList(venueDatas);
        Mockito.verify(view, Mockito.times(1)).publishVenueList(new ArrayList<VenueData>(Mockito.anyListOf(VenueData.class)));
    }

    @org.junit.Test
    public void testPresenter3() {
        presenter.setModel(null);
        presenter.setSearchString("jas");
        Mockito.verify(model, Mockito.times(0)).setSearchString("jas");
    }

    @org.junit.Test
    public void testPresenter4() {
        List<VenueData> venueDatas = new ArrayList<>();
        VenueData data = new VenueData();
        data.setVenueAddress("Oikotie");
        data.setVenueName("Koti");
        data.setVenueDistance("100");
        venueDatas.add(data);
        presenter.setView(null);
        presenter.publishVenueList(venueDatas);
        Mockito.verify(view, Mockito.times(0)).publishVenueList(new ArrayList<VenueData>(Mockito.anyListOf(VenueData.class)));
    }
}
