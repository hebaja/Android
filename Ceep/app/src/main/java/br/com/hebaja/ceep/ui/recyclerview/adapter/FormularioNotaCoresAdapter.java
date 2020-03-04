package br.com.hebaja.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.hebaja.ceep.R;
import br.com.hebaja.ceep.model.Circulo;

public class FormularioNotaCoresAdapter extends RecyclerView.Adapter<FormularioNotaCoresAdapter.CirculoViewHolder> {

    private final List<Circulo> circulos;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public FormularioNotaCoresAdapter(List<Circulo> circulos, Context context) {
        this.circulos = circulos;
        this.context = context;
    }

    @NonNull
    @Override
    public CirculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_circulo_cores, parent, false);
        return new CirculoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull CirculoViewHolder holder, int position) {
        Circulo circulo = circulos.get(position);
        holder.vincula(circulo);
    }

    @Override
    public int getItemCount() {
        return circulos.size();
    }

    class CirculoViewHolder extends RecyclerView.ViewHolder {

        private View circuloView;
        private Circulo circulo;
        private int cor;

        public CirculoViewHolder(@NonNull View itemView) {
            super(itemView);
            circuloView = itemView.findViewById(R.id.item_circulo_cores);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(circulo, getAdapterPosition());
                }
            });
        }

        private void vincula(Circulo circulo) {
            this.circulo = circulo;
            this.cor = this.circulo.getCor();
            GradientDrawable gradientDrawable = (GradientDrawable)circuloView.getBackground();
            gradientDrawable.setColor(this.cor);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Circulo circulo, int posicao);
    }
}
