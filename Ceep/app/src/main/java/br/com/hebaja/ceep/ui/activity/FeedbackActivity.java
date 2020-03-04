package br.com.hebaja.ceep.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.hebaja.ceep.R;

import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR_FEEDBACK;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(TITULO_APPBAR_FEEDBACK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback_envia, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_envia_feedback) {
            Toast.makeText(this, "Feedback enviado", Toast.LENGTH_SHORT).show();
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
