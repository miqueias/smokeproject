package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
    private ArrayList<Integer> arrayListCheck;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        CheckBox cbChecklist;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            cbChecklist = (CheckBox) itemView.findViewById(R.id.cbChecklist);
            cbChecklist.setTypeface(RalewayMedium);
        }
    }

    public ChecklistAdapter(ArrayList<Problemas> lista) {
        this.lista = lista;
    }

    public ChecklistAdapter(ArrayList<Problemas> lista, String mode, Context context) {
        this.lista = lista;
        this.mode = mode;
        this.context = context;
        arrayListCheck = new ArrayList<>();
    }

    public ArrayList<Problemas> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Problemas> lista) {
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

    public ArrayList<Integer> getArrayListCheck() {
        return arrayListCheck;
    }

    public void setArrayListCheck(ArrayList<Integer> arrayListCheck) {
        this.arrayListCheck = arrayListCheck;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checklist, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int position) {
        personViewHolder.cbChecklist.setText(lista.get(position).getDescricao());

        if (mode.equals("view")) {
            personViewHolder.cbChecklist.setChecked(true);
            personViewHolder.cbChecklist.setEnabled(false);
        } else {
            personViewHolder.cbChecklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (personViewHolder.cbChecklist.isChecked()) {
                        arrayListCheck.add(lista.get(position).getId());
                    } else {
                        arrayListCheck.remove(lista.get(position).getId());
                    }
                }
            });
        }

    }

    public void removeListItem(int position)
    {
        lista.remove(position);
        notifyItemRemoved(position);
        Log.e("removeListItem",""+ position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
//        return lista.size();
    }
}
