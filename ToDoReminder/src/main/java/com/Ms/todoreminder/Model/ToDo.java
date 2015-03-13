package com.Ms.todoreminder.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author SPEGAGNE Mathieu on 13/03/15.
 * @author https://github.com/mspegagne
 */
public class ToDo implements Parcelable {

    private String title;
    private String text;

    private int year, month, day;

    public ToDo(String title, String text, int year, int month, int day) {
        this.title = title;
        this.text = text;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        this.title = in.readString();
        this.text = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
    }

}
