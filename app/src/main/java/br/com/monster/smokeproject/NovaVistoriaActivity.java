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
import adapter.NovaVistoriaAdapter;
import adapter.PhotoGridViewAdapter;
import adapter.VistoriaAdapter;
import model.ImageItem;
import model.Lista;
import util.DividerItemDecoration;
import util.RecyclerItemClickListener;

public class NovaVistoriaActivity extends AppCompatActivity {

    private GridView gridView;
    private PhotoGridViewAdapter gridAdapter;

    private RecyclerView rvBombas;
    private LinearLayoutManager llm;
    private List<Lista> lista;
    private Button btnAddFoto;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_vistoria);

        //Fontes.ttf
        final Typeface RalewayBold = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Bold.ttf");
        final Typeface RalewayMedium = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Medium.ttf");
        Typeface RalewayRegular = Typeface.createFromAsset(getResources().getAssets(), "Raleway-Regular.ttf");
        Typeface Odebrecht = Typeface.createFromAsset(getResources().getAssets(), "odebrecht-slab-webfont.ttf");

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nova vistoria");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvVistoria = (TextView) findViewById(R.id.tvVistoria);
        tvVistoria.setTypeface(RalewayBold);
        tvData = (TextView) findViewById(R.id.tvData);
        tvData.setTypeface(RalewayMedium);
        tvAs = (TextView) findViewById(R.id.tvAs);
        tvAs.setTypeface(RalewayMedium);
        tvHora = (TextView) findViewById(R.id.tvHora);
        tvHora.setTypeface(RalewayMedium);
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
        tvEstacao = (TextView) findViewById(R.id.tvEstacao);
        tvEstacao.setTypeface(RalewayMedium);
        tvNomeEstacao = (TextView) findViewById(R.id.tvNomeEstacao);
        tvNomeEstacao.setTypeface(RalewayMedium);
        tvRegional = (TextView) findViewById(R.id.tvRegional);
        tvRegional.setTypeface(RalewayMedium);
        tvNomeRegional = (TextView) findViewById(R.id.tvNomeRegional);
        tvNomeRegional.setTypeface(RalewayMedium);
        tvCmb = (TextView) findViewById(R.id.tvCmb);
        tvCmb.setTypeface(RalewayMedium);
        tvQtdCmb = (TextView) findViewById(R.id.tvQtdCmb);
        tvQtdCmb.setTypeface(RalewayMedium);

        etLeituraCelpe = (EditText) findViewById(R.id.etLeituraCelpe);
        etLeituraCelpe.setTypeface(RalewayMedium);
        etLeituraCompesa = (EditText) findViewById(R.id.etLeituraCompesa);
        etLeituraCompesa.setTypeface(RalewayMedium);
        etCmb = (EditText) findViewById(R.id.etCmb);
        etCmb.setTypeface(RalewayMedium);
        etDescProblema = (EditText) findViewById(R.id.etDescProblema);
        etDescProblema.setTypeface(RalewayMedium);


        btnAddFoto = (Button) findViewById(R.id.btnAddFoto);
        btnAddFoto.setTypeface(RalewayBold);
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        gridView = (GridView) findViewById(R.id.gvPhotos);
        gridAdapter = new PhotoGridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
//                                                //Create intent
                                                Intent intent = new Intent(NovaVistoriaActivity.this, DetailsActivity.class);
                                                intent.putExtra("title", item.getTitle());
                                                intent.putExtra("image", item.getImage());
//
//                                                //Start details activity
                                                startActivity(intent);

                                            }
                                        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                Toast.makeText(NovaVistoriaActivity.this, "ONLONG CLICK " + position,
                        Toast.LENGTH_LONG).show();
                //gridAdapter.imageItems.remove(position);
                gridAdapter.notifyDataSetChanged();
                //set the image as wallpaper
                return true;
            }
        });

        rvBombas = (RecyclerView) findViewById(R.id.rvBombas);
        llm = new LinearLayoutManager(this);
        rvBombas.setLayoutManager(llm);
        rvBombas.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvBombas.addItemDecoration(itemDecoration);
        BombasAdapter adapter = new BombasAdapter(lista);
        rvBombas.setAdapter(adapter);

        rvBombas.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvBombas ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(NovaVistoriaActivity.this, "Posição " + position,
                                Toast.LENGTH_LONG).show();

                        LayoutInflater li = LayoutInflater.from(NovaVistoriaActivity.this);
                        View promptsView = li.inflate(R.layout.dialog_bombas, null);
                        final AlertDialog alertDialog = new AlertDialog.Builder(NovaVistoriaActivity.this).create();
                        alertDialog.setView(promptsView);
                        alertDialog.show();

                        //CUSTOM DIALOG
                        etHorimetro = (EditText) alertDialog.findViewById(R.id.etHorimetro);
                        etHorimetro.setTypeface(RalewayMedium);
                        etHorimetro.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (etHorimetro.length() >= 1) {
                                    btnConfirmar.setEnabled(true);
                                } else {
                                    btnConfirmar.setEnabled(false);
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }
                        });

                        etAmperagem = (EditText) alertDialog.findViewById(R.id.etAmperagem);
                        etHorimetro.setTypeface(RalewayMedium);
                        etAmperagem.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (etAmperagem.length() >= 1) {
                                    btnConfirmar.setEnabled(true);
                                } else {
                                    btnConfirmar.setEnabled(false);
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }
                        });

                        btnConfirmar = (Button) alertDialog.findViewById(R.id.btnConfirmar);
                        btnCancelar = (Button) alertDialog.findViewById(R.id.btnCancelar);
                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        //cbNaoPrecisaTroco = (CheckBox) alertDialog.findViewById(R.id.cbNaoPrecisoTroco);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
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

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
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

                    //viewImage.setImageBitmap(bitmap);

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
                //viewImage.setImageBitmap(thumbnail);
            }
        }
    }
}
