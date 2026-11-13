package com.joaovitor.log.view;

import com.joaovitor.log.exception.ArquivoLogException;
import com.joaovitor.log.model.LogEntry;
import com.joaovitor.log.service.LogService;

import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LogView {
  private LogService service;
  private Scanner sc;

  public LogView(LogService service) {
    this.service = service;
    this.sc = new Scanner(System.in);
  }

  //methods
  public void start() {
    boolean condition = true;

    while (condition) {
      showMenu();

      System.out.print("Escolha uma opção: ");
      String option = sc.nextLine();

      try {
        int convertedOptionInInt = Integer.parseInt(option);

        if (convertedOptionInInt == 0) {
          break;
        }

        switch (convertedOptionInInt) {
          case 1:
            System.out.print("Digite o caminho do arquivo: ");
            String path = sc.nextLine();
            service.processFile(path);
            System.out.println("-> Arquivo processado com sucesso! Foram carregados " + service.getLogs().size() + " logs válidos.");
            break;

          case 2:
            System.out.println("Resumo Geral");
            System.out.println(service.getSummary());
            break;

          case 3:
            System.out.print("Escolha um nível: ");
            String level = sc.nextLine();
            List<LogEntry> logs = service.searchByLevel(level);

            if (logs.isEmpty()) {
              System.out.println("-> Nenhum log encontrado para o nível: " + level);
            }
            else {
              for (LogEntry log : logs) {
                System.out.println(log);
              }
            }
            break;

          case 4:
            System.out.print("Qual a palavra-chave: ");
            String keyWord = sc.nextLine();
            List<LogEntry> results = service.searchByKeyword(keyWord);

            if (results.isEmpty()) {
              System.out.println("-> Nenhum log encontrado para a mensagem: " + keyWord);
            }
            else {
              for (LogEntry log : results) {
                System.out.println(log);
              }
            }

            break;
          default:
            System.out.println("OPÇÃO INVÁLIDA. SELECIONE UM NÚMERO DE 0-5");
        }

      } catch (NumberFormatException e) {
        System.out.println("OPÇÃO INVÁLIDA. Escolha apenas números");
      } catch (ArquivoLogException e) {
        System.out.println("ARQUIVO NÃO ENCONTRADO. TENTE NOVAMENTE");
      }
    }
  }

  public void showMenu() {
    System.out.println("----------------------");
    System.out.println("1 - Carregar arquivo de Log");
    System.out.println("2. Exibir Resumo Geral (INFO, WARNING, ERROR)");
    System.out.println("3. Filtrar Logs por Nível");
    System.out.println("4. Buscar Logs por Palavra-Chave");
    System.out.println("5. Exportar Relatório Final (report.txt)");
    System.out.println("0. Sair");
    System.out.println("----------------------");
  }
}

