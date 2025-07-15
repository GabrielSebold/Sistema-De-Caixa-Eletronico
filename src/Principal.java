import Models.ContaBancaria;
import Models.CaixaEletronico;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        //Scanner de leitura
        Scanner leitura = new Scanner(System.in);
        //======================================

        //Dados da Conta
        ContaBancaria conta = new ContaBancaria();
        conta.setNumeroConta(777);
        conta.setSaldo(0);
        conta.titular = "Gabriel Sebold dos Santos";
        //======================================

        //Inicialização do Caixa e do valor disponível no caixa
        CaixaEletronico caixa = new CaixaEletronico();
        //======================================

        //Funcionamento do Caixa Eletrônico
        boolean continua = true;
        int opcao = 0;
        double valor = 0;
        //======================================

        while (continua) {
            //Menu Principal
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("""
                    Bem vindo ao seu banco!
                    Segue abaixo o menu de operações disponíveis.
                    1 - Depósito;
                    2 - Saque;
                    3 - Consultar o saldo disponível em caixa;
                    4 - Exibir dados da sua conta;
                    5 - Encerrar operações;
                    """);
            opcao = leitura.nextInt();

            //Operação do Caixa
            if (opcao == 1) {
                //Depósito
                System.out.println("Qual valor você quer depositar?");
                valor = leitura.nextDouble();

                caixa.depositar(conta, valor);
                System.out.println("Novo saldo da conta: R$ " + conta.getSaldo());
            } else if (opcao == 2) {
                //Saque
                System.out.println("Qual valor você quer sacar?");
                valor = leitura.nextDouble();

                caixa.sacar(conta, valor);
                System.out.println("Saldo atual da conta: R$ " + conta.getSaldo());
            } else if (opcao == 3) {
                //Consulta do Caixa
                caixa.consultarSaldoCaixa();
            } else if (opcao == 4) {
                //Consulta da Conta
                conta.exibeFichaTecnica();
            } else if (opcao == 5) {
                //Encerra o Programa
                continua = false;
                System.out.println("Agradecemos a preferência.");
            } else {
                System.out.println("""
                        Operação solicitada inválida!
                        Tente novamente.
                        """);
            }
        }
    }
}
