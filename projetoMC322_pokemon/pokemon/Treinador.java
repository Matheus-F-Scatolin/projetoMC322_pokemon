package pokemon;

import pokemon.itens.ItemBatalha;

import java.util.ArrayList;
import java.util.List;

/**
 * Um treinador de Pokémons, que representa um jogador.
 */
public class Treinador {
    private String nome;        //String que representa o nome do treinador
    private ArrayList<Pokemon> time;    //Array que guarda os Pokémons no time do treinador
    private ArrayList<ItemBatalha> itens;    //Array que guarda os itens de batalha do treinador
    private Pokemon pokemonAtivo; //Pokemon que está em batalha

    //Construtor
    public Treinador(String nome) {
        this.nome = nome;
        this.time = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.pokemonAtivo = null;
    }

    //Getters
    public String getNome() {
        return nome;
    }

    public ArrayList<ItemBatalha> getItens() {
        return new ArrayList<>(itens);
    }

    public List<Pokemon> getPokemons() {
        return time;
    }

    public Pokemon getPokemonAtivo() {
        return pokemonAtivo;
    }

    public void setPokemonAtivo(Pokemon pokemon) {
        if (!time.contains(pokemon)) {
            throw new IllegalArgumentException("O Pokémon " + pokemon
                    + " não pertence ao jogador" + getNome() + ".");
        }
        pokemonAtivo = pokemon;
    }

    /**
     * Método para adicionar o pokémon escolhido ao time do treinador
     *
     * @param pokemon Instância da classe Pokemon
     * @return True, se foi possível adicionar o pokémon; ou false, caso contrário
     */
    public boolean adicionarPokemon(Pokemon pokemon) {
        return time.add(pokemon);
    }


    /**
     * Método para adicionar o item de batatlha escolhido aos itens do treinador
     *
     * @param item Item de batalha que o treinador escolheu
     */
    public void adicionarItem(ItemBatalha item) {
        itens.add(item);
    }


    /**
     * Método para remover o item de batlha que foi usado pelo treinador
     *
     * @param item Item de batalha que o treinador gastou
     * @return True, se foi possível remover o item; ou false, caso contrário
     */
    public boolean removerItem(ItemBatalha item) {
        return itens.remove(item);
    }


    /**
     * Método para verificar se o treinador ainda tem pokémons não desmaiados
     *
     * @return True, caso tenha perdido todos os pokémons; ou 0, caso contrário
     */
    public boolean treinadorDerrotado() {
        for (Pokemon pokemon : time) {
            if (pokemon.estaVivo()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Cura os Pokémons do treinador
     */
    public void curarPokemons() {
        for (Pokemon pokemon : time) {
            pokemon.restauraHP();
            pokemon.restaurarAtaques();
        }
    }


    /**
     * Método que define como as informações do treinador serão exibidas
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append("Nome: ").append(nome).append("\n");
        out.append("Pokémons:");

        for (Pokemon pokemon : time) {
            out.append(" ").append(pokemon.getNome());
        }

        out.append(".\n");

        out.append("Itens de batalha:");

        for (ItemBatalha item : itens) {
            out.append(" ").append(item.getNome());
        }

        out.append(".\n");

        return out.toString();
    }
}
