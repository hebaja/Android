package br.com.hebaja.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.ceep.R;
import br.com.hebaja.ceep.dao.CirculoDAO;
import br.com.hebaja.ceep.dao.RoomNotaDAO;
import br.com.hebaja.ceep.database.CeepDatabase;
import br.com.hebaja.ceep.model.Circulo;
import br.com.hebaja.ceep.model.Nota;
import br.com.hebaja.ceep.ui.recyclerview.adapter.FormularioNotaCoresAdapter;

import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_COLOR;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_COLOR_STATE;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_DEFAULT_COLOR;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.DATABSE_NAME;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR_ALTERA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR_INSERE;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.VALOR_INVALIDO;

public class FormularioNotaActivity extends AppCompatActivity {

    private RecyclerView listaCirculos;
    private TextView titulo;
    private TextView descricao;
    private FormularioNotaCoresAdapter adapter;
    private View formularioConstraintLayout;
    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        formularioConstraintLayout = findViewById(R.id.formulario_constraint_layout);
        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();
        List<Circulo> todosCirculos = pegaTodosCirculos();
        configuraRecyclerView(todosCirculos);
        Intent dadosRecebidos = getIntent();

        if(dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            nota = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            preencheCampos(nota);
            ConfiguraCorNotaBackground(dadosRecebidos.getIntExtra(CHAVE_COLOR, VALOR_INVALIDO));
        } else {
            nota = new Nota();
            configuraBackgroundDefaultNota();
        }
    }

    private void ConfiguraCorNotaBackground(int intExtra) {
        int color = intExtra;
        nota.setColor(color);
        formularioConstraintLayout.setBackgroundColor(color);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAVE_COLOR_STATE, nota.getColor());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ConfiguraCorNotaBackground(savedInstanceState.getInt(CHAVE_COLOR_STATE));
    }

    private void configuraBackgroundDefaultNota() {
            nota.setColor(Color.parseColor(CHAVE_DEFAULT_COLOR));
            formularioConstraintLayout.setBackgroundColor(nota.getColor());
    }

    private void configuraRecyclerView(List<Circulo> circulos) {
        listaCirculos = findViewById(R.id.formulario_lista_circulos_recyclerview);
        configuraAdapter(circulos, listaCirculos);
    }

    private void configuraAdapter(List<Circulo> circulos, RecyclerView listaCirculos) {
        adapter = new FormularioNotaCoresAdapter(circulos, this);
        listaCirculos.setAdapter(adapter);
        configuraListenerCliqueCores();
    }

    private void configuraListenerCliqueCores() {
        adapter.setOnItemClickListener(new FormularioNotaCoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Circulo circulo, int posicao) {
                int color = circulo.getCor();
                formularioConstraintLayout.setBackgroundColor(color);
                nota.setColor(color);
            }
        });
    }

    private List<Circulo> pegaTodosCirculos() {
        CirculoDAO circuloDao = new CirculoDAO();
        circuloDao.removeTodosCirculos();
        return circuloDao.todos();
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(ehMenuSalvaNota(item)) {
            criaNota();
            retornaNota(nota);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void criaNota() {
        nota.setTitulo(titulo.getText().toString());
        nota.setDescricao(descricao.getText().toString());
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_COLOR, nota.getColor());
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    private boolean ehMenuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}