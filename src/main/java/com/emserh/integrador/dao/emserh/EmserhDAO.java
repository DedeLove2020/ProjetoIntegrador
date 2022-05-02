/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */

package com.emserh.integrador.dao.emserh;

import com.emserh.integrador.dao.DAO_MYSQL;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author ronneyviana
 */

public class EmserhDAO extends DAO_MYSQL
{
    
    public EmserhDAO(String banco) throws IOException, GeneralSecurityException 
    {
        super
        (
            Util.GetConfig("emserh_host"), 
            Util.GetConfig("emserh_porta"), 
            banco, 
            Util.GetConfig("emserh_usuario"), 
            Util.GetConfig("emserh_senha")
        );
    }
}
