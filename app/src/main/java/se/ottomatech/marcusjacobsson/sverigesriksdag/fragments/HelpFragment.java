package se.ottomatech.marcusjacobsson.sverigesriksdag.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;

/**
 * Created by Marcus Jacobsson on 2015-03-24.
 */
public class HelpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);



        return view;
    }
}
