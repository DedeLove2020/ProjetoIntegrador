package com.emserh.integrador.dao;

import java.io.IOException;
import java.io.Serializable;

public class DAO_MYSQL implements Serializable
{
    private static final long serialVersionUID = 7209921614490717838L;

    private DB db = null;

    public DAO_MYSQL(String servidor,String porta,String banco,String usuario, String senha) throws IOException
    {
        this.db = new DB("com.mysql.cj.jdbc.Driver", ("jdbc:mysql://"+servidor+":"+porta+"/" +banco+"?useTimezone=true&serverTimezone=UTC"), usuario,senha);
    }

    public DB getDb()
    {
        return db;
    }
}
