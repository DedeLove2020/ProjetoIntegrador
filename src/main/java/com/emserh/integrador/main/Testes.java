/*
 *
 *  Projeto Integrador EMSERH
 *
 * 2020 (c) Empresa Maranhense de Serviços Hospitalares - EMSERH
 *
 */
package com.emserh.integrador.main;

import com.emserh.integrador.dao.alterdata.AlterDataBimerDAO;
import com.emserh.integrador.dao.alterdata.AlterDataPackDAO;
import com.emserh.integrador.dao.emserh.EmserhSistemasDAO;
import com.emserh.integrador.dao.emserh.PortalTranspDAO;
import com.emserh.integrador.util.Util;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Ronney Viana
 */
public class Testes 
{
    public static void main(String args[])
    {
        
        try
        {
            //objetos
            PortalTranspDAO portal = new PortalTranspDAO();
            AlterDataBimerDAO bimer = new AlterDataBimerDAO();
            //declara o objeto base
            EmserhSistemasDAO emserh = new EmserhSistemasDAO();
            AlterDataPackDAO alterdata = new AlterDataPackDAO();

            /*
            //pega a data atual
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int mesAtual = localDate.getMonthValue();
            int anoAtual = localDate.getYear();
            //3 meses anteriores
            */
            
            int mes = 2;
            int ano = 2020;

            /*
            while(ano < anoAtual || (ano == anoAtual && mes <= mesAtual))
            {
            */
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


                /*
                //incrementa o mês
                mes++;

                //incrementa o ano
                if(mes > 12)
                {
                    ano++;
                }
            }
                */

            Util.printaln("----------------------------------------- Finalizado extração portal da transparencia -----------------------------------------");

            /*----------------- FIM DO LOOP DE MESES POR ANO -----------------*/

            emserh.Historico("Finalizado integração EMSERH X AlterData");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
