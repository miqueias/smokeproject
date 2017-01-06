package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fragment.NovaVistoriaFragment;
import fragment.VistoriaRealizadaFragment;
import util.ViewPagerAdapter;

public class VistoriaActivity extends AppCompatActivity {

    private String posicao = "";

    private TabLayout tabLayout;
    public static ViewPager viewPager;
    private int[] tabIcons = {
            android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_dialog_alert};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistoria);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vistoria");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("posicao", posicao);

        NovaVistoriaFragment novaVistoriaFragment = new NovaVistoriaFragment();
        novaVistoriaFragment.setArguments(bundle);

        VistoriaRealizadaFragment vistoriaRealizadaFragment = new VistoriaRealizadaFragment();
        vistoriaRealizadaFragment.setArguments(bundle);


        adapter.addFragment(novaVistoriaFragment, "NOVA VISTORIA");
        adapter.addFragment(vistoriaRealizadaFragment, "VISTORIA/APOIO");
        viewPager.setAdapter(adapter);
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
