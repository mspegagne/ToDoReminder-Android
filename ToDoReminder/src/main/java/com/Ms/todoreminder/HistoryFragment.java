package com.Ms.todoreminder;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import com.Ms.todoreminder.Model.ToDo;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View HistoryView =  inflater.inflate(R.layout.fragment_history, container, false);


        CardListView listViewHistory = (CardListView) HistoryView.findViewById(R.id.myListHistory);

        ArrayList<Card> cardList = ToDo.getCardList(getActivity(), getActivity(), true);

        CardArrayAdapter mCardHistoryArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
        if (listViewHistory!=null) {
            listViewHistory.setAdapter(mCardHistoryArrayAdapter);
        }

        return HistoryView;
    }
}