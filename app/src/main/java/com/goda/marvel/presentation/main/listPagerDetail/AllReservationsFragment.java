package com.goda.marvel.presentation.main.listPagerDetail;

/*
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;*/
/*
import com.rklinic.R;
import com.rklinic.databinding.FragmentAllReservationsBinding;
import com.rklinic.home.HomeActivity;
import com.rklinic.home.reservation.model.Upcoming;
import com.rklinic.home.reservation.view.adapter.ViewPagerAdapter;
import com.rklinic.home.reservation.viewmodel.UpcomingViewModel;
import com.rklinic.utils.PrefUtils;
import com.rklinic.utils.ZoomOutTransformation;*/

public class AllReservationsFragment /*extends Fragment*/ {
   /* private FragmentAllReservationsBinding binding;
    private HomeActivity activity;
    private UpcomingViewModel viewModel;
    private List<Upcoming> reservationsList = new ArrayList<>();

    public static final int LOADING = 0;
    public static final int NO_RESERVATIONS = 1;
    public static final int DONE = 2;

    public AllReservationsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity) {
            activity = (HomeActivity) context;
            viewModel = ViewModelProviders.of(activity).get(UpcomingViewModel.class);
        } else {
            throw new RuntimeException(AllReservationsFragment.class.getName() + " must be attached to " + HomeActivity.class.getName());
        }
    }

    private Observer<List<Upcoming>> upcomingReservationsObserver = upcomingList -> {
        if (upcomingList == null || upcomingList.isEmpty()) {
            viewModel.state.set(NO_RESERVATIONS);
        } else {
            reservationsList.clear();
            viewModel.state.set(DONE);
            reservationsList.addAll(upcomingList);
            setupViewPager();
        }
    };

    private void getUpcomingReservations() {
        viewModel.upcoming().observe(this, upcomingReservationsObserver);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_reservations, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (PrefUtils.getUser() != null) {
            getUpcomingReservations();
        } else {
            viewModel.state.set(NO_RESERVATIONS);
        }
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());


        for (Upcoming upcoming : reservationsList) {
            adapter.addFragment(ReservationFragment.newInstance(upcoming), "");
        }
        binding.viewpager.setAdapter(adapter);

        binding.viewpager.setClipChildren(false);
        binding.viewpager.setClipToPadding(false);
        int margin = 32;
        int padding = 32;
        binding.viewpager.setPageMargin(margin);
        binding.viewpager.setPadding(padding * 2, 0, padding * 2, 0);
        binding.viewpager.setPageTransformer(false, (page, position) -> {
            final float MIN_SCALE = 0.65f;
            final float MIN_ALPHA = 0.3f;
            if (binding.viewpager.getCurrentItem() == 0) {
                page.setTranslationX(-(padding));
            } else if (binding.viewpager.getCurrentItem() == adapter.getCount() - 1) {
                page.setTranslationX(padding);
            } else {
                page.setTranslationX(0);
            }
        });
    }*/
}
