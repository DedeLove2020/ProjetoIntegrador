/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */

package com.emserh.integrador.dao.alterdata;

import com.emserh.integrador.entidade.alterdata.Despesa;
import com.emserh.integrador.entidade.alterdata.Receita;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronneyviana
 */

public class AlterDataBimerDAO extends AlterdataDAO
{
    public AlterDataBimerDAO() throws IOException, GeneralSecurityException 
    {
        super(Util.GetConfig("alterdata_schema_bimer"));
    }
    
    /**
     * Busca no AlterData os lançamentos de receitas da EMSERH para serem mostrados no portal da transparencia
     * @param ano
     * @param mes
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public List<Receita> getReceitasTransparencia(Integer ano, Integer mes) throws SQLException, IOException, Exception
    {
         List<Receita> receitas = new ArrayList<Receita>();
        String query = "SELECT DATEPART(YEAR,IB.DtBaixa) ANO, "
                    + "	   DATEPART(MONTH,IB.DtBaixa) MES,"
                    + "	   P.NmPessoa NOME,"
                    + "	   P.CdCPF_CGC CNPJ,"
                    + "	   CR.NrTitulo CONTRATO,"
                    + "	   IB.DtBaixa DATA_BAIXA,"
                    + "	   SUM(IB.VlBaixaItem) VALOR "
                    + "FROM BIMER_EMSERH.dbo.AReceber CR "
                    + "	INNER JOIN BIMER_EMSERH.dbo.AReceberItem IR "
                    + "		ON IR.IdAReceber = CR.IdAReceber "
                    + "	INNER JOIN BIMER_EMSERH.dbo.AReceberItemBaixa IB "
                    + "		ON IB.IdAReceberItem = IR.IdAReceberItem "
                    //+ "	INNER JOIN BIMER_EMSERH.dbo.MovimentoContabil MC "
                    //+ "		ON MC.IdEntidadeOrigem = IB.IdAReceberItemBaixa "
                    + "	INNER JOIN BIMER_EMSERH.dbo.Pessoa P "
                    + "		ON P.IdPessoa = CR.IdPessoa "
                    + "WHERE CR.CdEmpresa = ? "
                    //filtra somente os recursos vindos da SES e da SEGEP
                    //+ "AND MC.IdContaCredora in(234798,2087102) "
                    //----------------------------------------------------------------------------------------
                    //ALTERAÇÃO FEITA PARA PEGAR PELO ID DA ENTIDADE CREDORA /* Ronney Viana 14-06-2019 */
                    + "AND P.IdPessoa in('00A000006O','00A00001VD','00A000026M','00A000026N','00A00002DB') "
                    //+ "AND MC.DtExclusao IS NULL "
                    + (ano != null && mes != null ? "AND DATEPART(YEAR,IB.DtBaixa) = ?  AND DATEPART(MONTH,IB.DtBaixa) = ? " : "")
                    + "GROUP BY P.NmPessoa,P.CdCPF_CGC,CR.NrTitulo,IB.DtBaixa "
                    + "ORDER BY IB.DtBaixa";
        
        try
        {
            ResultSet rs;
            
            if(ano != null && mes != null)
            {
                rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"),ano,mes);
            }
            else
            {
                rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"));
            }
            
            while(rs.next())
            {
                Util.printaln(String.format("Lendo dados do AlterData. Receita : ano=%s mes=%s baixa=%s contrato=%s",rs.getInt("ANO"),rs.getInt("MES"),rs.getDate("DATA_BAIXA"),rs.getString("CONTRATO")));
                
                receitas.add
                (
                    new Receita
                    (
                        rs.getInt("ANO"), 
                        rs.getInt("MES"),
                        rs.getString("NOME"),
                        rs.getString("CNPJ"), 
                        rs.getString("CONTRATO"),
                        rs.getDate("DATA_BAIXA"),
                        rs.getDouble("VALOR")
                    )
                );
            }
            
            rs.close();
            
            return receitas;
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
     * Busca no AlterData os lançamentos de despesas da EMSERH para serem mostrados no portal da transparencia
     * @param ano
     * @param mes
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public List<Despesa> getDespesasTransparencia(Integer ano, Integer mes) throws SQLException, IOException, Exception
    {
        List<Despesa> despesas  = new ArrayList<Despesa>();
        
        String query = "SELECT DATEPART(YEAR,CPIB.DtBaixa) ANO,"
                    + "	   DATEPART(MONTH,CPIB.DtBaixa) MES,"
                    + "	   P.NmPessoa NOME,"
                    + "	   P.CdCPF_CGC CNPJ,"
                    + "	   CP.NrTitulo NF,"
                    + "	   CPIB.DtBaixa DATA_BAIXA,"
                    + "    ISNULL(N.NmNaturezaLancamento,'Várias Naturezas') NATUREZA,"
                    + "    ISNULL(N.IdNaturezaLancamento,'0') ID_NATUREZA,"
                    + "	   ROUND(SUM(CPIB.VlBaixa),2) VALOR "
                    + "FROM BIMER_EMSERH.dbo.APagar CP	"
                    + "	INNER JOIN BIMER_EMSERH.dbo.APagarBaixa CPIB "
                    + "		ON CPIB.IdAPagar = CP.IdAPagar "
                    + "	INNER JOIN BIMER_EMSERH.dbo.Pessoa P "
                    + "		ON P.IdPessoa = CP.IdPessoa "
                    + "	LEFT JOIN"
                    + "	("
                    + "		SELECT CPI.IdAPagar,MAX(CPI.IdNaturezaLancamento)ID_NATUREZA,COUNT(*) QTD "
                    + "		FROM APagarItem CPI "
                    + "		GROUP BY CPI.IdAPagar "
                    + "	)CPI "
                    + "		ON CPI.IdAPagar = CP.IdAPagar "
                    + "	LEFT JOIN BIMER_EMSERH.dbo.NaturezaLancamento N "
                    + "		ON N.IdNaturezaLancamento = ISNULL(CP.IdNaturezaLancamento,CPI.ID_NATUREZA) "
                    + "WHERE CP.CdEmpresa = ? "
                    + "AND CP.StPrevisao = 'N' "
                    + "AND CP.StBloqueado = 'N' "
                    + "AND CP.DtVencimento IS NOT NULL "
                    + (ano != null && mes != null ? "AND DATEPART(YEAR,CPIB.DtBaixa) = ? AND DATEPART(MONTH,CPIB.DtBaixa) = ? " : "")
                    + "AND CP.DtExclusao IS NULL "
                    + "GROUP BY P.NmPessoa,P.CdCPF_CGC,CP.NrTitulo,CPIB.DtBaixa,N.NmNaturezaLancamento,N.IdNaturezaLancamento";
        
        try
        {   
            ResultSet rs;
                    
            if(ano != null && mes != null)
            {
               rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"),ano,mes);
            }
            else
            {
                rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"));
            }
             
            
            while(rs.next())
            {
                Util.printaln(String.format("Lendo dados do AlterData. Despesa: ano=%s mes=%s baixa=%s nota=%s",rs.getInt("ANO"),rs.getInt("MES"),rs.getDate("DATA_BAIXA"),rs.getString("NF")));
                 
                despesas.add
                (
                    new Despesa
                    (
                        rs.getInt("ANO"), 
                        rs.getInt("MES"),
                        rs.getString("NOME"),
                        rs.getString("CNPJ"),
                        rs.getString("NF"),
                        rs.getDate("DATA_BAIXA"),
                        rs.getDouble("VALOR"),
                        rs.getString("NATUREZA"),
                        rs.getString("ID_NATUREZA")
                    )
                );
            }
            
            rs.close();
            
            return despesas;
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
}
