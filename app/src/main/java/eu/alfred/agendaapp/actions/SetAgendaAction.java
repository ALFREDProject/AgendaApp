package eu.alfred.agendaapp.actions;

import java.util.Map;

import eu.alfred.agendaapp.Alarm;
import eu.alfred.agendaapp.MainActivity;
import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;

/**
 * Created by Gary on 18.03.2016.
 */
public class SetAgendaAction implements ICadeCommand {

    MainActivity main;
    Alarm agendaAlarm;
    Cade cade;

    public SetAgendaAction(MainActivity mainActivity, Alarm agendaAlarm, Cade cade) {
        this.main = mainActivity;
        this.agendaAlarm = agendaAlarm;
        this.cade = cade;
    }

    @Override
    public void performAction(String s, Map<String, String> map) {
        if(!agendaAlarm.isActive()) {
            agendaAlarm.SetAlarm(main.getBaseContext(), map.get("selected_alert_time"),
                    map.get("selected_agenda_mode"));
        }
        cade.sendActionResult(true);
    }

    @Override
    public void performWhQuery(String s, Map<String, String> map) {

    }

    @Override
    public void performValidity(String s, Map<String, String> map) {

    }

    @Override
    public void performEntityRecognizer(String s, Map<String, String> map) {

    }
}
