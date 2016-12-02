package br.com.monster.smokeproject;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ValidarPlacaActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private String minhaPlaca;
    private EditText etPlaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_placa);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Validar placa");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etPlaca = (EditText) findViewById(R.id.etPlaca);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        Button btnValidar = (Button) findViewById(R.id.btnValidar);
            btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minhaPlaca = etPlaca.getText().toString();
                Snackbar snackbar = Snackbar.make(linearLayout, "Placa validada - " + minhaPlaca, Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundResource(R.color.black);
                snackbar.show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(it);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
