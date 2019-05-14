package com.example.user.furnitureproject2.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.furnitureproject2.Helper.DownloadImageTask;
import com.example.user.furnitureproject2.Model.Slider;
import com.example.user.furnitureproject2.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<Slider> myDataset;
//    private final List<Fragment> mFragmentList = new ArrayList<>();
//    private final List<String> mFragmentTitleList = new ArrayList<>();
//
//    public ViewPagerAdapter(FragmentManager manager) {
//        super(manager);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return mFragmentList.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//    public void addFragment(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }

    private Context context;
    private LayoutInflater layoutInflater;

//    private Integer [] images = {R.drawable.bed_r};
    public ViewPagerAdapter(Context context,List<Slider> myDataset) {
        this.context = context;
        this.myDataset = myDataset;
    }

    @Override
    public int getCount() {
        if(myDataset != null){
            return myDataset.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setImageResource(myDataset[position]);
        Log.e("error","+++++ "+myDataset.get(position).getImgLink());
        new DownloadImageTask(imageView).execute(myDataset.get(position).getImgLink());

//        ViewPager vp = (ViewPager) container;
//        vp.addView(view, 0);
//        return view;
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
