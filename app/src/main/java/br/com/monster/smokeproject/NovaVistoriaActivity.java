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
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Bitmap bitmap;
    private String sPhotoUm, sPhotoDois, sPhotoTres;
    private LinearLayout panel_problema, panel_problema_externo;
    private CardView cvAlerta;
    private String upLoadServerUri = "http://www.academiajedi.com.br/uploads/upload.php";
    private int serverResponseCode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_vistoria);

        auth = Auth.getInstance();
        util.setCtxAtual(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("posicao");
            mode = extras.getString("modo");
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
                Intent it = new Intent(getBaseContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                it.putExtra("imageUri", fileName);
                it.putExtras(extras);
                startActivity(it);
                //ivPhoto1.buildDrawingCache();
                //Bitmap image= ivPhoto1.getDrawingCache();
                // extras.putParcelable("imagebitmap", image);
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
                it.putExtra("imageUri", fileName);
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
                ivPhoto3.buildDrawingCache();
                Bitmap image= ivPhoto3.getDrawingCache();

                Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", image);
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

                        Util.AtivaDialogHandler(2, "", "Registrando Vistoria...");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Vistoria vistoria = new Vistoria();

                                vistoria.setLeituraCelpe(etLeituraCelpe.getText().toString().trim());
                                vistoria.setLeituraCompesa(etLeituraCompesa.getText().toString().trim());
                                vistoria.setCmbsEncontradas(Integer.parseInt(etCmb.getText().toString().trim()));
                                vistoria.setDescricaoProblemas(etDescProblema.getText().toString().trim());
                                vistoria.setFoto1(sPhotoUm);
                                vistoria.setFoto2(sPhotoDois);
                                vistoria.setFoto3(sPhotoTres);

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

                                    /*new AlertDialog.Builder(NovaVistoriaActivity.this)
                                            .setTitle("SisInspe")
                                            .setCancelable(false)
                                            // Set Dialog Message
                                            .setMessage("Vistoria registada com sucesso, obrigado!")
                                            // Positive button
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent it = new Intent(getBaseContext(), HomeActivity.class);
                                                    startActivity(it);
                                                    finish();
                                                }
                                            }).show();*/
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
                        }).start();
                    }
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
                    //Cria o caminho do arquivo no SD Card
                    file = SDCardUtils.getPrivateFile(getBaseContext(), "foto.jpg", Environment.DIRECTORY_PICTURES);
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
                //if (data != null) {
                    //Bundle bundle = data.getExtras();
                    //if (bundle != null) {
                    //    bitmap = (Bitmap) bundle.get("data");
                showImage(file);


                /**
                 * Monster, o mediaStorageDir deveria pegar a foto e colocar ela nessa pasta
                 * /storage/emulated/0/Android/data/br.com.monster.smokeproject/files
                 *
                 * o problema é que ele não faz isso, a pasta até existe, mas, está vazia.
                 * Com isso eu não consigo saber onde a foto ta pra mandar pro servidor
                 *
                 * é basicamente esse o problema, salvar a foto em algum lugar que a gente
                 * possa pegar e mandar pro servidor
                 *
                 */
//                if (ivPhoto1.getDrawable() == null) {
//                            File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
//                                    + "/Android/data/"
//                                    + getApplicationContext().getPackageName()
//                                    + "/files");
//                            if (! mediaStorageDir.exists()){
//                                if (! mediaStorageDir.mkdirs()){
//                                    Toast.makeText(getBaseContext(),
//
//                                            "Error while creaty derectory", Toast.LENGTH_LONG)
//
//                                            .show();
//                                }
//                            }
//                            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
//                            String imageName = "S_"+timeStamp+".jpg";
//                            File out = new File(mediaStorageDir.getPath(), imageName);
//
//
//
//
//                            /*if(!out.exists()) {
//
//                                Toast.makeText(getBaseContext(),
//
//                                        "Error while capturing image", Toast.LENGTH_LONG)
//
//                                        .show();
//
//                                return;
//
//                            }*/
//
//                            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
//                            ivPhoto1.setVisibility(View.VISIBLE);
//                            ivPhoto1.setImageBitmap(mBitmap);
//                            //sPhotoUm = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//                            final String imagepath = out.getAbsolutePath();
//
//                            //Util.AtivaDialogHandler(2, "", "Upload de foto...");
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    uploadFile(imagepath);
//
//                                }
//                            }).start();
//                        }
                    //}
                //}
            } else if (requestCode == 2) {

            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

    public int uploadFile(String sourceFileUri) {


        //String fileName = sourceFileUri;


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            //dialog.dismiss();

            //Log.e("uploadFile", "Source File not exist :"+imagepath);

            runOnUiThread(new Runnable() {
                public void run() {
                    //messageText.setText("Source File not exist :"+ imagepath);
                    //Toast.makeText(this, "Source File not exist :"+ imagepath, Toast.LENGTH_LONG)
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" F:/wamp/wamp/www/uploads";
                            //messageText.setText(msg);
                            Toast.makeText(NovaVistoriaActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                //dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(NovaVistoriaActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(NovaVistoriaActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
            }
            //dialog.dismiss();
            return serverResponseCode;

        } // End else block
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
        //byte[] byteFormat = "MIQUEIAS".getBytes();
        //return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

//    Bitmap myBitmapAgain = decodeBase64(myBase64Image);


private void showImage(File file) {
    if(file != null && file.exists()) {
        Log.d("foto", file.getAbsolutePath());
        if (ivPhoto1.getDrawable() == null) {
            ivPhoto1.setVisibility(View.VISIBLE);
            int w = ivPhoto1.getWidth();
            int h = ivPhoto1.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto1.setImageBitmap(bitmap);
//            sPhotoUm = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
            fileName = String.valueOf(Uri.fromFile(new File("/storage/emulated/0/Android/data/br.com.monster.smokeproject/files/Pictures/foto.jpg")));
            uploadFile(fileName);
        } else if (ivPhoto2.getDrawable() == null) {
            ivPhoto2.setVisibility(View.VISIBLE);
            int w = ivPhoto2.getWidth();
            int h = ivPhoto2.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto2.setImageBitmap(bitmap);
            //sPhotoDois = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
        } else if (ivPhoto3.getDrawable() == null) {
            int w = ivPhoto3.getWidth();
            int h = ivPhoto3.getHeight();
            //Redimensiona a imagem para o tamanho do IV
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w ,h , false);
            ivPhoto3.setImageBitmap(bitmap);
            ivPhoto3.setVisibility(View.VISIBLE);
            //sPhotoTres = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
        }

    }
}


}

