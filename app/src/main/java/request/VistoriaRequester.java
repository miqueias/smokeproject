package request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pojo.Auth;
import pojo.Vistoria;

/**
 * Created by Miqueias on 1/23/17.
 */

public class VistoriaRequester {

    Auth auth;

    public VistoriaRequester() {

    }

    public void registrarVistoria(Vistoria vistoria) throws JSONException, InterruptedException, ExecutionException {

        final JSONObject jsonPut = new JSONObject();
        auth =  Auth.getInstance();

        jsonPut.put("token", auth.getToken());
        jsonPut.put("operador_id", auth.getOperador().getId());
        jsonPut.put("leitura_celpe", vistoria.getLeituraCelpe());
        

    }



}
