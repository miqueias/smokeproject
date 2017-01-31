package request;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import exception.VistoriaException;
import pojo.Auth;
import pojo.ConjuntoMotorBomba;
import pojo.Vistoria;

/**
 * Created by Miqueias on 1/23/17.
 */

public class VistoriaRequester {

    Auth auth;

    public VistoriaRequester() {

    }

    public void registrarVistoria(Vistoria vistoria, ArrayList<Integer> arrayListCheckList, ArrayList<ConjuntoMotorBomba> arrayListCmb) throws JSONException, InterruptedException, ExecutionException, VistoriaException, UnsupportedEncodingException {

        JSONObject jsonPut = new JSONObject();
        auth =  Auth.getInstance();
        ArrayList<Integer> arrayListProblemas;

        jsonPut.put("token", auth.getToken());
        jsonPut.put("operador_id", auth.getOperador().getId());
        jsonPut.put("leitura_celpe", vistoria.getLeituraCelpe());
        jsonPut.put("leitura_compesa", vistoria.getLeituraCompesa());
        jsonPut.put("cmbs_encontradas", vistoria.getCmbsEncontradas());
        jsonPut.put("descricao_dos_problemas", vistoria.getDescricaoProblemas());

        if (vistoria.getFoto1() != null) {
            if (!vistoria.getFoto1().equals("")) {
                jsonPut.put("foto0", vistoria.getFoto1().replace("=", "#$#$"));
            }
        }

        if (vistoria.getFoto2() != null) {
            if (!vistoria.getFoto2().equals("")) {
                jsonPut.put("foto1", vistoria.getFoto2());
            }
        }

        if (vistoria.getFoto3() != null) {
            if (!vistoria.getFoto3().equals("")) {
                jsonPut.put("foto2", vistoria.getFoto3());
            }
        }

        if (arrayListCheckList.size() > 0) {
            //jsonPut.put("checklists", new JSONArray (arrayListCheckList));

            String checklists = "";
            for (int i = 0; i < arrayListCheckList.size(); i++) {
                checklists = checklists + arrayListCheckList.get(i) + ",";
            }

            if (!checklists.equals("")) {
                checklists = "{" + checklists.substring(0, checklists.length() - 1) + "}";
            }

            jsonPut.put("checklists", checklists);
        }

        if (arrayListCmb.size() > 0) {
            for (int i = 0; i < arrayListCmb.size(); i++) {
                JSONObject jsonObjectCmb = new JSONObject();
                jsonObjectCmb.put("id", arrayListCmb.get(i).getId());
                jsonObjectCmb.put("numero", arrayListCmb.get(i).getNumero());
                jsonObjectCmb.put("estacao_elevatoria", arrayListCmb.get(i).getEstacaoElevatoriaId());
                jsonObjectCmb.put("amperagem", arrayListCmb.get(i).getAmperagem());
                jsonObjectCmb.put("horimetro", arrayListCmb.get(i).getHorimetro());

                arrayListProblemas = new ArrayList<>();

                if (arrayListCmb.get(i).getProblemasArrayList().size() > 0) {
                    for (int j = 0; j < arrayListCmb.get(i).getProblemasArrayList().size(); j++) {
                        arrayListProblemas.add(arrayListCmb.get(i).getProblemasArrayList().get(j).getId());
                    }

                    if (arrayListProblemas.size() > 0) {
                        String problemas = "";
                        for (int j = 0; j < arrayListProblemas.size(); j++) {
                            problemas = problemas + arrayListProblemas.get(j) + ",";
                        }

                        if (!problemas.equals("")) {
                            problemas = "{" + problemas.substring(0, problemas.length() - 1) + "}";
                        }
                        jsonObjectCmb.put("problemas_encontrados", problemas);
                    }
                }
                jsonPut.put("cmbs"+i, jsonObjectCmb.toString());
            }
        }

        BaseRequester baseRequester = new BaseRequester();
        baseRequester.setUrl(Requester.API_URL + "/add_vistoria");
        baseRequester.setMethod(Method.POST);

        baseRequester.setJsonString(jsonPut.toString());

        String jsonReturn = baseRequester.execute(baseRequester).get();
        Log.d("API", jsonReturn);

        JSONObject jsonObjectAuth = new JSONObject(jsonReturn);

        auth.setStatusAPI(jsonObjectAuth.get("status").toString());
        auth.setMensagemErroApi(jsonObjectAuth.get("mensagem").toString());

        if (jsonObjectAuth.get("status").toString().equals("ERRO")) {
            String mensagemErro = jsonObjectAuth.get("mensagem").toString();
            Log.d("API", mensagemErro);
            throw new VistoriaException(mensagemErro);
        }
    }
}
