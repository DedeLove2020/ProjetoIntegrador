/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */

package com.emserh.integrador.dao.alterdata;

import com.emserh.integrador.entidade.alterdata.Funcionario;
import com.emserh.integrador.entidade.alterdata.Unidade;
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

public class AlterDataPackDAO extends AlterdataDAO
{
    public AlterDataPackDAO() throws IOException, GeneralSecurityException 
    {
        super(Util.GetConfig("alterdata_schema_pack"));
    }
    
    /**
     * Retorna lista de unidades do AlterData filtrando pelo codigo da empresa no arquivo config
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public List<Unidade> getUnidades() throws SQLException, IOException, Exception
    {
        String query = "SELECT iddepartamento, nmdepartamento,nmendereco,nrendereco,nmcomplemento,nmbairro,nmcidade,cduf,nrcep FROM wdp.depto WHERE idempresa = ?";
        List<Unidade> unidades = new ArrayList<Unidade>();
        
        try
        {
            ResultSet rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"));
            
            while(rs.next())
            {
                Util.printaln("Lendo dados do AlterData. Unidade: " + rs.getString("nmdepartamento"));
                
                unidades.add
                (
                    new Unidade
                    (
                        rs.getString("iddepartamento"), 
                        rs.getString("nmdepartamento"),
                        rs.getString("nmendereco"),
                        rs.getString("nrendereco"), 
                        rs.getString("nmcomplemento"),
                        rs.getString("nmbairro"),
                        rs.getString("nmcidade"),
                        rs.getString("cduf"),
                        rs.getString("nrcep")
                    )
                );
            }
            
            rs.close();
            
            return unidades;
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
     * Retorna lista de funcionários do AlterData filtrando pelos Departamentos correspondente ao codigo da empresa no arquivo config
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws Exception 
     */
    public List<Funcionario> getFuncionarios() throws SQLException, IOException, Exception
    {
        String query = "SELECT F.IdFuncionario,F.NmFuncionario,F.NrCPF,F.UsuariosWMail,F.NrIdentidade,F.DtNascimento,F.DtAdmissao,F.DtDemissao,F.IdDepartamento "
                     + "FROM wdp.F00001 F "
                     + "INNER JOIN wdp.depto D ON D.iddepartamento = F.IdDepartamento "
                     + "WHERE D.idempresa = ?";
        
        List<Funcionario> funcionarios  = new ArrayList<Funcionario>();
        
        try
        {       
            ResultSet rs = this.getDb().ExecuteQuery(query,Util.GetConfig("alterdata_cod_empresa"));
            
            while(rs.next())
            {
                Util.printaln("Lendo dados do AlterData. Funcionario: " + rs.getString("NmFuncionario"));
                 
                funcionarios.add
                (
                    new Funcionario
                    (
                        rs.getString("IdFuncionario"), 
                        rs.getString("NmFuncionario"),
                        rs.getString("NrCPF"),
                        rs.getString("UsuariosWMail"),
                        rs.getString("NrIdentidade"),
                        rs.getString("IdDepartamento"),
                        rs.getDate("DtNascimento"),
                        rs.getDate("DtAdmissao"),
                        rs.getDate("DtDemissao")
                    )
                );
            }
            
            rs.close();
            
            return funcionarios;
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
