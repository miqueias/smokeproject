package br.com.monster.smokeproject;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pojo.Auth;
import request.BaseRequester;
import request.Method;
import request.Requester;

public class ValidarPlacaActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private String minhaPlaca;
    private EditText etPlaca;
    private Snackbar snackbar;
    private Auth auth;

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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPlaca.getWindowToken(), 0);
                minhaPlaca = etPlaca.getText().toString();

                auth = Auth.getInstance();
                final JSONObject jsonPut = new JSONObject();

                try {
                    jsonPut.put("placa", minhaPlaca);
                    jsonPut.put("operador_id", auth.getOperador().getId());
                    jsonPut.put("token", auth.getToken());

                    BaseRequester baseRequester = new BaseRequester();
                    baseRequester.setUrl(Requester.API_URL + "/validar_placa");
                    baseRequester.setMethod(Method.POST);
                    baseRequester.setJsonString(jsonPut.toString());

                    String jsonReturn = baseRequester.execute(baseRequester).get();
                    Log.d("API", jsonReturn);

                    JSONObject jsonObjectPlaca = new JSONObject(jsonReturn);

                    if (jsonObjectPlaca.get("status").equals("ERRO")) {
                        snackbar = Snackbar.make(linearLayout, jsonObjectPlaca.get("mensagem").toString(), Snackbar.LENGTH_INDEFINITE);
                    } else {
                        snackbar = Snackbar.make(linearLayout, "CÓDIGO: " + jsonObjectPlaca.get("mensagem").toString(), Snackbar.LENGTH_INDEFINITE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


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
