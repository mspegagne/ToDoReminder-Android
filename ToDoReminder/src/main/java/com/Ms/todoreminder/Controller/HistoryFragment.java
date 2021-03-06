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
import com.Ms.todoreminder.Model.ToDo;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;

/**
 * History Fragment Controller
 */
public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View HistoryView = inflater.inflate(R.layout.fragment_history, container, false);

        //Display the list of history
        CardListView listViewHistory = (CardListView) HistoryView.findViewById(R.id.myListHistory);
        ArrayList<Card> cardList = ToDo.getCardList(getActivity(), getActivity(), true);
        CardArrayAdapter mCardHistoryArrayAdapter = new CardArrayAdapter(getActivity(), cardList);

        if (listViewHistory != null) {
            listViewHistory.setAdapter(mCardHistoryArrayAdapter);
        }

        return HistoryView;
    }
}