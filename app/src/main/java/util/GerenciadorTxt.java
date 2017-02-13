package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Miqueias on 2/12/17.
 */

public class GerenciadorTxt {

    public void escreverArquivoTxt(String texto, File arquivoTxt) {
        try {
            FileWriter fw = new FileWriter(arquivoTxt);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(texto);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String lerArquivoTxt(File arquivoTxt) {
        String conteudoArquivoTxt = "";
        try {
            FileReader fr = new FileReader(arquivoTxt);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";

            while((linha = br.readLine()) != null){
                conteudoArquivoTxt += linha;
            }

            fr.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conteudoArquivoTxt;
    }


    public String deletarArquivoTxt(File arquivoTxt) {
        String confirmacaoDeleteArquivoTxt = "";

        if(verificarExisteArquivoTxt(arquivoTxt)) {
            arquivoTxt.delete();
            confirmacaoDeleteArquivoTxt = "Arquivo deletado com sucesso!";
        }
        else {
            confirmacaoDeleteArquivoTxt = "Erro ao deletar o arquivo!";
        }

        return confirmacaoDeleteArquivoTxt;
    }


    public boolean verificarExisteArquivoTxt(File arquivoTxt) {
        boolean existe = false;

        if(arquivoTxt.exists()) {
            existe = true;
        }
        else {
            existe = false;
        }

        return existe;
    }

    public boolean verificarExisteDiretorioTxt(File diretorioTxt) {
        boolean existe = false;

        try {
            if (diretorioTxt.exists()) {
                existe = true;
            }
            else {
                existe = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    public void criarArquivoTxt(File arquivoTxt) {
        try {
            if(!verificarExisteArquivoTxt(arquivoTxt)) {
                try {
                    arquivoTxt.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criarDireotorioTxt(File diretorioTxt) {
        try {
            if (!verificarExisteDiretorioTxt(diretorioTxt)) {
                try {
                    diretorioTxt.mkdirs();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
