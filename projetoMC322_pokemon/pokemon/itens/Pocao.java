package pokemon.itens;

import pokemon.ExcecaoUsoItem;
import pokemon.Pokemon;
import pokemon.Stat;

/**
 * Um item que cura um Pokémon.
 */
public class Pocao extends ItemBatalha {
    private boolean isMax; // Se restaura toda a vida do Pokémon
    private int cura; // Quanto a poção vai curar

    //Construtor
    public Pocao(String nome, boolean isMax, int cura) {
        super(nome);
        this.isMax = isMax;
        this.cura = cura;
    }

    @Override
    public void usar(Pokemon pokemon) throws ExcecaoUsoItem {
        if (!usavel(pokemon)) {
            throw new ExcecaoUsoItem();
        }
        int hpFinal = pokemon.getHP_atual() + cura;
        if (isMax || hpFinal >= pokemon.getStat(Stat.HP)) {
            pokemon.setHP_atual(pokemon.getStat(Stat.HP));
        } else {
            pokemon.setHP_atual(hpFinal);
        }
    }

    @Override
    public boolean usavel(Pokemon pokemon) {
        return (pokemon.estaVivo()) && (pokemon.getHP_atual() < pokemon.getStat(Stat.HP));
    }
}
