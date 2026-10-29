# Nível 4: Analisador de Logs (Processamento de Dados & Arquivos)

## O Desafio
Sistema interativo via terminal para leitura, filtragem, análise estatística e geração de relatórios a partir de arquivos de log do sistema (`app.log`), aplicando Abstração via Interfaces e processamento eficiente de streams de texto.

## O que aprender
Processamento avançado de arquivos de texto (`java.nio.file.Files.lines` ou `BufferedReader`), Expressões Regulares (`java.util.regex.Pattern` e `Matcher`), API de Streams do Java (`filter`, `map`, `collect`, `count`), Contratos com Interfaces/Classes Abstratas (ex: `LogParser`), manipulação de Strings e exportação de relatórios formatados em disco.

---

## 📋 DETALHES DO DESAFIO

### Requisitos Funcionais (RF):
- **RF01:** O sistema deve exibir um menu interativo no terminal com as opções: `1. Carregar Arquivo de Log`, `2. Exibir Resumo Geral (INFO, WARNING, ERROR)`, `3. Filtrar Logs por Nível`, `4. Buscar Logs por Palavra-Chave`, `5. Exportar Relatório Final (report.txt)`, `0. Sair`.
- **RF02:** Ao carregar um arquivo de log (ex: `app.log`), o sistema deve ler o arquivo em disco e extrair a estrutura de cada registro (Data/Hora, Nível do Log e Mensagem).
- **RF03:** Ao exibir o resumo geral, o sistema deve computar e apresentar no terminal a quantidade exata e o percentual de logs categorizados como `INFO`, `WARNING` e `ERROR`.
- **RF04:** Ao filtrar por nível de log ou buscar por palavra-chave, o sistema deve listar apenas as linhas correspondentes no terminal de forma formatada.
- **RF05:** Ao exportar o relatório final, o sistema deve gerar um novo arquivo físico chamado `report.txt` contendo a data/hora do processamento, estatísticas gerais e a lista dos erros críticos (`ERROR`).

### Requisitos Não-Funcionais (RNF):
- **RNF01:** Aplicação do Princípio da Responsabilidade Única (SRP) e uso de Interfaces/Abstração:
    - `LogEntry.java`: Classe de modelo (Model ou Record) representando a estrutura de um registro de log (Data/Hora, Nível, Mensagem).
    - `LogParser.java`: Interface definindo o contrato para parsing de linhas de texto em objetos `LogEntry` utilizando Expressões Regulares (`Pattern`/`Matcher`).
    - `LogService.java`: Camada de Negócio utilizando a API de Streams do Java (`Files.lines()`) para processamento em memória, filtragem e agregação.
    - `ConsoleView.java`: Camada de Apresentação contendo os menus, leitura via `Scanner` e formatação visual.
- **RNF02:** Uso de Expressões Regulares (Regex) para extração das partes do log (ex: padrão `^\[(.*?)\] \[(INFO|WARNING|ERROR)\] (.*)$`).
- **RNF03:** Processamento performático utilizando a API de Streams do Java (`java.util.stream.Stream`) sem carregar o arquivo inteiro de uma só vez na memória desnecessariamente.

### Regras de Negócio & Tratamento de Falhas (RN):
- **RN01 (Arquivo Inexistente ou Vazio):** Caso o usuário informe um caminho de arquivo inexistente ou vazio, o serviço deve lançar `ArquivoLogException("O arquivo de log especificado não foi encontrado ou está vazio.")`.
- **RN02 (Resiliência a Linhas Corrompidas):** Linhas do arquivo que não sigam o padrão do log não devem derrubar o sistema; elas devem ser ignoradas ou contabilizadas como "Linhas Inválidas/Corrompidas" sem interromper o processamento das demais.
- **RN03 (Busca sem Resultados):** Caso uma busca por filtro ou palavra-chave não retorne nenhum registro, a aplicação deve tratar a situação e exibir `[AVISO]: Nenhum registro foi encontrado para o filtro aplicado.`.
- **RN04 (Tratamento de Exceções de I/O):** Erros de leitura ou escrita do relatório em disco devem ser capturados na `ConsoleView` via bloco `try-catch`, exibindo um alerta amigável ao usuário sem gerar *stacktrace* da JVM.