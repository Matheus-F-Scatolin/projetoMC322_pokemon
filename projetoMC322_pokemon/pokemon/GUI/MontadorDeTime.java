package pokemon.GUI;

import pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class MontadorDeTime extends JPanel {
    private int inicaisSelecionados = 0;
    private int lendariosSelecionados = 0;
    private JLabel prompt;
    private ArrayList<PokemonCheckBox> botoesIniciais;
    private ArrayList<PokemonCheckBox> botoesLendarios;
    private JButton confirmador;

    private ArrayList<Pokemon> escolhidos;
    private Runnable callback;

    /**
     * Um JCheckBox com um ícone extra.
     */
    private class PokemonCheckBox extends JPanel {
        JCheckBox checkBox;
        JLabel sprite;
        Pokemon pokemon;

        PokemonCheckBox(Pokemon pokemon) {
            super();
            this.pokemon = pokemon;
            checkBox = new JCheckBox(pokemon.getNome());
            sprite = new JLabel(new ImageIcon(pokemon.getSprite()));
            add(checkBox);
            add(sprite);
        }

        @Override
        public void setEnabled(boolean enabled) {
            sprite.setEnabled(enabled);
            checkBox.setEnabled(enabled);
        }

        void addActionListener(ActionListener l) {
            checkBox.addActionListener(l);
        }

        @Override
        public boolean isEnabled() {
            return checkBox.isEnabled();
        }

        boolean isSelected() {
            return checkBox.isSelected();
        }

        void setSelected(boolean b) {
            checkBox.setSelected(b);
        }

        JCheckBox getCheckBox() {
            return checkBox;
        }
    }

    public MontadorDeTime(Collection<? extends Pokemon> iniciais, Collection<? extends Pokemon> lendarios) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        prompt = new JLabel("Escolha um pokémon inicial e três lendários");
        add(prompt);
        add(criarPainelCheckbox(iniciais, lendarios));

    }

    private JPanel criarPainelCheckbox(Collection<? extends Pokemon> iniciais, Collection<? extends Pokemon> lendarios) {
        JPanel painel = new JPanel(new GridLayout(0, 6));
        botoesIniciais = new ArrayList<>();
        botoesLendarios = new ArrayList<>();
        escolhidos = new ArrayList<>();

        for (Pokemon pokemon : iniciais) {
            PokemonCheckBox checkBox = new PokemonCheckBox(pokemon);
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    inicaisSelecionados++;
                    escolhidos.add(pokemon);
                } else {
                    inicaisSelecionados--;
                    escolhidos.remove(pokemon);
                }
                update();
            });
            botoesIniciais.add(checkBox);
            painel.add(checkBox);
        }
        for (Pokemon pokemon : lendarios) {
            PokemonCheckBox checkBox = new PokemonCheckBox(pokemon);
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    lendariosSelecionados++;
                    escolhidos.add(pokemon);
                } else {
                    lendariosSelecionados--;
                    escolhidos.remove(pokemon);
                }
                update();
            });
            botoesLendarios.add(checkBox);
            painel.add(checkBox);
        }

        confirmador = new JButton("Confirmar");
        confirmador.addActionListener(e -> {
            callback.run();
        });
        confirmador.setEnabled(false);
        painel.add(confirmador);

        return painel;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void update() {
        for (PokemonCheckBox checkBox : botoesIniciais) {
            if (!checkBox.isSelected() && inicaisSelecionados >= 1) {
                checkBox.setEnabled(false);
            } else if (!checkBox.isEnabled() && inicaisSelecionados < 1) {
                checkBox.setEnabled(true);
            }
        }
        for (PokemonCheckBox checkBox : botoesLendarios) {
            if (!checkBox.isSelected() && lendariosSelecionados >= 3) {
                checkBox.setEnabled(false);
            } else if (!checkBox.isEnabled() && lendariosSelecionados < 3) {
                checkBox.setEnabled(true);
            }
        }
        confirmador.setEnabled(inicaisSelecionados == 1 && lendariosSelecionados == 3);
    }

    public ArrayList<Pokemon> getEscolhidos() {
        return escolhidos;
    }

    public void reset() {
        for (PokemonCheckBox checkBox : botoesIniciais) {
            checkBox.setEnabled(true);
            checkBox.setSelected(false);
        }
        for (PokemonCheckBox checkBox : botoesLendarios) {
            checkBox.setEnabled(true);
            checkBox.setSelected(false);
        }
        escolhidos = new ArrayList<>();
        inicaisSelecionados = 0;
        lendariosSelecionados = 0;
    }

    public void setPrompt(String text) {
        prompt.setText(text);
    }
}
