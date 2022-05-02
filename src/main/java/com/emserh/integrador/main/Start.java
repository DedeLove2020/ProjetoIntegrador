 /*
 *
 *  Projeto Integrador EMSERH
 *
 * 2019 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */

package com.emserh.integrador.main;

import com.emserh.integrador.dao.alterdata.AlterDataBimerDAO;
import com.emserh.integrador.dao.alterdata.AlterDataPackDAO;
import com.emserh.integrador.dao.emserh.EmserhSistemasDAO;
import com.emserh.integrador.dao.emserh.PortalTranspDAO;
import com.emserh.integrador.util.Util;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author ronneyviana
 */

public class Start 
{
    public static void main(String[] args)
    {         
        Util.printaln("----------------------------------------- Integrador EMSERH v1.0 -----------------------------------------");
        
        
        try   
        {
           // Util.printaln(args[0]);
            
            if(args.length > 0)
            {
                if(args[0].equalsIgnoreCase("cripto"))
                {
                    boolean loop = true;
                    
                    Util.printaln("---------------------> MÓDULO CRIPTOGRAFIA <---------------------");
                    
                    while(loop)
                    {
                        String comando = Util.LeComando("criptografia").toLowerCase();
                                
                        switch(comando)
                        {
                            case "encode":
                                Util.printaln(Util.Criptografa(Util.LeComando("encode")));
                                break;
                            case "decode":
                                Util.printaln(Util.Descriptografa(Util.LeComando("decode")));
                                break;
                            case "exit":
                                loop = false;
                                break;
                            default:
                                Util.printaln("ajuda: \nencode 'criptografa o texto digitado'\ndecode 'descriptografa a chave digitada'\nexit 'encerra a execução do integrador");
                                break;
                               
                        }
                    }
                }
            }
            else
            {
                Util.printaln("----------------------------------------- Iniciando Integração de Unidades -----------------------------------------");
                
                //declara o objeto base
                EmserhSistemasDAO emserh = new EmserhSistemasDAO();
                AlterDataPackDAO alterdata = new AlterDataPackDAO();
                        
                emserh.Historico("Iniciado integração EMSERH X AlterData");
                
                emserh.Historico("Iniciando unidades");
                
                //Integra Unidades
                int qtdUnidades = emserh.SalvaUnidade(alterdata.getUnidades());
               
                emserh.Historico(String.valueOf(qtdUnidades) + " unidades integradas");

                emserh.Historico("Iniciando funcionários");
                
                //Integra Funcionarios
                int qtdFuncionarios = emserh.SalvaFuncionario(alterdata.getFuncionarios());
                
                emserh.Historico(String.valueOf(qtdFuncionarios) + " funcionarios integrados");
                
                
                Util.printaln("----------------------------------------- Finalizado Integração de Funcionários -----------------------------------------");
                
                
                Util.printaln("----------------------------------------- Iniciando extração portal da transparencia -----------------------------------------");
                
                /*----------------- INICIO DO LOOP DE MESES POR ANO -----------------*/

                //objetos
                PortalTranspDAO portal = new PortalTranspDAO();
                AlterDataBimerDAO bimer = new AlterDataBimerDAO();
                
                //pega a data atual
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int mesAtual = localDate.getMonthValue();
                int anoAtual = localDate.getYear();
                //3 meses anteriores
                int mes = (mesAtual <= 3 ? 11 : (mesAtual - 3));
                int ano = (mesAtual <= 3 ? (anoAtual-1) : anoAtual);
                
                while(ano < anoAtual || (ano == anoAtual && mes <= mesAtual))
                {
                    emserh.Historico(String.format("Iniciando integração portal da transparencia -Ano=>%s Mês=>%s ",ano,mes));

                    emserh.Historico("Iniciando receitas");           

                    //Integra receitas
                    int qtdReceitas = portal.AtualizarReceitas(ano,mes,bimer.getReceitasTransparencia(ano,mes));

                    emserh.Historico(String.valueOf(qtdReceitas) + " receitas integradas");

                    emserh.Historico("Iniciando despesas");
                    //Integra despesas
                    int qtdDespesas = portal.AtualizarDespesas(ano,mes,bimer.getDespesasTransparencia(ano,mes));

                     emserh.Historico(String.valueOf(qtdDespesas) + " despesas integradas");


                    emserh.Historico("finalizado integração portal da transparencia");

                    
                    //incrementa o mês
                    mes++;
                    
                    //incrementa o ano
                    if(mes > 12)
                    {
                        ano++;
                    }
                }
                
                Util.printaln("----------------------------------------- Finalizado extração portal da transparencia -----------------------------------------");
                
                /*----------------- FIM DO LOOP DE MESES POR ANO -----------------*/
                
                emserh.Historico("Finalizado integração EMSERH X AlterData");
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        Util.printaln("-----------------------------------------FIM do processamento -----------------------------------------");
    }
}
