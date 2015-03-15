package com.Ms.todoreminder;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.Ms.todoreminder.Model.DBhelper;
import com.Ms.todoreminder.Model.SQLController;
import it.gmariotti.cardslib.library.internal.*;
import it.gmariotti.cardslib.library.view.CardListView;

import com.Ms.todoreminder.Model.ToDo;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View ToDoView =  inflater.inflate(R.layout.fragment_todo, container, false);


        CardListView listView = (CardListView) ToDoView.findViewById(R.id.myList);
        ToDo.setToDoList(this.getActivity(), listView);

        return ToDoView;
    }
}