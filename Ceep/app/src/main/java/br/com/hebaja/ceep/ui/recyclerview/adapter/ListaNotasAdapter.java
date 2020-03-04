package br.com.hebaja.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.hebaja.ceep.R;
import br.com.hebaja.ceep.dao.RoomNotaDAO;
import br.com.hebaja.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private List<Nota> notas;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private int position;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        this.position = position;
        Nota nota = notas.get(this.position);
        holder.vincula(nota);
        int color = nota.getColor();
        holder.setNotaBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    public void altera(Nota nota) {
        notas.set(nota.getPosicao(), nota);
        notifyDataSetChanged();
    }

    public void remove(Nota nota) {
        notas.remove(nota);
        notifyItemRemoved(nota.getPosicao());
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public void adiciona(Nota nota) {
        int indiceInsercaoNota = 0;
        notas.add(indiceInsercaoNota, nota);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(Nota nota);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        private NotaViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(nota);
                }
            });
        }

        private void setNotaBackgroundColor(int color) {
            itemView.setBackgroundColor(color);
        }

        private void vincula(Nota nota) {
            this.nota = nota;
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

}
