package com.example.halilgnal.mathsolver;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context itsContext;

    @Override
    public void onCreate() {
        super.onCreate();
        itsContext = this;
    }

    public static Context getContext(){
        return itsContext;
    }
}
