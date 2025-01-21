package pl.malezjaa.server_utils.toml;

public class ServerConfig {
    public Homes homes;

    public static class Homes {
        public int limit = 5;
        public String invalid_dimension = "You tried to teleport to your home in an invalid dimension!";
        public String nonexistent_home = "You don't have a home named %s!";
        public String limit_reached = "You have reached the limit of your homes! (%d)";
    }
}