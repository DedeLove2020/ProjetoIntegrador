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
public class Despesa implements Serializable
{
    private Integer ano;
    private Integer mes;
    private String nome;
    private String cnpj;
    private String notaFiscal;
    private String natureza;
    private String id_natureza;
    private Date dtBaixa;
    private Double valor;
    
    
    public Despesa(Integer ano,Integer mes,String nome,String cnpj,String notaFiscal,Date dtBaixa,Double valor,String natureza, String id_natureza)
    {
        this.ano = ano;
        this.mes = mes;
        this.nome = nome;
        this.cnpj = cnpj;
        this.notaFiscal = notaFiscal;
        this.dtBaixa = dtBaixa;
        this.valor = valor;
        this.natureza = natureza;
        this.id_natureza = id_natureza;
    }
    
    public Despesa()
    {
        
    }

    public Integer getAno()
    {
        return ano;
    }

    public void setAno(Integer ano)
    {
        this.ano = ano;
    }

    public Integer getMes()
    {
        return mes;
    }

    public void setMes(Integer mes)
    {
        this.mes = mes;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCnpj()
    {
        return cnpj;
    }

    public void setCnpj(String cnpj)
    {
        this.cnpj = cnpj;
    }

    public String getNotaFiscal()
    {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal)
    {
        this.notaFiscal = notaFiscal;
    }

    public Date getDtBaixa()
    {
        return dtBaixa;
    }

    public void setDtBaixa(Date dtBaixa)
    {
        this.dtBaixa = dtBaixa;
    }

    public Double getValor()
    {
        return valor;
    }

    public void setValor(Double valor)
    {
        this.valor = valor;
    }

    public String getNatureza()
    {
        return natureza;
    }

    public void setNatureza(String natureza)
    {
        this.natureza = natureza;
    }

    public String getId_natureza()
    {
        return id_natureza;
    }

    public void setId_natureza(String id_natureza)
    {
        this.id_natureza = id_natureza;
    }
  
    
    
}
