package pokemon;

import java.util.Collection;

/**
 * Os tipos (elementos) dos ataques e Pokémons.
 */
public enum Tipo {
    // NÂO MUDAR ORDEM
    NORMAL, FOGO, AGUA, PLANTA, ELETRICO, GELO,
    LUTADOR, VENENOSO, TERRA, VOADOR, PSIQUICO, INSETO,
    PEDRA, FANTASMA, DRAGAO, SOMBRIO, METALICO, FADA;

    /**
     * Calcula a eficácia de um ataque deste tipo.
     * Esse valor deve multiplicar o dano total do ataque.
     *
     * @param tiposAlvo os tipos do Pokémon atacado
     * @return o multiplicador de eficácia
     */
    public float multiplicador(Collection<Tipo> tiposAlvo) {
        float multiplicador = 1f;
        for (Tipo t : tiposAlvo) {
            multiplicador *= multiplicador(this, t);
        }
        return multiplicador;
    }

    private static float multiplicador(Tipo tipoAtk, Tipo tipoDef) {
        return switch (tipoAtk) {
            case NORMAL -> switch (tipoDef) {
                case FANTASMA -> 0.0f;
                case PEDRA, METALICO -> 0.5f;
                default -> 1.0f;
            };
            case FOGO -> switch (tipoDef) {
                case FOGO, AGUA, PEDRA, DRAGAO -> 0.5f;
                case PLANTA, GELO, INSETO, METALICO -> 2.0f;
                default -> 1.0f;
            };
            case AGUA -> switch (tipoDef) {
                case AGUA, PLANTA, DRAGAO -> 0.5f;
                case FOGO, TERRA, PEDRA -> 2.0f;
                default -> 1.0f;
            };
            case PLANTA -> switch (tipoDef) {
                case FOGO, PLANTA, VENENOSO, VOADOR, INSETO, DRAGAO, METALICO -> 0.5f;
                case AGUA, TERRA, PEDRA -> 2.0f;
                default -> 1.0f;
            };
            case ELETRICO -> switch (tipoDef) {
                case TERRA -> 0.0f;
                case PLANTA, ELETRICO, DRAGAO -> 0.5f;
                case AGUA, VOADOR -> 2.0f;
                default -> 1.0f;
            };
            case GELO -> switch (tipoDef) {
                case FOGO, AGUA, GELO, METALICO -> 0.5f;
                case PLANTA, TERRA, VOADOR, DRAGAO -> 2.0f;
                default -> 1.0f;
            };
            case LUTADOR -> switch (tipoDef) {
                case FANTASMA -> 0.0f;
                case VENENOSO, VOADOR, PSIQUICO, INSETO, FADA -> 0.5f;
                case NORMAL, GELO, PEDRA, SOMBRIO, METALICO -> 2.0f;
                default -> 1.0f;
            };
            case VENENOSO -> switch (tipoDef) {
                case METALICO -> 0.0f;
                case VENENOSO, TERRA, PEDRA, FANTASMA -> 0.5f;
                case PLANTA, FADA -> 2.0f;
                default -> 1.0f;
            };
            case TERRA -> switch (tipoDef) {
                case VOADOR -> 0.0f;
                case PLANTA, INSETO -> 0.5f;
                case FOGO, ELETRICO, VENENOSO, PEDRA, METALICO -> 2.0f;
                default -> 1.0f;
            };
            case VOADOR -> switch (tipoDef) {
                case ELETRICO, PEDRA, METALICO -> 0.5f;
                case PLANTA, LUTADOR, INSETO -> 2.0f;
                default -> 1.0f;
            };
            case PSIQUICO -> switch (tipoDef) {
                case SOMBRIO -> 0.0f;
                case PSIQUICO, METALICO -> 0.5f;
                case LUTADOR, VENENOSO -> 2.0f;
                default -> 1.0f;
            };
            case INSETO -> switch (tipoDef) {
                case FOGO, LUTADOR, VENENOSO, VOADOR, FANTASMA, METALICO, FADA -> 0.5f;
                case PLANTA, PSIQUICO, SOMBRIO -> 2.0f;
                default -> 1.0f;
            };
            case PEDRA -> switch (tipoDef) {
                case LUTADOR, TERRA, METALICO -> 0.5f;
                case FOGO, GELO, VOADOR, INSETO -> 2.0f;
                default -> 1.0f;
            };
            case FANTASMA -> switch (tipoDef) {
                case NORMAL -> 0.0f;
                case DRAGAO -> 0.5f;
                case PSIQUICO, FANTASMA -> 2.0f;
                default -> 1.0f;
            };
            case DRAGAO -> switch (tipoDef) {
                case FADA -> 0.0f;
                case METALICO -> 0.5f;
                case DRAGAO -> 2.0f;
                default -> 1.0f;
            };
            case SOMBRIO -> switch (tipoDef) {
                case LUTADOR, SOMBRIO, FADA -> 0.5f;
                case PSIQUICO, FANTASMA -> 2.0f;
                default -> 1.0f;
            };
            case METALICO -> switch (tipoDef) {
                case FOGO, AGUA, ELETRICO, METALICO -> 0.5f;
                case GELO, PEDRA, FADA -> 2.0f;
                default -> 1.0f;
            };
            case FADA -> switch (tipoDef) {
                case FOGO, VENENOSO, METALICO -> 0.5f;
                case LUTADOR, DRAGAO, SOMBRIO -> 2.0f;
                default -> 1.0f;
            };
        };

    }
}
