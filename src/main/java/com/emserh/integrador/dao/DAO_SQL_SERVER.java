package com.emserh.integrador.dao;

import java.io.IOException;
import java.io.Serializable;

public class DAO_SQL_SERVER implements Serializable
{
    private static final long serialVersionUID = 7209921614490717838L;

    private DB db = null;

    public DAO_SQL_SERVER(String servidor,String porta,String banco,String usuario, String senha) throws IOException
    {
        this.db = new DB("com.microsoft.sqlserver.jdbc.SQLServerDriver", ("jdbc:sqlserver://"+servidor+":"+porta+";databaseName=" +banco), usuario,senha);
    }

    public DB getDb()
    {
        return db;
    }    
}
