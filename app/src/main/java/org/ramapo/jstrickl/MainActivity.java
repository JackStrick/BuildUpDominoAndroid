package org.ramapo.jstrickl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button1 = findViewById(R.id.new_game);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newGame(v);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                loadGame(v);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                quit(v);
            }
        });
    }

    public void newGame(View view)
    {
        System.out.println("New Game!");
        Tournament game = new Tournament();
        game.StartGame(1);
    }

    public void loadGame(View view)
    {
        Tournament game = new Tournament();
        game.StartGame(2);
    }

    public void quit(View view)
    {
        System.exit(0);
    }
}


