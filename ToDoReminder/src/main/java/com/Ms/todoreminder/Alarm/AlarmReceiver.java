package com.Ms.todoreminder.Alarm;

/**
 * @author SPEGAGNE Mathieu on 19/03/15.
 * @author https://github.com/mspegagne
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.Ms.todoreminder.Model.ToDo;

/**
 * Receive the Alarm and give an Intent to Alarm Service to make a notification
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Get Data from Alarm
        int id = intent.getExtras().getInt("id");
        ToDo todo = intent.getExtras().getParcelable("todo");
        //Give the Data to Alarm Service
        Intent service1 = new Intent(context, AlarmService.class);
        service1.putExtra("id", id);
        service1.putExtra("todo", todo);
        context.startService(service1);
    }
}