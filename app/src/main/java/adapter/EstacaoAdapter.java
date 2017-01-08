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
import pojo.EstacoesElevatorias;

/**
 * Created by Miqueias on 1/8/17.
 */

public class EstacaoAdapter extends RecyclerView.Adapter<EstacaoAdapter.PersonViewHolder> {

    private ArrayList<EstacoesElevatorias> lista;
    public Context context;

    public EstacaoAdapter() {

    }

    public EstacaoAdapter(Context context, ArrayList<EstacoesElevatorias> lista) {
        this.context = context;
        this.lista = lista;
    }

    public EstacaoAdapter(ArrayList<EstacoesElevatorias> lista) {
        this.lista = lista;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        TextView tvSupervisor;
        CheckBox cbCheck;


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            tvSupervisor = (TextView) itemView.findViewById(R.id.tvNomeEstacao);
            tvSupervisor.setTypeface(RalewayMedium);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cbCheck);

        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_nova_vistoria, viewGroup, false);
        EstacaoAdapter.PersonViewHolder pvh = new EstacaoAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int position) {
        personViewHolder.tvSupervisor.setText(lista.get(position).getDescricao());

        personViewHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}
