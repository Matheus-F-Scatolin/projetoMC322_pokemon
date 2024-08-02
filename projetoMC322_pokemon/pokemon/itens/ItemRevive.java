package pokemon.itens;

import pokemon.ExcecaoUsoItem;
import pokemon.Pokemon;
import pokemon.Stat;

/**
 * Um item que revive um Pokémon desmaiado.
 * Sua implementação está incompleta.
 */
public class ItemRevive extends ItemBatalha {
    private boolean isMax;

    // Construtor;
    public ItemRevive(String nome, boolean isMax) {
        super(nome);
        this.isMax = isMax;
    }

    @Override
    public void usar(Pokemon pokemon) throws ExcecaoUsoItem {
        if (!usavel(pokemon)) {
            throw new ExcecaoUsoItem();
        }
        if (isMax) {
            pokemon.setHP_atual(pokemon.getStat(Stat.HP));
        } else {
            pokemon.setHP_atual(pokemon.getStat(Stat.HP) / 2);
        }
    }

    @Override
    public boolean usavel(Pokemon pokemon) {
        return (pokemon.getHP_atual() == 0);
    }

}
