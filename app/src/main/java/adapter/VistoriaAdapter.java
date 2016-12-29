package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Bold.ttf");
        Typeface RalewayMedium = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(itemView.getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(itemView.getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        TextView tvData, tvAs, tvHora, tvSupervisor, tvNomeSupervisor, tvEstacao, tvNomeEstacao, tvMaisDetalhes;
        Button alertProblema;


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            tvData = (TextView) itemView.findViewById(R.id.tvData);
            tvData.setTypeface(RalewayMedium);
            tvAs = (TextView) itemView.findViewById(R.id.tvAs);
            tvAs.setTypeface(RalewayMedium);
            tvHora = (TextView) itemView.findViewById(R.id.tvHora);
            tvHora.setTypeface(RalewayMedium);

            tvSupervisor = (TextView) itemView.findViewById(R.id.tvSupervisor);
            tvSupervisor.setTypeface(RalewayMedium);
            tvNomeSupervisor = (TextView) itemView.findViewById(R.id.tvNomeSupervisor);
            tvNomeSupervisor.setTypeface(RalewayMedium);
            tvEstacao = (TextView) itemView.findViewById(R.id.tvEstacao);
            tvEstacao.setTypeface(RalewayMedium);
            tvNomeEstacao = (TextView) itemView.findViewById(R.id.tvNomeEstacao);
            tvNomeEstacao.setTypeface(RalewayMedium);
            tvMaisDetalhes = (TextView) itemView.findViewById(R.id.tvMaisDetalhes);
            tvMaisDetalhes.setTypeface(RalewayMedium);
            alertProblema = (Button) itemView.findViewById(R.id.alertProblema);
            alertProblema.setTypeface(RalewayBold);

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
