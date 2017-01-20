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

import br.com.monster.smokeproject.R;
import pojo.ProblemasCheckList;

/**
 * Created by Marlon on 13/01/2017.
 */

public class ProblemasCheckListAdapter extends RecyclerView.Adapter<ProblemasCheckListAdapter.PersonViewHolder> {

    private ArrayList<ProblemasCheckList> lista;
    public Context context;
    private String mode;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        TextView cbChecklist;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            cbChecklist = (CheckBox) itemView.findViewById(R.id.cbChecklist);
            cbChecklist.setTypeface(RalewayMedium);
        }
    }

    public ProblemasCheckListAdapter(ArrayList<ProblemasCheckList> lista) {
        this.lista = lista;
    }

    public ProblemasCheckListAdapter(ArrayList<ProblemasCheckList> lista, String mode, Context context) {
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
        personViewHolder.cbChecklist.setText(lista.get(position).getDescricao());

        if (mode.equals("view")) {
            personViewHolder.cbChecklist.setChecked(true);
            personViewHolder.cbChecklist.setEnabled(false);
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
    }
}
