package util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by asoares on 25/04/16.
 */
public class PrefUser {

    /**
     * Chaves
     *  endereco_escolhido_nome_numero //retorna o endereco atual do usuario
     *  endereco_escolhido //retorno o id do endereco atual do usuario
     *  latLon //retorna a latitude e longitude do endereco atual
     *  token //retorno o token do usuario
     *  profilePicture //retorno a imagem do usuario caso o login tenha sido por facebook
     */

    public static final String PREF_ID = "Odebretch";

    public static void setString (Context context, String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString (Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        String valor = preferences.getString(key, "");
        return valor;
    }
}
