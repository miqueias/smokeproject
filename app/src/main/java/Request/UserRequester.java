package request;

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.monster.smokeproject.HomeActivity;
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
import pojo.Vistoria;

/**
 * Created by Miqueias on 1/8/17.
 */

public class UserRequester {

    private Auth auth;

    public UserRequester() {

    }

    public void loadAuth(String login, String senha, String token) throws JSONException, InterruptedException, ExecutionException {

        //login = "tiago"; //etLogin.getText().toString().toLowerCase().trim();
        //senha = "4297f44b13955235245b2497399d7a93"; //etSenha.getText().toString().toLowerCase().trim();

        final JSONObject jsonPut = new JSONObject();
        auth =  Auth.getInstance();

        jsonPut.put("login", login);
        jsonPut.put("senha", senha);

        BaseRequester baseRequester = new BaseRequester();
        baseRequester.setUrl(Requester.API_URL + "/auth");
        baseRequester.setMethod(Method.POST);
        baseRequester.setJsonString(jsonPut.toString());

        String jsonReturn = baseRequester.execute(baseRequester).get();
        Log.d("API", jsonReturn);

        JSONObject jsonObjectAuth = new JSONObject(jsonReturn);

        auth.setStatusAPI(jsonObjectAuth.get("status").toString());
        auth.setMensagemErroApi(jsonObjectAuth.get("mensagem").toString());

        if (jsonObjectAuth.get("status").toString().equals("ERRO")) {
            //informar ao usuario
            String mensagemErro = jsonObjectAuth.get("mensagem").toString();
            Log.d("API", mensagemErro);
        } else {
            //auth
            JSONObject jsonObjectDados = jsonObjectAuth.getJSONObject("dados");

            auth.setToken(jsonObjectDados.get("token").toString());
            auth.setLogin(login);
            auth.setSenha(senha);

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

                //vistoria
                JSONArray jsonArrayVistoria = jsonObjectDados.getJSONArray("vistorias");
                ArrayList<Vistoria> vistoriaArrayList = new ArrayList<Vistoria>();

                for (int n = 0; n < jsonArrayVistoria.length(); n++) {

                    JSONObject jsonObjectVistoria = jsonArrayVistoria.getJSONObject(n);
                    Vistoria vistoria = new Vistoria();
                    vistoria.setId(Integer.parseInt(jsonObjectVistoria.get("id").toString()));
                    vistoria.setLeituraCelpe(jsonObjectVistoria.get("leitura_celpe").toString());
                    vistoria.setLeituraCompesa(jsonObjectVistoria.get("leitura_compesa").toString());
                    vistoria.setCmbsEncontradas(Integer.parseInt(jsonObjectVistoria.get("cmbs_encontradas").toString()));
                    vistoria.setDescricaoProblemas(jsonObjectVistoria.get("descricao_dos_problemas").toString());
                    vistoria.setCreated(jsonObjectVistoria.get("created").toString());
                    vistoria.setEstacaoElevatoriaId(Integer.parseInt(jsonObjectVistoria.get("estacao_elevatoria_id").toString()));
                    vistoria.setOperadorId(Integer.parseInt(jsonObjectVistoria.get("operador_id").toString()));
                    vistoria.setStatus(Integer.parseInt(jsonObjectVistoria.get("status").toString()));
                    vistoria.setOsRealizada(Integer.parseInt(jsonObjectVistoria.get("os_realizada").toString()));
                    vistoria.setNumeroOs(jsonObjectVistoria.get("numero_os").toString());
                    vistoria.setSituacaoProblema(Integer.parseInt(jsonObjectVistoria.get("situacao_problema").toString()));

                    JSONArray jsonArrayVistoriaProblemasChecklist = jsonObjectVistoria.getJSONArray("problemachecklists");
                    ArrayList<ProblemasCheckList> problemasCheckListVistoriaArrayList = new ArrayList<>();

                    for (int c = 0; c < jsonArrayVistoriaProblemasChecklist.length(); c++) {

                        JSONObject jsonObjectProblemasCheckListVistoria = jsonArrayVistoriaProblemasChecklist.getJSONObject(c);

                        ProblemasCheckList problemasCheckList = new ProblemasCheckList();
                        problemasCheckList.setId(Integer.parseInt(jsonObjectProblemasCheckListVistoria.get("id").toString()));
                        problemasCheckList.setDescricao(jsonObjectProblemasCheckListVistoria.get("descricao").toString());
                        problemasCheckListVistoriaArrayList.add(problemasCheckList);
                    }

                    vistoria.setProblemasCheckListArrayList(problemasCheckListVistoriaArrayList);

                    JSONArray jsonArrayVistoriaProblemasMarcadosChecklist = jsonObjectVistoria.getJSONArray("marcadoschecklists");
                    ArrayList<ProblemasCheckList> problemasCheckListMarcadosVistoriaArrayList = new ArrayList<>();

                    for (int w = 0; w < jsonArrayVistoriaProblemasMarcadosChecklist.length(); w++) {
                        JSONObject jsonObjectProblemasMarcadosCheckListVistoria = jsonArrayVistoriaProblemasMarcadosChecklist.getJSONObject(w);

                        ProblemasCheckList problemasCheckList = new ProblemasCheckList();
                        problemasCheckList.setId(Integer.parseInt(jsonObjectProblemasMarcadosCheckListVistoria.get("id").toString()));
                        problemasCheckList.setDescricao(jsonObjectProblemasMarcadosCheckListVistoria.get("descricao").toString());
                        problemasCheckListMarcadosVistoriaArrayList.add(problemasCheckList);
                    }

                    vistoria.setProblemasMarcadosCheckListAdapters(problemasCheckListMarcadosVistoriaArrayList);

                    JSONObject jsonObjectEstacaoVistoria = jsonObjectVistoria.getJSONObject("estacao_elevatoria");
                    EstacoesElevatorias estacoesElevatoriasVistoria = new EstacoesElevatorias();
                    estacoesElevatoriasVistoria.setId(Integer.parseInt(jsonObjectEstacaoVistoria.get("id").toString()));
                    estacoesElevatoriasVistoria.setDescricao(jsonObjectEstacaoVistoria.get("descricao").toString());
                    estacoesElevatoriasVistoria.setRegionalId(Integer.parseInt(jsonObjectEstacaoVistoria.get("regional_id").toString()));
                    estacoesElevatoriasVistoria.setCreated(jsonObjectEstacaoVistoria.get("created").toString());
                    estacoesElevatoriasVistoria.setEndereco(jsonObjectEstacaoVistoria.get("endereco").toString());
                    estacoesElevatoriasVistoria.setNumero(jsonObjectEstacaoVistoria.get("numero").toString());
                    estacoesElevatoriasVistoria.setCidadeId(Integer.parseInt(jsonObjectEstacaoVistoria.get("cidade_id").toString()));
                    estacoesElevatoriasVistoria.setUfId(Integer.parseInt(jsonObjectEstacaoVistoria.get("uf_id").toString()));
                    estacoesElevatoriasVistoria.setStatus(Integer.parseInt(jsonObjectEstacaoVistoria.get("status").toString()));

                    JSONObject jsonObjectRegionalVistoria = jsonObjectEstacaoVistoria.getJSONObject("regional");
                    Regional regionalVistoria = new Regional();
                    regionalVistoria.setId(Integer.parseInt(jsonObjectRegionalVistoria.get("id").toString()));
                    regionalVistoria.setNome(jsonObjectRegionalVistoria.getString("nome"));
                    regionalVistoria.setCreated(jsonObjectRegionalVistoria.getString("created"));
                    estacoesElevatoriasVistoria.setRegional(regionalVistoria);

                    JSONArray jsonArrayCmblVistoria = jsonObjectEstacaoVistoria.getJSONArray("cmb");
                    ArrayList<ConjuntoMotorBomba> conjuntoMotorBombaVistoriaArrayList = new ArrayList<ConjuntoMotorBomba>();

                    for (int x = 0; x < jsonArrayCmblVistoria.length(); x++) {

                        JSONObject jsonObjectCmbVistoria = jsonArrayCmblVistoria.getJSONObject(x);
                        ConjuntoMotorBomba conjuntoMotorBombaVistoria = new ConjuntoMotorBomba();
                        conjuntoMotorBombaVistoria.setId(Integer.parseInt(jsonObjectCmbVistoria.get("id").toString()));
                        conjuntoMotorBombaVistoria.setNumero(jsonObjectCmbVistoria.get("numero").toString());
                        conjuntoMotorBombaVistoria.setEstacaoElevatoriaId(Integer.parseInt(jsonObjectCmbVistoria.get("estacao_elevatoria_id").toString()));
                        conjuntoMotorBombaVistoria.setAmperagem(jsonObjectCmbVistoria.get("amperagem").toString());
                        conjuntoMotorBombaVistoria.setHorimetro(jsonObjectCmbVistoria.get("horimetro").toString());
                        conjuntoMotorBombaVistoriaArrayList.add(conjuntoMotorBombaVistoria);

                        JSONArray jsonArrayProblemasVistoria = jsonObjectCmbVistoria.getJSONArray("problemas");
                        ArrayList<Problemas> problemasVistoriaArrayList = new ArrayList<Problemas>();

                        for (int y = 0; y < jsonArrayProblemasVistoria.length(); y++) {

                            JSONObject jsonObjectProblemasVistoria = jsonArrayProblemasVistoria.getJSONObject(y);
                            Problemas problemasVistoria = new Problemas();
                            problemasVistoria.setId(Integer.parseInt(jsonObjectProblemasVistoria.get("id").toString()));
                            problemasVistoria.setDescricao(jsonObjectProblemasVistoria.get("descricao").toString());
                            problemasVistoria.setStatus(Integer.parseInt(jsonObjectProblemasVistoria.get("status").toString()));
                            problemasVistoriaArrayList.add(problemasVistoria);
                        }
                        conjuntoMotorBombaVistoria.setProblemasArrayList(problemasVistoriaArrayList);

                        JSONArray jsonArrayProblemasNaoMarcadosVistoria = jsonObjectCmbVistoria.getJSONArray("problemas_nao_marcados");
                        ArrayList<Problemas> problemasNaoMarcadosVistoriaArrayList = new ArrayList<Problemas>();

                        for (int u = 0; u < jsonArrayProblemasNaoMarcadosVistoria.length(); u++) {

                            JSONObject jsonObjectProblemasNaoMarcadosVistoria = jsonArrayProblemasNaoMarcadosVistoria.getJSONObject(u);
                            Problemas problemasVistoria = new Problemas();
                            problemasVistoria.setId(Integer.parseInt(jsonObjectProblemasNaoMarcadosVistoria.get("id").toString()));
                            problemasVistoria.setDescricao(jsonObjectProblemasNaoMarcadosVistoria.get("descricao").toString());
                            problemasVistoria.setStatus(Integer.parseInt(jsonObjectProblemasNaoMarcadosVistoria.get("status").toString()));
                            problemasNaoMarcadosVistoriaArrayList.add(problemasVistoria);
                        }
                        conjuntoMotorBombaVistoria.setProblemasNaoMarcadosArrayList(problemasNaoMarcadosVistoriaArrayList);
                    }

                    estacoesElevatoriasVistoria.setConjuntoMotorBombaArrayList(conjuntoMotorBombaVistoriaArrayList);
                    vistoria.setEstacoesElevatorias(estacoesElevatoriasVistoria);
                    vistoriaArrayList.add(vistoria);
                }

                auth.setVistoriasArrayList(vistoriaArrayList);

            }
        }
    }



}
