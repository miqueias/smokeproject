package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import adapter.ChecklistAdapter;
import model.Lista;
import pojo.Auth;
import util.DividerItemDecoration;
import util.RecyclerItemClickListener;

import static br.com.monster.smokeproject.R.id.rvChecklist;

public class MotorBombaActivity extends AppCompatActivity {

    private RecyclerView rvChecklist;
    private LinearLayoutManager llm;
    private List<Lista> lista;

    private EditText etHorimetro;
    private EditText etAmperagem;
    private Button btnSalvar;
    private int position;
    private String mode;
    private Auth auth;
    private int idVistoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_bomba);

        auth = Auth.getInstance();

        //Fontes.ttf
        final Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        final Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Motor Bomba");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("posicao");
            mode = extras.getString("mode");
            idVistoria = extras.getInt("vistoria_id");
        }

        etHorimetro = (EditText) findViewById(R.id.etHorimetro);
        etHorimetro.setTypeface(RalewayMedium);
        etAmperagem = (EditText) findViewById(R.id.etAmperagem);
        etAmperagem.setTypeface(RalewayMedium);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayMedium);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rvChecklist = (RecyclerView) findViewById(R.id.rvChecklist);
        llm = new LinearLayoutManager(this);
        rvChecklist.setLayoutManager(llm);
        rvChecklist.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvChecklist.addItemDecoration(itemDecoration);

        ChecklistAdapter adapter;
        adapter = new ChecklistAdapter(auth.getVistoriasArrayList().get(idVistoria).getConjuntoMotorBombaArrayList().get(position).getProblemasArrayList(), mode, this);

        rvChecklist.setAdapter(adapter);

//        rvChecklist.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, rvChecklist ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
////                        Toast.makeText(NovaVistoriaActivity.this, "Posição " + position,
////                                Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
//                startActivity(it);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
