package Models;

public class ContaBancaria {
    private int numeroConta;
    private double saldo;
    public String titular;

    //Getters e Setters
    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
    //========================================================

    //Metodo de exibição
    public void exibeFichaTecnica(){
        System.out.println("Nome do Titular: " + titular);
        System.out.println("Numero da Conta: " + numeroConta);
        System.out.println("Saldo: " + saldo);
    }
    //========================================================
}
