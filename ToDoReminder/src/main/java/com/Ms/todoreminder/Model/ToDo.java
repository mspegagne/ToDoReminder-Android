package com.Ms.todoreminder.Model;

import android.content.Context;
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

    public ToDo(String title, String text, Calendar date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getDate() { return date; }


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

    public static final Parcelable.Creator<ToDo> CREATOR = new Parcelable.Creator<ToDo>()
    {
        @Override
        public ToDo createFromParcel(Parcel source)
        {
            return new ToDo(source);
        }

        @Override
        public ToDo[] newArray(int size)
        {
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

    public static void setToDoList(Context context, CardListView listView){

        SQLController dbcon;
        dbcon = new SQLController(context);
        dbcon.open();

        // Attach The Data From DataBase Into ListView Using Crusor Adapter
        Cursor cursor = dbcon.fetch();

        ArrayList<Card> cards = new ArrayList<Card>();

        // looping through all rows and adding to list
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // Create a Card
            Card card = new Card(context);
            // Create a CardHeader
            CardHeader header = new CardHeader(context);
            // Add Header to card
            header.setTitle(cursor.getString(1));
            card.setTitle(cursor.getString(2));
            card.addCardHeader(header);

            cards.add(card);
        }

        cursor.close();

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(context, cards);

        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }
}
