package com.Ms.todoreminder.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import com.Ms.todoreminder.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author SPEGAGNE Mathieu on 13/03/15.
 * @author https://github.com/mspegagne
 */
public class ToDo implements Parcelable {

    private String title;
    private String text;
    private Calendar date;
    private Boolean history;
    private Boolean notif;

    public ToDo(String title, String text, Calendar date, Boolean history, Boolean notif) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.history = history;
        this.notif = notif;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDate() {
        return date;
    }

    public Boolean getNotif() {
        return notif;
    }

    public Boolean getHistory() {
        return history;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        int day = date.get(Calendar.DAY_OF_MONTH);

        dest.writeString(title);
        dest.writeString(text);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }

    public static final Parcelable.Creator<ToDo> CREATOR = new Parcelable.Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel source) {
            return new ToDo(source);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    public ToDo(Parcel in) {
        int year, month, day;

        this.title = in.readString();
        this.text = in.readString();

        year = in.readInt();
        month = in.readInt();
        day = in.readInt();

        this.date = Calendar.getInstance();
        this.date.set(year, month, day);
    }

    public static ArrayList<Card> getCardList(final Context context, final Activity activity, Boolean history) {

        final SQLController dbcon;
        dbcon = new SQLController(context);
        dbcon.open();

        final Cursor cursor = dbcon.fetch();
        ArrayList<Card> cardsToDo = new ArrayList<Card>();
        ArrayList<Card> cardsHistory = new ArrayList<Card>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            final int id = cursor.getInt(0);
            int year = cursor.getInt(3);
            int month = cursor.getInt(4);
            int day = cursor.getInt(5);
            int historyCardInt = cursor.getInt(6);
            Boolean historyCard = false;
            if (historyCardInt == 1)
                historyCard = true;

            // Create a Card
            Card card = new Card(context);

            // Create a CardHeader
            CardHeader header = new CardHeader(context);
            // Add Header to card
            header.setTitle(cursor.getString(1) + "  -  " + month + "/" + day + "/" + year);
            card.setTitle(cursor.getString(2) + "History :" + cursor.getInt(6) + "Notify :" + cursor.getInt(7));

            card.addCardHeader(header);

            //Set onClick listener
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, final View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Manage ToDo")
                            .setMessage("What do you want to do?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dbcon.delete(id);

                                    CardListView listViewTodo = (CardListView) activity.findViewById(R.id.myListToDo);

                                    ArrayList<Card> cardListT = ToDo.getCardList(context, activity, false);

                                    CardArrayAdapter mCardToDoArrayAdapter = new CardArrayAdapter(context, cardListT);
                                    if (listViewTodo!=null){
                                        listViewTodo.setAdapter(mCardToDoArrayAdapter);
                                    }

                                    CardListView listViewHistory = (CardListView) activity.findViewById(R.id.myListHistory);

                                    ArrayList<Card> cardListH = ToDo.getCardList(context, activity, true);

                                    CardArrayAdapter mCardHistoryArrayAdapter = new CardArrayAdapter(context, cardListH);
                                    if (listViewHistory!=null){
                                        listViewHistory.setAdapter(mCardHistoryArrayAdapter);
                                    }
                                }
                            })
                            .setNegativeButton("Archive", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dbcon.archive(id);
                                    CardListView listViewTodo = (CardListView) activity.findViewById(R.id.myListToDo);

                                    ArrayList<Card> cardListT = ToDo.getCardList(context, activity, false);

                                    CardArrayAdapter mCardToDoArrayAdapter = new CardArrayAdapter(context, cardListT);
                                    if (listViewTodo!=null){
                                        listViewTodo.setAdapter(mCardToDoArrayAdapter);
                                    }

                                    CardListView listViewHistory = (CardListView) activity.findViewById(R.id.myListHistory);

                                    ArrayList<Card> cardListH = ToDo.getCardList(context, activity, true);

                                    CardArrayAdapter mCardHistoryArrayAdapter = new CardArrayAdapter(context, cardListH);
                                    if (listViewHistory!=null){
                                        listViewHistory.setAdapter(mCardHistoryArrayAdapter);
                                    }
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            /*card.setSwipeable(true);

            //You can set a SwipeListener.
            card.setOnSwipeListener(new Card.OnSwipeListener() {
                @Override
                public void onSwipe(Card card) {
                    Toast.makeText(context, "Swipable card", Toast.LENGTH_LONG).show();
                }
            });*/

            if (historyCard)
                cardsHistory.add(card);
            else
                cardsToDo.add(card);

        }

        cursor.close();

        if (history)
            return cardsHistory;
        else
            return cardsToDo;

    }
}
