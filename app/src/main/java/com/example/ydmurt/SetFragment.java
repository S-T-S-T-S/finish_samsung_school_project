package com.example.ydmurt;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SetFragment {
    public static void setFragment(FragmentManager manager, Fragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.mainframe, fragment);

        ft.addToBackStack(null);

        ft.commit();
    }
}
