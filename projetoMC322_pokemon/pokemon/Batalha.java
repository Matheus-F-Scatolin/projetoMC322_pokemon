package pokemon;

import pokemon.ataques.Ataque;
import pokemon.itens.ItemBatalha;

public class Batalha {
    private final Treinador jogador1;
    private final Treinador jogador2;
    private Clima clima;


    // Últimas escolhas de cada jogador
    private Acao acao1;
    private Acao acao2;
    private Ataque ataque1;
    private Ataque ataque2;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private ItemBatalha item1;
    private ItemBatalha item2;

    /**
     * Os tipos de ação que um jogador pode fazer em um turno
     */
    public enum Acao {
        ATACAR, TROCAR, ITEM
    }

    /**
     * @return o jogador 1.
     */
    public Treinador getJogador1() {
        return jogador1;
    }

    /**
     * @return o jogador 2.
     */
    public Treinador getJogador2() {
        return jogador2;
    }

    public Batalha(Treinador jogador1, Treinador jogador2) {
        if (jogador1 == null || jogador2 == null) {
            throw new IllegalArgumentException("Os jogadores não podem ser null.");
        }
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        clima = Clima.NORMAL;
    }

    public void setAtaqueEscolhido(Ataque ataque, int jogador) {
        if (jogador == 1) {
            acao1 = Acao.ATACAR;
            ataque1 = ataque;
        } else {
            acao2 = Acao.ATACAR;
            ataque2 = ataque;
        }
    }

    public void setPokemonEscolhido(Pokemon pokemon, int jogador) {
        if (jogador == 1) {
            acao1 = Acao.TROCAR;
            pokemon1 = pokemon;
        } else {
            acao2 = Acao.TROCAR;
            pokemon2 = pokemon;
        }
    }

    public void setItemEscolhido(ItemBatalha item, int jogador) {
        if (jogador == 1) {
            acao1 = Acao.ITEM;
            item1 = item;
        } else {
            acao2 = Acao.ITEM;
            item2 = item;
        }
    }

    public String realizarAcao(Treinador jogador) {
        if (jogador == jogador1) {
            return switch (acao1) {
                case ATACAR -> atacar(ataque1, jogador1.getPokemonAtivo(), jogador2.getPokemonAtivo());
                case TROCAR -> {
                    String out = jogador1.getPokemonAtivo().getNome() + ", volte!\n" +
                            pokemon1.getNome() + ", avance!";
                    jogador1.setPokemonAtivo(pokemon1);
                    yield out;
                }
                case ITEM -> {
                    try {
                        item1.usar(jogador1.getPokemonAtivo());
                        jogador1.removerItem(item1);
                        yield jogador1.getNome() + " usou " + item1.getNome();
                    } catch (ExcecaoUsoItem e) {
                        yield jogador1.getNome() + " não pode usar o item " + item1.getNome();
                    }
                }
                case null -> null;
            };
        } else {
            return switch (acao2) {
                case ATACAR -> atacar(ataque2, jogador2.getPokemonAtivo(), jogador1.getPokemonAtivo());
                case TROCAR -> {
                    String out = jogador2.getPokemonAtivo().getNome() + ", volte!\n" +
                            pokemon2.getNome() + ", avance!";
                    jogador2.setPokemonAtivo(pokemon2);
                    yield out;
                }
                case ITEM -> {
                    try {
                        item2.usar(jogador2.getPokemonAtivo());
                        jogador2.removerItem(item2);
                        yield jogador2.getNome() + " usou " + item2.getNome();
                    } catch (ExcecaoUsoItem e) {
                        yield jogador2.getNome() + " não pode usar o item " + item2.getNome();
                    }
                }
                case null -> null;
            };
        }
    }

    public boolean primeiroComeca() {
        if (acao2 != Acao.ATACAR) {
            return false;
        }
        if (acao1 != Acao.ATACAR) {
            return true;
        }

        int difPrioridade = ataque1.getPrioridade() - ataque2.getPrioridade();
        if (difPrioridade != 0) {
            return difPrioridade > 0;
        }
        return (jogador1.getPokemonAtivo().getStat(Stat.SPEED)
                - jogador2.getPokemonAtivo().getStat(Stat.SPEED)) > 0;
    }

    /**
     * Realiza um ataque.
     *
     * @param ataque  o ataque a ser realizado
     * @param usuario o Pokémon que está usando o ataque
     * @param alvo    o Pokémon que está sendo atacado
     */
    private String atacar(Ataque ataque, Pokemon usuario, Pokemon alvo) {
        String out = usuario.getNome() + " usou " + ataque.getNome() + ".\n";
        ataque.somaPp(-1);

        // Verifica se o ataque acontece e
        boolean ataqueAcertou = (Util.randInt(0, 101) < ataque.getPrecisao());
        boolean fezEfeito = ataqueAcertou && (Util.randInt(0, 101) < ataque.getProbEfeito());


        int ultimoDano = ataqueAcertou ? ataque.dano(usuario, alvo, clima) : 0;

        // Efeitos de held items virão aqui

        // Dá o dano
        ultimoDano = Math.min(ultimoDano, alvo.getHP_atual());
        if (ultimoDano > 0) {
            out += alvo.getNome() + " tomou " + ultimoDano + " de dano!";
            alvo.somaHP_atual(-ultimoDano);
        } else {
            out += "O ataque não causou dano.";
        }
        if (fezEfeito) {
            alvo.setEfeito(ataque.getEfeito());
        }
        return out;
    }
}

