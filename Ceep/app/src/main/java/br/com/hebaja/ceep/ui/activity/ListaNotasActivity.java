package br.com.hebaja.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.ceep.R;
import br.com.hebaja.ceep.dao.RoomNotaDAO;
import br.com.hebaja.ceep.database.CeepDatabase;
import br.com.hebaja.ceep.model.Nota;
import br.com.hebaja.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.hebaja.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_COLOR;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_LAYOUT_STATE;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_LAYOUT_STATE_ON_PAUSE;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.DATABSE_NAME;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.NOME_SHARED_PREFERENCES;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.ORIENTACAO_STAGGERED_GRID_LAYOUT;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.QUANTIDADE_DE_COLUNAS;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.TITULO_APPBAR;
import static br.com.hebaja.ceep.ui.activity.NotaActivityConstantes.VALOR_INVALIDO;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private RecyclerView listaNotas;
    private LayoutState layoutState = LayoutState.LINEAR;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<Nota> todasNotas;
    private RoomNotaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        configuraRoomDao();
        pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
        setTitle(TITULO_APPBAR);
    }

    private void configuraRoomDao() {
        dao = Room.databaseBuilder(this, CeepDatabase.class, DATABSE_NAME)
                .allowMainThreadQueries()
                .build()
                .getRoomNotaDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_nota_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lista_nota_ic_muda_staggered_grid_layout:

                mudaParaStaggeredGridLayout(StaggeredGridLayoutManager.VERTICAL);
                layoutState = LayoutState.STAGGERED_GRID;
                enviaLayoutStateParaSharedPreferences(CHAVE_LAYOUT_STATE);
                invalidateOptionsMenu();
                break;

            case R.id.menu_lista_nota_ic_muda_linear_layout:

                mudaParaLinearLayout();
                layoutState = LayoutState.LINEAR;
                enviaLayoutStateParaSharedPreferences(CHAVE_LAYOUT_STATE);
                invalidateOptionsMenu();
                break;
        }

        if(ehMenuListaAcessaFeedback(item)) {
            vaiParaFeedbackActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void vaiParaFeedbackActivity() {
        Intent iniciaFeedback = new Intent(ListaNotasActivity.this, FeedbackActivity.class);
        startActivity(iniciaFeedback);
    }

    private boolean ehMenuListaAcessaFeedback(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_lista_acessa_feedback;
    }

    private void enviaLayoutStateParaSharedPreferences(String layout_state_on_options_item_selected) {
        editor = preferences.edit();
        editor.putInt(layout_state_on_options_item_selected, layoutState.getValue());
        editor.apply();
    }

    private void mudaParaLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listaNotas.setLayoutManager(linearLayoutManager);
    }

    private void mudaParaStaggeredGridLayout(int vertical) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(vertical, ORIENTACAO_STAGGERED_GRID_LAYOUT);
        listaNotas.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        preferences = getSharedPreferences(NOME_SHARED_PREFERENCES, MODE_PRIVATE);

        if(preferences.contains(CHAVE_LAYOUT_STATE)) {
            pegaLayoutState(CHAVE_LAYOUT_STATE, VALOR_INVALIDO);
        }

        if(layoutEhLinear(LayoutState.LINEAR)) {
            mudaIconeDoMenu(menu, true, false);
            mudaParaLinearLayout();
        } else if (layoutEhStaggerdGrid(LayoutState.STAGGERED_GRID)) {
            mudaIconeDoMenu(menu, false, true);
            mudaParaStaggeredGridLayout(QUANTIDADE_DE_COLUNAS);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void mudaIconeDoMenu(Menu menu, boolean staggeredGrid, boolean linear) {
        menu.findItem(R.id.menu_lista_nota_ic_muda_staggered_grid_layout).setVisible(staggeredGrid);
        menu.findItem(R.id.menu_lista_nota_ic_muda_linear_layout).setVisible(linear);
    }

    private void pegaLayoutState(String layout_state_on_options_item_selected, int valorInvalido) {
        int layoutStateOnOptionsItemSelected = preferences.getInt(layout_state_on_options_item_selected, valorInvalido);
        layoutState = LayoutState.valueOf(layoutStateOnOptionsItemSelected);
    }

    private boolean layoutEhStaggerdGrid(LayoutState staggeredGrid) {
        return layoutState == staggeredGrid;
    }

    private boolean layoutEhLinear(LayoutState linear) {
        return layoutState == linear;
    }

    private void configuraBotaoInsereNota() {
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        todasNotas = dao.todos();
        Collections.sort(todasNotas);
        return todasNotas;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ehResultadoInsereNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                recebeCorFormulario(data);
                adiciona(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                if (ehPosicaoValida(notaRecebida.getPosicao())) {
                    altera(notaRecebida);
                    recebeCorFormulario(data);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recebeCorFormulario(@org.jetbrains.annotations.NotNull Intent data) {
        data.getIntExtra(CHAVE_COLOR, VALOR_INVALIDO);
    }

    private void altera(Nota nota) {
        dao.altera(nota);
        adapter.altera(nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        long id = dao.salva(nota);
        nota.setId(id);
        incrementeListaNotas();
        adapter.adiciona(nota);
    }

    private void incrementeListaNotas() {
        for (Nota notaNaLista :
                todasNotas) {
            int posicaoAlterada = notaNaLista.getPosicao() + 1;
            notaNaLista.setPosicao(posicaoAlterada);
            dao.altera(notaNaLista);
        }
    }

    private boolean ehResultadoInsereNota(int requestCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> notas) {
        listaNotas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(notas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, this, todasNotas));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListaNotasAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Nota nota) {
                vaiParaFormularioActivityAltera(nota);
            }
        });
    }

    private void vaiParaFormularioActivityAltera(Nota nota) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_COLOR, nota.getColor());
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}