package pokemon.ataques;

import pokemon.Clima;
import pokemon.Efeito;
import pokemon.Pokemon;
import pokemon.Tipo;

/**
 * Um ataque que não dá dano direto, mas causa efeitos no oponente.
 */
public class AtaqueEfeito extends Ataque {

    public AtaqueEfeito(String nome, Tipo tipo, int poder, int ppMax, int prioridade, int precisao, Efeito efeito, int probEfeito) {
        super(nome, tipo, poder, ppMax, prioridade, precisao, efeito, probEfeito);
    }


    /**
     * @return 0 (ataques de efeito não causam dano).
     */
    @Override
    public int dano(Pokemon usuario, Pokemon alvo, Clima clima) {
        return 0;
    }


    @Override
    public AtaqueEfeito copiar() {
        return new AtaqueEfeito(nome, tipo, poder, ppMax, prioridade, precisao, efeito, probEfeito);
    }
}
