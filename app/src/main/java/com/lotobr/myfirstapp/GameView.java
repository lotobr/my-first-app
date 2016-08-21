package com.lotobr.myfirstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rivsen on 8/14/16.
 */
public class GameView extends LinearLayout {
    private Card[][] cardsMap = new Card[Config.LINE][Config.LINE];
    private List<Point> emptyPoints = new ArrayList<Point>();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    private void initGameView() {
        setOrientation(VERTICAL);
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new OnTouchListener() {
            private float startX;
            private float startY;
            private float offsetX;
            private float offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX > 5) {
                                swipeToRight();
                                Log.d("game view move to ", "right");
                            }
                            else if (offsetX < -5) {
                                swipeToLeft();
                                Log.d("game view move to ", "left");
                            }
                        } else {
                            if (offsetY > 5) {
                                swipeToDown();
                                Log.d("game view move to ", "down");
                            }
                            else if (offsetY < -5) {
                                swipeToUp();
                                Log.d("game view move to ", "up");
                            }
                        }
                        break;
                    default:break;
                }
                return true;
            }
        });
    }

    private void swipeToLeft() {
        boolean merge = false;
        for (int y = 0;y < Config.LINE;y++){
            for (int x = 0;x < Config.LINE;x++){

                for (int x1 = x + 1;x1 < Config.LINE;x1++){
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        }else if (cardsMap[x][y].isEqual(cardsMap[x1][y])) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MyActivity.getMyActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeToRight() {
        boolean merge = false;
        for (int y = 0;y < Config.LINE;y++){
            for (int x = Config.LINE - 1;x >= 0;x--){

                for (int x1 = x - 1;x1 >= 0;x1--){
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        }else if (cardsMap[x][y].isEqual(cardsMap[x1][y])) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MyActivity.getMyActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeToUp() {
        boolean merge = false;
        for (int x = 0;x < Config.LINE;x++){
            for (int y = 0;y < Config.LINE;y++){

                for (int y1 = y + 1;y1 < Config.LINE;y1++){
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        }else if (cardsMap[x][y].isEqual(cardsMap[x][y1])) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MyActivity.getMyActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeToDown() {
        boolean merge = false;
        for (int x = 0;x < Config.LINE;x++){
            for (int y = Config.LINE - 1;y >= 0;y--){

                for (int y1 = y - 1;y1 >= 0;y1--){
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].isEqual(cardsMap[x][y1])) {
                            MyActivity.getMyActivity().getAnimLayer()
                                    .createMoveAnim(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MyActivity.getMyActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (isCardsMapNull()) {
            Log.d("xxxxxxxxxxxxxx", "yyyyyyyyyyyyyyyyyy");
            Config.CARD_WIDTH = (Math.min(w, h) - 10) / 4;
            addCards(Config.CARD_WIDTH, Config.CARD_WIDTH);
            startGame();
        }
    }

    private boolean isCardsMapNull() {
        for (int y = 0;y < Config.LINE;y++){
            for (int x = 0;x < Config.LINE;x++){
                if (cardsMap[x][y] != null)
                    return false;
            }
        }
        return true;
    }

    public void startGame() {
        MyActivity myActivity = MyActivity.getMyActivity();
        myActivity.clearScore();
        myActivity.showBestScore(myActivity.getBestScore());
        for (int y = 0;y < 4;y++){
            for(int x = 0;x < 4;x++){
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    private void addCards(int cardWidth, int cardHeight) {
        Card card;
        LinearLayout line;
        LinearLayout.LayoutParams lineLp;

        for (int y = 0; y < 4; y++) {
            line = new LinearLayout(getContext());
            lineLp = new LinearLayout.LayoutParams(-1, cardHeight);
            addView(line, lineLp);

            for (int x = 0; x < 4; x++) {
                card = new Card(getContext());
                line.addView(card, cardWidth, cardHeight);

                cardsMap[x][y] = card;
            }
        }
    }

    private void addRandomNum() {
        emptyPoints.clear();

        for (int y = 0;y < 4;y++){
            for (int x = 0;x < 4;x++){
                if (cardsMap[x][y].getNum() <= 0)
                    emptyPoints.add(new Point(x, y));
            }
        }
        if (emptyPoints.size() > 0){
            Point p = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
            cardsMap[p.x][p.y].setNum(Math.random() > 0.5 ? 2 : 4);
        }
    }

    private void checkComplete(){
        boolean complete = false;
        for (int y = 0;y < Config.LINE;y++){
            for (int x = 0;x < Config.LINE;x++){
                if (cardsMap[x][y].getNum() == Config.VALUE) {
                    complete = true;
                    break;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("游戏成功！")
                    .setMessage("游戏结束！").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            })
                    .setNegativeButton("结束游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyActivity.getMyActivity().finish();
                        }
                    })
                    .show();
        }
    }

}
