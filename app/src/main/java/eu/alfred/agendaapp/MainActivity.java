package eu.alfred.agendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import eu.alfred.agendaapp.actions.SetAgendaAction;
import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.ui.AppActivity;
import eu.alfred.ui.BackToPAButton;
import eu.alfred.ui.CircleButton;

public class MainActivity extends AppActivity implements ICadeCommand {

    private final String SET_AGENDA_ACTION = "SetAgendaAction";
    Alarm agendaAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Change your view contents. Note, the the button has to be included last.
        setContentView(R.layout.activity_main);

        circleButton = (CircleButton) findViewById(R.id.voiceControlBtn);
        circleButton.setOnTouchListener(new MicrophoneTouchListener());

        backToPAButton = (BackToPAButton) findViewById(R.id.backControlBtn);
        backToPAButton.setOnTouchListener(new BackTouchListener());

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TextView agenda = (TextView) findViewById(R.id.alarm_txt);
        String alarm = intent.getStringExtra("agendatype");
        if(alarm==null) alarm = "";
        agenda.setText(alarm);
    }

    @Override
    public void performAction(String command, Map<String, String> map) {
        agendaAlarm = new Alarm();
        switch (command) {
            case(SET_AGENDA_ACTION):
                SetAgendaAction saa = new SetAgendaAction(this, agendaAlarm, cade);
                saa.performAction(command, map);
                break;
        }
    }

    @Override
    public void performWhQuery(String command, Map<String, String> map) {

    }

    @Override
    public void performValidity(String command, Map<String, String> map) {

    }

    @Override
    public void performEntityRecognizer(String command, Map<String, String> map) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if (agendaAlarm!= null) agendaAlarm.CancelAlarm(getBaseContext());
        super.onDestroy();
    }

    public void onEndAlarm (View v) {
        finish();
    }
}