package com.Ms.todoreminder.Controller;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */

import android.app.AlarmManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.Toast;
import com.Ms.todoreminder.DataBase.SQLController;
import com.Ms.todoreminder.Model.ToDo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.Ms.todoreminder.R;
import com.beardedhen.androidbootstrap.BootstrapButton;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.Calendar;

public class AddFragment extends Fragment implements OnClickListener {

    private EditText txtDate = null;
    private EditText editTitle = null;
    private EditText editText = null;
    private CheckBox addNotif = null;
    private BootstrapButton save = null;

    private SQLController dbController;
    private AlarmManager am;

    private int year, month, day;
    private String text = null;
    private String title = null;
    private Boolean history = false;
    private Boolean notif = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View AddView = inflater.inflate(R.layout.fragment_add, container, false);

        txtDate = (EditText) AddView.findViewById(R.id.addDate);
        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getView().getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(android.widget.DatePicker view, int chosenYear, int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + chosenYear);
                                year = chosenYear;
                                month = monthOfYear + 1;
                                day = dayOfMonth;
                            }


                        }, year, month, day);
                dpd.show();
            }
        });

        editText = (EditText) AddView.findViewById(R.id.addText);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                text = editText.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editTitle = (EditText) AddView.findViewById(R.id.addTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                title = editTitle.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        addNotif = (CheckBox) AddView.findViewById(R.id.addNotif);
        addNotif.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    notif = true;
                } else
                    notif = false;
            }
        });


        save = (BootstrapButton) AddView.findViewById(R.id.Save);
        save.setOnClickListener(this);


        dbController = new SQLController(this.getActivity());
        dbController.open();

        return AddView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Save:

                if (title == null
                        || text == null
                        || year == 0
                        || month == 0
                        || day == 0) {
                    Toast.makeText(v.getContext(), "All Fields Required", Toast.LENGTH_SHORT).show();
                } else {

                    long res = dbController.insert(title, text, year, month, day, history, notif);

                    CardListView listViewTodo = (CardListView) getActivity().findViewById(R.id.myListToDo);

                    ArrayList<Card> cardList = ToDo.getCardList(getActivity(), getActivity(), false);

                    CardArrayAdapter mCardToDoArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
                    if (listViewTodo != null) {
                        listViewTodo.setAdapter(mCardToDoArrayAdapter);
                    }

                    if (res == -1) {
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        //Set Notification
                        if (notif) {
                            Calendar date = Calendar.getInstance();
                            month = month -1;
                            date.set(year, month , day);

                            date.set(Calendar.HOUR_OF_DAY, 10);
                            date.set(Calendar.MINUTE, 15);
                            date.set(Calendar.SECOND, 0);

                            ToDo todo = new ToDo(title, text, date, history, notif);

                            MainActivity.setAlarm(v.getContext(), todo, (int) res);

                        }
                    }

                    //RAZ form
                    txtDate.setText(null);
                    editText.setText(null);
                    editTitle.setText(null);
                    addNotif.setChecked(true);

                    year = 0;
                    month = 0;
                    day = 0;
                    text = null;
                    title = null;
                    notif = true;

                    //Change frag
                    MainActivity.mViewPager.setCurrentItem(1);
                }
                break;
        }
    }

}