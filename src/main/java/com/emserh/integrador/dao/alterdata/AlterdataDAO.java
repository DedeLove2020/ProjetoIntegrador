/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */

package com.emserh.integrador.dao.alterdata;

import com.emserh.integrador.dao.DAO_SQL_SERVER;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author ronneyviana
 */

public class AlterdataDAO extends DAO_SQL_SERVER
{
    
    public AlterdataDAO(String banco) throws IOException, GeneralSecurityException 
    {
        super(Util.GetConfig("alterdata_host"), Util.GetConfig("alterdata_porta"), banco, Util.GetConfig("alterdata_usuario"), Util.GetConfig("alterdata_senha"));
    }
    
}
