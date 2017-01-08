package util;

import android.content.Context;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pojo.Auth;


public abstract class Util
{
    private static Gson gson;
    private static Context ctxAtual;

    public static String ChangeStringTosha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static void armazenar_dados_do_usuario_em_json_no_sharedPreferences(Context context, String chave, Auth auth) {
        gson = new Gson();
        String retorno = "";

        try
        {
            if (auth != null)
            {
                retorno = gson.toJson(auth);
            }
            PrefUser.setString(context, chave, retorno);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Auth retornar_dados_user_armazenado_no_sharedPreferences(String chave_shared, Context context)
    {
        String dadosDoShared = PrefUser.getString(context, chave_shared);

        Auth user_retorno = new Auth();
        gson = new Gson();

        try
        {
            if(dadosDoShared.trim() != null || dadosDoShared.trim() != "")
            {
                user_retorno = gson.fromJson(dadosDoShared, Auth.class);
            }

            return user_retorno;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void setCtxAtual(Context ctx)
    {
        ctxAtual = ctx;
    }

    public static Context getCtxAtual()
    {
        return ctxAtual;
    }
}
