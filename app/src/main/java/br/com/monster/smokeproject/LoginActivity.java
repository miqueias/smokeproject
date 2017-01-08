package br.com.monster.smokeproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pojo.Auth;
import pojo.Cargo;
import pojo.ConjuntoMotorBomba;
import pojo.Escala;
import pojo.EstacoesElevatorias;
import pojo.Lider;
import pojo.Motivo;
import pojo.Operador;
import pojo.Problemas;
import pojo.ProblemasCheckList;
import pojo.Regional;
import pojo.Rota;
import pojo.TipoRota;
import request.BaseRequester;
import request.Method;
import request.Requester;
import util.Internet;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText etLogin, etSenha;
    private TextView tvOdebretch, tvGestao, tvEstado;
    private Internet internet;
    private Auth auth; //SingleUser

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

        internet = new Internet(this);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setTypeface(RalewayBold);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!internet.verificarConexao()) {
                    //mensagem que precisa conectar a internet

                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String login = "tiago"; //etLogin.getText().toString().toLowerCase().trim();
                            String senha = "4297f44b13955235245b2497399d7a93"; //etSenha.getText().toString().toLowerCase().trim();

                            final JSONObject jsonPut = new JSONObject();

                            try {
                                jsonPut.put("login", login);
                                jsonPut.put("senha", senha);

                                BaseRequester baseRequester = new BaseRequester();
                                baseRequester.setUrl(Requester.API_URL + "/auth");
                                baseRequester.setMethod(Method.POST);
                                baseRequester.setJsonString(jsonPut.toString());

                                String jsonReturn = baseRequester.execute(baseRequester).get();
                                Log.d("API", jsonReturn);

                                JSONObject jsonObjectAuth = new JSONObject(jsonReturn);

                                if (jsonObjectAuth.get("status").toString().equals("ERRO")) {
                                    //informar ao usuario
                                    String mensagemErro = jsonObjectAuth.get("status").toString();
                                    Log.d("API", mensagemErro);
                                } else {
                                    //auth
                                    auth = Auth.getInstance();
                                    auth.setStatus(jsonObjectAuth.get("status").toString());
                                    auth.setMensagem(jsonObjectAuth.get("mensagem").toString());

                                    JSONObject jsonObjectDados = jsonObjectAuth.getJSONObject("dados");

                                    auth.setToken(jsonObjectDados.get("token").toString());

                                    //operador
                                    JSONObject jsonObjectOperador = jsonObjectDados.getJSONObject("operador");
                                    Operador operador = new Operador();
                                    operador.setId(Integer.parseInt(jsonObjectOperador.get("id").toString()));
                                    operador.setNome(jsonObjectOperador.get("nome").toString());
                                    operador.setLogin(jsonObjectOperador.get("login").toString());
                                    operador.setCpf(jsonObjectOperador.get("cpf").toString());
                                    operador.setUsuarioId(Integer.parseInt(jsonObjectOperador.get("usuario_id").toString()));
                                    operador.setCargoId(Integer.parseInt(jsonObjectOperador.get("cargo_id").toString()));
                                    operador.setEscalaId(Integer.parseInt(jsonObjectOperador.get("escala_id").toString()));
                                    operador.setRotaId(Integer.parseInt(jsonObjectOperador.get("rota_id").toString()));
                                    operador.setValidaPlaca(jsonObjectOperador.get("valida_placa").toString());
                                    auth.setOperador(operador);

                                    //lider
                                    JSONObject jsonObjectLider = jsonObjectDados.getJSONObject("lider");
                                    Lider lider = new Lider();
                                    lider.setId(Integer.parseInt(jsonObjectLider.get("id").toString()));
                                    lider.setNome(jsonObjectLider.get("nome").toString());
                                    lider.setEmail(jsonObjectLider.get("email").toString());
                                    lider.setTelefone(jsonObjectLider.get("telefone").toString());
                                    auth.setLider(lider);

                                    //motivos
                                    JSONArray jsonArrayMotivos = jsonObjectDados.getJSONArray("motivos");
                                    ArrayList<Motivo> arrayListMotivos = new ArrayList<Motivo>();

                                    for (int i = 0; i < jsonArrayMotivos.length(); i++) {

                                        JSONObject jsonObjectMotivo = jsonArrayMotivos.getJSONObject(i);

                                        Motivo motivo = new Motivo();
                                        motivo.setId(Integer.parseInt(jsonObjectMotivo.get("id").toString()));
                                        motivo.setDescricao(jsonObjectMotivo.get("descricao").toString());

                                        arrayListMotivos.add(motivo);
                                    }
                                    auth.setMotivoArrayList(arrayListMotivos);

                                    //cargo
                                    JSONObject jsonObjectCargo = jsonObjectDados.getJSONObject("cargo");
                                    Cargo cargo = new Cargo();
                                    cargo.setId(Integer.parseInt(jsonObjectCargo.get("id").toString()));
                                    cargo.setDescricao(jsonObjectCargo.get("descricao").toString());
                                    auth.setCargo(cargo);

                                    //escala
                                    JSONObject jsonObjectEscala = jsonObjectDados.getJSONObject("escala");
                                    Escala escala = new Escala();
                                    escala.setId(Integer.parseInt(jsonObjectEscala.get("id").toString()));
                                    escala.setDescricao(jsonObjectEscala.get("descricao").toString());
                                    auth.setEscala(escala);

                                    //rota
                                    JSONObject jsonObjectRota = jsonObjectDados.getJSONObject("rota");
                                    Rota rota = new Rota();
                                    rota.setId(Integer.parseInt(jsonObjectRota.get("id").toString()));
                                    rota.setDescricao(jsonObjectRota.get("descricao").toString());

                                    //tipo rota
                                    JSONObject jsonObjectTipoRota = jsonObjectRota.getJSONObject("tiporota");
                                    TipoRota tipoRota = new TipoRota();
                                    tipoRota.setId(Integer.parseInt(jsonObjectTipoRota.get("id").toString()));
                                    tipoRota.setDescricao(jsonObjectTipoRota.get("descricao").toString());
                                    rota.setTipoRota(tipoRota);


                                    //estações elevatorias
                                    JSONArray jsonArrayEstacoesElevatorias = jsonObjectRota.getJSONArray("estacoes_elevatorias");
                                    ArrayList<EstacoesElevatorias> estacoesElevatoriasArrayList = new ArrayList<EstacoesElevatorias>();

                                    for (int j = 0; j < jsonArrayEstacoesElevatorias.length(); j++) {

                                        JSONObject jsonObjectEstacoesElevatorias = jsonArrayEstacoesElevatorias.getJSONObject(j);

                                        EstacoesElevatorias estacoesElevatorias = new EstacoesElevatorias();
                                        estacoesElevatorias.setId(Integer.parseInt(jsonObjectEstacoesElevatorias.get("id").toString()));
                                        estacoesElevatorias.setDescricao(jsonObjectEstacoesElevatorias.get("descricao").toString());
                                        estacoesElevatorias.setRegionalId(Integer.parseInt(jsonObjectEstacoesElevatorias.get("regional_id").toString()));

                                        //regional
                                        JSONObject jsonObjectRegional = jsonObjectEstacoesElevatorias.getJSONObject("regional");
                                        Regional regional = new Regional();
                                        regional.setId(Integer.parseInt(jsonObjectRegional.get("id").toString()));
                                        regional.setNome(jsonObjectRegional.get("nome").toString());
                                        estacoesElevatorias.setRegional(regional);

                                        //conjunto motor bomba
                                        JSONArray jsonArrayConjuntoMotorBomba = jsonObjectEstacoesElevatorias.getJSONArray("cmb");
                                        ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaArrayList = new ArrayList<ConjuntoMotorBomba>();

                                        for (int k = 0; k < jsonArrayConjuntoMotorBomba.length(); k++) {

                                            JSONObject jsonObjectConjuntoMotorBomba = jsonArrayConjuntoMotorBomba.getJSONObject(k);

                                            ConjuntoMotorBomba conjuntoMotorBomba = new ConjuntoMotorBomba();
                                            conjuntoMotorBomba.setId(Integer.parseInt(jsonObjectConjuntoMotorBomba.get("id").toString()));
                                            conjuntoMotorBomba.setNumero(jsonObjectConjuntoMotorBomba.get("numero").toString());
                                            conjuntoMotorBomba.setEstacaoElevatoriaId(Integer.parseInt(jsonObjectConjuntoMotorBomba.get("estacao_elevatoria_id").toString()));
                                            conjuntoMotorBombaArrayList.add(conjuntoMotorBomba);

                                        }
                                        estacoesElevatorias.setConjuntoMotorBombaArrayList(conjuntoMotorBombaArrayList);
                                        estacoesElevatoriasArrayList.add(estacoesElevatorias);
                                        rota.setEstacoesElevatoriasArrayList(estacoesElevatoriasArrayList);
                                        auth.setRota(rota);

                                        //problemas
                                        JSONArray jsonArrayProblemas = jsonObjectDados.getJSONArray("problemas");
                                        ArrayList<Problemas> problemasArrayList = new ArrayList<Problemas>();

                                        for (int m = 0; m < jsonArrayProblemas.length(); m++) {

                                            JSONObject jsonObjectProblemas = jsonArrayProblemas.getJSONObject(m);
                                            Problemas problemas = new Problemas();
                                            problemas.setId(Integer.parseInt(jsonObjectProblemas.get("id").toString()));
                                            problemas.setDescricao(jsonObjectProblemas.get("descricao").toString());
                                            problemas.setStatus(Integer.parseInt(jsonObjectProblemas.get("status").toString()));
                                            problemasArrayList.add(problemas);
                                        }
                                        auth.setProblemasArrayList(problemasArrayList);

                                        //problemas chek list
                                        JSONArray jsonArrayProblemasCheckList = jsonObjectDados.getJSONArray("problemachecklists");
                                        ArrayList<ProblemasCheckList> problemasCheckListArrayList = new ArrayList<ProblemasCheckList>();

                                        for (int m = 0; m < jsonArrayProblemasCheckList.length(); m++) {

                                            JSONObject jsonObjectProblemasCkeckList = jsonArrayProblemasCheckList.getJSONObject(m);
                                            ProblemasCheckList problemasCheckList = new ProblemasCheckList();
                                            problemasCheckList.setId(Integer.parseInt(jsonObjectProblemasCkeckList.get("id").toString()));
                                            problemasCheckList.setDescricao(jsonObjectProblemasCkeckList.get("descricao").toString());
                                            problemasCheckListArrayList.add(problemasCheckList);
                                        }
                                        auth.setProblemasCheckListArrayList(problemasCheckListArrayList);

                                        Intent it = new Intent(getBaseContext(), HomeActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}
