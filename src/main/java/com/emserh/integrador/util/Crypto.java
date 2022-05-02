/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */
package com.emserh.integrador.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author ronneyviana
 */
@SuppressWarnings("restriction")
public class Crypto
{
    private static final char[] PASSWORD = ("EmS#3rH@2019!").toCharArray();
    private static final byte[] SALT = 
    { 
        (byte) 0xde, (byte) 0x30, (byte) 0x10, (byte) 0x66, (byte) 0xde, (byte) 0x37,(byte) 0x16, (byte) 0x12, 
    };

    public static String encrypt(String texto) throws GeneralSecurityException, UnsupportedEncodingException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));

        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));

        return base64Encode(pbeCipher.doFinal(texto.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes)
    {
        return new String(Base64.encodeBase64(bytes));
    }

    public static String decrypt(String texto) throws GeneralSecurityException, IOException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));

        return new String(pbeCipher.doFinal(base64Decode(texto)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException
    {
        return Base64.decodeBase64(property);
    }
}

