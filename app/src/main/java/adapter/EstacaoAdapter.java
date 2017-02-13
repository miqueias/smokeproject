package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.monster.smokeproject.NovaVistoriaActivity;
import br.com.monster.smokeproject.R;
import br.com.monster.smokeproject.VistoriaActivity;
import pojo.EstacoesElevatorias;

/**
 * Created by Miqueias on 1/8/17.
 */

public class EstacaoAdapter extends RecyclerView.Adapter<EstacaoAdapter.PersonViewHolder> {

    private ArrayList<EstacoesElevatorias> lista;
    public Context context;
    int positionItem;
    SharedPreferences sharedPreferences;

    public EstacaoAdapter() {

    }

    public EstacaoAdapter(Context context, ArrayList<EstacoesElevatorias> lista) {
        this.context = context;
        this.lista = lista;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public EstacaoAdapter(ArrayList<EstacoesElevatorias> lista) {
        this.lista = lista;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        CheckBox cbCheck;
        TextView tvNomeEstacao;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cbCheck);
            tvNomeEstacao = (TextView) itemView.findViewById(R.id.tvNomeEstacao);
            tvNomeEstacao.setTypeface(RalewayMedium);

        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_nova_vistoria, viewGroup, false);
        EstacaoAdapter.PersonViewHolder pvh = new EstacaoAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int position) {
        personViewHolder.tvNomeEstacao.setText(lista.get(position).getDescricao());

        int i = sharedPreferences.getInt("estacao_elevatoria_"+position, -1);

        if (i > -1) {
            personViewHolder.cbCheck.setChecked(true);
        }

        personViewHolder.tvNomeEstacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,VistoriaActivity.class);
                intent.putExtra("posicao", position);
                intent.putExtra("estacao_elevatoria", lista.get(position).getId());
                intent.putExtra("estacao_elevatoria_position", position);
                context.startActivity(intent);
            }
        });
        personViewHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = sharedPreferences.getInt("estacao_elevatoria_"+position, -1);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (i == -1) {
                    editor.putInt("estacao_elevatoria_"+position, position);
                    editor.apply();
                } else {
                    editor.remove("estacao_elevatoria_"+position);
                    editor.apply();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}
