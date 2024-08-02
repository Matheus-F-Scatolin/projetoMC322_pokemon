package pokemon;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    /**
     * Retorna um inteiro pseudoaleatório entre min (incluindo)
     * e max (excluindo).
     */
    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * @return {@code true} com probabilidade (numerador/denominador).
     */
    public static boolean randBool(int numerador, int denominador) {
        return numerador > randInt(0, denominador);
    }
}
