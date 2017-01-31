package br.com.monster.smokeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;
import pojo.Auth;
import request.UserRequester;
import util.Internet;
import util.Util;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText etLogin, etSenha;
    private TextView tvOdebretch, tvGestao, tvEstado;
    private Internet internet;
    private Auth auth; //SingleUser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);

        Util.setCtxAtual(this);

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
                    new AlertDialog.Builder(LoginActivity.this)
                            .setCancelable(false)
                            .setMessage("Por favor, verifique sua conexao com a internet.")

                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                } else {

                    final String senha = Util.md5(etSenha.getText().toString().trim());

                    Util.AtivaDialogHandler(2, "", "Efetuando Login...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            UserRequester userRequester = new UserRequester();
                            try {
                                //userRequester.loadAuth("tiago", "4297f44b13955235245b2497399d7a93", "");
                                userRequester.loadAuth(etLogin.getText().toString().toLowerCase().trim(),
                                        senha, "");

                                auth = Auth.getInstance();

                                if (auth.getStatusAPI().equals("ERRO")) {
                                    Util.AtivaDialogHandler(5, "", "");
                                    Util.AtivaDialogHandler(1, "SisInspe", auth.getMensagemErroApi());
                                } else {
                                    Intent it = new Intent(getBaseContext(), HomeActivity.class);
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
            }
        });
    }
}
