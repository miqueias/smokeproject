package br.com.monster.smokeproject;

import android.content.Intent;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnVistoriaRealizada;
    private Button btnPlaca;
    private Button btnNovaVistoria;
    private Button btnSair;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Início");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView profilename = (TextView) headerview.findViewById(R.id.tvNomeBV);
        profilename.setText("Antonio Neto");
        navigationView.setNavigationItemSelectedListener(this);

        btnNovaVistoria = (Button) headerview.findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               // drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnVistoriaRealizada = (Button) headerview.findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                //drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnPlaca = (Button) headerview.findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               // drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnSair = (Button) headerview.findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                // drawer.closeDrawer(GravityCompat.START);
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
            }
        });

        btnNovaVistoria = (Button) findViewById(R.id.btnNovaVistoria);
        btnNovaVistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), VistoriaActivity.class);
                startActivity(it);
                finish();
            }
        });



        btnVistoriaRealizada = (Button) findViewById(R.id.btnVistoriaRealizada);
        btnVistoriaRealizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), VistoriaRealizadaActivity.class);
                startActivity(it);
//                finish();
            }
        });

        btnPlaca = (Button) findViewById(R.id.btnPlaca);
        btnPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), ValidarPlacaActivity.class);
                startActivity(it);
                finish();
            }
        });
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
