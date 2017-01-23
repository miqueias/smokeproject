package br.com.monster.smokeproject;

import android.app.AlertDialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import adapter.BombasAdapter;
import adapter.ChecklistAdapter;
import adapter.NovaVistoriaAdapter;
import adapter.PhotoGridViewAdapter;
import adapter.ProblemasCheckListAdapter;
import adapter.VistoriaAdapter;
import model.ImageItem;
import model.Lista;
import pojo.Auth;
import pojo.Problemas;
import pojo.ProblemasCheckList;
import util.DividerItemDecoration;
import util.RecyclerItemClickListener;
import util.Util;

public class NovaVistoriaActivity extends AppCompatActivity {

    private PhotoGridViewAdapter gridAdapter;

    private RecyclerView rvBombas, rvChecklist, rvPhotos;
    private LinearLayoutManager llm;
    private List<Lista> lista;
    public static Button btnAddFoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;


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
        }

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
        }

        tvVistoria = (TextView) findViewById(R.id.tvVistoria);
        tvVistoria.setTypeface(RalewayBold);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvData.setText(Util.getTimeNow());
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
                        Toast.makeText(NovaVistoriaActivity.this, "Posição " + position,
                                Toast.LENGTH_LONG).show();
//                        ImageItem item = (ImageItem) parent.getItemAtPosition(position);
////                      //Create intent
//                        Intent intent = new Intent(NovaVistoriaActivity.this, DetailsActivity.class);
//                        intent.putExtra("image", item.getImage());
////
////                      //Start details activity
//                        startActivity(intent);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(NovaVistoriaActivity.this, "ONLONG CLICK " + position,
                                Toast.LENGTH_LONG).show();
                        //gridAdapter.imageItems.remove(position);
                        //gridAdapter.notifyDataSetChanged();

                    }
                })
        );

        rvChecklist = (RecyclerView) findViewById(R.id.rvChecklist);
        llm = new LinearLayoutManager(this);
        rvChecklist.setLayoutManager(llm);
        rvChecklist.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecorationDois = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvChecklist.addItemDecoration(itemDecorationDois);
        ProblemasCheckListAdapter adapterDois = new ProblemasCheckListAdapter(auth.getVistoriasArrayList().get(position).getProblemasCheckListArrayList(), mode, this);
        rvChecklist.setAdapter(adapterDois);

//        rvChecklist.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, rvBombas ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
////                        Toast.makeText(NovaVistoriaActivity.this, "Posição " + position,
////                                Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

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
//                        Toast.makeText(NovaVistoriaActivity.this, "Posição " + position,
//                                Toast.LENGTH_LONG).show();
                        Intent it = new Intent(getBaseContext(), MotorBombaActivity.class);
                        it.putExtra("posicao", position_cmb);
                        it.putExtra("modo", "view");
                        it.putExtra("vistoria_id", position);
                        startActivity(it);
//                        finish();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setTypeface(RalewayBold);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        if (mode.equals("view")) {
            btnSalvar.setVisibility(View.INVISIBLE);
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

        final CharSequence[] options = { "Camera", "Galeria"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NovaVistoriaActivity.this);
            builder.setTitle("Seleciona uma foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Galeria"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancelar")) {
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
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    //PhotoGridViewAdapter.ivPhoto.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("image from gallery....", picturePath+"");
                PhotoGridViewAdapter.ivPhoto.setImageBitmap(thumbnail);
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

}

