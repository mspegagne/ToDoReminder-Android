package com.Ms.ToDoReminder;

/**
 * @author SPEGAGNE Mathieu on 08/03/15.
 * @author https://github.com/mspegagne
 */

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

public class AddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        return rootView;
    }
}
