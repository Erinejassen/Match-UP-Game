package com.example.pairspre_finals_taskperformance;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;



public class Game extends AppCompatActivity {

    private List<ImageButton> buttons;
    private List<Integer> cards;
    private Integer clickedIndex = null;
    private int matchCount = 0;
    private boolean isClickable = true;
    private ImageButton refresh;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttons = Arrays.asList(
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9),
                findViewById(R.id.button10),
                findViewById(R.id.button11),
                findViewById(R.id.button12)
        );

        refresh = findViewById(R.id.btnrefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        home = findViewById(R.id.btnhome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Game.this, MainActivity.class);
                startActivity(home);
            }
        });
        initializeGame();
    }

    private void initializeGame() {
        cards = Arrays.asList(
                R.drawable.apple, R.drawable.apple,
                R.drawable.banana, R.drawable.banana,
                R.drawable.grapes, R.drawable.grapes,
                R.drawable.mango, R.drawable.mango,
                R.drawable.orange, R.drawable.orange,
                R.drawable.pineapple, R.drawable.pineapple
        );
        Collections.shuffle(cards);

        for (int i = 0; i < buttons.size(); i++) {
            final int index = i;
            buttons.get(i).setImageResource(R.drawable.tree);
            buttons.get(i).setTag(null);
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardClicked(index);
                }
            });
        }

        clickedIndex = null;
        matchCount = 0;
        isClickable = true;
    }

    private void onCardClicked(int index) {
        if (!isClickable) return;

        final ImageButton button = buttons.get(index);
        if (button.getTag() != null) return;

        button.setImageResource(cards.get(index));
        if (clickedIndex == null) {
            clickedIndex = index;
        } else {
            isClickable = false;
            final int firstIndex = clickedIndex;
            if (cards.get(firstIndex).equals(cards.get(index))) {
                buttons.get(firstIndex).setTag("matched");
                button.setTag("matched");
                matchCount += 2;
                if (matchCount == cards.size()) {
                    Intent gameover = new Intent(Game.this, GameOver.class);
                    startActivity(gameover);
                }
                isClickable = true;

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttons.get(firstIndex).setImageResource(R.drawable.tree);
                        button.setImageResource(R.drawable.tree);
                        isClickable = true;
                    }
                }, 1000);
            }
            clickedIndex = null;
        }
    }

    private void resetGame() {
        initializeGame();
    }
}
