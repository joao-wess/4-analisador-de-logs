# Analisador de Logs (Nível 4) 🚀

Este projeto é um analisador de arquivos de log via terminal (CLI) desenvolvido em Java. Ele é capaz de ler arquivos de texto estruturados, processar os dados aplicando resiliência a falhas, realizar filtros avançados e exportar um relatório sumarizado.

O foco deste projeto foi a aplicação de **Orientação a Objetos (SOLID)**, **Padrões de Projeto (Strategy)** e a utilização intensiva da **Java Stream API** e da API **NIO.2** para manipulação de arquivos com alta performance.

## 🛠️ Tecnologias e Conceitos Aplicados
- **Java 21**
- **Arquitetura em Camadas** (`model`, `view`, `service`, `utils`, `exception`)
- **Padrões de Projeto:** Injeção de Dependência e Strategy Pattern (via interface `LogParser`).
- **Java Stream API:** Uso de `.filter()`, `.map()`, `.toList()` e agrupamentos complexos com `Collectors.groupingBy`.
- **Expressões Regulares (Regex):** Extração de dados da string bruta através de `Pattern` e `Matcher`.
- **NIO.2 (`java.nio.file`):** Leitura de arquivos por demanda (Stream) com `Files.lines()` e gravação com `Files.writeString()`, incluindo a criação dinâmica de diretórios.
- **Tratamento de Exceções:** Criação de exceções customizadas (`ArquivoLogException`) e estratégias *Fail-Safe* para ignorar linhas corrompidas sem interromper a esteira de processamento.

## ⚙️ Funcionalidades
O sistema possui um menu interativo (CLI) com as seguintes opções:

1. **Carregar Arquivo de Log:** Lê o arquivo especificado pelo usuário (ex: `logs/app.log`), processando apenas linhas válidas.
2. **Exibir Resumo Geral:** Exibe o total de logs e a contagem agrupada por nível (`INFO`, `WARNING`, `ERROR`).
3. **Filtrar Logs por Nível:** Retorna todos os registros que correspondem a um nível específico.
4. **Buscar Logs por Palavra-Chave:** Realiza uma busca textual (*case-insensitive*) dentro das mensagens dos logs.
5. **Exportar Relatório Final:** Gera um arquivo físico (`logs/report.txt`) no disco com o resumo processado, criando as pastas necessárias automaticamente.

## 📂 Estrutura do Arquivo de Log
O sistema espera um arquivo `.txt` ou `.log` contendo o seguinte padrão (Regex estrutural):
`[Data Hora] [Nível] Mensagem`

*Exemplo:*
```text
[2026-10-27 10:15:30] [INFO] Servidor iniciado na porta 8080
[2026-10-27 10:16:05] [WARNING] Uso de CPU acima de 80%
```
*(Linhas fora deste padrão são capturadas pelo sistema e ignoradas, garantindo que o processamento não seja interrompido).*

## 🚀 Como Executar
1. Compile o projeto garantindo que todos os pacotes (`com.joaovitor.log.*`) estejam no classpath.
2. Execute a classe `Main`.
3. Siga as instruções do menu interativo no terminal.