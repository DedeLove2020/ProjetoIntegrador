/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */
package com.emserh.integrador.dao.emserh;

import com.emserh.integrador.entidade.alterdata.Funcionario;
import com.emserh.integrador.entidade.alterdata.Unidade;
import com.emserh.integrador.util.RandomString;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ronneyviana
 */
public class EmserhSistemasDAO extends EmserhDAO
{
    private String cod_hex = new RandomString(20, ThreadLocalRandom.current()).nextString();
    
    public EmserhSistemasDAO() throws IOException, GeneralSecurityException
    {
        super(Util.GetConfig("emserh_schema_sistemas"));
    }
    
     /**
     * Recebe a lista de funcionários do ALTERDATA e atualiza ou insere na base da EMSERH
     * @param funcionarios
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public Integer SalvaFuncionario(List<Funcionario> funcionarios) throws SQLException,IOException, Exception
    {
        String queryInsert = "INSERT INTO TBG_funcionario(FUNC_FK_UNIDADE_COD,FUNC_nome,FUNC_cpf,FUNC_email,FUNC_identidade,FUNC_dt_nascimento,FUNC_dt_admissao,FUNC_dt_demissao,FUNC_dt_criacao,FUNC_dt_alteracao,FUNC_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String queryUpdate = "UPDATE TBG_funcionario SET FUNC_FK_UNIDADE_COD= ?,FUNC_nome= ?,FUNC_cpf= ?,FUNC_email=?,FUNC_identidade= ?,FUNC_dt_nascimento= ?,FUNC_dt_admissao= ? ,FUNC_dt_demissao= ?,FUNC_dt_alteracao= ? WHERE FUNC_id = ?";
        
        try
        {
            int qtd = 0;
            for(Funcionario func : funcionarios)
            {
                Util.printaln("Salvando dados na EMSERH. Funcionario: " + func.getNome());
                
                if(this.getDb().ExecuteCommand(queryUpdate,func.getFkUnidade(),func.getNome() ,func.getCpf(),func.getEmail(),func.getIdentidade(),func.getDtNascimento(),func.getDtAdmissao(),func.getDtDemissao(),new Date(),func.getId()) == 0)
                {
                    this.getDb().ExecuteCommand(queryInsert, func.getFkUnidade(),func.getNome() ,func.getCpf(),func.getEmail(),func.getIdentidade(),func.getDtNascimento(),func.getDtAdmissao(),func.getDtDemissao(),new Date(),new Date(),func.getId());
                }
                qtd++;
            }
            
            return qtd;
        }
        catch(IOException | SQLException ex)
        {
            throw ex;
        }
        finally
        {
            this.getDb().close();
        }
    }
    
    /**
     * Recebe a lista de Unidades do ALTERDATA e atualiza ou insere na base da EMSERH
     * @param unidades
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public Integer SalvaUnidade(List<Unidade> unidades) throws SQLException,IOException, Exception
    {
        String queryInsert = "INSERT INTO TBG_unidade(UNID_cod_alterdata,UNID_descricao,UNID_endereco,UNID_nro,UNID_complemento,UNID_bairro,UNID_cep,UNID_ativo) VALUES(?,?,?,?,?,?,?,?)";
        String queryUpdate = "UPDATE TBG_unidade SET UNID_descricao=?,UNID_endereco=?,UNID_nro=?,UNID_complemento=?,UNID_bairro=?,UNID_cep=? WHERE UNID_cod_alterdata = ?";
        
        try
        {
            int qtd = 0;
            for(Unidade unid : unidades)
            {
                Util.printaln("Salvando dados na EMSERH. Unidade: " + unid.getNome());
                 
                if(this.getDb().ExecuteCommand(queryUpdate,unid.getNome(),unid.getEndereco(),unid.getNumero(),unid.getComplemento(),unid.getBairro(),unid.getCep(),unid.getId()) == 0)
                {
                    this.getDb().ExecuteCommand(queryInsert, unid.getId(),unid.getNome(),unid.getEndereco(),unid.getNumero(),unid.getComplemento(),unid.getBairro(),unid.getCep(),true);
                }
                
                qtd++;
            }
            
            return qtd;
        }
        catch(IOException | SQLException ex)
        {
            throw ex;
        }
        finally
        {
            this.getDb().close();
        }
    }
    
    /**
     * insere histórico
     * @param msg
     * @throws SQLException
     * @throws Exception 
     */
    public void Historico(String msg) throws SQLException, Exception
    {
        String query = "INSERT INTO INTEGRA_historico(data,msg,cod_hex) VALUES(NOW(),?,?)";
        
        try
        {
            this.getDb().ExecuteCommand(query,msg,this.cod_hex);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        finally
        {
            this.getDb().close();
        }
    }
}
