/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */
package com.emserh.integrador.dao.emserh;

import com.emserh.integrador.dao.DAO_MYSQL;
import com.emserh.integrador.entidade.alterdata.Despesa;
import com.emserh.integrador.entidade.alterdata.Receita;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ronneyviana
 */
public class PortalTranspDAO extends DAO_MYSQL
{
    public PortalTranspDAO() throws IOException, GeneralSecurityException 
    {
        super
        (
            Util.GetConfig("emserh_portal_transp_host"),
            Util.GetConfig("emserh_portal_transp_porta"),
            Util.GetConfig("emserh_portal_transp_schema"),
            Util.GetConfig("emserh_portal_transp_usuario"),
            Util.GetConfig("emserh_portal_transp_senha")
        );
    }
    
    /**
     * Atualiza os dados de receitas do portal da transparência
     * @param ano
     * @param mes
     * @param receitas
     * @throws SQLException
     * @throws Exception 
     */
    public Integer AtualizarReceitas(Integer ano, Integer mes, List<Receita> receitas) throws SQLException, Exception
    {
        String queryDelete = String.format("DELETE FROM RECEITA %s",(ano != null && mes != null ? "WHERE REC_ano = ? AND REC_mes = ?":""));
        String queryInsert = "INSERT INTO RECEITA(REC_ano,REC_mes,REC_cliente,REC_cnpj,REC_contrato,REC_dataBaixa,REC_valor,REC_cod_cliente) VALUES(?,?,?,?,?,?,?,?)";

        try
        {
            //exclui os registros
            if(ano != null && mes != null)
            {
                this.getDb().ExecuteCommand(queryDelete,ano,mes);
            }
            else
            {
              this.getDb().ExecuteCommand(queryDelete); 
            }
            
            int qtd = 0;
            
            //insere os novos dados
            for(Receita receita : receitas)
            {
                Util.printaln(String.format("Inserindo Receita na EMSERH : ano=%s mes=%s baixa=%s cliente=%s",receita.getAno(),receita.getMes(),receita.getDtBaixa(),receita.getCliente()));
                
               qtd += this.getDb().ExecuteCommand(queryInsert, receita.getAno(),receita.getMes(),receita.getCliente(),receita.getCnpj(),receita.getContrato(),receita.getDtBaixa(),receita.getValor(),receita.getId_cliente());
            }
            
            Util.printaln("--------------------------- TOTAL DE RECEITAS INSERIDAS: " + String.valueOf(qtd));
            
            return qtd;
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
    
    /**
     * Atualiza os dados de despesas do portal da transparência
     * @param ano
     * @param mes
     * @param despesas
     * @throws SQLException
     * @throws Exception 
     */
    public Integer AtualizarDespesas(Integer ano, Integer mes, List<Despesa> despesas) throws SQLException, Exception
    {
        String queryDelete = String.format("DELETE FROM DESPESA %s",(ano != null && mes != null ? "WHERE DESP_ano = ? AND DESP_mes = ?":""));
        String queryInsert = "INSERT INTO DESPESA(DESP_ano,DESP_mes,DESP_fornecedor,DESP_cnpj,DESP_notaFiscal,DESP_dataBaixa,DESP_valor,DESP_natureza,DESP_id_natureza) VALUES(?,?,?,?,?,?,?,?,?)";

        try
        {
            //exclui os registros
            if(ano != null && mes != null)
            {
                this.getDb().ExecuteCommand(queryDelete,ano,mes);
            }
            else
            {
              this.getDb().ExecuteCommand(queryDelete); 
            }
            
            int qtd = 0;
            
            //insere os novos dados
            for(Despesa despesa : despesas)
            {
                Util.printaln(String.format("Inserindo Despesa na EMSERH : ano=%s mes=%s baixa=%s cliente=%s",despesa.getAno(),despesa.getMes(),despesa.getDtBaixa(),despesa.getNome()));
                
               qtd += this.getDb().ExecuteCommand(queryInsert, despesa.getAno(),despesa.getMes(),despesa.getNome(),despesa.getCnpj(),despesa.getNotaFiscal(),despesa.getDtBaixa(),despesa.getValor(),despesa.getNatureza(),despesa.getId_natureza());
            }
            
            Util.printaln("--------------------------- TOTAL DE DESPESAS INSERIDAS: " + String.valueOf(qtd));
            
            return qtd;
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
