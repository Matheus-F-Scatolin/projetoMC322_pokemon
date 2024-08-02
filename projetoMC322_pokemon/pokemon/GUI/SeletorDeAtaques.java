package pokemon.GUI;

import pokemon.ataques.Ataque;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class SeletorDeAtaques extends JPanel {
    private Runnable callback;
    private Ataque escolhido;


    public SeletorDeAtaques(Collection<? extends Ataque> ataques, String prompt) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel promptECancelar = new JPanel(new FlowLayout());
        promptECancelar.add(new JLabel(prompt));
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> {
            getParent().remove(this);
        });
        promptECancelar.add(cancelar);
        add(promptECancelar);

        JPanel botoes = new JPanel(new GridLayout(2, 2));
        // Criando os botões para cada ataque
        for (Ataque ataque : ataques) {
            botoes.add(criarBotao(ataque));
        }
        add(botoes);

        setVisible(true);
    }

    private JButton criarBotao(Ataque ataque) {
        int pp = ataque.getPp();
        JButton botao = new JButton(ataque.getNome() + " (" + ataque.getTipo() + ") " +
                pp + "/" + ataque.getPpMax());
        if (pp == 0) {
            botao.setEnabled(false);
        }
        botao.addActionListener(e -> {
            int escolha = JOptionPane.showConfirmDialog(null, "Deseja usar este ataque " +
                    ataque.getNome() + "?", null, JOptionPane.YES_NO_OPTION);
            if (escolha == 0) {  // Sim
                escolhido = ataque;
                callback.run();
            }
        });

        return botao;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public Ataque ataqueEscolhido() {
        return escolhido;
    }
}
