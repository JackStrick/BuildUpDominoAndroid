package org.ramapo.jstrickl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

public class MainGameActivity extends AppCompatActivity {

    private TextView alertTextView;
    private Round round;

    private int StackPosition;
    private int HandTile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);

        alertTextView = (TextView) findViewById(R.id.AlertTextView);



        if (getIntent().getExtras() != null)
        {
            round = (Round) getIntent().getSerializableExtra("round");
            int choice = (int) getIntent().getSerializableExtra("choice");

            if (choice == 1)
            {
                // Original Game Setup
                round.StartNew();
            }
            else if (choice == 2)
            {
                round.StartFromFile();
            }

            BuildBoard();

        }

    }

    public void BuildBoard() {
        // Starting A Hand
        //while (round.GetHandCount() < 4) {
        if ((round.GetComputerHand().isEmpty() && round.GetHumanHand().isEmpty()) && !round.GetComputerBY().isEmpty() && !round.GetHumanBY().isEmpty()) {
            String first = round.DetermineFirst();
            AlertBuilder("First Up!", first);
        }

        Object player = round.GetPlayerTurn();
        SetTurnView(player);
        updateBoneyardTiles();
        updateHandTiles();
        updateStackTiles();
        //while (round.IsPlaceableTiles(round.GetComputerHand()) || round.IsPlaceableTiles(round.GetHumanHand())) {
        //round.RoundWin();
    }

    public void SetTurnView(Object player){
        if (player instanceof Human){
            ((Button) findViewById(R.id.humanHelp)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.humanContinue)).setVisibility(View.GONE);
        }
        else if (player instanceof Computer){
            ((Button) findViewById(R.id.humanHelp)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.humanContinue)).setVisibility(View.VISIBLE);
        }
    }


    public void Continue(View view){
        round.SwitchTurn();
    }

    public void TileSelect(View view){
        int tileId = view.getId();
        String buttonName = getResources().getResourceEntryName(tileId);
        switch (view.getId()){
            case R.id.humHand1:
                HandTile = 0;
                break;
            case R.id.humHand2:
                HandTile = 1;
                break;
            case R.id.humHand3:
                HandTile = 2;
                break;
            case R.id.humHand4:
                HandTile = 3;
            case R.id.humHand5:
                HandTile = 4;
                break;
            case R.id.humHand6:
                HandTile = 5;
        }
        final ImageButton cStack1 = findViewById(R.id.compStack1);
        final ImageButton cStack2 = findViewById(R.id.compStack2);
        final ImageButton cStack3 = findViewById(R.id.compStack3);
        final ImageButton cStack4 = findViewById(R.id.compStack4);
        final ImageButton cStack5 = findViewById(R.id.compStack5);
        final ImageButton cStack6 = findViewById(R.id.compStack6);
        final ImageButton hStack1 = findViewById(R.id.compStack1);
        final ImageButton hStack2 = findViewById(R.id.compStack2);
        final ImageButton hStack3 = findViewById(R.id.compStack3);
        final ImageButton hStack4 = findViewById(R.id.compStack4);
        final ImageButton hStack5 = findViewById(R.id.compStack5);
        final ImageButton hStack6 = findViewById(R.id.compStack6);

        cStack1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 6;
                CheckIfValid();
            }
        });
        cStack2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 7;
                CheckIfValid();
            }
        });
        cStack3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 8;
                CheckIfValid();
            }
        });
        cStack4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 9;
                CheckIfValid();
            }
        });
        cStack5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 10;
                CheckIfValid();
            }
        });
        cStack6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 11;
                CheckIfValid();
            }
        });
        hStack1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 0;
                CheckIfValid();
            }
        });
        hStack2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 1;
                CheckIfValid();
            }
        });
        hStack3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 2;
                CheckIfValid();
            }
        });
        hStack4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 3;
                CheckIfValid();
            }
        });
        hStack5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 4;
                CheckIfValid();
            }
        });
        hStack6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StackPosition = 5;
                CheckIfValid();
            }
        });

    }
    public void updateStackTiles(){
        Vector<Tile> stack = round.GetGameStacks();
        for (int i = 0; i < stack.size(); i++){
            if (i > 5) {
                // Get color of current tile
                char letter = Character.toLowerCase(stack.get(i).getColor());
                // Create a string for the fileName to access the image needed for button
                String fileName = letter + Integer.toString(stack.get(i).getLeftPips()) + Integer.toString(stack.get(i).getRightPips());
                int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
                switch (i) {
                    case 6:
                        ((ImageButton) findViewById(R.id.compStack1)).setImageResource(picId);
                        break;
                    case 7:
                        ((ImageButton) findViewById(R.id.compStack2)).setImageResource(picId);
                        break;
                    case 8:
                        ((ImageButton) findViewById(R.id.compStack3)).setImageResource(picId);
                        break;
                    case 9:
                        ((ImageButton) findViewById(R.id.compStack4)).setImageResource(picId);
                        break;
                    case 10:
                        ((ImageButton) findViewById(R.id.compStack5)).setImageResource(picId);
                        break;
                    case 11:
                        ((ImageButton) findViewById(R.id.compStack6)).setImageResource(picId);
                        break;
                }
            }
            if (i < 6) {
                // Get color of current tile
                char letter = Character.toLowerCase(stack.get(i).getColor());
                // Create a string for the fileName to access the image needed for button
                String fileName = letter + Integer.toString(stack.get(i).getLeftPips()) + Integer.toString(stack.get(i).getRightPips());
                int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
                switch (i) {
                    case 0:
                        ((ImageButton) findViewById(R.id.humStack1)).setImageResource(picId);
                        break;
                    case 1:
                        ((ImageButton) findViewById(R.id.humStack2)).setImageResource(picId);
                        break;
                    case 2:
                        ((ImageButton) findViewById(R.id.humStack3)).setImageResource(picId);
                        break;
                    case 3:
                        ((ImageButton) findViewById(R.id.humStack4)).setImageResource(picId);
                        break;
                    case 4:
                        ((ImageButton) findViewById(R.id.humStack5)).setImageResource(picId);
                        break;
                    case 5:
                        ((ImageButton) findViewById(R.id.humStack6)).setImageResource(picId);
                        break;
                }
            }
        }

    }

    public void updateBoneyardTiles(){
        Vector<Tile> compBY = round.GetComputerBY();
        Vector<Tile> humBY = round.GetHumanBY();
        for (int i = 0; i < compBY.size(); i++){
            // Get color of current tile
            char letter = Character.toLowerCase(compBY.get(i).getColor());
            // Create a string for the fileName to access the image needed for button
            String fileName = letter + Integer.toString(compBY.get(i).getLeftPips()) + Integer.toString(compBY.get(i).getRightPips());
            int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
            switch(i) {
                case 0:
                    ((ImageButton) findViewById(R.id.compBY1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.compBY2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.compBY3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.compBY4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.compBY5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.compBY6)).setImageResource(picId);
                    break;
                case 6:
                    ((ImageButton) findViewById(R.id.compBY7)).setImageResource(picId);
                    break;
                case 7:
                    ((ImageButton) findViewById(R.id.compBY8)).setImageResource(picId);
                    break;
                case 8:
                    ((ImageButton) findViewById(R.id.compBY9)).setImageResource(picId);
                    break;
                case 9:
                    ((ImageButton) findViewById(R.id.compBY10)).setImageResource(picId);
                    break;
                case 10:
                    ((ImageButton) findViewById(R.id.compBY11)).setImageResource(picId);
                    break;
                case 11:
                    ((ImageButton) findViewById(R.id.compBY12)).setImageResource(picId);
                    break;
                case 12:
                    ((ImageButton) findViewById(R.id.compBY13)).setImageResource(picId);
                    break;
                case 13:
                    ((ImageButton) findViewById(R.id.compBY14)).setImageResource(picId);
                    break;
                case 14:
                    ((ImageButton) findViewById(R.id.compBY15)).setImageResource(picId);
                    break;
                case 15:
                    ((ImageButton) findViewById(R.id.compBY16)).setImageResource(picId);
                    break;
                case 16:
                    ((ImageButton) findViewById(R.id.compBY17)).setImageResource(picId);
                    break;
                case 17:
                    ((ImageButton) findViewById(R.id.compBY18)).setImageResource(picId);
                    break;
                case 18:
                    ((ImageButton) findViewById(R.id.compBY19)).setImageResource(picId);
                    break;
                case 19:
                    ((ImageButton) findViewById(R.id.compBY20)).setImageResource(picId);
                    break;
                case 20:
                    ((ImageButton) findViewById(R.id.compBY21)).setImageResource(picId);
                    break;
                case 21:
                    ((ImageButton) findViewById(R.id.compBY22)).setImageResource(picId);
                    break;
            }
        }
        for (int i = 0; i < humBY.size(); i++){
            // Get color of current tile
            char letter = Character.toLowerCase(humBY.get(i).getColor());
            // Create a string for the fileName to access the image needed for button
            String fileName = letter + Integer.toString(humBY.get(i).getLeftPips()) + Integer.toString(humBY.get(i).getRightPips());
            int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
            switch(i) {
                case 0:
                    ((ImageButton) findViewById(R.id.humBY1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.humBY2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.humBY3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.humBY4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.humBY5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.humBY6)).setImageResource(picId);
                    break;
                case 6:
                    ((ImageButton) findViewById(R.id.humBY7)).setImageResource(picId);
                    break;
                case 7:
                    ((ImageButton) findViewById(R.id.humBY8)).setImageResource(picId);
                    break;
                case 8:
                    ((ImageButton) findViewById(R.id.humBY9)).setImageResource(picId);
                    break;
                case 9:
                    ((ImageButton) findViewById(R.id.humBY10)).setImageResource(picId);
                    break;
                case 10:
                    ((ImageButton) findViewById(R.id.humBY11)).setImageResource(picId);
                    break;
                case 11:
                    ((ImageButton) findViewById(R.id.humBY12)).setImageResource(picId);
                    break;
                case 12:
                    ((ImageButton) findViewById(R.id.humBY13)).setImageResource(picId);
                    break;
                case 13:
                    ((ImageButton) findViewById(R.id.humBY14)).setImageResource(picId);
                    break;
                case 14:
                    ((ImageButton) findViewById(R.id.humBY15)).setImageResource(picId);
                    break;
                case 15:
                    ((ImageButton) findViewById(R.id.humBY16)).setImageResource(picId);
                    break;
                case 16:
                    ((ImageButton) findViewById(R.id.humBY17)).setImageResource(picId);
                    break;
                case 17:
                    ((ImageButton) findViewById(R.id.humBY18)).setImageResource(picId);
                    break;
                case 18:
                    ((ImageButton) findViewById(R.id.humBY19)).setImageResource(picId);
                    break;
                case 19:
                    ((ImageButton) findViewById(R.id.humBY20)).setImageResource(picId);
                    break;
                case 20:
                    ((ImageButton) findViewById(R.id.humBY21)).setImageResource(picId);
                    break;
                case 21:
                    ((ImageButton) findViewById(R.id.humBY22)).setImageResource(picId);
                    break;
            }
        }

    }

    public void updateHandTiles() {
        Vector<Tile> compHand = round.GetComputerHand();
        Vector<Tile> humHand = round.GetHumanHand();
        for (int i = 0; i < compHand.size(); i++) {
            // Get color of current tile
            char letter = Character.toLowerCase(compHand.get(i).getColor());
            // Create a string for the fileName to access the image needed for button
            String fileName = letter + Integer.toString(compHand.get(i).getLeftPips()) + Integer.toString(compHand.get(i).getRightPips());
            int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
            switch (i) {
                case 0:
                    ((ImageButton) findViewById(R.id.compHand1)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.compHand2)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.compHand3)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.compHand4)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.compHand5)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.compHand6)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compHand6)).setImageResource(picId);
                    break;
            }
        }
        if (compHand.size() < 6){
            ((ImageButton) findViewById(R.id.compHand6)).setVisibility(View.GONE);
            if (compHand.size() < 5){
                ((ImageButton) findViewById(R.id.compHand5)).setVisibility(View.GONE);
            }
            if (compHand.size() < 4){
                ((ImageButton) findViewById(R.id.compHand4)).setVisibility(View.GONE);
            }
            if (compHand.size() < 3){
                ((ImageButton) findViewById(R.id.compHand3)).setVisibility(View.GONE);
            }
            if (compHand.size() < 2){
                ((ImageButton) findViewById(R.id.compHand2)).setVisibility(View.GONE);
            }
            if (compHand.size() < 1){
                ((ImageButton) findViewById(R.id.compHand2)).setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < humHand.size(); i++) {
            // Get color of current tile
            char letter = Character.toLowerCase(humHand.get(i).getColor());
            // Create a string for the fileName to access the image needed for button
            String fileName = letter + Integer.toString(humHand.get(i).getLeftPips()) + Integer.toString(humHand.get(i).getRightPips());
            int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
            switch (i) {
                case 0:
                    ((ImageButton) findViewById(R.id.humHand1)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.humHand2)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.humHand3)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.humHand4)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.humHand5)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.humHand6)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humHand6)).setImageResource(picId);
                    break;

            }
        }

        if (humHand.size() < 6){
            ((ImageButton) findViewById(R.id.humHand6)).setVisibility(View.GONE);
            if (humHand.size() < 5){
                ((ImageButton) findViewById(R.id.humHand5)).setVisibility(View.GONE);
            }
            if (humHand.size() < 4){
                ((ImageButton) findViewById(R.id.humHand4)).setVisibility(View.GONE);
            }
            if (humHand.size() < 3){
                ((ImageButton) findViewById(R.id.humHand3)).setVisibility(View.GONE);
            }
            if (humHand.size() < 2){
                ((ImageButton) findViewById(R.id.humHand2)).setVisibility(View.GONE);
            }
            if (humHand.size() < 1){
                ((ImageButton) findViewById(R.id.humHand2)).setVisibility(View.GONE);
            }
        }
    }

    public void CheckIfValid(){
        boolean play = round.CheckValidity(HandTile, StackPosition, round.GetPlayerTurn());
        if (!play){
            AlertBuilder("Invalid Tile Placement", "The move you attempted was not valid");
        }
        else{
            updateHandTiles();
            updateStackTiles();
            round.SwitchTurn();
        }
    }

    public void AlertBuilder(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGameActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }




}
