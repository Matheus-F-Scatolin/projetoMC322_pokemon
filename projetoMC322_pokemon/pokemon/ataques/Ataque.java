package pokemon.ataques;

import pokemon.Clima;
import pokemon.Efeito;
import pokemon.Pokemon;
import pokemon.Tipo;

import java.io.Serial;
import java.io.Serializable;

/**
 * Um ataque que pode ser usado por um Pokémon
 */
public abstract class Ataque implements Serializable {
    /**
     * Serial version UID
     */
    @Serial
    private static final long serialVersionUID = 2L;
    /**
     * O tipo do ataque.
     */
    protected Tipo tipo;
    /**
     * O poder (força) do ataque.
     */
    protected int poder;
    /**
     * O número de PPs disponíveis (pontos de poder,
     * que limitam quantas vezes é possível usar o ataque).
     */
    protected int pp;
    /**
     * O máximo de PPs que o ataque pode ter.
     */
    protected int ppMax;
    /**
     * A prioridade do ataque.
     * Ataques com prioridades mais altas acontecem primeiro.
     */
    protected int prioridade;
    /**
     * A probabilidade (em porcentagem) de o ataque
     * acertar o alvo, desprezando evasão e outros efeitos.
     */
    protected int precisao;
    /**
     * Efeito que um ataque pode ter
     */
    protected Efeito efeito;
    /**
     * A probabilidade (em porcentagem) do efeito ser aplicado
     */
    protected int probEfeito;
    /**
     * String que representa o nome do ataque
     */
    protected String nome;

    /**
     * Retorna as informações do ataque.
     *
     * @return uma string com as informações do ataque.
     */
    public String toString() {
        return nome + " (" + tipo + ")";
    }


    public Ataque(String nome, Tipo tipo, int poder, int ppMax, int prioridade, int precisao, Efeito efeito, int probEfeito) {
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.pp = ppMax;
        this.ppMax = ppMax;
        this.prioridade = prioridade;
        this.precisao = precisao;
        this.efeito = efeito;
        this.probEfeito = probEfeito;
    }


    //Getter
    public String getNome() {
        return this.nome;
    }

    /**
     * Calcula o dano do ataque, caso ele acerte.
     *
     * @param usuario o Pokémon usando o ataque
     * @param alvo    o Pokémon alvo
     * @param clima   o clima atual
     * @return o dano.
     */
    public abstract int dano(Pokemon usuario, Pokemon alvo, Clima clima);

    /**
     * Cria uma cópia do ataque.
     * Ataques precisam ser copiados para manter
     * a contagem de PP consistente.
     *
     * @return uma cópia do ataque.
     */
    public abstract Ataque copiar();

    /**
     * @return um inteiro representando a prioridade do ataque.
     */
    public int getPrioridade() {
        return prioridade;
    }

    /**
     * @return o efeito que o ataque pode causar.
     */
    public Efeito getEfeito() {
        return efeito;
    }

    /**
     * @return a probabilidade (em porcentagem) de o ataque
     * atingir o oponente.
     */
    public int getPrecisao() {
        return precisao;
    }

    /**
     * @return probabilidade (em porcentagem) de, caso o ataque
     * acerte, causar um efeito no oponente.
     */
    public int getProbEfeito() {
        return probEfeito;
    }

    /**
     * @return o PP disponível para o ataque.
     */
    public int getPp() {
        return pp;
    }

    /**
     * @return O tipo do ataque
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param pp o novo PP.
     */
    public void setPp(int pp) {
        this.pp = pp;
    }

    /**
     * @return o máximo de PP que o ataque pode ter.
     */
    public int getPpMax() {
        return ppMax;
    }

    /**
     * Soma um valor ao PP atual do ataque.
     */
    public void somaPp(int soma) {
        this.pp += soma;
    }

    /**
     * Restaura o PP do ataque para o valor máximo.
     */
    public void restauraPp() {
        this.pp = ppMax;
    }
}
