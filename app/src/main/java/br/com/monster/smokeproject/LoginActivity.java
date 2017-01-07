package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import request.BaseRequester;
import request.Method;
import request.Requester;
import util.Internet;
import util.Util;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText etLogin, etSenha;
    private TextView tvOdebretch, tvGestao, tvEstado;
    Util util;
    Internet internet;
    String jsonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        etLogin = (EditText) findViewById(R.id.etLogin);
        etLogin.setTypeface(RalewayBold);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etSenha.setTypeface(RalewayBold);
        tvOdebretch = (TextView) findViewById(R.id.tvOdebretch);
        tvOdebretch.setTypeface(Odebrecht);
        tvEstado = (TextView) findViewById(R.id.tvOdebretch);
        tvEstado.setTypeface(Odebrecht);
        tvGestao = (TextView) findViewById(R.id.tvGestao);
        tvGestao.setTypeface(Odebrecht);

        internet = new Internet(this);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setTypeface(RalewayBold);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!internet.verificarConexao()) {
                    //mensagem que precisa conectar a internet

                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String login = "tiago"; //etLogin.getText().toString().toLowerCase().trim();
                            String senha = "4297f44b13955235245b2497399d7a93"; //etSenha.getText().toString().toLowerCase().trim();

                            final JSONObject jsonPut = new JSONObject();

                            try {
                                jsonPut.put("login", login);
                                jsonPut.put("senha", senha);

                                BaseRequester baseRequester = new BaseRequester();
                                baseRequester.setUrl(Requester.API_URL + "/auth");
                                baseRequester.setMethod(Method.POST);
                                baseRequester.setGsonString(jsonPut.toString());

                                String jsonReturn = baseRequester.execute(baseRequester).get();
                                Log.i("API", jsonReturn);

                                jsonReturn = baseRequester.execute(baseRequester).get();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();



                }




                Intent it = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
