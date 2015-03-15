package com.Ms.todoreminder;

/**
 * @author SPEGAGNE Mathieu on 11/03/15.
 * @author https://github.com/mspegagne
 */

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import com.Ms.todoreminder.Model.SQLController;
import com.Ms.todoreminder.Model.ToDo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.beardedhen.androidbootstrap.BootstrapButton;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.Calendar;

public class AddFragment extends Fragment implements OnClickListener {

    private EditText txtDate = null;
    private EditText editTitle = null;
    private EditText editText = null;
    private BootstrapButton save = null;

    private SQLController dbController;

    private int year, month, day;
    private String text = null;
    private String title = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View AddView =  inflater.inflate(R.layout.fragment_add, container, false);

        txtDate = (EditText)AddView.findViewById(R.id.addDate);
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
                            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }


                        }, year, month, day);
                dpd.show();
            }
        });

        editText = (EditText)AddView.findViewById(R.id.addText);
        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                text = editText.getText().toString();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        editTitle = (EditText)AddView.findViewById(R.id.addTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                title = editTitle.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

                Calendar date = Calendar.getInstance();
                date.set(year, month, day);

                ToDo add = new ToDo(title, text, date);

                long res = dbController.insert(title, text, year, month, day);

                CardListView listView = (CardListView) getActivity().findViewById(R.id.myList);
                ToDo.setToDoList(this.getActivity(), listView);

                if(res == -1)
                    Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(), "Saved", Toast.LENGTH_SHORT).show();


                //Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                //intent.putExtra("data", data);
                //startActivity(intent);


                //switchFragment(Fragment.TAG);

                break;
        }
    }
}