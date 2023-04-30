package org.ramapo.jstrickl;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;
import java.util.Vector;

public class MainGameActivity extends AppCompatActivity {

    private Round round;
    private int choice;
    private int StackPosition;
    private int HandTile;

    /**
     * Creates the in game GUI
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);

        if (getIntent().getExtras() != null)
        {
            round = (Round) getIntent().getSerializableExtra("round");
            choice = (int) getIntent().getSerializableExtra("choice");

            if (choice == 1)
            {
                // Original Game Setup
                ((TextView) findViewById(R.id.MoveLog)).append("Starting New Game");
                round.StartNew();
            }
            else if (choice == 2)
            {
                // For launching from file
                ((TextView) findViewById(R.id.MoveLog)).append("Starting Game From File");
                Uri uri = ((Uri) getIntent().getParcelableExtra("uri"));
                round.StartFromFile(uri);

            }
            BuildBoard();
        }
    }

    /**
     * Builds the GUI for the game
     */
    public void BuildBoard() {
        if ((round.GetComputerHand().isEmpty() && round.GetHumanHand().isEmpty()) && !round.GetComputerBY().isEmpty() && !round.GetHumanBY().isEmpty()) {
            String first = round.DetermineFirst();
            AlertBuilder("First Up!", first);
            ((TextView) findViewById(R.id.MoveLog)).append("\nFirst Up: " + first);
        }
        Object player = round.GetPlayerTurn();
        SetTurnView(player);
        updateBoneyardTiles();
        updateHandTiles();
        updateStackTiles();
        ((TextView) findViewById(R.id.roundsWon)).setText("\n" + round.GetRoundsHumanWon() + "\n" + round.GetRoundsComputerWon());
        ((TextView) findViewById(R.id.currentScore)).setText("\n" + round.GetHumanPoints() + "\n" + round.GetComputerPoints());

        if (round.GetHandCount() == 4){
           round.EndRound();
           String myRoundString = round.ScoreGame();
           AlertBuilder("Round Complete", myRoundString);
            ((TextView) findViewById(R.id.MoveLog)).append("\n\nRound Complete\n" + myRoundString);
           ((TextView) findViewById(R.id.turn)).setText("");
           choice = 1;
            ((TextView) findViewById(R.id.roundsWon)).setText("\n" + round.GetRoundsHumanWon() + "\n" + round.GetRoundsComputerWon());
           ((TextView) findViewById(R.id.currentScore)).setText("\n" + round.GetHumanPoints() + "\n" + round.GetComputerPoints());
           ((Button) findViewById(R.id.quitGame)).setVisibility(View.VISIBLE);
           ((Button) findViewById(R.id.new_game)).setVisibility(View.VISIBLE);
           ((Button) findViewById(R.id.humanHelp)).setVisibility(View.GONE);
           ((Button) findViewById(R.id.humanSkip)).setVisibility(View.GONE);
           ((Button) findViewById(R.id.humanContinue)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.humanSave)).setVisibility(View.GONE);

        }
    }

    /**
     * Sets the GUI view based on who's turn it is
     * @param player the player whos turn it currently is
     */
    public void SetTurnView(Object player){

        if (player instanceof Human){
            ((Button) findViewById(R.id.humanHelp)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.humanSkip)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.humanContinue)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.humanSave)).setVisibility(View.GONE);
            if (round.GetHumanHand().isEmpty())
            {
                ((TextView) findViewById(R.id.turn)).setText("Click Continue To End Hand");
            }
            else {
                ((TextView) findViewById(R.id.turn)).setText("Your Turn");
            }

        }
        else if (player instanceof Computer){
            ((Button) findViewById(R.id.humanHelp)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.humanSkip)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.humanContinue)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.humanSave)).setVisibility(View.VISIBLE);
            if (round.GetComputerHand().isEmpty())
            {
                ((TextView) findViewById(R.id.turn)).setText("Click Continue To End Hand");
            }
            else {
                ((TextView) findViewById(R.id.turn)).setText("Computer's Turn");
            }
        }
    }

    /**
     * Initiates the computer's turn when Continue is clicked
     * @param view of Continue
     */
    public void Continue(View view){
        //Check if either have available move
        if (round.CheckPlaceable()) {
            //Computer Make Turn
            // Get the player type
            Player player = round.GetPlayerTurn();
            // Computer Makes Move
            String placement = round.CompTurn();
            ((TextView) findViewById(R.id.MoveLog)).append("\nComputer: " + placement);
            AlertBuilder("Computer Move", "The computer is " + placement);
            //Update Board
            updateHandTiles();
            updateStackTiles();

            round.SwitchTurn();
            SetTurnView(round.GetPlayerTurn());
        }

        if (!(round.CheckPlaceable())){
            if (!round.GetComputerHand().isEmpty() || !round.GetHumanHand().isEmpty())
            {
                AlertBuilder("Unplayable", "No tiles in either hand can be played");
                ((TextView) findViewById(R.id.MoveLog)).append("\n\nRemaining Hand Tiles Could Not Be Played\n");
            }
            if (round.GetHandCount() == 5){
                ((Button) findViewById(R.id.quitGame)).setVisibility(View.GONE);
                ((Button) findViewById(R.id.new_game)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.MoveLog)).append("\n\nStarting New Round");
                round.StartNew();
                BuildBoard();
            }
            else {
                NextHand();
            }
        }
    }

    /**
     * Initiates the next hand
     */
    public void NextHand() {
        if (round.UnPlaceableTilesInHand()) {
            ((TextView) findViewById(R.id.MoveLog)).append("\n\nHand Complete: \nNo more tiles in either hand can be placed\nUpdating Score");
            AlertBuilder("Hand Complete","No more tiles in either hand can be placed\nUpdating Score");
        }
        else {
            ((TextView) findViewById(R.id.MoveLog)).append("\n\nHand Complete: \nUpdating Score");
            AlertBuilder("Hand Complete", "Updating Score");
        }
        String score = round.EndHand();
        ((TextView) findViewById(R.id.MoveLog)).append("\n\nSCOREBOARD: \n" + score);
        AlertBuilder("SCOREBOARD", score);
        BuildBoard();
    }

    /**
     * Provides help to the user after Help button is clicked
     * @param view of Help
     */
    public void Help(View view) {
        Player player = round.GetPlayerTurn();
        player.Strategy(round.GetGameStacks());
        String move = player.GetStratString();
        AlertBuilder("Best Move", move);
    }

    /**
     * Checks if can skip then creates the alert and uses the round model after Skip is clicked
     * @param view of Skip
     */
    public void Skip(View view) {
        Player player = round.GetPlayerTurn();
        if (player.GetStratString() == "There is no possible move you can make. You need to skip your turn. Press Skip"
                || !(player.IsValidPlaceableTile(player.GetHand(), round.GetGameStacks())))
        {
            AlertBuilder("Skip", "No Playable Tiles So Player Can Pass");
            round.SwitchTurn();
            SetTurnView(round.GetPlayerTurn());
        }
        else {
            AlertBuilder("Can't Skip", "You have at least 1 playable tile. You may not skip your turn.");
        }
    }

    /**
     * Creates the alert and uses the round model after quit game is clicked
     * @param view of QuitGame
     */
    public void QuitGame(View view) {
        String finish = round.EndGame();
        ((TextView) findViewById(R.id.MoveLog)).append("\n\n\"Game Over\nScoreboard\n" + finish);
        AlertBuilder("Game Over --- Scoreboard", finish);
    }

    /**
     * Creates the alert and uses the round model after save game is clicked
     * @param view of SaveGame
     */
    public void SaveGameClicked(View view) {
        AlertBuilder("Saving Game", "Current Game Progress Is Being Saved");
        ((TextView) findViewById(R.id.MoveLog)).append("\n\nCurrent Game Progress Is Being Saved");
        round.SaveGame();
    }

    /**
     * Onclick function to initiate the tile selection that will be placed
     * @param view of the screen where the hand tile is selected
     */
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
                break;
            case R.id.humHand5:
                HandTile = 4;
                break;
            case R.id.humHand6:
                HandTile = 5;
                break;
        }
        final ImageButton cStack1 = findViewById(R.id.compStack1);
        final ImageButton cStack2 = findViewById(R.id.compStack2);
        final ImageButton cStack3 = findViewById(R.id.compStack3);
        final ImageButton cStack4 = findViewById(R.id.compStack4);
        final ImageButton cStack5 = findViewById(R.id.compStack5);
        final ImageButton cStack6 = findViewById(R.id.compStack6);
        final ImageButton hStack1 = findViewById(R.id.humStack1);
        final ImageButton hStack2 = findViewById(R.id.humStack2);
        final ImageButton hStack3 = findViewById(R.id.humStack3);
        final ImageButton hStack4 = findViewById(R.id.humStack4);
        final ImageButton hStack5 = findViewById(R.id.humStack5);
        final ImageButton hStack6 = findViewById(R.id.humStack6);

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

    /**
     * Sets up the stack tiles on the GUI
     */
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

    /**
     * Sets up the boneyard tiles on the GUI
     */
    public void updateBoneyardTiles(){
        Vector<Tile> compBY = round.GetComputerBY();
        Vector<Tile> humBY = round.GetHumanBY();
        // Loops through all tiles and matches the tile with the correct tile image
        for (int i = 0; i < compBY.size(); i++){
            // Get color of current tile
            char letter = Character.toLowerCase(compBY.get(i).getColor());
            // Create a string for the fileName to access the image needed for button
            String fileName = letter + Integer.toString(compBY.get(i).getLeftPips()) + Integer.toString(compBY.get(i).getRightPips());
            int picId = getResources().getIdentifier(fileName, "drawable", getApplicationContext().getPackageName());
            switch(i) {
                case 0:
                    ((ImageButton) findViewById(R.id.compBY1)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.compBY2)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.compBY3)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.compBY4)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.compBY5)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.compBY6)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY6)).setImageResource(picId);
                    break;
                case 6:
                    ((ImageButton) findViewById(R.id.compBY7)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY7)).setImageResource(picId);
                    break;
                case 7:
                    ((ImageButton) findViewById(R.id.compBY8)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY8)).setImageResource(picId);
                    break;
                case 8:
                    ((ImageButton) findViewById(R.id.compBY9)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY9)).setImageResource(picId);
                    break;
                case 9:
                    ((ImageButton) findViewById(R.id.compBY10)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY10)).setImageResource(picId);
                    break;
                case 10:
                    ((ImageButton) findViewById(R.id.compBY11)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY11)).setImageResource(picId);
                    break;
                case 11:
                    ((ImageButton) findViewById(R.id.compBY12)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY12)).setImageResource(picId);
                    break;
                case 12:
                    ((ImageButton) findViewById(R.id.compBY13)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY13)).setImageResource(picId);
                    break;
                case 13:
                    ((ImageButton) findViewById(R.id.compBY14)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY14)).setImageResource(picId);
                    break;
                case 14:
                    ((ImageButton) findViewById(R.id.compBY15)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY15)).setImageResource(picId);
                    break;
                case 15:
                    ((ImageButton) findViewById(R.id.compBY16)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY16)).setImageResource(picId);
                    break;
                case 16:
                    ((ImageButton) findViewById(R.id.compBY17)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY17)).setImageResource(picId);
                    break;
                case 17:
                    ((ImageButton) findViewById(R.id.compBY18)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY18)).setImageResource(picId);
                    break;
                case 18:
                    ((ImageButton) findViewById(R.id.compBY19)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY19)).setImageResource(picId);
                    break;
                case 19:
                    ((ImageButton) findViewById(R.id.compBY20)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY20)).setImageResource(picId);
                    break;
                case 20:
                    ((ImageButton) findViewById(R.id.compBY21)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY21)).setImageResource(picId);
                    break;
                case 21:
                    ((ImageButton) findViewById(R.id.compBY22)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.compBY22)).setImageResource(picId);
                    break;
            }
        }

        // When the boneyard size gets smaller, the old tiles are no longer visible
        if (compBY.size() < 22){
            ((ImageButton) findViewById(R.id.compBY22)).setVisibility(View.GONE);
            if (compBY.size() < 21){
                ((ImageButton) findViewById(R.id.compBY21)).setVisibility(View.GONE);
            }
            if (compBY.size() < 20){
                ((ImageButton) findViewById(R.id.compBY20)).setVisibility(View.GONE);
            }
            if (compBY.size() < 19){
                ((ImageButton) findViewById(R.id.compBY19)).setVisibility(View.GONE);
            }
            if (compBY.size() < 18){
                ((ImageButton) findViewById(R.id.compBY18)).setVisibility(View.GONE);
            }
            if (compBY.size() < 17){
                ((ImageButton) findViewById(R.id.compBY17)).setVisibility(View.GONE);
            }
            if (compBY.size() < 16){
                ((ImageButton) findViewById(R.id.compBY16)).setVisibility(View.GONE);
            }
            if (compBY.size() < 15){
                ((ImageButton) findViewById(R.id.compBY15)).setVisibility(View.GONE);
            }
            if (compBY.size() < 14){
                ((ImageButton) findViewById(R.id.compBY14)).setVisibility(View.GONE);
            }
            if (compBY.size() < 13){
                ((ImageButton) findViewById(R.id.compBY13)).setVisibility(View.GONE);
            }
            if (compBY.size() < 12){
                ((ImageButton) findViewById(R.id.compBY12)).setVisibility(View.GONE);
            }
            if (compBY.size() < 11){
                ((ImageButton) findViewById(R.id.compBY11)).setVisibility(View.GONE);
            }
            if (compBY.size() < 10){
                ((ImageButton) findViewById(R.id.compBY10)).setVisibility(View.GONE);
            }
            if (compBY.size() < 9){
                ((ImageButton) findViewById(R.id.compBY9)).setVisibility(View.GONE);
            }
            if (compBY.size() < 8){
                ((ImageButton) findViewById(R.id.compBY8)).setVisibility(View.GONE);
            }
            if (compBY.size() < 7){
                ((ImageButton) findViewById(R.id.compBY7)).setVisibility(View.GONE);
            }
            if (compBY.size() < 6){
                ((ImageButton) findViewById(R.id.compBY6)).setVisibility(View.GONE);
            }
            if (compBY.size() < 5){
                ((ImageButton) findViewById(R.id.compBY5)).setVisibility(View.GONE);
            }
            if (compBY.size() < 4){
                ((ImageButton) findViewById(R.id.compBY4)).setVisibility(View.GONE);
            }
            if (compBY.size() < 3){
                ((ImageButton) findViewById(R.id.compBY3)).setVisibility(View.GONE);
            }
            if (compBY.size() < 2){
                ((ImageButton) findViewById(R.id.compBY2)).setVisibility(View.GONE);
            }
            if (compBY.size() < 1){
                ((ImageButton) findViewById(R.id.compBY1)).setVisibility(View.GONE);
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
                    ((ImageButton) findViewById(R.id.humBY1)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY1)).setImageResource(picId);
                    break;
                case 1:
                    ((ImageButton) findViewById(R.id.humBY2)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY2)).setImageResource(picId);
                    break;
                case 2:
                    ((ImageButton) findViewById(R.id.humBY3)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY3)).setImageResource(picId);
                    break;
                case 3:
                    ((ImageButton) findViewById(R.id.humBY4)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY4)).setImageResource(picId);
                    break;
                case 4:
                    ((ImageButton) findViewById(R.id.humBY5)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY5)).setImageResource(picId);
                    break;
                case 5:
                    ((ImageButton) findViewById(R.id.humBY6)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY6)).setImageResource(picId);
                    break;
                case 6:
                    ((ImageButton) findViewById(R.id.humBY7)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY7)).setImageResource(picId);
                    break;
                case 7:
                    ((ImageButton) findViewById(R.id.humBY8)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY8)).setImageResource(picId);
                    break;
                case 8:
                    ((ImageButton) findViewById(R.id.humBY9)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY9)).setImageResource(picId);
                    break;
                case 9:
                    ((ImageButton) findViewById(R.id.humBY10)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY10)).setImageResource(picId);
                    break;
                case 10:
                    ((ImageButton) findViewById(R.id.humBY11)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY11)).setImageResource(picId);
                    break;
                case 11:
                    ((ImageButton) findViewById(R.id.humBY12)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY12)).setImageResource(picId);
                    break;
                case 12:
                    ((ImageButton) findViewById(R.id.humBY13)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY13)).setImageResource(picId);
                    break;
                case 13:
                    ((ImageButton) findViewById(R.id.humBY14)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY14)).setImageResource(picId);
                    break;
                case 14:
                    ((ImageButton) findViewById(R.id.humBY15)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY15)).setImageResource(picId);
                    break;
                case 15:
                    ((ImageButton) findViewById(R.id.humBY16)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY16)).setImageResource(picId);
                    break;
                case 16:
                    ((ImageButton) findViewById(R.id.humBY17)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY17)).setImageResource(picId);
                    break;
                case 17:
                    ((ImageButton) findViewById(R.id.humBY18)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY18)).setImageResource(picId);
                    break;
                case 18:
                    ((ImageButton) findViewById(R.id.humBY19)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY19)).setImageResource(picId);
                    break;
                case 19:
                    ((ImageButton) findViewById(R.id.humBY20)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY20)).setImageResource(picId);
                    break;
                case 20:
                    ((ImageButton) findViewById(R.id.humBY21)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY21)).setImageResource(picId);
                    break;
                case 21:
                    ((ImageButton) findViewById(R.id.humBY22)).setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.humBY22)).setImageResource(picId);
                    break;
            }
        }

        if (humBY.size() < 22){
            ((ImageButton) findViewById(R.id.humBY22)).setVisibility(View.GONE);
            if (humBY.size() < 21){
                ((ImageButton) findViewById(R.id.humBY21)).setVisibility(View.GONE);
            }
            if (humBY.size() < 20){
                ((ImageButton) findViewById(R.id.humBY20)).setVisibility(View.GONE);
            }
            if (humBY.size() < 19){
                ((ImageButton) findViewById(R.id.humBY19)).setVisibility(View.GONE);
            }
            if (humBY.size() < 18){
                ((ImageButton) findViewById(R.id.humBY18)).setVisibility(View.GONE);
            }
            if (humBY.size() < 17){
                ((ImageButton) findViewById(R.id.humBY17)).setVisibility(View.GONE);
            }
            if (humBY.size() < 16){
                ((ImageButton) findViewById(R.id.humBY16)).setVisibility(View.GONE);
            }
            if (humBY.size() < 15){
                ((ImageButton) findViewById(R.id.humBY15)).setVisibility(View.GONE);
            }
            if (humBY.size() < 14){
                ((ImageButton) findViewById(R.id.humBY14)).setVisibility(View.GONE);
            }
            if (humBY.size() < 13){
                ((ImageButton) findViewById(R.id.humBY13)).setVisibility(View.GONE);
            }
            if (humBY.size() < 12){
                ((ImageButton) findViewById(R.id.humBY12)).setVisibility(View.GONE);
            }
            if (humBY.size() < 11){
                ((ImageButton) findViewById(R.id.humBY11)).setVisibility(View.GONE);
            }
            if (humBY.size() < 10){
                ((ImageButton) findViewById(R.id.humBY10)).setVisibility(View.GONE);
            }
            if (humBY.size() < 9){
                ((ImageButton) findViewById(R.id.humBY9)).setVisibility(View.GONE);
            }
            if (humBY.size() < 8){
                ((ImageButton) findViewById(R.id.humBY8)).setVisibility(View.GONE);
            }
            if (humBY.size() < 7){
                ((ImageButton) findViewById(R.id.humBY7)).setVisibility(View.GONE);
            }
            if (humBY.size() < 6){
                ((ImageButton) findViewById(R.id.humBY6)).setVisibility(View.GONE);
            }
            if (humBY.size() < 5){
                ((ImageButton) findViewById(R.id.humBY5)).setVisibility(View.GONE);
            }
            if (humBY.size() < 4){
                ((ImageButton) findViewById(R.id.humBY4)).setVisibility(View.GONE);
            }
            if (humBY.size() < 3){
                ((ImageButton) findViewById(R.id.humBY3)).setVisibility(View.GONE);
            }
            if (humBY.size() < 2){
                ((ImageButton) findViewById(R.id.humBY2)).setVisibility(View.GONE);
            }
            if (humBY.size() < 1){
                ((ImageButton) findViewById(R.id.humBY1)).setVisibility(View.GONE);
            }
        }

    }

    /**
     * Sets up the hand tiles on the GUI
     */
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
                ((ImageButton) findViewById(R.id.compHand1)).setVisibility(View.GONE);
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
                ((ImageButton) findViewById(R.id.humHand1)).setVisibility(View.GONE);
            }
        }
    }

    /**
     * Checks if the move being made is valid
     */
    public void CheckIfValid(){
        boolean play = round.CheckValidity(HandTile, StackPosition, round.GetPlayerTurn());
        if (!play){
            AlertBuilder("Invalid Tile Placement", "The move you attempted was not valid");
        }
        else{
            String stackName = "";
            switch (StackPosition) {
                case 0:
                    stackName = "B1";
                    break;
                case 1:
                    stackName = "B2";
                    break;
                case 2:
                    stackName = "B3";
                    break;
                case 3:
                    stackName = "B4";
                    break;
                case 4:
                    stackName = "B5";
                    break;
                case 5:
                    stackName = "B6";
                    break;
                case 6:
                    stackName = "W1";
                    break;
                case 7:
                    stackName = "W2";
                    break;
                case 8:
                    stackName = "W3";
                    break;
                case 9:
                    stackName = "W4";
                    break;
                case 10:
                    stackName = "W5";
                    break;
                case 11:
                    stackName = "W6";
                    break;
            }
            Tile tile = round.GetSelectedTile();
            ((TextView) findViewById(R.id.MoveLog)).append("\n\nHuman: placing tile {" + tile.getColor() + tile.getLeftPips() + tile.getRightPips() + "} on stack " + stackName);
            updateHandTiles();
            updateStackTiles();
            round.SwitchTurn();
            SetTurnView(round.GetPlayerTurn());
        }
    }

    /**
     * Creates the dismissible popups in game
     * @param title String that is displayed in the title section of the popup
     * @param message String that is displayed in the message section of the popup
     */
    public void AlertBuilder(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGameActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (title == "Game Over --- Scoreboard")
                {
                    System.exit(0);
                }
                else{

                }
                dialog.cancel();
            }
        });
        builder.show();
    }
}
