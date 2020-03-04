package br.com.hebaja.ceep.ui.recyclerview.helper.callback;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.ceep.dao.RoomNotaDAO;
import br.com.hebaja.ceep.database.CeepDatabase;
import br.com.hebaja.ceep.model.Nota;
import br.com.hebaja.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private final Context context;
    private List<Nota> todasNotas;
    private RoomNotaDAO dao;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter, Context context, List<Nota> todasNotas) {
        this.adapter = adapter;
        this.context = context;
        this.todasNotas = todasNotas;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        Nota nota1 = todasNotas.get(posicaoInicial);
        Nota nota2 = todasNotas.get(posicaoFinal);
        trocaNotas(nota1, nota2, posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(Nota nota1, Nota nota2, int posicaoInicial, int posicaoFinal) {
        configuraRoomDao();
        nota1.setPosicao(posicaoFinal);
        nota2.setPosicao(posicaoInicial);
        dao.altera(nota1);
        dao.altera(nota2);
        adapter.troca(posicaoInicial,posicaoFinal);
    }

    private void configuraRoomDao() {
        dao = Room.databaseBuilder(context, CeepDatabase.class, "ceep.db")
                .allowMainThreadQueries()
                .build()
                .getRoomNotaDao();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        Nota notaRemovida = todasNotas.get(posicaoDaNotaDeslizada);
        removeNota(notaRemovida);
    }

    private void removeNota(Nota nota) {
        configuraRoomDao();
        for (Nota notaNaLista :
                todasNotas) {
            if (notaNaLista.getPosicao() > nota.getPosicao()) {
                notaNaLista.setPosicao(notaNaLista.getPosicao() - 1);
                dao.altera(notaNaLista);
            }
        }
        dao.remove(nota);
        adapter.remove(nota);
    }
}
