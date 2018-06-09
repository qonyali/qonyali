package com.example.halilgnal.mathsolver;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.halilgnal.mathsolver.util.QUtil;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class GameActivity extends BaseActivity {

    private Game game;
    private HandleGame handleGame;
    private Intent itsIntent;
    private int itsLevel;

    private ArrayList<Button> btnGameNumberButtons;
    private TextView tvTargetValue, tvShowCalculation, tvFindThis, txtTimer;
    private ImageView plusArea, minusArea, multiArea, divArea;
    private Button currentBtn;
    private Step itsStep;

    private int total;
    private int calculation = 0;
    private int moveCount = 0;

    private Handler itsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        itsHandler = new Handler();
        itsIntent = getIntent();
        itsLevel = itsIntent.getIntExtra("game_level", 0);

        game = new GameImpl(QUtil.GameLevel.values()[itsLevel]);
        handleGame = new HandleGameImpl();
        btnGameNumberButtons = new ArrayList<>();

        handleGame.initializeGame(game);
        findResources();
        implementEvents();
        setGame(game);
    }

    private void findResources() {
        Typeface normalLcd = Typeface.createFromAsset(getAssets(), "fonts/digital-dream.regular.ttf");
        Typeface fatLcd = Typeface.createFromAsset(getAssets(), "fonts/digital-dream.fat-skew.ttf");
        for (int i = 0; i < game.getNumbers().length; i++) {
            int btnId = getResources().getIdentifier("button" + (i + 1), "id", getPackageName());
            btnGameNumberButtons.add((Button) findViewById(btnId));
            btnGameNumberButtons.get(i).setTypeface(fatLcd);
        }

        tvFindThis = (TextView) findViewById(R.id.find_this);
        tvTargetValue = (TextView) findViewById(R.id.targetValue);
        tvShowCalculation = (TextView) findViewById(R.id.showCalculation);
        txtTimer = findViewById(R.id.txtTimer);

        // test2
        // test neu


        tvFindThis.setTypeface(normalLcd);
        tvTargetValue.setTypeface(normalLcd);
        tvShowCalculation.setTypeface(fatLcd);
        txtTimer.setTypeface(normalLcd);

        plusArea = (ImageView) findViewById(R.id.plusArea);
        minusArea = (ImageView) findViewById(R.id.minusArea);
        multiArea = (ImageView) findViewById(R.id.multiArea);
        divArea = (ImageView) findViewById(R.id.divArea);
    }

    private void implementEvents() {
        for (int i = 0; i < btnGameNumberButtons.size(); i++) {
            btnGameNumberButtons.get(i).setOnTouchListener(new MyTouchListener());
        }

        plusArea.setOnDragListener(new DragListener());
        minusArea.setOnDragListener(new DragListener());
        multiArea.setOnDragListener(new DragListener());
        divArea.setOnDragListener(new DragListener());

        txtTimer.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                if (Integer.valueOf(txtTimer.getText().toString()) == 0) {
                                                    endGame();
                                                }
                                            }
                                        }

        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.mn_new:
                handleGame.initializeGame(game);
                findResources();
                implementEvents();
                setGame(game);
                tvShowCalculation.setText("");
                break;
            case R.id.mn_restart:
                resetGame();
                break;
            case R.id.mn_solve:
                tvShowCalculation.setText(game.getSolution());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setGame(Game mathGame) {
        GameTimer.stop();
        GameTimer.initialize(txtTimer, itsHandler);
        GameTimer.start();
        for (int i = 0; i < mathGame.getNumbers().length; i++) {
            btnGameNumberButtons.get(i).setText("" + mathGame.getNumbers()[i]);
            btnGameNumberButtons.get(i).setBackgroundResource(R.drawable.buttonshape);
        }
        tvTargetValue.setText("" + mathGame.getTarget());
    }

    private void resetGame() {
        moveCount = 0;
        calculation = 0;
        total = 0;
        setGame(game);
        tvShowCalculation.setText("reset");
        implementEvents();

    }

    private void disableButton(Button btn) {
        btn.setOnTouchListener(new DeleteTouchListener());
        btn.setBackgroundResource(R.drawable.buttonshape_used);
    }

    private void endGame() {
        for (int i = 0; i < btnGameNumberButtons.size(); i++) {
            disableButton(btnGameNumberButtons.get(i));
        }
        GameTimer.stop();
        total = 0;
        calculation = 0;
        moveCount = 0;
    }

    public class DragListener extends Activity implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            final int action = event.getAction();
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED: {

                    //System.out.println("ACTION_DRAG_STARTED");
                    return true; //Accept
                }
                case DragEvent.ACTION_DRAG_ENDED: {
                    //System.out.println("ACTION_DRAG_ENDED");
                    return true;
                }
                case DragEvent.ACTION_DRAG_ENTERED: {
                    if (v.getId() == R.id.plusArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                        //tvShowCalculation.setText("test: " + calculation + " + "+ currentBtn.getText().toString());
                    }
                    if (v.getId() == R.id.minusArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                        //tvShowCalculation.setText("test: " + calculation + " - "+ currentBtn.getText().toString());
                    }
                    if (v.getId() == R.id.multiArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    if (v.getId() == R.id.divArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    //System.out.println("ACTION_DRAG_ENTERED");


                    return true;
                }
                case DragEvent.ACTION_DRAG_EXITED: {
                    if (v.getId() == R.id.plusArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    if (v.getId() == R.id.minusArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    if (v.getId() == R.id.multiArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    if (v.getId() == R.id.divArea) {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                    }
                    //System.out.println("ACTION_DRAG_EXITED");
                    return true;
                }
                case DragEvent.ACTION_DROP: {
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    itsStep = new Step();
                    itsStep.setNum(parseInt(item.getText().toString()));

                    switch (v.getId()) {
                        case R.id.plusArea:
                            total = total + parseInt(item.getText().toString());
                            calculation += parseInt(item.getText().toString());
                            itsStep.setSign(" + ");
                            break;
                        case R.id.minusArea:
                            total = (total == 0) ? parseInt(item.getText().toString()) : total - parseInt(item.getText().toString());
                            calculation = (calculation == 0) ? parseInt(item.getText().toString()) : calculation - parseInt(item.getText().toString());
                            itsStep.setSign(" - ");
                            break;
                        case R.id.multiArea:
                            total = (total == 0) ? parseInt(item.getText().toString()) : total * parseInt(item.getText().toString());
                            calculation = (calculation == 0) ? parseInt(item.getText().toString()) : calculation * parseInt(item.getText().toString());
                            itsStep.setSign(" * ");
                            break;
                        case R.id.divArea:
                            // TODO: handle if mod != 0
                            if (calculation % parseInt(item.getText().toString()) > 0) {
                                tvShowCalculation.setText(R.string.modNotZero);
                                System.out.println("log 1");
                                return true;
                            } else {
                                System.out.println("log 2");
                                total = (total == 0) ? parseInt(item.getText().toString()) : total / parseInt(item.getText().toString());
                                calculation = (calculation == 0) ? parseInt(item.getText().toString()) : calculation / parseInt(item.getText().toString());
                                itsStep.setSign(" / ");
                            }
                            break;
                    }

                    if (handleGame.compareResult(game, total)) {
                        //tvShowCalculation.setText("u win in " + GameTimer.getElapsedTime() +  getResources().getString(R.string.SECONDS).toString()+System.getProperty("line.separator") + total);
                        tvShowCalculation.setText(App.getContext().getResources().getString(R.string.U_WIN, System.getProperty("line.separator") + GameTimer.getElapsedTime() + System.getProperty("line.separator")));
                        endGame();
                        return true;
                    }
                    tvShowCalculation.setText("" + total);

                    disableButton(currentBtn);
                    itsStep.setResult(calculation);
                    handleGame.addStep(itsStep);
                    if (moveCount % 2 == 1) {
                        currentBtn.setOnTouchListener(new MyTouchListener());
                        currentBtn.setText("" + calculation);
                        currentBtn.setBackgroundResource(R.drawable.buttonshape);
                        total = 0;
                        calculation = 0;
                        //tvShowCalculation.setText("test: " + total);
                        tvShowCalculation.setText(handleGame.printSteps());
                        System.out.println(handleGame.printSteps());
                    }

                    moveCount++;

                    return true;
                }
            }
            return false;
        }
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            currentBtn = (Button) view;
            ClipData data = ClipData.newPlainText("datalabel", "" + currentBtn.getText());
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
            view.startDrag(data, shadow, view, 0);
            return false;
        }
    }


    private final class DeleteTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }
}