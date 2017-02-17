package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pojo.Auth;
import pojo.EstacoesElevatorias;
import pojo.Motivo;
import request.BaseRequester;
import request.Method;
import request.Requester;
import request.UserRequester;
import util.Util;

public class ApoioActivity extends AppCompatActivity {

    private TextView tvRegistrarApoio, tvData, tvAs, tvHora, tvSupervisor, tvNomeSupervisor,
            tvEstacao, tvNomeEstacao, tvRegional, tvNomeRegional;
    private EditText etNumeroOS, etDescProblema;
    private Button btnSalvar;
    private Auth auth;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoio);

        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vistoria/Apoio");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Util.setCtxAtual(this);
        auth = Auth.getInstance();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("posicao");
        }

        //data atual begin
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);

        sdf = new SimpleDateFormat("hh:mm:ss");
        String timeString = sdf.format(date);
        //data atual end

        final EstacoesElevatorias estacoesElevatorias = auth.getRota().getEstacoesElevatoriasArrayList().get(position);

        tvRegistrarApoio = (TextView) findViewById(R.id.tvRegistrarApoio);
        tvRegistrarApoio.setTypeface(RalewayBold);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvData.setText(dateString);
        tvAs = (TextView) findViewById(R.id.tvAs);
        tvAs.setTypeface(RalewayMedium);
        tvHora = (TextView) findViewById(R.id.tvHora);
        tvHora.setTypeface(RalewayMedium);
        tvHora.setText(timeString);

        tvSupervisor = (TextView) findViewById(R.id.tvSupervisor);
        tvSupervisor.setTypeface(RalewayMedium);
        tvNomeSupervisor = (TextView) findViewById(R.id.tvNomeSupervisor);
        tvNomeSupervisor.setTypeface(RalewayMedium);
        tvNomeSupervisor.setText(auth.getLider().getNome());
        tvEstacao = (TextView) findViewById(R.id.tvEstacao);
        tvEstacao.setTypeface(RalewayMedium);
        tvNomeEstacao = (TextView) findViewById(R.id.tvNomeEstacao);
        tvNomeEstacao.setTypeface(RalewayMedium);
        tvNomeEstacao.setText(estacoesElevatorias.getDescricao());
        tvRegional = (TextView) findViewById(R.id.tvRegional);
        tvRegional.setTypeface(RalewayMedium);
        tvNomeRegional = (TextView) findViewById(R.id.tvNomeRegional);
        tvNomeRegional.setTypeface(RalewayMedium);
        tvNomeRegional.setText(estacoesElevatorias.getRegional().getNome());

        etDescProblema = (EditText) findViewById(R.id.etDescProblema);
        etDescProblema.setTypeface(RalewayMedium);

        etNumeroOS = (EditText) findViewById(R.id.etNumeroOS);
        etNumeroOS.setTypeface(RalewayMedium);

        ArrayList<String> stringArrayList = new ArrayList<>();
        final ArrayList<Motivo> motivoArrayList;

        motivoArrayList = auth.getMotivoArrayList();

        for (int i = 0; i < motivoArrayList.size(); i++) {
            stringArrayList.add(motivoArrayList.get(i).getDescricao());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(spinnerArrayAdapter);


        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayMedium);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.AtivaDialogHandler(2, "", "Registrando Apoio...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            UserRequester userRequester = new UserRequester();
                            userRequester.loadAuth(auth.getLogin(), auth.getSenha(), "");
                            auth = Auth.getInstance();
                            JSONObject jsonPut = new JSONObject();
                            jsonPut.put("token", auth.getToken());
                            jsonPut.put("operador_id", auth.getOperador().getId());
                            jsonPut.put("motivo_id", motivoArrayList.get(spinner.getSelectedItemPosition()).getId());
                            jsonPut.put("numero_os", etNumeroOS.getText().toString().trim());
                            jsonPut.put("descricao", etDescProblema.getText().toString().trim());
                            jsonPut.put("estacao_elevatoria_id", estacoesElevatorias.getId());

                            BaseRequester baseRequester = new BaseRequester();
                            baseRequester.setUrl(Requester.API_URL + "/add_apoio");
                            baseRequester.setMethod(Method.POST);
                            baseRequester.setJsonString(jsonPut.toString());

                            String jsonReturn = baseRequester.execute(baseRequester).get();
                            Log.d("API", jsonReturn);

                            JSONObject jsonObjectApoio = new JSONObject(jsonReturn);

                            if (jsonObjectApoio.get("status").toString().equals("ERRO")) {
                                Util.AtivaDialogHandler(5, "", "");
                                Util.AtivaDialogHandler(1, "SisInspe", jsonObjectApoio.get("mensagem").toString());
                            } else {
                                Intent it = new Intent(getBaseContext(), EstacoesElevatoriasActivity.class);
                                startActivity(it);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
                return true;
            case R.id.action_home:
                Intent iti = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(iti);
                finish();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homee, menu);
        return true;
    }
}
