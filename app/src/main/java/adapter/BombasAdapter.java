package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.monster.smokeproject.R;
import model.Lista;
import pojo.ConjuntoMotorBomba;

/**
 * Created by Marlon on 09/12/2016.
 */

public class BombasAdapter extends RecyclerView.Adapter<BombasAdapter.PersonViewHolder> {

    private ArrayList<ConjuntoMotorBomba> lista;
    public Context context;
    private String mode;
    private ArrayList<Integer> arrayListSelect;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        TextView tvNomeEstacao;
        LinearLayout linearLayoutBomba;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            tvNomeEstacao = (TextView) itemView.findViewById(R.id.tvNomeEstacao);
            tvNomeEstacao.setTypeface(RalewayMedium);
            linearLayoutBomba = (LinearLayout) itemView.findViewById(R.id.linearLayoutBomba);

        }
    }

    public BombasAdapter(ArrayList<ConjuntoMotorBomba> lista) {
        this.lista = lista;
    }

    public BombasAdapter(ArrayList<ConjuntoMotorBomba> lista, String mode, Context context) {
        this.lista = lista;
        this.mode = mode;
        this.context = context;
        arrayListSelect = new ArrayList<>();
    }

    public ArrayList<ConjuntoMotorBomba> getLista() {
        return lista;
    }

    public void setLista(ArrayList<ConjuntoMotorBomba> lista) {
        this.lista = lista;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ArrayList<Integer> getArrayListSelect() {
        return arrayListSelect;
    }

    public void setArrayListSelect(ArrayList<Integer> arrayListSelect) {
        this.arrayListSelect = arrayListSelect;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bomba, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {
        personViewHolder.tvNomeEstacao.setText(lista.get(position).getNumero());

        if (mode.equals("view")) {
            personViewHolder.linearLayoutBomba.setBackgroundColor(context.getResources().getColor(R.color.bg_lista));
        }
        if (arrayListSelect.size() > 0) {
            for (int i = 0; i < arrayListSelect.size(); i++) {
                if (arrayListSelect.get(i) == lista.get(position).getId()) {
                    //personViewHolder.tvNomeEstacao.setBackgroundColor(context.getResources().getColor(R.color.bg_lista));
                    personViewHolder.linearLayoutBomba.setBackgroundColor(context.getResources().getColor(R.color.bg_lista));
                } else {
                    //personViewHolder.tvNomeEstacao.setBackgroundColor(context.getResources().getColor(R.color.white));
                    //personViewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }
}
