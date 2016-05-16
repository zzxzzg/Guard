package me.relex.circleindicator.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imbryk.viewPager.LoopViewPager;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.sample.R;
import me.relex.circleindicator.sample.SamplePagerAdapter;

public class DynamicAdapterFragment extends Fragment implements View.OnClickListener {

    private SamplePagerAdapter mAdapter;
    private LoopViewPager viewpager;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_dynamic_adapter, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.add).setOnClickListener(this);
        view.findViewById(R.id.remove).setOnClickListener(this);

        mAdapter = new SamplePagerAdapter(1) {
            @Override public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };

        viewpager = (LoopViewPager) view.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewpager.setAdapter(mAdapter);
        indicator.setViewPager(viewpager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mAdapter.addItem();
                viewpager.getRealAdapter().notifyDataSetChanged();
                break;
            case R.id.remove:
                mAdapter.removeItem();
                viewpager.getRealAdapter().notifyDataSetChanged();
                break;
        }
    }
}
