package Logs;

//Permite criar, verificar existência ou alterar arquivos de um diretório.
import java.io.File;
//Permite escrever em arquivos de texto.
import java.io.FileWriter;
//Serve para tratamento de erros como o New Exception do PHP.
import java.io.IOException;
//Pega a data e hora do computador que está rodando o sistema.
import java.time.LocalDateTime;
//Permite formatar datas.
import java.time.format.DateTimeFormatter;

public class LogArquivo{
    private final String nomeArquivo = "Logs/log.txt";

    public LogArquivo() {
        criarPastaLogsSeNaoExistir();
    }

    private void criarPastaLogsSeNaoExistir() {
        File pasta = new File("Logs");
        if (!pasta.exists()) {
            pasta.mkdir();
        }
    }

    public void registrarEvento(String mensagem) {
        try (FileWriter writer = new FileWriter(nomeArquivo, true)) {
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String linha = "[" + agora.format(formato) + "] " + mensagem + "\n";
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no log: " + e.getMessage());
        }
    }
}
