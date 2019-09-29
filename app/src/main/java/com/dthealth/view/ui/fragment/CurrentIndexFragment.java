package com.dthealth.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dthealth.R;
import com.dthealth.background.BackgroundService;
import com.dthealth.model.PhysicalIndex;
import com.dthealth.viewmodel.CurrentStatusViewModel;


public class CurrentIndexFragment extends Fragment {

    private TextView hb;
    private TextView hbs;
    private TextView bp;
    private TextView bf;
    private TextView bg;
    private TextView tp;

    public static CurrentIndexFragment newInstance() {
        return new CurrentIndexFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current_index_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hb = getActivity().findViewById(R.id.hb);
        hbs = getActivity().findViewById(R.id.hbs);
        bp = getActivity().findViewById(R.id.bp);
        bf = getActivity().findViewById(R.id.bf);
        bg = getActivity().findViewById(R.id.bg);
        tp = getActivity().findViewById(R.id.tp);
        BackgroundService.getPhysicalIndexMutableLiveData().observeForever(new Observer<PhysicalIndex>() {
            @Override
            public void onChanged(PhysicalIndex physicalIndex) {
                hb.setText(String.valueOf(physicalIndex.getHeartbeat()));
                hbs.setText(String.valueOf(physicalIndex.getHeartbeatStrength()));
                bp.setText(String.valueOf(physicalIndex.getBloodPressure()));
                bf.setText(String.valueOf(physicalIndex.getBloodFat()));
                bg.setText(String.valueOf(physicalIndex.getBloodGlucose()));
                tp.setText(String.valueOf(physicalIndex.getTemperature()));
            }
        });
    }
}
