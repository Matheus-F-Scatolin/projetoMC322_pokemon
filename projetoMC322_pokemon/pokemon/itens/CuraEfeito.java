package pokemon.itens;


import pokemon.Efeito;
import pokemon.ExcecaoUsoItem;
import pokemon.Pokemon;

/**
 * Um item que cancela o efeito aplicado em um Pokémon.
 */
public class CuraEfeito extends ItemBatalha {
    private Efeito efeito; // Determina o efeito que o remédio cura
    private boolean isFull; // Determina se é o FullHeal (cura qualquer efeito que o pokemon tenha)


    // Construtor
    public CuraEfeito(String nome, Efeito efeito, boolean isFull) {
        super(nome);
        this.efeito = efeito;
        this.isFull = isFull;
    }

    @Override
    public void usar(Pokemon pokemon) throws ExcecaoUsoItem {
        if (!usavel(pokemon)) {
            throw new ExcecaoUsoItem();
        }
        pokemon.setEfeito(null);
    }

    @Override
    public boolean usavel(Pokemon pokemon) {
        if (isFull) {
            return pokemon.getEfeito() != null;
        }
        return (pokemon.getEfeito() == efeito);
    }
}
