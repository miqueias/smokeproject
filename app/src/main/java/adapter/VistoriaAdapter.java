package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.monster.smokeproject.R;
import model.Lista;

/**
 * Created by Marlon on 09/12/2016.
 */

public class VistoriaAdapter extends RecyclerView.Adapter<VistoriaAdapter.PersonViewHolder> {

    private List<Lista> lista;
    public Context context;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView textView19;
        TextView textView17;
        TextView textView11;
        TextView textView13;


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

        }
    }

    public VistoriaAdapter(List<Lista> lista) {
        this.lista = lista;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_vistoria_realizada, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
//        personViewHolder.tvNomeEstacao.setText(endereco.get(position).getComplemento());

    }

    @Override
    public int getItemCount() {
        return 10;
//        return lista.size();
    }
}
