package br.com.hebaja.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import br.com.hebaja.ceep.R;

import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_JA_ABRIU;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOME_PREFERENCES;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences preferences = getSharedPreferences(CHAVE_NOME_PREFERENCES, MODE_PRIVATE);
        Handler handler = new Handler();

        if(preferences.contains(CHAVE_JA_ABRIU)){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mostraSplashScreen();
                }
            }, 500);
        } else {
            adicionaPreferenceJaAbriu(preferences);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mostraSplashScreen();
                }
            }, 2000);
        }
    }

    private void mostraSplashScreen() {
        Intent intent = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
        startActivity(intent);
        finish();
    }

    private void adicionaPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CHAVE_JA_ABRIU, true);
        editor.apply();
    }
}
