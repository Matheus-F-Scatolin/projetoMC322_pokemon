package pokemon.GUI;

import pokemon.BancoDados;
import pokemon.Batalha;
import pokemon.Pokemon;
import pokemon.Treinador;
import pokemon.ataques.Ataque;
import pokemon.itens.Item;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private CardLayout cardLayout;
    private Batalha batalha;
    private MontadorDeTime montadorDeTime;
    private SeletorDePokemon seletorDePokemon;
    private MenuBatalha menuBatalha;
    private Pokemon pokemonEscolhido;
    private Ataque ataqueEscolhido;
    private Item itemEscolhido;

    public MenuPrincipal(Batalha batalha) {
        super();
        this.batalha = batalha;
        setTitle("Pokémon MC322");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cPane = getContentPane();
        cardLayout = new CardLayout();
        cPane.setLayout(cardLayout);

        montadorDeTime = new MontadorDeTime(BancoDados.getPokemonsIniciais().values(),
                BancoDados.getPokemons().values());
        setVisible(true);
    }

    public void start() {
        setup();
    }

    private void montarTime(Treinador jogador) {
        for (Pokemon pokemon : montadorDeTime.getEscolhidos()) {
            jogador.adicionarPokemon(new Pokemon(pokemon));
        }
    }

    private void setup() {
        // Callback do primeiro jogador
        montadorDeTime.setCallback(() -> {
            montarTime(batalha.getJogador1());
            montadorDeTime.reset();
            montadorDeTime.setPrompt("Escolha um Pokémon inicial e três lendários, " +
                    batalha.getJogador2().getNome());

            // Callback do segundo jogador
            montadorDeTime.setCallback(() -> {
                montarTime(batalha.getJogador2());
                getContentPane().remove(montadorDeTime);
                escolherPrimeiroPokemonAtivo();
            });
        });
        montadorDeTime.setPrompt("Escolha um Pokémon inicial e três lendários, " +
                batalha.getJogador1().getNome());
        getContentPane().add(montadorDeTime);
        cardLayout.next(getContentPane());
        pack();
    }

    private void escolherPrimeiroPokemonAtivo() {
        Treinador jogador1 = batalha.getJogador1();
        Treinador jogador2 = batalha.getJogador2();
        SeletorDePokemon seletor1 = new SeletorDePokemon(jogador1.getPokemons(),
                "Escolha um Pokémon para enviar, " + jogador1.getNome(), false);
        SeletorDePokemon seletor2 = new SeletorDePokemon(jogador2.getPokemons(),
                "Escolha um Pokémon para enviar, " + jogador2.getNome(), false);

        Container cPane = getContentPane();

        seletor1.setCallback(() -> {
            jogador1.setPokemonAtivo(seletor1.pokemonEscolhido());
            cPane.remove(seletor1);
            cPane.add(seletor2);
            cardLayout.next(cPane);
        });

        seletor2.setCallback(() -> {
            jogador2.setPokemonAtivo(seletor2.pokemonEscolhido());
            menuBatalha = new MenuBatalha(batalha);
            escolherAcao1();
            cPane.remove(seletor2);
            cPane.add(menuBatalha);
            cardLayout.next(cPane);
        });

        cPane.add(seletor1);
        cardLayout.next(cPane);
    }

    private void escolherAcao1() {
        Container cPane = getContentPane();
        Treinador jogador = batalha.getJogador1();

        menuBatalha.setPrompt("Escolha uma ação, " + jogador.getNome() + ":");
        menuBatalha.update();
        menuBatalha.setCallbackAtacar(() -> {
            SeletorDeAtaques seletor = new SeletorDeAtaques(jogador.getPokemonAtivo().getAtaques(),
                    "Escolha um ataque, " + jogador.getPokemonAtivo().getNome());
            seletor.setCallback(() -> {
                batalha.setAtaqueEscolhido(seletor.ataqueEscolhido(), 1);
                cPane.remove(seletor);
                escolherAcao2();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });

        menuBatalha.setCallbackTrocar(() -> {
            SeletorDePokemon seletor = new SeletorDePokemon(jogador.getPokemons(),
                    "Escolha um Pokémon para enviar, " + jogador.getNome());
            seletor.setCallback(() -> {
                batalha.setPokemonEscolhido(seletor.pokemonEscolhido(), 1);
                cPane.remove(seletor);
                escolherAcao2();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });

        menuBatalha.setCallbackItem(() -> {
            SeletorDeItens seletor = new SeletorDeItens(jogador.getItens(),
                    "Escolha um item, " + jogador.getNome());
            seletor.setCallback(() -> {
                batalha.setItemEscolhido(seletor.itemEscolhido(), 1);
                cPane.remove(seletor);
                escolherAcao2();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });
    }

    private void escolherAcao2() {
        Container cPane = getContentPane();
        Treinador jogador = batalha.getJogador2();

        menuBatalha.setPrompt("Escolha uma ação, " + jogador.getNome() + ":");

        menuBatalha.setCallbackAtacar(() -> {
            SeletorDeAtaques seletor = new SeletorDeAtaques(jogador.getPokemonAtivo().getAtaques(),
                    "Escolha um ataque, " + jogador.getPokemonAtivo().getNome());
            seletor.setCallback(() -> {
                batalha.setAtaqueEscolhido(seletor.ataqueEscolhido(), 2);
                cPane.remove(seletor);
                realizarAcoes();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });

        menuBatalha.setCallbackTrocar(() -> {
            SeletorDePokemon seletor = new SeletorDePokemon(jogador.getPokemons(),
                    "Escolha um Pokémon para enviar, " + jogador.getNome());
            seletor.setCallback(() -> {
                batalha.setPokemonEscolhido(seletor.pokemonEscolhido(), 2);
                cPane.remove(seletor);
                realizarAcoes();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });

        menuBatalha.setCallbackItem(() -> {
            SeletorDeItens seletor = new SeletorDeItens(jogador.getItens(),
                    "Escolha um item, " + jogador.getNome());
            seletor.setCallback(() -> {
                batalha.setItemEscolhido(seletor.itemEscolhido(), 2);
                cPane.remove(seletor);
                realizarAcoes();
            });
            cPane.add(seletor);
            cardLayout.next(cPane);
        });
    }

    private void realizarAcoes() {
        if (batalha.primeiroComeca()) {
            realizarAcoes(batalha.getJogador1(), batalha.getJogador2());
        } else {
            realizarAcoes(batalha.getJogador2(), batalha.getJogador1());
        }
    }


    private void realizarAcoes(Treinador ageAntes, Treinador ageDepois) {
        JOptionPane.showMessageDialog(null, batalha.realizarAcao(ageAntes));
        menuBatalha.update();
        if (!ageDepois.getPokemonAtivo().estaVivo()) {
            if (ageDepois.treinadorDerrotado()) {
                anunciarVitoria(ageAntes);
            }
            SeletorDePokemon seletor = new SeletorDePokemon(ageDepois.getPokemons(),
                    "Escolha um Pokémon para enviar, " + ageDepois.getNome(), false);
            seletor.setCallback(() -> {
                ageDepois.setPokemonAtivo(seletor.pokemonEscolhido());
                getContentPane().remove(seletor);
                escolherAcao1();
            });
            getContentPane().add(seletor);
            cardLayout.next(getContentPane());
        } else {
            JOptionPane.showMessageDialog(null, batalha.realizarAcao(ageDepois));
            menuBatalha.update();
            if (!ageAntes.getPokemonAtivo().estaVivo()) {
                if (ageAntes.treinadorDerrotado()) {
                    anunciarVitoria(ageDepois);
                }
                SeletorDePokemon seletor = new SeletorDePokemon(ageAntes.getPokemons(),
                        "Escolha um Pokémon para enviar, " + ageAntes.getNome(), false);
                seletor.setCallback(() -> {
                    ageAntes.setPokemonAtivo(seletor.pokemonEscolhido());
                    getContentPane().remove(seletor);
                    escolherAcao1();
                });
                getContentPane().add(seletor);
                cardLayout.next(getContentPane());
            } else {
                escolherAcao1();
            }
        }
    }

    private void anunciarVitoria(Treinador vencedor) {
        JOptionPane.showMessageDialog(null, vencedor.getNome() + " venceu a batalha!");
        System.exit(0);
    }
}
