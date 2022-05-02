/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */
package com.emserh.integrador.entidade.alterdata;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ronneyviana
 */
public class Receita implements Serializable
{
    private int ano;
    private int mes;
    private String cliente;
    private String cnpj;
    private String contrato;
    private Date dtBaixa;
    private double valor;
    
    public Receita(int ano,int mes,String cliente,String cnpj,String contrato,Date dtBaixa,Double valor)
    {
        this.ano = ano;
        this.mes = mes;
        this.cliente = cliente;
        this.cnpj = cnpj;
        this.contrato = contrato;
        this.dtBaixa = dtBaixa;
        this.valor = valor;
    }
    
    public Receita()
    {
        
    }

    public int getAno()
    {
        return ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public int getMes()
    {
        return mes;
    }

    public void setMes(int mes)
    {
        this.mes = mes;
    }

    public String getCliente()
    {
        return cliente;
    }

    public void setCliente(String cliente)
    {
        this.cliente = cliente;
    }

    public String getCnpj()
    {
        return cnpj;
    }

    public void setCnpj(String cnpj)
    {
        this.cnpj = cnpj;
    }

    public String getContrato()
    {
        return contrato;
    }

    public void setContrato(String contrato)
    {
        this.contrato = contrato;
    }

    public Date getDtBaixa()
    {
        return dtBaixa;
    }

    public void setDtBaixa(Date dtBaixa)
    {
        this.dtBaixa = dtBaixa;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public String getId_cliente()
    {
       return this.cnpj.replaceAll("\\.", "").replaceAll("/","").replaceAll("-","");
    }

}
