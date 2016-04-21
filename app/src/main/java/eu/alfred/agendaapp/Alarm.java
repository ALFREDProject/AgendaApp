package eu.alfred.agendaapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by Gary on 18.03.2016.
 */
public class Alarm extends BroadcastReceiver {

    boolean active;
    String agenda;
    Ringtone ringtone;

    public Alarm() {
    }

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        Toast.makeText(context, "Went off", Toast.LENGTH_LONG).show();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("agendatype",intent.getStringExtra("agendatype"));
        context.startActivity(i);
        wl.release();

        int interval = 5000; // 5 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
;               CancelAlarm(context);
            }
        };
        handler.postDelayed(runnable, interval);
    }

    public void SetAlarm(final Context context, String minutes, String agenda)
    {
        int i = Integer.parseInt(minutes);
        Toast.makeText(context, "Alarm set to "+i+" minute(s).", Toast.LENGTH_LONG).show();
        final AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent in = new Intent(context, Alarm.class);
        in.putExtra("agendatype",agenda);
        in.setAction(Intent.ACTION_MAIN);
        in.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pi = PendingIntent.getBroadcast(context, 0, in, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000 * 60), pi); // Millisec * Second * Minute
        active = true;
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        if(ringtone!=null) ringtone.stop();
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
