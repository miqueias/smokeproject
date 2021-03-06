package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.monster.smokeproject.R;
import model.Lista;

/**
 * Created by Marlon on 09/12/2016.
 */

public class BombasAdapter extends RecyclerView.Adapter<BombasAdapter.PersonViewHolder> {

    private List<Lista> lista;
    public Context context;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

        }
    }

    public BombasAdapter(List<Lista> lista) {
        this.lista = lista;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bomba, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
//        personViewHolder.tvNomeEstacao.setText(endereco.get(position).getComplemento());

    }

    public void removeListItem(int position)
    {
        lista.remove(position);
        notifyItemRemoved(position);
        Log.e("removeListItem",""+ position);
    }

    @Override
    public int getItemCount() {
        return 4;
//        return lista.size();
    }
}
