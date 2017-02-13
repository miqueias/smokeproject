package util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import br.com.monster.smokeproject.LoginActivity;
import pojo.Auth;


public abstract class Util
{
    private static Gson gson;
    private static Context ctxAtual;
    private static Message message = null;
    private static ProgressDialog pd = null;
    public static final String PREFS_NAME = "ESTACOES_TRABALHADAS";
    public static final String VISTORIA_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/VISTORIAS_APP/";
    public static final String VISTORIA_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/VISTORIAS_APP/VISTORIAS_APP.txt";

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

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void AtivaDialogHandler(int Evento, String Titulo, String Mensagem)
    {
        message = new Message();
        message.what = Evento;
        message.obj = Titulo+";"+Mensagem;

        dialogHandler.sendMessage(message);
    }

    final static Handler dialogHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            String texto = (String) msg.obj;
            String[] Queb = texto.split(";");

            if(msg.what == 1)//Dialog
            {
                Util.showMessage(Queb[1], Queb[0], Util.getCtxAtual(), 0);
            }
            else if(msg.what == 4)//Dialog close app
            {
                Util.showMessage(Queb[1], Queb[0], Util.getCtxAtual(), 1);
            }
            else if(msg.what == 9)//Dialog repetir pedidos
            {
                Util.showMessage(Queb[1], Queb[0], Util.getCtxAtual(), 2);
            }
            else if(msg.what == 10)//Dialog repetir pedidos mensagem de item  no carrinho
            {
                Util.showMessage(Queb[1], Queb[0], Util.getCtxAtual(), 3);
            }
            else if(msg.what == 2)//Progress Dialog /* Title;Mensagem */
            {
                Util.startProgressDialog(Queb[0], Queb[1]);
            }
            else if(msg.what == 5)//Fecha Progress Dialog
            {
                Util.stopProgressDialog();
            }
        }
    };

    public static void startProgressDialog(String Title, String Message)
    {
        pd = new ProgressDialog(Util.getCtxAtual());
        pd.setTitle(Title);
        pd.setMessage(Message);
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
    }

    public static void stopProgressDialog()
    {
        pd.dismiss();
    }

    public static void showMessage(String Mensagem, String Titulo, final Context Activity, int acao)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(Activity);
        dialogo.setTitle(Titulo);
        dialogo.setMessage(Mensagem);
        dialogo.setCancelable(false);
        if(acao == 1)
        {
            dialogo.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
                }
            });
        }
        else if(acao == 2)
        {
            dialogo.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //Intent it = new Intent(Activity, PedidoActivity.class);
                    //it.putExtra("id_produto_temp", 999999);
                    //Activity.startActivity(it);
                }
            });
        }
        else if(acao == 3)
        {
            dialogo.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialogo.setPositiveButton("Limpar Carrinho?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                    new AlertDialog.Builder(Activity)
                            .setCancelable(false)
                            .setMessage("Carrinho limpado com sucesso.")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
            });
        }
        else
        {
            dialogo.setNeutralButton("OK",null);
        }


        dialogo.show();
    }

    public  static String getDateNow() {
        //data atual begin
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        //data atual end

        return dateString;
    }

    public static String getTimeNow() {
        //data atual begin
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        sdf = new SimpleDateFormat("hh:mm:ss");
        String timeString = sdf.format(date);
        //data atual end

        return timeString;
    }

    public static void saveFile(String text) throws IOException {
        File diretorio = new File(VISTORIA_FOLDER);

        if (diretorio.exists()) {
            File fileExt = new File(VISTORIA_FOLDER, "VISTORIAS_OFF_LINE.txt");

            if (!fileExt.exists()) {
                fileExt.getParentFile().mkdirs();
            }

            FileOutputStream fosExt = null;
            try {
                text = text + "++++";
                fosExt = new FileOutputStream(fileExt);
                fosExt.write(text.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fosExt.close();
            }

        } else {
            diretorio.mkdirs();
            saveFile(text);
        }
    }

}
