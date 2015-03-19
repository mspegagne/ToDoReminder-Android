package com.Ms.todoreminder.Controller;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.Ms.todoreminder.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import com.Ms.todoreminder.Model.ToDo;

import java.util.ArrayList;


public class TodoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View ToDoView =  inflater.inflate(R.layout.fragment_todo, container, false);


        CardListView listViewTodo = (CardListView) ToDoView.findViewById(R.id.myListToDo);

        ArrayList<Card> cardList = ToDo.getCardList(getActivity(), getActivity(), false);

        CardArrayAdapter mCardToDoArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
        if (listViewTodo!=null){
            listViewTodo.setAdapter(mCardToDoArrayAdapter);
        }

        return ToDoView;
    }
}