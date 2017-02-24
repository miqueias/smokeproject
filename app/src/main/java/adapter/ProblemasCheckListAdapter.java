package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.monster.smokeproject.R;
import pojo.Auth;
import pojo.Problemas;
import pojo.ProblemasCheckList;

/**
 * Created by Marlon on 13/01/2017.
 */

public class ProblemasCheckListAdapter extends RecyclerView.Adapter<ProblemasCheckListAdapter.PersonViewHolder> {

    private ArrayList<ProblemasCheckList> lista;
    public Context context;
    private String mode;
    private ArrayList<Integer> arrayListCheck;
    private Auth auth;
    private int idEstacaoElevatoria;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        CheckBox cbChecklist;
        LinearLayout linearLayoutBomba;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            cbChecklist = (CheckBox) itemView.findViewById(R.id.cbChecklist);
            cbChecklist.setTypeface(RalewayMedium);
            linearLayoutBomba = (LinearLayout) itemView.findViewById(R.id.linearLayoutCheckLists);
        }
    }

    public ProblemasCheckListAdapter(ArrayList<ProblemasCheckList> lista) {
        this.lista = lista;
    }

    public ArrayList<ProblemasCheckList> getLista() {
        return lista;
    }

    public void setLista(ArrayList<ProblemasCheckList> lista) {
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

    public int getIdEstacaoElevatoria() {
        return idEstacaoElevatoria;
    }

    public void setIdEstacaoElevatoria(int idEstacaoElevatoria) {
        this.idEstacaoElevatoria = idEstacaoElevatoria;
    }

    public ProblemasCheckListAdapter(ArrayList<ProblemasCheckList> lista, String mode, Context context) {
        this.lista = lista;
        this.mode = mode;
        this.context = context;
        arrayListCheck = new ArrayList<>();
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
            personViewHolder.cbChecklist.setEnabled(false);

            if (lista.get(position).isChecked()) {
                personViewHolder.cbChecklist.setChecked(true);
            }
        } else {

            auth = Auth.getInstance();
            if (auth.getRota().getEstacoesElevatoriasArrayList().get(idEstacaoElevatoria).getArrayListCheckListNaoMarcadoUltimaVistoria().size() > 0) {
                ArrayList<ProblemasCheckList> problemasCheckLists;
                problemasCheckLists = auth.getRota().getEstacoesElevatoriasArrayList().get(idEstacaoElevatoria).getArrayListCheckListNaoMarcadoUltimaVistoria();

                personViewHolder.cbChecklist.setChecked(true);
                //lista.get(position).setChecked(true);

                personViewHolder.linearLayoutBomba.setBackgroundColor(context.getResources().getColor(R.color.bg_lista));

                if (!arrayListCheck.contains(lista.get(position).getId())) {
                    arrayListCheck.add(lista.get(position).getId());
                }

                for (int i = 0; i < problemasCheckLists.size(); i++) {
                    ProblemasCheckList problemasCheckList;
                    problemasCheckList = problemasCheckLists.get(i);

                    if (lista.get(position).getId() == problemasCheckList.getId()) {
                        personViewHolder.cbChecklist.setChecked(false);
                        //lista.get(position).setChecked(false);
                        personViewHolder.linearLayoutBomba.setBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
                        for (int j = 0; j < arrayListCheck.size(); j++) {
                            if (arrayListCheck.get(j) == lista.get(position).getId()) {
                                arrayListCheck.remove(j);
                            }
                        }
                    }
                }
            }

            personViewHolder.cbChecklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (personViewHolder.cbChecklist.isChecked()) {
                        if (!arrayListCheck.contains(lista.get(position).getId())) {
                            arrayListCheck.add(lista.get(position).getId());
                        }
                    } else {
                        if (arrayListCheck.size() > 0) {
                            for (int i = 0; i < arrayListCheck.size(); i++) {
                                if (arrayListCheck.get(i) == lista.get(position).getId()) {
                                    arrayListCheck.remove(i);
                                }
                            }
                        }
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
    }
}
