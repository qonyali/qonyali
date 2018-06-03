package com.example.halilgnal.mathsolver;

import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class GameTimer {

    private static final Logger LOGGER = Logger.getLogger("GameTimer");

    private static final String JOB_NAME = "Timer task";
    private static final long JOB_START_DELAY = 0L;
    private static final long JOB_FREQUENCY = 1000L;

    private volatile static GameTimer timerSingleton;
    private volatile static boolean isRunning;
    private volatile static TimerTaskJob task;
    private volatile static TimerTaskJob timerTask;
    private volatile static Timer timer;

    private TextView itsTextView;
    private int itsCount = 3;

    private GameTimer(TextView theTextView)
    {
        itsTextView = theTextView;
    }

    /**
     * Operate as a lazy singleton with synchronized locking.
     *
     * @return returns the singleton instance
     */
    public static GameTimer initialize(TextView theTextView) {

        if (timerSingleton == null) {

            synchronized (GameTimer.class) {

                if (timerSingleton == null) {

                    timerSingleton = new GameTimer(theTextView);
                    task = timerSingleton.new TimerTaskJob();
                    isRunning = false;
                }
            }
        }

        return timerSingleton;
    }

    public static void start() {

        if (!isRunning) {

            isRunning = true;
            task.start();
            LOGGER.info("Starting " + JOB_NAME + " " + new Date());
        }
    }

    public static void stop() {

        if (isRunning) {

            isRunning = false;
            task.stop();
            timerSingleton = null;
            LOGGER.info("Stopping " + JOB_NAME + " " + new Date());
        }
    }

    public static boolean isRunning() {

        LOGGER.info("Running: " + isRunning + " " + JOB_NAME + " " + new Date());
        return isRunning;
    }

    /*
     * A Sub class to run timer task
     */
    private class TimerTaskJob extends TimerTask {

        private final Logger TIMER_LOGGER = Logger.getLogger("TimerTaskJob");

        @Override
        public void run() {
            doTask();
        }

        void start() {

            timerTask = new TimerTaskJob();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, JOB_START_DELAY, JOB_FREQUENCY);
        }

        void stop() {

            timerTask.cancel();
            timer.cancel();
        }

        private void doTask() {

            itsTextView.setText(Integer.toString(itsCount));

            if(itsCount == 0)
                GameTimer.stop();
            else
                itsCount--;
        }
    }

}