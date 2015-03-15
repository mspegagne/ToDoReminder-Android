package com.Ms.todoreminder;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.Ms.todoreminder.Model.DBhelper;
import com.Ms.todoreminder.Model.SQLController;

public class TodoFragment extends Fragment {

    private SQLController dbcon;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View ToDoView =  inflater.inflate(R.layout.fragment_todo, container, false);


        dbcon = new SQLController(this.getActivity());
        dbcon.open();
        listView = (ListView) ToDoView.findViewById(R.id.list_view);
        listView.setEmptyView(ToDoView.findViewById(R.id.empty));

        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.fetch();
        String[] from = new String[] {DBhelper.TODO_TITLE, DBhelper.TODO_TEXT };
        int[] to = new int[] { R.id.titleview, R.id.textview };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.todo, cursor, from, to);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);



        return ToDoView;
    }
    
}