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

public class Funcionario  implements Serializable
{
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String identidade;
    private String fkUnidade;
    private Date dtNascimento;
    private Date dtAdmissao;
    private Date dtDemissao;
    
    public Funcionario(String id,String nome,String cpf,String email,String identidade,String fkUnidade,Date dtNascimento,Date dtAdmissao,Date dtDemissao)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.identidade = identidade;
        this.fkUnidade = fkUnidade;
        this.dtNascimento = dtNascimento;
        this.dtAdmissao = dtAdmissao;
        this.dtDemissao = dtDemissao;
    }
    
    public Funcionario()
    {
        
    }
    

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }

    public String getIdentidade()
    {
        return identidade;
    }

    public void setIdentidade(String identidade)
    {
        this.identidade = identidade;
    }

    public String getFkUnidade()
    {
        return fkUnidade;
    }

    public void setFkUnidade(String fkUnidade)
    {
        this.fkUnidade = fkUnidade;
    }

    public Date getDtNascimento()
    {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento)
    {
        this.dtNascimento = dtNascimento;
    }

    public Date getDtAdmissao()
    {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao)
    {
        this.dtAdmissao = dtAdmissao;
    }

    public Date getDtDemissao()
    {
        return dtDemissao;
    }

    public void setDtDemissao(Date dtDemissao)
    {
        this.dtDemissao = dtDemissao;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    
}
