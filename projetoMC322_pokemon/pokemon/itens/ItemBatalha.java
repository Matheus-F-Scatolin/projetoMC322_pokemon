package pokemon.itens;


import pokemon.ExcecaoUsoItem;
import pokemon.Pokemon;

/**
 * Um item que pode ser usado diretamente por um treinador.
 * Seu use consome um turno, e ele só pode ser usado uma vez.
 */
public abstract class ItemBatalha extends Item {
    // Construtor
    public ItemBatalha(String nome) {
        super(nome);
    }

    /**
     * Usa o item em um Pokémon. Sempre verifique se o item pode ser
     * usado agora, por meio do método
     * {@link ItemBatalha#usavel(Pokemon)}.
     *
     * @param pokemon o Pokémon no qual o item é usado
     */
    public abstract void usar(Pokemon pokemon) throws ExcecaoUsoItem;
    //Uso fantasma para poder instanciar um array de Itens de Batalha

    /**
     * Diz se o item pode ser usado agora.
     *
     * @param pokemon o Pokémon no qual o item é usado
     * @return {@code true} se for usável.
     */
    public boolean usavel(Pokemon pokemon) {
        return true;
    }
}
