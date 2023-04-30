package org.ramapo.jstrickl;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private static final int requestCode = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the app has permission to read from external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

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

    /**
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resulCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    public void onActivityResult(int requestCode, int resulCode, Intent data)
    {
        super.onActivityResult(requestCode, resulCode, data);
        Context context = getApplicationContext();
        if (requestCode == requestCode && resulCode == Activity.RESULT_OK)
        {
            if (data == null){
                return;
            }
            Uri uri = data.getData();
            Toast.makeText(context,  uri.getPath(),Toast.LENGTH_SHORT).show();
            Tournament game = new Tournament();
            Round round = game.getRound();
            Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
            intent.putExtra("round", round);
            intent.putExtra("choice", 2);
            intent.putExtra("uri", uri);
            startActivity(intent);
        }
    }

    /**
     * Starts a new game
     * @param view the current view state from the main menu
     */
    public void newGame(View view)
    {
        System.out.println("New Game!");
        Tournament game = new Tournament();
        Round round = game.getRound();
        game.SetRounds(0);
        Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
        intent.putExtra("round", round);
        intent.putExtra("choice", 1);
        startActivity(intent);
    }

    /**
     * Loads a game option
     * @param view the current view state from the main menu
     */
    public void loadGame(View view)
    {
        openFileChooser();
    }

    /**
     * Quit the game option
     * @param view the current view state from the main menu
     */
    public void quit(View view)
    {
        System.exit(0);
    }

    /**
     * Opens the file directory to select a file from
     */
    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        startActivityForResult(intent, requestCode);
    }
}


