package br.com.monster.smokeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import pojo.Auth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnVistoriaRealizada;
    private Button btnPlaca;
    private Button btnNovaVistoria;
    private Button btnSair;

    private TextView tvOla, tvNomeBV, tvData, tvEscala, tvTipoRota, tvDescRota;
    private Auth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Início");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        auth = Auth.getInstance();

        //data atual begin
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        //data atual end


        //Fontes.ttf
        Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        TextView profilename = (TextView) headerview.findViewById(R.id.tvNomeBV);
        profilename.setText(Auth.getInstance().getOperador().getNome());

        TextView rota = (TextView) headerview.findViewById(R.id.tvDescRota);
        rota.setText("Rota: " + auth.getRota().getDescricao());

        TextView tipoRota = (TextView) headerview.findViewById(R.id.tvTipoRota);
        tipoRota.setText("Tipo de Rota: " + auth.getRota().getTipoRota().getDescricao());

        TextView escala = (TextView) headerview.findViewById(R.id.tvEscala);
        escala.setText("Escala: " + auth.getEscala().getDescricao());

        TextView data = (TextView) headerview.findViewById(R.id.tvData);
        data.setText(dateString);

        navigationView.setNavigationItemSelectedListener(this);

        tvOla = (TextView) headerview.findViewById(R.id.tvOla);
        tvOla = (TextView) findViewById(R.id.tvOla);
        tvOla.setTypeface(RalewayMedium);

        tvNomeBV = (TextView) headerview.findViewById(R.id.tvNomeBV);
        tvNomeBV = (TextView) findViewById(R.id.tvNomeBV);
        tvNomeBV.setTypeface(RalewayMedium);
        tvNomeBV.setText(auth.getOperador().getNome()); // API

        tvData = (TextView) headerview.findViewById(R.id.tvData);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvData.setText(dateString);

        tvEscala = (TextView) findViewById(R.id.tvEscala);
        tvEscala.setTypeface(RalewayMedium);
        tvEscala.setText("Escala: " + auth.getEscala().getDescricao());//API
        tvTipoRota = (TextView) findViewById(R.id.tvTipoRota);
        tvTipoRota.setTypeface(RalewayMedium);
        tvTipoRota.setText("Tipo de Rota: " + auth.getRota().getTipoRota().getDescricao()); //API
        tvDescRota = (TextView) findViewById(R.id.tvDescRota);
        tvDescRota.setTypeface(RalewayMedium);
        tvDescRota.setText("Rota: " + auth.getRota().getDescricao()); //API


        btnNovaVistoria = (Button) headerview.findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setTypeface(RalewayMedium);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               // drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), EstacoesElevatoriasActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnVistoriaRealizada = (Button) headerview.findViewById(R.id.btnVistoriaRealizada);
        btnVistoriaRealizada.setTypeface(RalewayMedium);
        btnVistoriaRealizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                //drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), VistoriaRealizadaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnPlaca = (Button) headerview.findViewById(R.id.btnPlaca);
        btnPlaca.setTypeface(RalewayMedium);
        btnPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               // drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), ValidarPlacaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnSair = (Button) headerview.findViewById(R.id.btnSair);
        btnSair.setTypeface(RalewayMedium);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("SisInspe")
                        .setCancelable(false)
                        // Set Dialog Message
                        .setMessage("Deseja realmente sair?")
                        .setNegativeButton("Cancelar", null) // dismisses by default
                        // Positive button
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            finish();
                            }
                        }).show();
            }
        });

        btnNovaVistoria = (Button) findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setTypeface(RalewayMedium);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), EstacoesElevatoriasActivity.class);
                startActivity(it);
                finish();
            }
        });



        btnVistoriaRealizada = (Button) findViewById(R.id.btnVistoriaRealizada);
        btnVistoriaRealizada.setTypeface(RalewayMedium);
        btnVistoriaRealizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), VistoriaRealizadaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnPlaca = (Button) findViewById(R.id.btnPlaca);
        btnPlaca.setTypeface(RalewayMedium);
        btnPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), ValidarPlacaActivity.class);
                startActivity(it);
                finish();
            }
        });

        if (auth.getOperador().getValidaPlaca().equals("false")) {
            btnPlaca.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//            Intent it = new Intent(getBaseContext(), ValidarPlacaActivity.class);
//            startActivity(it);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
