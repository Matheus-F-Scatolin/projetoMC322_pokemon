package pokemon.GUI;

import pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class SeletorDePokemon extends JPanel {
    private Runnable callback;
    private Pokemon escolhido;

    public SeletorDePokemon(Collection<? extends Pokemon> pokemons, String prompt) {
        this(pokemons, prompt, true);
    }

    public SeletorDePokemon(Collection<? extends Pokemon> pokemons, String prompt, boolean cancelavel) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel promptECancelar = new JPanel();
        promptECancelar.add(new JLabel(prompt));
        if (cancelavel) {
            JButton cancelar = new JButton("Cancelar");
            cancelar.addActionListener(e -> {
                getParent().remove(this);
            });
            promptECancelar.add(cancelar);
        }
        add(promptECancelar);

        JPanel botoes = new JPanel(new GridLayout((int) Math.sqrt(pokemons.size()), 0, 6, 6));
        for (Pokemon pokemon : pokemons) {
            botoes.add(criarBotao(pokemon));
        }
        add(botoes);
    }

    private JButton criarBotao(Pokemon pokemon) {
        JButton botao = new JButton(pokemon.getNome());
        botao.setIcon(new ImageIcon(pokemon.getSprite()));
        botao.setFocusPainted(false);
        if (!pokemon.estaVivo()) {
            botao.setEnabled(false);
        }
        botao.setFocusPainted(false);
        botao.addActionListener(e -> {
            int escolha = JOptionPane.showConfirmDialog(null, "Deseja enviar " +
                    pokemon.getNome() + "?", null, JOptionPane.YES_NO_OPTION);
            if (escolha == 0) {  // Sim
                escolhido = pokemon;
                callback.run();
            }
        });

        return botao;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public Pokemon pokemonEscolhido() {
        return escolhido;
    }
}
