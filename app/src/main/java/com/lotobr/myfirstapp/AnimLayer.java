package com.lotobr.myfirstapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rivsen on 8/14/16.
 */
public class AnimLayer extends FrameLayout {
    private List<Card> cards = new ArrayList<Card>();

    public AnimLayer(Context context) {
        super(context);
    }

    public AnimLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void createMoveAnim(final Card from, final Card to, int fromX, int toX,int fromY,int toY) {
        final Card c = getCard(from.getNum());

        LayoutParams lp = new LayoutParams(0, 0);
        lp.leftMargin = fromX * Config.CARD_WIDTH;
        lp.topMargin = fromY * Config.CARD_WIDTH;
        c.setLayoutParams(lp);

        if (to.getNum() <= 0) {
            to.getLabel().setVisibility(INVISIBLE);
        }
        TranslateAnimation ta = new TranslateAnimation(0, Config.CARD_WIDTH * (toX - fromX), 0, Config.CARD_WIDTH * (toY - fromY));
        ta.setDuration(100);
        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                to.getLabel().setVisibility(VISIBLE);
                recycleCard(c);
            }
        });
        c.startAnimation(ta);
    }

    private Card getCard(int num) {
        Card c;
        if (cards.size() > 0)
            c = cards.remove(0);
        else {
            c = new Card(getContext());
            addView(c);
        }
        c.setVisibility(VISIBLE);
        c.setNum(num);
        return c;
    }

    private void recycleCard(Card c) {
        c.setVisibility(INVISIBLE);
        c.setAnimation(null);
        cards.add(c);
    }

}
