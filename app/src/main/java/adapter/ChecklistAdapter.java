package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.monster.smokeproject.R;
import model.Lista;
import pojo.Problemas;

/**
 * Created by Marlon on 09/12/2016.
 */

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.PersonViewHolder> {

    private ArrayList<Problemas> lista;
    public Context context;
    private String mode;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

        }
    }

    public ChecklistAdapter(ArrayList<Problemas> lista) {
        this.lista = lista;
    }

    public ChecklistAdapter(ArrayList<Problemas> lista, String mode, Context context) {
        this.lista = lista;
        this.mode = mode;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checklist, viewGroup, false);
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
        return 5;
//        return lista.size();
    }
}
