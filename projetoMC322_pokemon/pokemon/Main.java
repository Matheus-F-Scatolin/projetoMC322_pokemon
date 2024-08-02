package pokemon;

import pokemon.GUI.MenuPrincipal;
import pokemon.itens.ItemBatalha;

import javax.swing.*;

public class Main {

    /**
     * Realiza um torneio de Pokémons e imprime o vencedor.
     */
    public static void main(String[] args) {
        //Inicializar o banco de dados
        BancoDados.inicializar();
        String nomeTreinador1 = JOptionPane.showInputDialog(null,
                "Insira o nome do primeiro jogador:");
        if (nomeTreinador1 == null) {
            System.exit(0);
        }
        String nomeTreinador2 = JOptionPane.showInputDialog(null,
                "Insira o nome do segundo jogador:");
        if (nomeTreinador2 == null) {
            System.exit(0);
        }
        Treinador jogador1 = new Treinador(nomeTreinador1);
        Treinador jogador2 = new Treinador(nomeTreinador2);

        for (ItemBatalha item : BancoDados.getItensBatalha().values()) {
            jogador1.adicionarItem(item);
            jogador2.adicionarItem(item);
        }
        Batalha batalha = new Batalha(jogador1, jogador2);

        SwingUtilities.invokeLater(() -> new MenuPrincipal(batalha).start());
    }
}