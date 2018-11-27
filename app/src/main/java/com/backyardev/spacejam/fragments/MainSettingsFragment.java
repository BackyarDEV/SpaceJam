package com.backyardev.spacejam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backyardev.spacejam.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainSettingsFragment extends Fragment {

    private ArrayList<String> listItems = new ArrayList<>();
    ListView settingsListView;
    private static String TAG_PROFILE_SETTING = "profile_setting";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_settings,container,false);
        settingsListView = view.findViewById( R.id.settingsListView );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        listItems.add( 0,"Profile Settings" );
        listItems.add( 1,"Account Settings" );
        listItems.add( 2,"Notification Settings" );
        listItems.add( 3,"Application Settings" );
        listItems.add( 4,"Privacy Settings" );
        settingsListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view1, int position, long l ) {
                getFragmentById( position );
            }
        } );
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems);
        settingsListView.setAdapter( arrayAdapter );

    }

    private void getFragmentById(int id){

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById( R.id.settings_container );
        switch (listItems.get( id )){
            case "Profile Settings":
                ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( profileSettingFragment, TAG_PROFILE_SETTING );
                }
                else getFragment( null,TAG_PROFILE_SETTING );
                break;
            case "Account Settings":
                ProfileSettingFragment fragment = new ProfileSettingFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( fragment, TAG_PROFILE_SETTING );
                }
                else getFragment( null,TAG_PROFILE_SETTING );
                break;
        }
    }

    private void getFragment(Fragment fragment, String TAG) {
        if(fragment != null) {
            FragmentTransaction fragmentTransaction = Objects.requireNonNull( getActivity() ).
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
            fragmentTransaction.replace( R.id.settings_container, fragment, TAG );
            fragmentTransaction.addToBackStack( TAG_PROFILE_SETTING );
            fragmentTransaction.commit();

            listItems.clear();
        }
    }
}