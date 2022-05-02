package com.emserh.integrador.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author ronney
 */
public class Util implements Serializable
{
    private static Properties propConfig;
    private static Scanner console;
    
    public static boolean GeraArquivo(String caminhoArquivo,String conteudo) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(caminhoArquivo);
        writer.print(conteudo);
        writer.close();
        
        return true;
    }
    
    public static String GetConfig(String chave) throws IOException, GeneralSecurityException
    {
        if (propConfig == null)
        {
            propConfig = new Properties();
            propConfig.load(new FileInputStream("config.properties"));
        }
        
        String valor = propConfig.getProperty(chave);
        
        if(valor.length() > 2 && valor.substring(0,1).equalsIgnoreCase("%"))
        {
            return Util.Descriptografa(valor.substring(1));
        }
        else
        {
            return valor;
        }
    }
    
    public static void printa(Object obj)
    {
        System.out.print(obj);
    }
    
    public static void printaln(Object obj)
    {
        System.out.println(obj);
    }
    public static String LeComando(String categoria)
    {
        if(console == null)
        {
            console = new Scanner(System.in);
        }
        
        printa(String.format("[%s]comando=> ",categoria));
        return console.nextLine();
    }
    
    
    public static String primeiraMaiuscula(String palavra)
    {
        palavra = palavra.toLowerCase();
        
        if(palavra.length() > 1)
        {
            return (palavra.substring(0, 1).toUpperCase() + palavra.substring(1));
        }
        else
        {
            return "";
        }
    }
    
    public static String Criptografa(String texto) throws GeneralSecurityException, UnsupportedEncodingException
    {
        return Crypto.encrypt(texto);
    }
    
    public static String Descriptografa(String texto) throws GeneralSecurityException, IOException
    {
        return Crypto.decrypt(texto);
    }
    
}
