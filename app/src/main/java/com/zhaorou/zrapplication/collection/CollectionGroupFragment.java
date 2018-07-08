package com.zhaorou.zrapplication.collection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionGroupFragment extends Fragment {


    public CollectionGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_group, container, false);
    }

}
