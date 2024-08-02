package pokemon;

/**
 * As possíveis naturezas de um Pokémon.
 * Dependendo da natureza, os Pokémons podem ter
 * certos stats aumentados ou reduzidos.
 */
public enum Natureza {
    LONELY(Stat.HP, Stat.SPEED),
    ADAMANT(Stat.ATK, Stat.ATK_SP),
    NAUGHTY(Stat.ATK, Stat.DEF_SP),
    BRAVE(Stat.ATK, Stat.SPEED),
    HARDY(null, null),
    BOLD(Stat.DEF, Stat.ATK),
    IMPISH(Stat.DEF, Stat.ATK_SP),
    LAX(Stat.DEF, Stat.DEF_SP),
    RELAXED(Stat.DEF, Stat.SPEED),
    DOCILE(null, null),
    MILD(Stat.ATK_SP, Stat.DEF),
    MODEST(Stat.ATK_SP, Stat.ATK),
    RASH(Stat.ATK_SP, Stat.DEF_SP),
    QUIET(Stat.ATK_SP, Stat.SPEED),
    BASHFUL(null, null),
    GENTLE(Stat.DEF_SP, Stat.DEF),
    CALM(Stat.DEF_SP, Stat.ATK),
    CAREFUL(Stat.DEF_SP, Stat.ATK_SP),
    SASSY(Stat.DEF_SP, Stat.SPEED),
    SERIOUS(null, null),
    HASTY(Stat.SPEED, Stat.DEF),
    TIMID(Stat.SPEED, Stat.ATK),
    JOLLY(Stat.SPEED, Stat.ATK_SP),
    NAIVE(Stat.SPEED, Stat.DEF_SP),
    QUIRKY(null, null);

    private final Stat buff;    //Status que será buffado no pokémon devido a sua natureza
    private final Stat nerf;    //Status que será nerfado no pokémon devido a sua natureza

    //Construtor
    Natureza(Stat buff, Stat nerf) {
        this.buff = buff;
        this.nerf = nerf;
    }

    //Getter
    public double getMultiplicadorStat(Stat stat) {
        if (buff == stat) {
            return 1.1;
        }
        if (nerf == stat) {
            return 0.9;
        }
        return 1.0;
    }

    public static Natureza random() {
        Natureza[] naturezas = values();
        return naturezas[Util.randInt(0, naturezas.length)];
    }
}

