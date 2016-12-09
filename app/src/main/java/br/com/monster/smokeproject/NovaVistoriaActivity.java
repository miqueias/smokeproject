package br.com.monster.smokeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class NovaVistoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_vistoria);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nova vistoria");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
