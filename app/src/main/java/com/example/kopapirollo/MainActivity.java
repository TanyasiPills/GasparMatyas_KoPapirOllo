package com.example.kopapirollo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button ko;
    Button papir;
    Button ollo;

    TextView eredmeny;
    ImageView ember;
    ImageView bot;
    int szEmber = 0;
    int szBot = 0;
    int emberPont = 0;
    int botPont = 0;

    Random random = new Random();

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Játék Vége").setMessage("Szeretne e új játékot?").setCancelable(false).setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewGame();
            }
        }).setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create();
        init();
    }

    public void init() {
        ko = findViewById(R.id.ko);
        papir = findViewById(R.id.papir);
        ollo = findViewById(R.id.ollo);

        eredmeny = findViewById(R.id.eredmeny);
        ember = findViewById(R.id.ember);
        bot = findViewById(R.id.bot);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v);
            }
        };

        ko.setOnClickListener(listener);
        papir.setOnClickListener(listener);
        ollo.setOnClickListener(listener);
    }

    public void click(View v){
        Button button = (Button)v;
        if(button.getId() == R.id.ollo){
            ember.setImageResource(R.drawable.scissors);
            szEmber = 1;
        }
        else if(button.getId() == R.id.papir){
            ember.setImageResource(R.drawable.paper);
            szEmber = 2;
        }
        else {
            ember.setImageResource(R.drawable.rock);
            szEmber = 3;
        }
        EnemyTurn();
    }
    public void EnemyTurn(){
        int number = random.nextInt(3);
        if(number == 0){
            bot.setImageResource(R.drawable.scissors);
        }
        else if(number == 1){
            bot.setImageResource(R.drawable.paper);
        }
        else {
            bot.setImageResource(R.drawable.rock);
        }
        szBot = number+1;
        Evaluate();
    }
    public void Evaluate(){
        if((szEmber == 1 && szBot == 3) || (szEmber == 2 && szBot == 1) || (szEmber == 3 && szBot == 2)){
            botPont++;
            Toast.makeText(this,"Vesztettél!", Toast.LENGTH_SHORT).show();
        }
        else if((szEmber == 1 && szBot == 2) || (szEmber == 2 && szBot == 3) || (szEmber == 3 && szBot == 1)){
            emberPont++;
            Toast.makeText(this,"Nyertél!", Toast.LENGTH_SHORT).show();
        }
        Pontok();
    }
    public void Pontok(){
        eredmeny.setText(String.format("Eredmény: Ember: %s Gép: %s",emberPont,botPont));
        if(emberPont == 3 ){
            alertDialog.setTitle("Győzelem!");
            alertDialog.create();
            alertDialog.show();
        }
        else if(botPont == 3){
            alertDialog.setTitle("Vereség!");
            alertDialog.create();
            alertDialog.show();
        }
    }
    public void NewGame(){
        botPont = 0;
        emberPont = 0;
        Pontok();
    }
}