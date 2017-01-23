package br.com.monster.smokeproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.EstacaoAdapter;
import adapter.NovaVistoriaAdapter;
import adapter.VistoriaAdapter;
import model.Lista;
import pojo.Auth;
import pojo.EstacoesElevatorias;
import util.DividerItemDecoration;
import util.RecyclerItemClickListener;
import util.Util;

public class EstacoesElevatoriasActivity extends AppCompatActivity {

    public static Activity estacao;

    private RecyclerView rvNovaVistoria;
    private LinearLayoutManager llm;
    private ArrayList<EstacoesElevatorias> lista;
    private TextView tvEstacaoElevatoria;
    private Auth auth = Auth.getInstance();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nova_vistoria);
        estacao = this;

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, 0);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vistoria");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");

        tvEstacaoElevatoria = (TextView) findViewById(R.id.tvEstacaoElevatoria);
        tvEstacaoElevatoria.setTypeface(RalewayBold);

        rvNovaVistoria = (RecyclerView) findViewById(R.id.rvNovaVistoria);
        llm = new LinearLayoutManager(this);
        rvNovaVistoria.setLayoutManager(llm);
        rvNovaVistoria.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvNovaVistoria.addItemDecoration(itemDecoration);
        EstacaoAdapter adapter = new EstacaoAdapter(EstacoesElevatoriasActivity.this, auth.getRota().getEstacoesElevatoriasArrayList());
        adapter.setSharedPreferences(sharedPreferences);
        rvNovaVistoria.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    private void loadPreferencesCheckList() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(it);
                finish();
                return true;
            case R.id.action_settings:
                //EstacaoAdapter.PersonViewHolder.cbCheck.isChecked();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
