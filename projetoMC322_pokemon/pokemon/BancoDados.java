package pokemon;

import pokemon.ataques.Ataque;
import pokemon.ataques.AtaqueEfeito;
import pokemon.ataques.AtaqueEspecial;
import pokemon.ataques.AtaqueFisico;
import pokemon.itens.BoostEV;
import pokemon.itens.CuraEfeito;
import pokemon.itens.ItemBatalha;
import pokemon.itens.Pocao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Um banco de dados que guarda instâncias pré-definidas
 * de Pokémons, Ataques e Itens.
 *
 * <p>Antes de utilizar o banco de dados, é necessário
 * inicializá-lo, usando o método {@link BancoDados#inicializar()}.
 * Como todas as instâncias são salvas na própria classe, não é
 * necessário instanciar o banco de dados em si.
 */
public class BancoDados implements Serializable {
    private static Map<String, Pokemon> pokemonsIniciais;
    private static Map<String, Pokemon> pokemons;
    private static Map<String, Ataque> ataques;
    private static Map<String, ItemBatalha> itensBatalha;

    // Getters

    /**
     * Retorna um mapa com os Pokémons iniciais pré-instanciados.
     * Os Pokémons iniciais são as formas mais evoluídas dos Pokémons que podem
     * ser escolhidos no começo dos jogos de Pokémon das gerações III e VI.
     * A chave de cada Pokémon é seu nome, com a primeira letra maiúscula.
     *
     * <p>Note que o mapa retornado é apenas uma cópia rasa do mapa
     * interno do banco de dados, ou seja, os Pokémons contidos nele
     * precisam ser copiados antes de serem modificados (por exemplo,
     * usando o construtor {@link Pokemon#Pokemon(Pokemon)}).
     *
     * @return mapa com os Pokémons iniciais.
     */
    public static Map<String, Pokemon> getPokemonsIniciais() {
        return new HashMap<>(pokemonsIniciais);
    }

    /**
     * Retorna um mapa com os Pokémons não-iniciais (no caso, todos lendários)
     * pré-instanciados. A chave de cada Pokémon é seu nome, com a primeira letra maiúscula.
     *
     * <p>Note que o mapa retornado é apenas uma cópia rasa do mapa
     * interno do banco de dados, ou seja, os Pokémons contidos nele
     * precisam ser copiados antes de serem modificados (por exemplo,
     * usando o construtor {@link Pokemon#Pokemon(Pokemon)}).
     *
     * @return mapa com os Pokémons não iniciais.
     */
    public static Map<String, Pokemon> getPokemons() {
        return pokemons;
    }

    /**
     * Retorna um mapa com os ataques pré-instanciados.
     * A chave de cada ataque é seu nome, com a primeira letra de cada palavra maiúscula.
     *
     * <p>Note que o mapa retornado é apenas uma cópia rasa do mapa
     * interno do banco de dados, ou seja, os ataques contidos nele
     * precisam ser copiados antes de serem modificados usando o método
     * {@link Ataque#copiar()}.
     *
     * @return mapa com os ataques.
     */
    public static Map<String, Ataque> getAtaques() {
        return ataques;
    }

    /**
     * Retorna um mapa com os itens pré-instanciados.
     * A chave de cada item é seu nome, com a primeira letra de cada palavra maiúscula.
     *
     * @return mapa com os itens.
     */
    public static Map<String, ItemBatalha> getItensBatalha() {
        return itensBatalha;
    }

    /**
     * Retorna um novo Pokémon da espécie escolhida
     *
     * @param especie o nome da espécie do Pokémon, com a primeira letra maiúscula.
     * @return o novo Pokémon criado; {@code null} se a espécie
     * não estiver no banco de dados.
     */
    public Pokemon copiaPokemon(String especie) {
        if (pokemons.containsKey(especie)) {
            return new Pokemon(pokemons.get(especie));
        }
        return null;
    }

    private static void inicializarItens() {
        //Criando 3 itens de exemplo
        itensBatalha = new HashMap<>();
        itensBatalha.put("HP Up", new BoostEV("HP Up", Stat.HP, 10));
        itensBatalha.put("Protein", new BoostEV("Protein", Stat.ATK, 10));
        itensBatalha.put("Iron", new BoostEV("Iron", Stat.DEF, 10));
        itensBatalha.put("Calcium", new BoostEV("Calcium", Stat.ATK_SP, 10));
        itensBatalha.put("Zinc", new BoostEV("Zinc", Stat.DEF_SP, 10));
        itensBatalha.put("Carbos", new BoostEV("Carbos", Stat.SPEED, 10));
        itensBatalha.put("Potion", new Pocao("Potion", false, 20));
        itensBatalha.put("Super Potion", new Pocao("Super Potion", false, 60));
        itensBatalha.put("Hyper Potion", new Pocao("Hyper Potion", false, 120));
        itensBatalha.put("Max Potion", new Pocao("Max Potion", true, 9999));
        itensBatalha.put("Antidote", new CuraEfeito("Antidote", Efeito.ENVENENADO, false));
        itensBatalha.put("Burn Heal", new CuraEfeito("Burn Heal", Efeito.QUEIMADO, false));
        itensBatalha.put("Awakening", new CuraEfeito("Awakening", Efeito.DORMINDO, false));
        itensBatalha.put("Paralyze Heal", new CuraEfeito("Paralyze Heal", Efeito.PARALISADO, false));
        itensBatalha.put("Ice Heal", new CuraEfeito("Ice Heal", Efeito.CONGELADO, false));
        itensBatalha.put("Full Heal", new CuraEfeito("Full Heal", Efeito.CONFUSO, true));


    }

    /**
     * Inicializa o banco de dados. Deve ser chamado uma única vez
     * antes que os demais métodos da classe sejam chamados.
     */
    @SuppressWarnings("unchecked")
    // Isso foi necessário devido ao cast de Object para HashMap (fizemos as verificações necessárias para garantir que o cast é seguro)
    public static void inicializar() {
        // Se o arquivo "bancoDados.bytej" existir, carregar o banco de dados a partir dele
        Path path = Paths.get("bancoDados.bytej");
        if (Files.exists(path)) {
            try {
                FileInputStream fileIn = new FileInputStream("bancoDados.bytej");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                try {
                    ataques = (HashMap<String, Ataque>) in.readObject();
                    pokemons = (HashMap<String, Pokemon>) in.readObject();
                    pokemonsIniciais = (HashMap<String, Pokemon>) in.readObject();
                    itensBatalha = (HashMap<String, ItemBatalha>) in.readObject();
                    in.close();
                    fileIn.close();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            //Array com os tipos do pokémon que está sendo criado
            List<Tipo> tiposPokemon = new ArrayList<>();
            //HashMap com os status base do pokémon que está sendo criado
            Map<Stat, Integer> statsBasePokemon = new HashMap<>();
            //Array com os ataques do pokémon que está sendo criado
            ArrayList<Ataque> ataquesPokemon = new ArrayList<>();

            if (itensBatalha == null) {
                inicializarItens();
            }

            if (ataques == null) {
                ataques = new HashMap<>();
                ataques.put("Draco Meteor", new AtaqueEspecial("Draco Meteor", Tipo.DRAGAO, 130, 5, 0, 90, null, 0));
                ataques.put("Fire Blast", new AtaqueEspecial("Fire Blast", Tipo.FOGO, 110, 5, 0, 85, Efeito.QUEIMADO, 10));
                ataques.put("Thunder", new AtaqueEspecial("Thunder", Tipo.ELETRICO, 110, 5, 0, 70, Efeito.PARALISADO, 30));
                ataques.put("Flash Cannon", new AtaqueEspecial("Flash Cannon", Tipo.METALICO, 80, 10, 0, 100, null, 0));
                ataques.put("Spacial Rend", new AtaqueEspecial("Spacial Rend", Tipo.DRAGAO, 100, 5, 0, 95, null, 0));
                ataques.put("Hydro Pump", new AtaqueEspecial("Hydro Pump", Tipo.AGUA, 110, 5, 0, 80, null, 0));
                ataques.put("Earth Power", new AtaqueEspecial("Earth Power", Tipo.TERRA, 90, 10, 0, 100, null, 0));
                ataques.put("Leaf Blade", new AtaqueFisico("Leaf Blade", Tipo.PLANTA, 90, 15, 0, 100, null, 0));
                ataques.put("Sacred Sword", new AtaqueFisico("Sacred Sword", Tipo.LUTADOR, 90, 15, 0, 100, null, 0));
                ataques.put("Knock Off", new AtaqueFisico("Knock Off", Tipo.SOMBRIO, 65, 20, 0, 100, null, 0));
                ataques.put("Smart Strike", new AtaqueFisico("Smart Strike", Tipo.METALICO, 70, 10, 0, 100, null, 0));
                ataques.put("Behemoth Blade", new AtaqueFisico("Behemoth Blade", Tipo.METALICO, 100, 5, 0, 100, null, 0));
                ataques.put("Play Rough", new AtaqueFisico("Play Rough", Tipo.FADA, 90, 10, 0, 100, null, 0));
                ataques.put("Wild Charge", new AtaqueFisico("Wild Charge", Tipo.ELETRICO, 90, 15, 0, 100, null, 0));
                ataques.put("Close Combat", new AtaqueFisico("Close Combat", Tipo.LUTADOR, 120, 5, 0, 100, null, 0));
                ataques.put("Psystrike", new AtaqueEspecial("Psystrike", Tipo.PSIQUICO, 100, 10, 0, 100, null, 0));
                ataques.put("Shadow Ball", new AtaqueEspecial("Shadow Ball", Tipo.FANTASMA, 80, 15, 0, 100, null, 0));
                ataques.put("Ice Beam", new AtaqueEspecial("Ice Beam", Tipo.GELO, 90, 10, 0, 100, Efeito.CONGELADO, 10));
                ataques.put("Aura Sphere", new AtaqueEspecial("Aura Sphere", Tipo.LUTADOR, 80, 20, 0, 100, null, 0));
                ataques.put("Origin Pulse", new AtaqueEspecial("Origin Pulse", Tipo.AGUA, 110, 10, 0, 85, null, 0));
                ataques.put("Water Spout", new AtaqueEspecial("Water Spout", Tipo.AGUA, 150, 5, 0, 100, null, 0));
                ataques.put("Precipice Blades", new AtaqueFisico("Precipice Blades", Tipo.TERRA, 120, 10, 0, 85, null, 0));
                ataques.put("Stone Edge", new AtaqueFisico("Stone Edge", Tipo.PEDRA, 100, 5, 0, 80, null, 0));
                ataques.put("Earthquake", new AtaqueFisico("Earthquake", Tipo.TERRA, 100, 10, 0, 100, null, 0));
                ataques.put("Toxic", new AtaqueEfeito("Toxic", Tipo.VENENOSO, 0, 10, 0, 90, Efeito.ENVENENADO, 90));
                ataques.put("Flamethrower", new AtaqueEspecial("Flamethrower", Tipo.FOGO, 90, 15, 0, 90, Efeito.QUEIMADO, 10));
                ataques.put("Blizzard", new AtaqueEspecial("Blizzard", Tipo.GELO, 110, 5, 0, 70, Efeito.CONGELADO, 10));
                ataques.put("Psychic", new AtaqueEspecial("Psychic", Tipo.PSIQUICO, 90, 10, 0, 100, null, 0));
                ataques.put("Hypnosis", new AtaqueEfeito("Hypnosis", Tipo.PSIQUICO, 0, 20, 0, 60, Efeito.DORMINDO, 60));
                ataques.put("Dark Pulse", new AtaqueEspecial("Dark Pulse", Tipo.SOMBRIO, 80, 15, 0, 100, null, 0));
                ataques.put("Sludge Bomb", new AtaqueEspecial("Sludge Bomb", Tipo.VENENOSO, 90, 10, 0, 100, Efeito.ENVENENADO, 30));
                ataques.put("Hyper Beam", new AtaqueEspecial("Hyper Beam", Tipo.NORMAL, 150, 5, 0, 90, null, 0));
                ataques.put("Aeroblast", new AtaqueEspecial("Aeroblast", Tipo.VOADOR, 100, 5, 0, 95, null, 0));
                ataques.put("Blue Flare", new AtaqueEspecial("Blue Flare", Tipo.FOGO, 130, 5, 0, 85, Efeito.QUEIMADO, 20));
                ataques.put("Extreme Speed", new AtaqueFisico("Extreme Speed", Tipo.NORMAL, 80, 5, 2, 100, null, 0));
                ataques.put("Drill Peck", new AtaqueFisico("Drill Peck", Tipo.VOADOR, 80, 20, 0, 100, null, 0));
                ataques.put("Outrage", new AtaqueFisico("Outrage", Tipo.DRAGAO, 120, 10, 0, 100, null, 0));
                ataques.put("Bolt Strike", new AtaqueFisico("Bolt Strike", Tipo.ELETRICO, 130, 5, 0, 85, Efeito.PARALISADO, 20));
                ataques.put("Moonblast", new AtaqueEspecial("Moonblast", Tipo.FADA, 95, 15, 0, 100, null, 0));
                ataques.put("Focus Blast", new AtaqueEspecial("Focus Blast", Tipo.LUTADOR, 120, 5, 0, 70, null, 0));
                ataques.put("Surf", new AtaqueEspecial("Surf", Tipo.AGUA, 90, 15, 0, 100, null, 0));
                ataques.put("Oblivion Wing", new AtaqueEspecial("Oblivion Wing", Tipo.VOADOR, 80, 10, 0, 100, null, 0));
                ataques.put("U-turn", new AtaqueFisico("U-turn", Tipo.INSETO, 70, 20, 0, 100, null, 0));
                ataques.put("Sacred Fire", new AtaqueFisico("Sacred Fire", Tipo.FOGO, 100, 5, 0, 90, Efeito.QUEIMADO, 50));
                ataques.put("Brave Bird", new AtaqueFisico("Brave Bird", Tipo.VOADOR, 120, 15, 0, 100, null, 0));
                ataques.put("Gunk Shot", new AtaqueFisico("Gunk Shot", Tipo.VENENOSO, 120, 5, 0, 80, Efeito.ENVENENADO, 30));
                ataques.put("Fire Punch", new AtaqueFisico("Fire Punch", Tipo.FOGO, 75, 15, 0, 100, Efeito.QUEIMADO, 10));
                ataques.put("Hyperspace Fury", new AtaqueFisico("Hyperspace Fury", Tipo.SOMBRIO, 100, 5, 0, 100, null, 0));
                ataques.put("Psychic Fangs", new AtaqueFisico("Psychic Fangs", Tipo.PSIQUICO, 85, 10, 0, 100, null, 0));
                ataques.put("High Horsepower", new AtaqueFisico("High Horsepower", Tipo.TERRA, 95, 10, 0, 95, null, 0));
                ataques.put("Glacial Lance", new AtaqueFisico("Glacial Lance", Tipo.GELO, 130, 5, 0, 100, null, 0));
                ataques.put("Psyshock", new AtaqueEspecial("Psyshock", Tipo.PSIQUICO, 80, 10, 0, 100, null, 0));
                ataques.put("Astral Barrage", new AtaqueEspecial("Astral Barrage", Tipo.FANTASMA, 120, 5, 0, 100, null, 0));
                ataques.put("Ice Punch", new AtaqueFisico("Ice Punch", Tipo.GELO, 75, 15, 0, 100, Efeito.CONGELADO, 10));
                ataques.put("Thunder Punch", new AtaqueFisico("Thunder Punch", Tipo.ELETRICO, 75, 15, 0, 100, Efeito.PARALISADO, 10));
                ataques.put("Body Slam", new AtaqueFisico("Body Slam", Tipo.NORMAL, 85, 15, 0, 100, Efeito.PARALISADO, 30));
                ataques.put("Megahorn", new AtaqueFisico("Megahorn", Tipo.INSETO, 120, 10, 0, 85, null, 0));
                ataques.put("Diamond Storm", new AtaqueFisico("Diamond Storm", Tipo.PEDRA, 100, 5, 0, 95, null, 0));
                ataques.put("Sunsteel Strike", new AtaqueFisico("Sunsteel Strike", Tipo.METALICO, 100, 5, 0, 100, null, 0));
                ataques.put("Body Press", new AtaqueFisico("Body Press", Tipo.LUTADOR, 80, 10, 0, 100, null, 0));
                ataques.put("Heat Wave", new AtaqueEspecial("Heat Wave", Tipo.FOGO, 95, 10, 0, 90, Efeito.QUEIMADO, 10));
                ataques.put("Flare Blitz", new AtaqueFisico("Flare Blitz", Tipo.FOGO, 120, 15, 0, 100, Efeito.QUEIMADO, 10));
                ataques.put("Darkest Lariat", new AtaqueFisico("Darkest Lariat", Tipo.SOMBRIO, 85, 10, 0, 100, null, 0));
                ataques.put("Water Shuriken", new AtaqueEspecial("Water Shuriken", Tipo.AGUA, 85, 20, 1, 100, null, 0));
                ataques.put("Waterfall", new AtaqueFisico("Waterfall", Tipo.AGUA, 80, 15, 0, 100, null, 0));
                ataques.put("Superpower", new AtaqueFisico("Superpower", Tipo.LUTADOR, 120, 5, 0, 100, null, 0));
                ataques.put("Aqua Tail", new AtaqueFisico("Aqua Tail", Tipo.AGUA, 90, 10, 0, 90, null, 0));
                ataques.put("Splishy Splash", new AtaqueEspecial("Splishy Splash", Tipo.AGUA, 80, 15, 0, 100, Efeito.PARALISADO, 30));
                ataques.put("Leaf Storm", new AtaqueEspecial("Leaf Storm", Tipo.PLANTA, 130, 5, 0, 90, null, 0));
                ataques.put("Dragon Pulse", new AtaqueEspecial("Dragon Pulse", Tipo.DRAGAO, 85, 10, 0, 100, null, 0));
                ataques.put("Spark", new AtaqueFisico("Spark", Tipo.ELETRICO, 65, 20, 0, 100, Efeito.PARALISADO, 30));
                ataques.put("Wood Hammer", new AtaqueFisico("Wood Hammer", Tipo.PLANTA, 120, 15, 0, 100, null, 0));
                ataques.put("Hammer Arm", new AtaqueFisico("Hammer Arm", Tipo.LUTADOR, 100, 10, 0, 90, null, 0));
            }

            if (pokemons == null) {
                pokemons = new HashMap<>();


                // Criando o Dialga
                tiposPokemon.add(Tipo.METALICO);
                tiposPokemon.add(Tipo.DRAGAO);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 120);
                statsBasePokemon.put(Stat.DEF, 120);
                statsBasePokemon.put(Stat.ATK_SP, 150);
                statsBasePokemon.put(Stat.DEF_SP, 100);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Draco Meteor"));
                ataquesPokemon.add(ataques.get("Fire Blast"));
                ataquesPokemon.add(ataques.get("Thunder"));
                ataquesPokemon.add(ataques.get("Flash Cannon"));

                pokemons.put("Dialga", new Pokemon("Dialga", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/dialga.png"));


                // Criando o Palkia
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.AGUA);
                tiposPokemon.add(Tipo.DRAGAO);

                statsBasePokemon.put(Stat.HP, 90);
                statsBasePokemon.put(Stat.ATK, 120);
                statsBasePokemon.put(Stat.DEF, 100);
                statsBasePokemon.put(Stat.ATK_SP, 150);
                statsBasePokemon.put(Stat.DEF_SP, 120);
                statsBasePokemon.put(Stat.SPEED, 100);

                ataquesPokemon.add(ataques.get("Spacial Rend"));
                ataquesPokemon.add(ataques.get("Hydro Pump"));
                ataquesPokemon.add(ataques.get("Fire Blast"));
                ataquesPokemon.add(ataques.get("Earth Power"));

                pokemons.put("Palkia", new Pokemon("Palkia", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/palkia.png"));


                //Criando a Kartana
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PLANTA);
                tiposPokemon.add(Tipo.METALICO);

                statsBasePokemon.put(Stat.HP, 59);
                statsBasePokemon.put(Stat.ATK, 181);
                statsBasePokemon.put(Stat.DEF, 131);
                statsBasePokemon.put(Stat.ATK_SP, 59);
                statsBasePokemon.put(Stat.DEF_SP, 31);
                statsBasePokemon.put(Stat.SPEED, 109);

                ataquesPokemon.add(ataques.get("Leaf Blade"));
                ataquesPokemon.add(ataques.get("Sacred Sword"));
                ataquesPokemon.add(ataques.get("Smart Strike"));
                ataquesPokemon.add(ataques.get("Knock Off"));

                pokemons.put("Kartana", new Pokemon("Kartana", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/kartana.png"));


                //Criando a Zacian
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FADA);
                tiposPokemon.add(Tipo.METALICO);

                statsBasePokemon.put(Stat.HP, 92);
                statsBasePokemon.put(Stat.ATK, 170);
                statsBasePokemon.put(Stat.DEF, 115);
                statsBasePokemon.put(Stat.ATK_SP, 80);
                statsBasePokemon.put(Stat.DEF_SP, 115);
                statsBasePokemon.put(Stat.SPEED, 148);

                ataquesPokemon.add(ataques.get("Behemoth Blade"));
                ataquesPokemon.add(ataques.get("Wild Charge"));
                ataquesPokemon.add(ataques.get("Close Combat"));
                ataquesPokemon.add(ataques.get("Play Rough"));

                pokemons.put("Zacian", new Pokemon("Zacian", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/zacian.png"));


                //Criando o Mewtwo
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PSIQUICO);

                statsBasePokemon.put(Stat.HP, 106);
                statsBasePokemon.put(Stat.ATK, 110);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 154);
                statsBasePokemon.put(Stat.DEF_SP, 90);
                statsBasePokemon.put(Stat.SPEED, 130);

                ataquesPokemon.add(ataques.get("Psystrike"));
                ataquesPokemon.add(ataques.get("Shadow Ball"));
                ataquesPokemon.add(ataques.get("Aura Sphere"));
                ataquesPokemon.add(ataques.get("Ice Beam"));

                pokemons.put("Mewtwo", new Pokemon("Mewtwo", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/mewtwo.png"));


                //Criando o Kyogre
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.AGUA);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 100);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 150);
                statsBasePokemon.put(Stat.DEF_SP, 140);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Ice Beam"));
                ataquesPokemon.add(ataques.get("Thunder"));
                ataquesPokemon.add(ataques.get("Origin Pulse"));
                ataquesPokemon.add(ataques.get("Water Spout"));

                pokemons.put("Kyogre", new Pokemon("Kyogre", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/kyogre.png"));


                //Criando o Groudon
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.TERRA);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 150);
                statsBasePokemon.put(Stat.DEF, 140);
                statsBasePokemon.put(Stat.ATK_SP, 100);
                statsBasePokemon.put(Stat.DEF_SP, 90);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Precipice Blades"));
                ataquesPokemon.add(ataques.get("Stone Edge"));
                ataquesPokemon.add(ataques.get("Toxic"));
                ataquesPokemon.add(ataques.get("Earthquake"));

                pokemons.put("Groudon", new Pokemon("Groudon", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/groudon.png"));


                //Criando o Blacephalon
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FOGO);
                tiposPokemon.add(Tipo.FANTASMA);

                statsBasePokemon.put(Stat.HP, 53);
                statsBasePokemon.put(Stat.ATK, 127);
                statsBasePokemon.put(Stat.DEF, 53);
                statsBasePokemon.put(Stat.ATK_SP, 151);
                statsBasePokemon.put(Stat.DEF_SP, 79);
                statsBasePokemon.put(Stat.SPEED, 107);

                ataquesPokemon.add(ataques.get("Flamethrower"));
                ataquesPokemon.add(ataques.get("Shadow Ball"));
                ataquesPokemon.add(ataques.get("Blizzard"));
                ataquesPokemon.add(ataques.get("Psychic"));

                pokemons.put("Blacephalon", new Pokemon("Blacephalon", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/blacephalon.png"));


                //Criando o Darkrai
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.SOMBRIO);

                statsBasePokemon.put(Stat.HP, 70);
                statsBasePokemon.put(Stat.ATK, 90);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 135);
                statsBasePokemon.put(Stat.DEF_SP, 90);
                statsBasePokemon.put(Stat.SPEED, 125);

                ataquesPokemon.add(ataques.get("Hypnosis"));
                ataquesPokemon.add(ataques.get("Sludge Bomb"));
                ataquesPokemon.add(ataques.get("Dark Pulse"));
                ataquesPokemon.add(ataques.get("Psychic"));

                pokemons.put("Darkrai", new Pokemon("Darkrai", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/darkrai.png"));


                //Criando o Reshiram
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.DRAGAO);
                tiposPokemon.add(Tipo.FOGO);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 120);
                statsBasePokemon.put(Stat.DEF, 100);
                statsBasePokemon.put(Stat.ATK_SP, 150);
                statsBasePokemon.put(Stat.DEF_SP, 120);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Blue Flare"));
                ataquesPokemon.add(ataques.get("Draco Meteor"));
                ataquesPokemon.add(ataques.get("Hyper Beam"));
                ataquesPokemon.add(ataques.get("Aeroblast"));

                pokemons.put("Reshiram", new Pokemon("Reshiram", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/reshiram.png"));


                //Criando o Zekrom
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.DRAGAO);
                tiposPokemon.add(Tipo.ELETRICO);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 150);
                statsBasePokemon.put(Stat.DEF, 120);
                statsBasePokemon.put(Stat.ATK_SP, 120);
                statsBasePokemon.put(Stat.DEF_SP, 100);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Bolt Strike"));
                ataquesPokemon.add(ataques.get("Outrage"));
                ataquesPokemon.add(ataques.get("Extreme Speed"));
                ataquesPokemon.add(ataques.get("Drill Peck"));

                pokemons.put("Zekrom", new Pokemon("Zekrom", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/zekrom.png"));


                //Criando o Xerneas
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FADA);

                statsBasePokemon.put(Stat.HP, 126);
                statsBasePokemon.put(Stat.ATK, 131);
                statsBasePokemon.put(Stat.DEF, 95);
                statsBasePokemon.put(Stat.ATK_SP, 131);
                statsBasePokemon.put(Stat.DEF_SP, 98);
                statsBasePokemon.put(Stat.SPEED, 99);

                ataquesPokemon.add(ataques.get("Moonblast"));
                ataquesPokemon.add(ataques.get("Focus Blast"));
                ataquesPokemon.add(ataques.get("Surf"));
                ataquesPokemon.add(ataques.get("Thunder"));

                pokemons.put("Xerneas", new Pokemon("Xerneas", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/xerneas.png"));


                //Criando o Yveltal
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.SOMBRIO);
                tiposPokemon.add(Tipo.VOADOR);

                statsBasePokemon.put(Stat.HP, 126);
                statsBasePokemon.put(Stat.ATK, 131);
                statsBasePokemon.put(Stat.DEF, 95);
                statsBasePokemon.put(Stat.ATK_SP, 131);
                statsBasePokemon.put(Stat.DEF_SP, 98);
                statsBasePokemon.put(Stat.SPEED, 99);

                ataquesPokemon.add(ataques.get("Dark Pulse"));
                ataquesPokemon.add(ataques.get("Oblivion Wing"));
                ataquesPokemon.add(ataques.get("U-turn"));
                ataquesPokemon.add(ataques.get("Earth Power"));

                pokemons.put("Yveltal", new Pokemon("Yveltal", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/yveltal.png"));


                //Criando o Ho-Oh
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FOGO);
                tiposPokemon.add(Tipo.VOADOR);

                statsBasePokemon.put(Stat.HP, 106);
                statsBasePokemon.put(Stat.ATK, 130);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 110);
                statsBasePokemon.put(Stat.DEF_SP, 154);
                statsBasePokemon.put(Stat.SPEED, 90);

                ataquesPokemon.add(ataques.get("Sacred Fire"));
                ataquesPokemon.add(ataques.get("Brave Bird"));
                ataquesPokemon.add(ataques.get("Toxic"));
                ataquesPokemon.add(ataques.get("Earthquake"));

                pokemons.put("Ho-OH", new Pokemon("Ho-Oh", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/ho-oh.png"));


                //Criando o Hoopa
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.SOMBRIO);
                tiposPokemon.add(Tipo.PSIQUICO);

                statsBasePokemon.put(Stat.HP, 80);
                statsBasePokemon.put(Stat.ATK, 160);
                statsBasePokemon.put(Stat.DEF, 60);
                statsBasePokemon.put(Stat.ATK_SP, 170);
                statsBasePokemon.put(Stat.DEF_SP, 130);
                statsBasePokemon.put(Stat.SPEED, 80);

                ataquesPokemon.add(ataques.get("Gunk Shot"));
                ataquesPokemon.add(ataques.get("Fire Punch"));
                ataquesPokemon.add(ataques.get("Hyperspace Fury"));
                ataquesPokemon.add(ataques.get("Psychic Fangs"));

                pokemons.put("Hoopa", new Pokemon("Hoopa", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/hoopa.png"));


                //Criando o Calyrex-Ice
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PSIQUICO);
                tiposPokemon.add(Tipo.GELO);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 165);
                statsBasePokemon.put(Stat.DEF, 150);
                statsBasePokemon.put(Stat.ATK_SP, 85);
                statsBasePokemon.put(Stat.DEF_SP, 130);
                statsBasePokemon.put(Stat.SPEED, 50);

                ataquesPokemon.add(ataques.get("Glacial Lance"));
                ataquesPokemon.add(ataques.get("Psychic Fangs"));
                ataquesPokemon.add(ataques.get("High Horsepower"));
                ataquesPokemon.add(ataques.get("Close Combat"));

                pokemons.put("Calyrex-Ice", new Pokemon("Calyrex-Ice", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/calyrex-ice.png"));


                //Criando o Calyrex-Shadow
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PSIQUICO);
                tiposPokemon.add(Tipo.FANTASMA);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 165);
                statsBasePokemon.put(Stat.DEF, 150);
                statsBasePokemon.put(Stat.ATK_SP, 85);
                statsBasePokemon.put(Stat.DEF_SP, 130);
                statsBasePokemon.put(Stat.SPEED, 50);

                ataquesPokemon.add(ataques.get("Psyshock"));
                ataquesPokemon.add(ataques.get("Astral Barrage"));
                ataquesPokemon.add(ataques.get("Moonblast"));
                ataquesPokemon.add(ataques.get("Aura Sphere"));

                pokemons.put("Calyrex-Shadow", new Pokemon("Calyrex-Shadow", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/calyrex-shadow.png"));


                //Criando o Regigigas
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.NORMAL);

                statsBasePokemon.put(Stat.HP, 110);
                statsBasePokemon.put(Stat.ATK, 160);
                statsBasePokemon.put(Stat.DEF, 110);
                statsBasePokemon.put(Stat.ATK_SP, 80);
                statsBasePokemon.put(Stat.DEF_SP, 110);
                statsBasePokemon.put(Stat.SPEED, 100);

                ataquesPokemon.add(ataques.get("Ice Punch"));
                ataquesPokemon.add(ataques.get("Thunder Punch"));
                ataquesPokemon.add(ataques.get("Body Slam"));
                ataquesPokemon.add(ataques.get("Knock Off"));

                pokemons.put("Regigigas", new Pokemon("Regigigas", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/regigigas.png"));


                //Criando a Pheromosa
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.LUTADOR);
                tiposPokemon.add(Tipo.INSETO);

                statsBasePokemon.put(Stat.HP, 71);
                statsBasePokemon.put(Stat.ATK, 137);
                statsBasePokemon.put(Stat.DEF, 37);
                statsBasePokemon.put(Stat.ATK_SP, 137);
                statsBasePokemon.put(Stat.DEF_SP, 37);
                statsBasePokemon.put(Stat.SPEED, 151);

                ataquesPokemon.add(ataques.get("Megahorn"));
                ataquesPokemon.add(ataques.get("Close Combat"));
                ataquesPokemon.add(ataques.get("Diamond Storm"));
                ataquesPokemon.add(ataques.get("Sunsteel Strike"));

                pokemons.put("Pheromosa", new Pokemon("Pheromosa", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/pheromosa.png"));


                //Criando a Diancie
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PEDRA);
                tiposPokemon.add(Tipo.FADA);

                statsBasePokemon.put(Stat.HP, 50);
                statsBasePokemon.put(Stat.ATK, 100);
                statsBasePokemon.put(Stat.DEF, 150);
                statsBasePokemon.put(Stat.ATK_SP, 100);
                statsBasePokemon.put(Stat.DEF_SP, 150);
                statsBasePokemon.put(Stat.SPEED, 50);

                ataquesPokemon.add(ataques.get("Body Press"));
                ataquesPokemon.add(ataques.get("Heat Wave"));
                ataquesPokemon.add(ataques.get("Diamond Storm"));
                ataquesPokemon.add(ataques.get("Moonblast"));

                pokemons.put("Diancie", new Pokemon("Diancie", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/diancie.png"));
            }

            // Criando os pokemons iniciais
            if (pokemonsIniciais == null) {
                pokemonsIniciais = new HashMap<>();


                //Criando o Blaziken
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FOGO);
                tiposPokemon.add(Tipo.LUTADOR);

                statsBasePokemon.put(Stat.HP, 80);
                statsBasePokemon.put(Stat.ATK, 120);
                statsBasePokemon.put(Stat.DEF, 70);
                statsBasePokemon.put(Stat.ATK_SP, 110);
                statsBasePokemon.put(Stat.DEF_SP, 70);
                statsBasePokemon.put(Stat.SPEED, 80);

                ataquesPokemon.add(ataques.get("Flare Blitz"));
                ataquesPokemon.add(ataques.get("Stone Edge"));
                ataquesPokemon.add(ataques.get("Close Combat"));
                ataquesPokemon.add(ataques.get("Knock Off"));

                pokemonsIniciais.put("Blaziken", new Pokemon("Blaziken", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/blaziken.png"));


                //Criando o Incineroar
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.FOGO);
                tiposPokemon.add(Tipo.SOMBRIO);

                statsBasePokemon.put(Stat.HP, 95);
                statsBasePokemon.put(Stat.ATK, 115);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 80);
                statsBasePokemon.put(Stat.DEF_SP, 90);
                statsBasePokemon.put(Stat.SPEED, 60);

                ataquesPokemon.add(ataques.get("Earthquake"));
                ataquesPokemon.add(ataques.get("Flare Blitz"));
                ataquesPokemon.add(ataques.get("Darkest Lariat"));
                ataquesPokemon.add(ataques.get("U-turn"));

                pokemonsIniciais.put("Incineroar", new Pokemon("Incineroar", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/incineroar.png"));


                //Criando o Greninja
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.AGUA);
                tiposPokemon.add(Tipo.SOMBRIO);

                statsBasePokemon.put(Stat.HP, 72);
                statsBasePokemon.put(Stat.ATK, 95);
                statsBasePokemon.put(Stat.DEF, 67);
                statsBasePokemon.put(Stat.ATK_SP, 103);
                statsBasePokemon.put(Stat.DEF_SP, 71);
                statsBasePokemon.put(Stat.SPEED, 122);

                ataquesPokemon.add(ataques.get("Surf"));
                ataquesPokemon.add(ataques.get("Dark Pulse"));
                ataquesPokemon.add(ataques.get("Water Shuriken"));
                ataquesPokemon.add(ataques.get("Ice Beam"));

                pokemonsIniciais.put("Greninja", new Pokemon("Greninja", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/greninja.png"));


                //Criando o Swampert
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.AGUA);
                tiposPokemon.add(Tipo.TERRA);

                statsBasePokemon.put(Stat.HP, 100);
                statsBasePokemon.put(Stat.ATK, 110);
                statsBasePokemon.put(Stat.DEF, 90);
                statsBasePokemon.put(Stat.ATK_SP, 85);
                statsBasePokemon.put(Stat.DEF_SP, 90);
                statsBasePokemon.put(Stat.SPEED, 60);

                ataquesPokemon.add(ataques.get("Waterfall"));
                ataquesPokemon.add(ataques.get("Ice Punch"));
                ataquesPokemon.add(ataques.get("Earthquake"));
                ataquesPokemon.add(ataques.get("Superpower"));

                pokemonsIniciais.put("Swampert", new Pokemon("Swampert", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/swampert.png"));


                //Criando o Sceptile
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PLANTA);

                statsBasePokemon.put(Stat.HP, 70);
                statsBasePokemon.put(Stat.ATK, 85);
                statsBasePokemon.put(Stat.DEF, 65);
                statsBasePokemon.put(Stat.ATK_SP, 105);
                statsBasePokemon.put(Stat.DEF_SP, 85);
                statsBasePokemon.put(Stat.SPEED, 120);

                ataquesPokemon.add(ataques.get("Leaf Storm"));
                ataquesPokemon.add(ataques.get("Dragon Pulse"));
                ataquesPokemon.add(ataques.get("Splishy Splash"));
                ataquesPokemon.add(ataques.get("Stone Edge"));

                pokemonsIniciais.put("Sceptile", new Pokemon("Sceptile", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/sceptile.png"));


                //Criando o Chesnaught
                tiposPokemon.clear();
                statsBasePokemon.clear();
                ataquesPokemon.clear();

                tiposPokemon.add(Tipo.PLANTA);
                tiposPokemon.add(Tipo.LUTADOR);

                statsBasePokemon.put(Stat.HP, 88);
                statsBasePokemon.put(Stat.ATK, 107);
                statsBasePokemon.put(Stat.DEF, 122);
                statsBasePokemon.put(Stat.ATK_SP, 74);
                statsBasePokemon.put(Stat.DEF_SP, 75);
                statsBasePokemon.put(Stat.SPEED, 64);

                ataquesPokemon.add(ataques.get("Wood Hammer"));
                ataquesPokemon.add(ataques.get("Hammer Arm"));
                ataquesPokemon.add(ataques.get("Aqua Tail"));
                ataquesPokemon.add(ataques.get("Spark"));

                pokemonsIniciais.put("Chesnaught", new Pokemon("Chesnaught", tiposPokemon, 100, statsBasePokemon, ataquesPokemon,
                        "pokemon/sprites/chesnaught.png"));
            }

            // Criando o arquivo .bytej que contém a serialização do banco de dados se ele ainda não tiver sido criado
            if (!Files.exists(path)) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("bancoDados.bytej");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(ataques);
                    out.writeObject(pokemons);
                    out.writeObject(pokemonsIniciais);
                    out.writeObject(itensBatalha);
                    out.close();
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}