package Models;

import Logs.LogArquivo;

public class CaixaEletronico {
    private int menorNota = 1;
    private int[] valoresNotas = {};
    private int[] quantidadeNotas = {};

    //Getters e Setters do Caixa Eletrônico
    public void setValoresNotas(int[] novosValoresNotas) {
        this.valoresNotas = novosValoresNotas;
    }

    public void setQuantidadeNotas(int[] novasQuantidadesNotas) {
        this.quantidadeNotas = novasQuantidadesNotas;
    }
    //================================================================

    //Organizador simples para as notas
    public void ordenarNotasEQuantidades() {
        int n = valoresNotas.length;
        for (int i = 0; i < n - 1; i++) {
            int peimeiroIndice = i;
            for (int maiorNota = i + 1; maiorNota < n; maiorNota++) {
                if (valoresNotas[maiorNota] > valoresNotas[peimeiroIndice]) {
                    peimeiroIndice = maiorNota;
                }
            }
            if (peimeiroIndice != i) {
                int auxiliarNota = valoresNotas[i];
                valoresNotas[i] = valoresNotas[peimeiroIndice];
                valoresNotas[peimeiroIndice] = auxiliarNota;

                int auxiliarQuantidade = quantidadeNotas[i];
                quantidadeNotas[i] = quantidadeNotas[peimeiroIndice];
                quantidadeNotas[peimeiroIndice] = auxiliarQuantidade;
            }
        }
        this.menorNota = valoresNotas[n - 1];
    }
    //================================================================

    //Inicia LogArquivo
    private final LogArquivo notificador = new LogArquivo();

    private void adicionaLog(String mensagem) {
        notificador.registrarEvento(mensagem); // chama direto
    }
    //================================================================


    //Metodo de depósito
    public void depositar(ContaBancaria conta, double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para depósito.");
            adicionaLog("Erro no depósito: valor inválido (" + valor + ")");
            return;
        }

        double novoSaldo = conta.getSaldo() + valor;
        conta.setSaldo(novoSaldo);

        String mensagem = "Depósito realizado: R$" + valor;
        System.out.println(mensagem);
        adicionaLog(mensagem);
    }
    //===========================================================================


    //Metodo de Saque
    public void sacar(ContaBancaria conta, double valor) {

        if (valor <= 0 || valor % menorNota != 0) {
            System.out.println("Valor inválido para saque.");
            adicionaLog("Erro no saque: valor inválido (" + valor + ")");
            sugerirSaqueMaisProximo(valor);
            return;
        } else if (conta.getSaldo() < valor) {
            System.out.println("Saldo insuficiente na conta.");
            adicionaLog("Erro no saque: saldo insuficiente para R$" + valor);
            return;
        }

            int restante = (int) valor;
            int[] notasParaUsar = new int[valoresNotas.length];

            //Monta valor com as notas
            //Percorre o array de nota e quantidade
            //Verifica quantas notas seriam necessárias com a divisão entre restante e o valora atual da nota
            //Após isso verifica a quantidade, se nao tiver a quantidade necessária acaba pegando a quantidade que tem por isso uso o Math.
            for (int i = 0; i < valoresNotas.length; i++) {
                int nota = valoresNotas[i];
                int disponivel = quantidadeNotas[i];
                int usar = Math.min(restante / nota, disponivel);

                if (usar > 0) {
                    //Aqui é adicionada a quantidade que será usada na posição atual
                    notasParaUsar[i] = usar;
                    restante -= usar * nota;
                }
            }

            if (restante != 0) {
                System.out.println("Não foi possível realizar o saque com as notas disponíveis.");
                adicionaLog("Erro no saque: Não foi possível montar R$" + valor + " com as notas disponíveis");
                sugerirSaqueMaisProximo(valor);

                return;
            }

            //Atualiza estoque de notas
            for (int i = 0; i < valoresNotas.length; i++) {
                //Como as posições são as mesma é só subtrair um pelo outro
                quantidadeNotas[i] -= notasParaUsar[i];
            }
            adicionaLog("Saque realizado: R$" + valor);
            conta.setSaldo(conta.getSaldo() - valor);
            System.out.println("Saque de R$" + valor + " realizado com sucesso.");
            System.out.println("Notas entregues:");
            for (int i = 0; i < valoresNotas.length; i++) {
                if (notasParaUsar[i] > 0) {
                    System.out.println("R$" + valoresNotas[i] + ": " + notasParaUsar[i] + "x");
                }
            }
        }
        //===========================================================================

        //Consulta Saldo do caixa e quais notas ainda tem em estoque
        public void consultarSaldoCaixa () {
            int total = 0;
            System.out.println("Notas disponíveis no caixa:");
            for (int i = 0; i < valoresNotas.length; i++) {
                int valor = valoresNotas[i];
                int quantidade = quantidadeNotas[i];
                int subtotal = valor * quantidade;
                total += subtotal;
                System.out.println("R$" + valor + ": " + quantidade + "x (Total: R$" + subtotal + ")");
            }
            System.out.println("Total disponível no caixa: R$" + total);
        }
        //===========================================================================

        //Metodo de sugestão de saque
        public void sugerirSaqueMaisProximo(double valorDesejado) {
            //vai pegar o número inteiro ja que o caixa só funciona com inteiros.
            int valorMaximo = (int) Math.floor(valorDesejado);
            valorMaximo -= valorMaximo % menorNota;

            while (valorMaximo > 0) {
                int restante = valorMaximo;
                int[] estoqueTemp = quantidadeNotas.clone();

                for (int i = 0; i < valoresNotas.length; i++) {
                    int nota = valoresNotas[i];
                    int usar = Math.min(restante / nota, estoqueTemp[i]);
                    restante -= usar * nota;
                }

                if (restante == 0) {
                    System.out.println("Sugestão de saque mais próxima: R$" + (double) valorMaximo);
                    return;
                }

                valorMaximo -= menorNota;
            }

            System.out.println("Nenhuma sugestão possível com o estoque atual de notas.");
        }
        //===========================================================================
    }
