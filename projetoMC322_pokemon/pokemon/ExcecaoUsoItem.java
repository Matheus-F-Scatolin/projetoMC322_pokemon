package pokemon;

/**
 * Exceção lançada quando um item é usado de forma incorreta.
 */
public class ExcecaoUsoItem extends Exception {
    // Construtor padrão
    public ExcecaoUsoItem() {
        super("Esse item não pode ser usado agora.");
    }
}
