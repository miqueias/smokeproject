package fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.monster.smokeproject.ApoioActivity;
import br.com.monster.smokeproject.NovaVistoriaActivity;
import br.com.monster.smokeproject.R;
import br.com.monster.smokeproject.ValidarPlacaActivity;

public class VistoriaRealizadaFragment extends Fragment {

    private LinearLayoutManager llm;
    private Button btnNovaVistoria;
    private Button btnApoio;
    private TextView tvEscolhaAcao;



    public VistoriaRealizadaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vistoria_realizada, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        tvEscolhaAcao = (TextView) getView().findViewById(R.id.tvEscolhaAcao);
        tvEscolhaAcao.setTypeface(RalewayBold);

        btnNovaVistoria=(Button) getView().findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setTypeface(RalewayBold);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), NovaVistoriaActivity.class);
                startActivity(it);
                getActivity().finish();
            }
        });

        btnApoio=(Button) getView().findViewById(R.id.btnApoio);
        btnApoio.setTypeface(RalewayBold);
        btnApoio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ApoioActivity.class);
                startActivity(it);
                getActivity().finish();
            }
        });


    }
}
