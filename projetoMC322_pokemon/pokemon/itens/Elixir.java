package pokemon.itens;

import pokemon.Pokemon;
import pokemon.ataques.Ataque;

/**
 * Um item que aumenta o PP de todos os ataques de um Pokémon.
 */
public class Elixir extends ItemBatalha {
    private int aumento;

    public Elixir(String nome, int aumento) {
        super(nome);
        this.aumento = aumento;
    }

    /**
     * Aumenta o PP de todos os ataques do Pokémon.
     * Não ultrapassa o PP máximo dos ataques.
     *
     * @param pokemon o Pokémon
     */
    @Override
    public void usar(Pokemon pokemon) {
        for (Ataque ataque : pokemon.getAtaques()) {
            if (ataque.getPp() + aumento < ataque.getPpMax()) {
                ataque.somaPp(aumento);
            } else {
                ataque.restauraPp();    // Impede que o máximo seja ultrapassado
            }
        }
    }
}
