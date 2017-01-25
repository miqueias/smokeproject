package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import adapter.ChecklistAdapter;
import adapter.ProblemasCheckListAdapter;
import dto.DataTransferObject;
import model.Lista;
import pojo.Auth;
import pojo.ConjuntoMotorBomba;
import pojo.Problemas;
import util.DividerItemDecoration;

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
            mode = extras.getString("modo");
            idVistoria = extras.getInt("vistoria_id");
        }

        etHorimetro = (EditText) findViewById(R.id.etHorimetro);
        etHorimetro.setTypeface(RalewayMedium);
        etAmperagem = (EditText) findViewById(R.id.etAmperagem);
        etAmperagem.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            etHorimetro.setText(auth.getVistoriasArrayList().get(idVistoria).getEstacoesElevatorias().getConjuntoMotorBombaArrayList().get(position).getHorimetro());
            etAmperagem.setText(auth.getVistoriasArrayList().get(idVistoria).getEstacoesElevatorias().getConjuntoMotorBombaArrayList().get(position).getAmperagem());
        }

        rvChecklist = (RecyclerView) findViewById(R.id.rvChecklist);
        llm = new LinearLayoutManager(this);
        rvChecklist.setLayoutManager(llm);
        rvChecklist.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvChecklist.addItemDecoration(itemDecoration);

        ChecklistAdapter adapter;
        if (mode.equals("view")) {
            adapter = new ChecklistAdapter(auth.getVistoriasArrayList().get(idVistoria).getEstacoesElevatorias().getConjuntoMotorBombaArrayList().get(position).getProblemasArrayList(), mode, this);
        } else {
            adapter = new ChecklistAdapter(auth.getProblemasArrayList(), mode, this);
        }

        rvChecklist.setAdapter(adapter);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayMedium);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConjuntoMotorBomba conjuntoMotorBomba = new ConjuntoMotorBomba();
                conjuntoMotorBomba.setEstacaoElevatoriaId(auth.getVistoriasArrayList().get(idVistoria).getEstacaoElevatoriaId());
                conjuntoMotorBomba.setId(auth.getVistoriasArrayList().get(idVistoria).getEstacoesElevatorias().getConjuntoMotorBombaArrayList().get(position).getId());
                conjuntoMotorBomba.setHorimetro(etHorimetro.getText().toString().trim());
                conjuntoMotorBomba.setAmperagem(etAmperagem.getText().toString().trim());

                ChecklistAdapter checkListAdapter = (ChecklistAdapter) rvChecklist.getAdapter();
                ArrayList<Integer> checklists = checkListAdapter.getArrayListCheck();
                ArrayList<Problemas> problemasArrayList = new ArrayList<Problemas>();

                for (int i = 0; i < checklists.size(); i++) {
                    Problemas problemas = new Problemas();
                    problemas.setId(checklists.get(i));
                    problemasArrayList.add(problemas);
                }
                conjuntoMotorBomba.setProblemasArrayList(problemasArrayList);
                DataTransferObject.getInstance().setDto(conjuntoMotorBomba);
                finish();
            }
        });

        if (mode.equals("view")) {
            btnSalvar.setVisibility(View.INVISIBLE);
            etHorimetro.setEnabled(false);
            etAmperagem.setEnabled(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
//                startActivity(it);
                finish();
                return true;
//            case R.id.action_home:
//                Intent iti = new Intent(getBaseContext(), HomeActivity.class);
//                startActivity(iti);
//                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }

}
