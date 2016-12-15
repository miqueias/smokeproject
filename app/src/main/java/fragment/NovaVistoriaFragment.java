package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import adapter.NovaVistoriaAdapter;
import br.com.monster.smokeproject.R;
import br.com.monster.smokeproject.VistoriaActivity;
import br.com.monster.smokeproject.VistoriaRealizadaActivity;
import model.Lista;
import util.DividerItemDecoration;
import util.RecyclerItemClickListener;


public class NovaVistoriaFragment extends Fragment {

    private RecyclerView rvNovaVistoria;
    private List<Lista> lista;
    private LinearLayoutManager llm;


    public NovaVistoriaFragment() {
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
        return inflater.inflate(R.layout.fragment_nova_vistoria, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvNovaVistoria=(RecyclerView) getView().findViewById(R.id.rvNovaVistoria);
        llm = new LinearLayoutManager(getActivity());
        rvNovaVistoria.setLayoutManager(llm);
        rvNovaVistoria.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        rvNovaVistoria.addItemDecoration(itemDecoration);
        NovaVistoriaAdapter adapter = new NovaVistoriaAdapter(lista);
        rvNovaVistoria.setAdapter(adapter);
        rvNovaVistoria.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvNovaVistoria ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
//                        Toast.makeText(getActivity(), "Posição " + position,
//                                Toast.LENGTH_LONG).show();
                        Intent it = new Intent(getContext(), VistoriaRealizadaActivity.class);
                        startActivity(it);
                        getActivity().finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
}