package br.com.monster.smokeproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import adapter.BombasAdapter;
import adapter.PhotoGridViewAdapter;
import adapter.ProblemasCheckListAdapter;
import dto.DataTransferObject;
import exception.VistoriaException;
import model.Lista;
import pojo.Auth;
import pojo.ConjuntoMotorBomba;
import pojo.ProblemasCheckList;
import pojo.Vistoria;
import request.UserRequester;
import request.VistoriaRequester;
import util.DividerItemDecoration;
import util.ImageResizeUtils;
import util.MyFileContentProvider;
import util.RecyclerItemClickListener;
import util.SDCardUtils;
import util.Util;

public class NovaVistoriaActivity extends AppCompatActivity {

    private PhotoGridViewAdapter gridAdapter;

    private RecyclerView rvBombas, rvChecklist, rvPhotos;
    private LinearLayoutManager llm;
    private List<Lista> lista;
    public static Button btnAddFoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File file;
    private String fileName;


    //CUSTOM DIALOG
    private EditText etHorimetro;
    private EditText etAmperagem;
    private Button btnConfirmar, btnCancelar;

    private TextView tvVistoria, tvData, tvAs, tvHora, tvSupervisor, tvNomeSupervisor,
            tvEstacao, tvNomeEstacao, tvRegional, tvNomeRegional, tvCmb, tvQtdCmb, tvChecklist,
            tvGaleriaImagem, tvTitulo;
    private EditText etLeituraCelpe, etLeituraCompesa, etCmb, etDescProblema;
    private Button btnSalvar;
    private  TextView tvAlertaSub;
    private Auth auth;
    private Util util;
    private int position;
    private String mode;
    private ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3, ivPhoto4, ivPhoto5, ivPhoto6, ivPhoto7, ivPhoto8, ivPhoto9;
    private Bitmap bitmap;
    private String sPhotoUm, sPhotoDois, sPhotoTres, sPhotoQuatro, sPhotoCinco, sPhotoSeis, sPhotoSete, sPhotoOito, sPhotoNove;
    private LinearLayout panel_problema, panel_problema_externo;
    private CardView cvAlerta;
    private ArrayList<String> arrayListFotos;
    String timeStamp;
    String imageName;
    int estacaoElevatoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_vistoria);

        auth = Auth.getInstance();
        Util.setCtxAtual(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("posicao");
            mode = extras.getString("modo");
            estacaoElevatoria = extras.getInt("estacao_elevatoria");
        }

        //Fontes.ttf
        final Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        final Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mode.equals("view")) {
            toolbar.setTitle("Vistoria");
        } else {
            toolbar.setTitle("Nova Vistoria");
            conjuntoMotorBombaArrayList = new ArrayList<>();
            arrayListFotos = new ArrayList<>();
        }

        panel_problema_externo = (LinearLayout) findViewById(R.id.panel_problema_externo);
        panel_problema = (LinearLayout) findViewById(R.id.panel_problema);
        cvAlerta = (CardView) findViewById(R.id.cvAlerta);

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvAlertaSub = (TextView) findViewById(R.id.tvAlertaSub);
        tvAlertaSub.setTypeface(RalewayBold);
        tvAlertaSub.setText("Nenhum problema encontrado");

        if (mode.equals("view")) {
            int qtdProbelmasDestacados = auth.getVistoriasArrayList().get(position).getProblemasCheckListArrayList().size();

            if (qtdProbelmasDestacados == 0) {
                tvAlertaSub.setText("Nenhum problema encontrado");
            } else if (qtdProbelmasDestacados == 1) {
                tvAlertaSub.setText("1 problema encontrado");
            } else {
                tvAlertaSub.setText(qtdProbelmasDestacados + " problemas encontrados");
            }
        } else {
            panel_problema.setVisibility(View.GONE);
            panel_problema_externo.setVisibility(View.GONE);
            cvAlerta.setVisibility(View.GONE);
            tvAlertaSub.setText("");
        }

        tvVistoria = (TextView) findViewById(R.id.tvVistoria);
        tvVistoria.setTypeface(RalewayBold);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvData.setText(Util.getDateNow());
        tvAs = (TextView) findViewById(R.id.tvAs);
        tvAs.setTypeface(RalewayMedium);
        tvHora = (TextView) findViewById(R.id.tvHora);
        tvHora.setTypeface(RalewayMedium);
        tvHora.setText(Util.getTimeNow());
        tvChecklist = (TextView) findViewById(R.id.tvChecklist);
        tvChecklist.setTypeface(RalewayBold);
        tvGaleriaImagem = (TextView) findViewById(R.id.tvGaleriaImagem);
        tvGaleriaImagem.setTypeface(RalewayBold);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvTitulo.setTypeface(RalewayBold);

        tvSupervisor = (TextView) findViewById(R.id.tvSupervisor);
        tvSupervisor.setTypeface(RalewayMedium);
        tvNomeSupervisor = (TextView) findViewById(R.id.tvNomeSupervisor);
        tvNomeSupervisor.setTypeface(RalewayMedium);
        tvNomeSupervisor.setText(auth.getLider().getNome());
        tvEstacao = (TextView) findViewById(R.id.tvEstacao);
        tvEstacao.setTypeface(RalewayMedium);
        tvNomeEstacao = (TextView) findViewById(R.id.tvNomeEstacao);
        tvNomeEstacao.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            tvNomeEstacao.setText(auth.getVistoriasArrayList().get(position).getEstacoesElevatorias().getDescricao());
        } else {
            tvNomeEstacao.setText(auth.getRota().getEstacoesElevatoriasArrayList().get(position).getDescricao());
        }

        tvRegional = (TextView) findViewById(R.id.tvRegional);
        tvRegional.setTypeface(RalewayMedium);
        tvNomeRegional = (TextView) findViewById(R.id.tvNomeRegional);
        tvNomeRegional.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            tvNomeRegional.setText(auth.getVistoriasArrayList().get(position).getEstacoesElevatorias().getRegional().getNome());
        } else {
            tvNomeRegional.setText(auth.getRota().getEstacoesElevatoriasArrayList().get(position).getRegional().getNome());
        }

        tvCmb = (TextView) findViewById(R.id.tvCmb);
        tvCmb.setTypeface(RalewayMedium);
        tvQtdCmb = (TextView) findViewById(R.id.tvQtdCmb);
        tvQtdCmb.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            tvQtdCmb.setText(String.valueOf(auth.getVistoriasArrayList().get(position).getEstacoesElevatorias().getConjuntoMotorBombaArrayList().size()));
        } else {
            tvQtdCmb.setText(String.valueOf(auth.getRota().getEstacoesElevatoriasArrayList().get(position).getConjuntoMotorBombaArrayList().size()));
        }

        etLeituraCelpe = (EditText) findViewById(R.id.etLeituraCelpe);
        etLeituraCelpe.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            etLeituraCelpe.setText(auth.getVistoriasArrayList().get(position).getLeituraCelpe());
            etLeituraCelpe.setEnabled(false);
        }

        etLeituraCompesa = (EditText) findViewById(R.id.etLeituraCompesa);
        etLeituraCompesa.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            etLeituraCompesa.setText(auth.getVistoriasArrayList().get(position).getLeituraCompesa());
            etLeituraCompesa.setEnabled(false);
        }

        etCmb = (EditText) findViewById(R.id.etCmb);
        etCmb.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            etCmb.setText(String.valueOf(auth.getVistoriasArrayList().get(position).getCmbsEncontradas()));
            etCmb.setEnabled(false);
        }

        etDescProblema = (EditText) findViewById(R.id.etDescProblema);
        etDescProblema.setTypeface(RalewayMedium);

        if (mode.equals("view")) {
            etDescProblema.setText(auth.getVistoriasArrayList().get(position).getDescricaoProblemas());
            etDescProblema.setEnabled(false);
        }

        btnAddFoto = (Button) findViewById(R.id.btnAddFoto);
        btnAddFoto.setTypeface(RalewayBold);
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        rvPhotos = (RecyclerView) findViewById(R.id.rvPhotos);
        llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPhotos.setLayoutManager(llm);
        RecyclerView.ItemDecoration itemDecorationTres = new
                DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
        rvPhotos.addItemDecoration(itemDecorationTres);
        PhotoGridViewAdapter adapterTres = new PhotoGridViewAdapter(lista);
        rvPhotos.setAdapter(adapterTres);

        rvPhotos.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvPhotos ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        selectImage();
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        ivPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getResources().getResourceName((Integer)ivPhoto1.getTag());

                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();

                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("1")) {
                            break;
                        }
                    }
                }

                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto1.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto1.setImageResource(0);
                                    ivPhoto1.setVisibility(View.GONE);
                                    sPhotoUm = "";

                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("1")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto2 = (ImageView) findViewById(R.id.ivPhoto2);
        ivPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("2")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto2.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto2.setImageResource(0);
                                    ivPhoto2.setVisibility(View.GONE);
                                    sPhotoDois = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("2")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto3 = (ImageView) findViewById(R.id.ivPhoto3);
        ivPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                //ivPhoto3.buildDrawingCache();
                //Bitmap image= ivPhoto3.getDrawingCache();

                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("3")) {
                            break;
                        }
                    }
                }

                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto3.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto3.setImageResource(0);
                                    ivPhoto3.setVisibility(View.GONE);
                                    sPhotoTres = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("3")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();
                }
                return false;
            }
        });
        ivPhoto4 = (ImageView) findViewById(R.id.ivPhoto4);
        ivPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("4")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto4.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto4.setImageResource(0);
                                    ivPhoto4.setVisibility(View.GONE);
                                    sPhotoQuatro = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("4")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto5 = (ImageView) findViewById(R.id.ivPhoto5);
        ivPhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("5")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto5.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto5.setImageResource(0);
                                    ivPhoto5.setVisibility(View.GONE);
                                    sPhotoCinco = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("5")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto6 = (ImageView) findViewById(R.id.ivPhoto6);
        ivPhoto6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("6")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto6.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto6.setImageResource(0);
                                    ivPhoto6.setVisibility(View.GONE);
                                    sPhotoSeis = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("6")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto7 = (ImageView) findViewById(R.id.ivPhoto7);
        ivPhoto7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("7")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto7.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto7.setImageResource(0);
                                    ivPhoto7.setVisibility(View.GONE);
                                    sPhotoSete = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("7")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto8 = (ImageView) findViewById(R.id.ivPhoto8);
        ivPhoto8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("8")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto8.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto8.setImageResource(0);
                                    ivPhoto8.setVisibility(View.GONE);
                                    sPhotoOito = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("8")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });
        ivPhoto9 = (ImageView) findViewById(R.id.ivPhoto9);
        ivPhoto9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                String fileNameAux = "";
                if (arrayListFotos.size() > 0) {
                    for (int i = 0; i < arrayListFotos.size(); i++) {
                        fileNameAux = arrayListFotos.get(i);

                        if (String.valueOf(fileNameAux.charAt(76)).equals("9")) {
                            break;
                        }
                    }
                }
                it.putExtra("imageUri", fileNameAux);
                it.putExtras(extras);
                startActivity(it);
            }
        });
        ivPhoto9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ivPhoto9.getDrawable() != null){
                    new AlertDialog.Builder(NovaVistoriaActivity.this)
                            .setTitle("SisInspe")
                            .setCancelable(false)
                            // Set Dialog Message
                            .setMessage("Deseja realmente apagar foto?")
                            .setNegativeButton("Cancelar", null) // dismisses by default
                            // Positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ivPhoto9.setImageResource(0);
                                    ivPhoto9.setVisibility(View.GONE);
                                    sPhotoNove = "";
                                    String fileNameAux = "";
                                    if (arrayListFotos.size() > 0) {
                                        for (int i = 0; i < arrayListFotos.size(); i++) {
                                            fileNameAux = arrayListFotos.get(i);

                                            if (String.valueOf(fileNameAux.charAt(76)).equals("9")) {
                                                arrayListFotos.remove(i);
                                            }
                                        }
                                    }
                                }
                            }).show();

                }
                return false;
            }
        });

        rvChecklist = (RecyclerView) findViewById(R.id.rvChecklist);
        llm = new LinearLayoutManager(this);
        rvChecklist.setLayoutManager(llm);
        rvChecklist.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecorationDois = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvChecklist.addItemDecoration(itemDecorationDois);
        ProblemasCheckListAdapter adapterDois;
        if (mode.equals("view")) {
            ArrayList<ProblemasCheckList> problemasCheckLists = new ArrayList<>();

            for (int i = 0; i < auth.getVistoriasArrayList().get(position).getProblemasMarcadosCheckListAdapters().size(); i++) {
                ProblemasCheckList problemasCheckList = new ProblemasCheckList();
                problemasCheckList.setId(auth.getVistoriasArrayList().get(position).getProblemasMarcadosCheckListAdapters().get(i).getId());
                problemasCheckList.setDescricao(auth.getVistoriasArrayList().get(position).getProblemasMarcadosCheckListAdapters().get(i).getDescricao());
                problemasCheckList.setChecked(true);
                problemasCheckLists.add(problemasCheckList);
            }

            for (int i = 0; i < auth.getVistoriasArrayList().get(position).getProblemasCheckListArrayList().size(); i++) {
                ProblemasCheckList problemasCheckList = new ProblemasCheckList();
                problemasCheckList.setId(auth.getVistoriasArrayList().get(position).getProblemasCheckListArrayList().get(i).getId());
                problemasCheckList.setDescricao(auth.getVistoriasArrayList().get(position).getProblemasCheckListArrayList().get(i).getDescricao());
                problemasCheckList.setChecked(false);
                problemasCheckLists.add(problemasCheckList);
            }
            adapterDois = new ProblemasCheckListAdapter(problemasCheckLists, mode, this);
        } else {
            adapterDois = new ProblemasCheckListAdapter(auth.getProblemasCheckListArrayList(), mode, this);
        }

        rvChecklist.setAdapter(adapterDois);

        rvBombas = (RecyclerView) findViewById(R.id.rvBombas);
        llm = new LinearLayoutManager(this);
        rvBombas.setLayoutManager(llm);
        rvBombas.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvBombas.addItemDecoration(itemDecoration);

        BombasAdapter adapter;

        if (mode.equals("view")) {
            adapter = new BombasAdapter(auth.getVistoriasArrayList().get(position).getEstacoesElevatorias().getConjuntoMotorBombaArrayList(), mode, this);
        } else {
            adapter = new BombasAdapter(auth.getRota().getEstacoesElevatoriasArrayList().get(position).getConjuntoMotorBombaArrayList(), mode, this);
        }

        rvBombas.setAdapter(adapter);

        rvBombas.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvBombas ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position_cmb) {
                        Intent it = new Intent(getBaseContext(), MotorBombaActivity.class);
                        it.putExtra("posicao", position_cmb);
                        it.putExtra("estacao_elevatoria", estacaoElevatoria);
                        if (mode.equals("new")) {
                            it.putExtra("id_cmb", auth.getRota().getEstacoesElevatoriasArrayList().get(position).getConjuntoMotorBombaArrayList().get(position_cmb).getId());
                        }
                        it.putExtra("modo", mode);
                        it.putExtra("vistoria_id", position);
                        startActivity(it);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayBold);

        if (mode.equals("view")) {
            btnSalvar.setVisibility(View.INVISIBLE);
        } else {
            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Util.AtivaDialogHandler(2, "", "Registrando Vistoria...");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (etLeituraCelpe.getText().toString().trim().equals("")) {
                                    Util.AtivaDialogHandler(5, "", "");
                                    Util.AtivaDialogHandler(1, "SisInspe", "Leitura celpe não informada!");
                                } else if (etLeituraCompesa.getText().toString().trim().equals("")) {
                                    Util.AtivaDialogHandler(5, "", "");
                                    Util.AtivaDialogHandler(1, "SisInspe", "Leitura compesa não informada!");
                                } else if (etCmb.getText().toString().trim().equals("")) {
                                    Util.AtivaDialogHandler(5, "", "");
                                    Util.AtivaDialogHandler(1, "SisInspe", "Quantidade de CMB`s não informada!");
                                } else {

                                    Vistoria vistoria = new Vistoria();

                                    vistoria.setLeituraCelpe(etLeituraCelpe.getText().toString().trim());
                                    vistoria.setLeituraCompesa(etLeituraCompesa.getText().toString().trim());
                                    vistoria.setCmbsEncontradas(Integer.parseInt(etCmb.getText().toString().trim()));
                                    vistoria.setDescricaoProblemas(etDescProblema.getText().toString().trim());
                                    vistoria.setFoto1(sPhotoUm);
                                    vistoria.setFoto2(sPhotoDois);
                                    vistoria.setFoto3(sPhotoTres);
                                    vistoria.setFoto3(sPhotoQuatro);
                                    vistoria.setFoto3(sPhotoCinco);
                                    vistoria.setFoto3(sPhotoSeis);
                                    vistoria.setFoto3(sPhotoSete);
                                    vistoria.setFoto3(sPhotoOito);
                                    vistoria.setFoto3(sPhotoNove);

                                    ProblemasCheckListAdapter problemasCheckListAdapter = (ProblemasCheckListAdapter) rvChecklist.getAdapter();
                                    ArrayList<Integer> checklists = problemasCheckListAdapter.getArrayListCheck();

                                    VistoriaRequester vistoriaRequester = new VistoriaRequester();

                                    try {
                                        UserRequester userRequester = new UserRequester();
                                        userRequester.loadAuth(auth.getLogin(), auth.getSenha(), "");

                                        vistoriaRequester.registrarVistoria(vistoria, checklists, conjuntoMotorBombaArrayList);
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", "Vistoria registada com sucesso, obrigado!");
                                        Intent it = new Intent(getBaseContext(), HomeActivity.class);
                                        startActivity(it);
                                        finish();
                                    } catch (JSONException e) {
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", e.getMessage());
                                    } catch (InterruptedException e) {
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", e.getMessage());
                                    } catch (ExecutionException e) {
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", e.getMessage());
                                    } catch (VistoriaException e) {
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", e.getMessage());
                                    } catch (UnsupportedEncodingException e) {
                                        Util.AtivaDialogHandler(5, "", "");
                                        Util.AtivaDialogHandler(1, "SisInspe", e.getMessage());
                                    }
                                }
                            }
                        }).start();
                    }
            });
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mode.equals("new")) {
            if (DataTransferObject.getInstance().getDto() != null) {
                if (conjuntoMotorBombaArrayList.size() > 0) {
                    ConjuntoMotorBomba conjuntoMotorBomba = (ConjuntoMotorBomba) DataTransferObject.getInstance().getDto();
                    for (int i = 0; i < conjuntoMotorBombaArrayList.size(); i++) {
                        if (conjuntoMotorBombaArrayList.get(i).getId() == conjuntoMotorBomba.getId()) {
                            conjuntoMotorBombaArrayList.remove(i);
                        }
                    }
                }
                conjuntoMotorBombaArrayList.add((ConjuntoMotorBomba) DataTransferObject.getInstance().getDto());

                BombasAdapter bombasAdapter = (BombasAdapter) rvBombas.getAdapter();

                if (conjuntoMotorBombaArrayList.size() > 0) {
                    ArrayList<Integer> arrayListConjuntoMotorBomba = new ArrayList<>();
                    for (int i = 0; i < conjuntoMotorBombaArrayList.size(); i++) {
                        arrayListConjuntoMotorBomba.add(conjuntoMotorBombaArrayList.get(i).getId());
                    }
                    bombasAdapter.setArrayListSelect(arrayListConjuntoMotorBomba);
                    rvBombas.setAdapter(bombasAdapter);
                }
                DataTransferObject.getInstance().setDto(null);
            } else {
                DataTransferObject.getInstance().setDto(null);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(it);
                finish();
                return true;
            case R.id.action_home:
                Intent iti = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(iti);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectImage() {

        final CharSequence[] options = { "Abrir Câmera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NovaVistoriaActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Abrir Câmera")) {
                    timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
                    imageName = "S_"+timeStamp+".jpg";

                    if (ivPhoto1.getDrawable() == null && (ivPhoto2.getDrawable() != null && ivPhoto3.getDrawable() != null)) {
                        imageName = "1_" + imageName;
                    }

                    if (ivPhoto2.getDrawable() == null && (ivPhoto1.getDrawable() != null && ivPhoto3.getDrawable() != null)) {
                        imageName = "2_" + imageName;
                    }

                    if (ivPhoto3.getDrawable() == null && (ivPhoto1.getDrawable() != null && ivPhoto2.getDrawable() != null)) {
                        imageName = "3_" + imageName;
                    }

                    if (ivPhoto3.getDrawable() == null && ivPhoto1.getDrawable() == null && ivPhoto2.getDrawable() == null) {
                        imageName = "1_" + imageName;
                    }


                    //Cria o caminho do arquivo no SD Card
                    file = SDCardUtils.getPrivateFile(getBaseContext(), imageName, Environment.DIRECTORY_PICTURES);
                    //Chama itent informando o arquivo para salvar a foto
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(i,1);
                } else if (options[item].equals("Galeria")) {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                arrayListFotos.add("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName);
                showImage(file, imageName);
            } else if (requestCode == 2) {

            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homee, menu);
        return true;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        byte[] byteFormat = byteArrayOS.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
}

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


private void showImage(File file, String imageName) {
    if(file != null && file.exists()) {

        Log.d("foto", file.getAbsolutePath());
        if (ivPhoto1.getDrawable() == null) {
            ivPhoto1.setVisibility(View.VISIBLE);
            int w = ivPhoto1.getWidth();
            int h = ivPhoto1.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto1.setImageBitmap(bitmap);
            sPhotoUm = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
            //uploadFile(fileName);
        } else if (ivPhoto2.getDrawable() == null) {
            ivPhoto2.setVisibility(View.VISIBLE);
            int w = ivPhoto2.getWidth();
            int h = ivPhoto2.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto2.setImageBitmap(bitmap);
            sPhotoDois = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto3.getDrawable() == null) {
            int w = ivPhoto3.getWidth();
            int h = ivPhoto3.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto3.setImageBitmap(bitmap);
            ivPhoto3.setVisibility(View.VISIBLE);
            sPhotoTres = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto4.getDrawable() == null) {
            int w = ivPhoto4.getWidth();
            int h = ivPhoto4.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto4.setImageBitmap(bitmap);
            ivPhoto4.setVisibility(View.VISIBLE);
            sPhotoQuatro = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto5.getDrawable() == null) {
            int w = ivPhoto5.getWidth();
            int h = ivPhoto5.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto5.setImageBitmap(bitmap);
            ivPhoto5.setVisibility(View.VISIBLE);
            sPhotoCinco = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto6.getDrawable() == null) {
            int w = ivPhoto6.getWidth();
            int h = ivPhoto6.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto6.setImageBitmap(bitmap);
            ivPhoto6.setVisibility(View.VISIBLE);
            sPhotoSeis = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto7.getDrawable() == null) {
            int w = ivPhoto7.getWidth();
            int h = ivPhoto7.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto7.setImageBitmap(bitmap);
            ivPhoto7.setVisibility(View.VISIBLE);
            sPhotoSete = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto8.getDrawable() == null) {
            int w = ivPhoto8.getWidth();
            int h = ivPhoto8.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto8.setImageBitmap(bitmap);
            ivPhoto8.setVisibility(View.VISIBLE);
            sPhotoOito = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        } else if (ivPhoto9.getDrawable() == null) {
            int w = ivPhoto9.getWidth();
            int h = ivPhoto9.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto9.setImageBitmap(bitmap);
            ivPhoto9.setVisibility(View.VISIBLE);
            sPhotoNove = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/"+imageName)));
        }


    }
}


}

