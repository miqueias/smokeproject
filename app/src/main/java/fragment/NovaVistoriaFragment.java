package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import adapter.NovaVistoriaAdapter;
import br.com.monster.smokeproject.R;
import model.Lista;
import util.DividerItemDecoration;


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

    }
}