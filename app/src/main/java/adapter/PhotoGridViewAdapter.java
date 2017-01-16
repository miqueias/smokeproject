package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.monster.smokeproject.NovaVistoriaActivity;
import br.com.monster.smokeproject.R;
import model.ImageItem;
import model.Lista;

/**
 * Created by Marlon on 26/12/2016.
 */

public class PhotoGridViewAdapter extends RecyclerView.Adapter<PhotoGridViewAdapter.PersonViewHolder> {
//    private ArrayList<ImageItem> lista;
    private List<Lista> lista;
    public Context context;
    public static ImageView ivPhoto;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        ImageView ivCheck;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            ivCheck = (ImageView) itemView.findViewById(R.id.ivCheck);

        }
    }

    public PhotoGridViewAdapter(List<Lista> lista) {
        this.lista = lista;
    }


    public PhotoGridViewAdapter(List<Lista> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PhotoGridViewAdapter.PersonViewHolder personViewHolder, int position) {
//        personViewHolder.tvNomeEstacao.setText(endereco.get(position).getComplemento());
//        NovaVistoriaActivity.btnAddFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "teste " , Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    public void removeListItem(int position)
    {
        lista.remove(position);
        notifyItemRemoved(position);
        Log.e("removeListItem",""+ position);
    }

    @Override
    public int getItemCount() {
        return 5;
//        return lista.size();
    }
}
