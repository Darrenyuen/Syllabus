package com.example.daidaijie.syllabusapplication.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 课程提醒服务
 */
public class SyllabusService extends Service {

    public static Intent getIntent(Context context) {
        return new Intent(context, SyllabusService.class);
    }

    public SyllabusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
