package com.mejorandola.ejemplo18.fragments;

import com.mejorandola.ejemplo18.R;
import com.mejorandola.ejemplo18.data.CustomPageAdapter;
import com.mejorandola.ejemplo18.fragments.dialogs.SelectMapTypeDialog;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment implements ActionBar.TabListener, 
													  ViewPager.OnPageChangeListener,
													  SelectMapTypeDialog.DialogListener {
	private ViewPager view_pager;
	private CustomPageAdapter adapter;	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new CustomPageAdapter(getActivity().getSupportFragmentManager());

        view_pager.setAdapter(adapter);
        view_pager.setOnPageChangeListener(this);
        
	    ActionBar actionBar = getActivity().getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.removeAllTabs();
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.title_tab_list)).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.title_tab_grid)).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.title_tab_map)).setTabListener(this));
        
		SharedPreferences settings = getActivity().getPreferences(0);
		int index = settings.getInt("tabindex", 0);
		actionBar.setSelectedNavigationItem(index);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		SharedPreferences settings = getActivity().getPreferences(0);
		SharedPreferences.Editor editor = settings.edit();
		int index = getActivity().getActionBar().getSelectedNavigationIndex();
		index = index >= 0? index : 0;
		editor.putInt("tabindex", index);
		editor.commit();			
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
        view_pager = (ViewPager) view.findViewById(R.id.pager);
		return view;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int position) {
		getActivity().getActionBar().setSelectedNavigationItem(position);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		view_pager.setCurrentItem(tab.getPosition());
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onSelectMapTypeDialogSelected(int type) {
		PlacesMapFragment map_fragment = (PlacesMapFragment) adapter.getItem(CustomPageAdapter.PLACE_FRAGMENT_INDEX);		
		map_fragment.onSelectMapTypeDialogSelected(type);
	}

	@Override
	public int getCurrentMapType() {
		PlacesMapFragment map_fragment = (PlacesMapFragment) adapter.getItem(CustomPageAdapter.PLACE_FRAGMENT_INDEX);		
		return map_fragment.getCurrentMapType();
	}

}
