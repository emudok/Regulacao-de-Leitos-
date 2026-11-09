# Sistema de Regulação de Leitos

Projeto acadêmico simples em Java para cadastrar pacientes, registrar solicitações
com prioridades e encaminhar pacientes para hospitais com leitos disponíveis.

## Executar

Na pasta `oo`, compile os arquivos:

```bash
javac -d out Demo.java model/*.java swing/*.java
java -cp out Demo
```

O `Demo` mostra a fila de prioridades, a alocação automática e a liberação de
um leito. Para abrir as duas telas Swing:

```bash
java -cp out swing.TelaSolicitacao
```

### Telas

1. **Lançamento de solicitação:** pergunta nome, tipo de documento (CPF ou RG),
   idade, sintomas, especialidade, prioridade e hospital de destino.
2. **Acompanhamento da regulação:** exibe os leitos, os lançamentos realizados,
   processa a fila por prioridade, mostra o hospital encaminhado e libera o
   leito selecionado.

A tela também mostra um resumo de leitos disponíveis, ocupados e solicitações
pendentes. Ao liberar um leito, a fila é processada automaticamente e o próximo
paciente compatível pode ser encaminhado sem uma nova ação manual.

As telas compartilham a mesma `CentralRegulacao`, então os lançamentos feitos na
primeira tela aparecem na segunda.

O encaminhamento só acontece quando existe um leito disponível no hospital
escolhido. Caso contrário, a solicitação continua pendente para ser processada
quando um leito for liberado.

Após o processamento, o paciente deixa a lista **Aguardando vaga** e passa para
o **Histórico**, com a mensagem de encaminhamento, hospital e número do leito.
O CPF é validado pelos dígitos verificadores; para RG, o sistema exige entre 5
e 12 números.

As solicitações possuem três níveis: `Normal`, `Urgente` e `VagaZero`, nessa
ordem crescente de prioridade.

## Relação com as etapas da atividade

- **OO:** as classes `Paciente`, `Hospital`, `Leito` e `Solicitacao`, com as
  especializações `Normal`, `Urgente` e `VagaZero`, demonstram encapsulamento,
  herança e polimorfismo.
- **Funcional:** `oo/funcional/ConsultaRegulacao.java` usa `stream`, `filter` e
  `sorted` para consultar solicitações sem alterar a lista original.
- **Lógica:** `logica/regulacao.pl` contém fatos, regras e quatro consultas de
  exemplo.

O aplicativo ainda precisa ser acompanhado do relatório exigido pelo professor,
com requisitos, diagrama de classes, evidências de execução, forma complementar
escolhida e tabela de comparação entre paradigmas. Portanto, o código cobre a
base prática das etapas, mas o relatório final é necessário para afirmar que a
atividade está completamente atendida.
