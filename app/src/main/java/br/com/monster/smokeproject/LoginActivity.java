package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText etLogin, etSenha;
    private TextView tvOdebretch, tvGestao, tvEstado;

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


        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setTypeface(RalewayBold);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
