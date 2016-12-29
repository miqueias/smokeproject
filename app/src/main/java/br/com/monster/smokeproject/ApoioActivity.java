package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ApoioActivity extends AppCompatActivity {

    private TextView tvRegistrarApoio, tvData, tvAs, tvHora, tvSupervisor, tvNomeSupervisor,
            tvEstacao, tvNomeEstacao, tvRegional, tvNomeRegional;
    private EditText etNumeroOS, etDescProblema;
    private Button btnSalvar;

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



        tvRegistrarApoio = (TextView) findViewById(R.id.tvRegistrarApoio);
        tvRegistrarApoio.setTypeface(RalewayBold);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvAs = (TextView) findViewById(R.id.tvAs);
        tvAs.setTypeface(RalewayMedium);
        tvHora = (TextView) findViewById(R.id.tvHora);
        tvHora.setTypeface(RalewayMedium);


        tvSupervisor = (TextView) findViewById(R.id.tvSupervisor);
        tvSupervisor.setTypeface(RalewayMedium);
        tvNomeSupervisor = (TextView) findViewById(R.id.tvNomeSupervisor);
        tvNomeSupervisor.setTypeface(RalewayMedium);
        tvEstacao = (TextView) findViewById(R.id.tvEstacao);
        tvEstacao.setTypeface(RalewayMedium);
        tvNomeEstacao = (TextView) findViewById(R.id.tvNomeEstacao);
        tvNomeEstacao.setTypeface(RalewayMedium);
        tvRegional = (TextView) findViewById(R.id.tvRegional);
        tvRegional.setTypeface(RalewayMedium);
        tvNomeRegional = (TextView) findViewById(R.id.tvNomeRegional);
        tvNomeRegional.setTypeface(RalewayMedium);

        etDescProblema = (EditText) findViewById(R.id.etDescProblema);
        etDescProblema.setTypeface(RalewayMedium);

        etNumeroOS = (EditText) findViewById(R.id.etNumeroOS);
        etNumeroOS.setTypeface(RalewayMedium);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayMedium);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
