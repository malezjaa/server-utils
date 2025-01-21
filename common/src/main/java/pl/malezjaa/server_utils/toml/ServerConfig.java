package pl.malezjaa.server_utils.toml;

public class ServerConfig {
    public Homes homes;

    public static class Homes {
        public int limit = 5;
        public String invalid_dimension = "You tried to teleport to your home in an invalid dimension!";
        public String nonexistent_home = "You don't have a home named %s!";
        public String limit_reached = "You have reached the limit of your homes! (%d)";
        public String set_home = "You have set your home named %s! (%d/%d)";
        public String deleted_nonexistent_code = "You tried to delete a home named %s, but it doesn't exist!";
        public String deleted_home = "You have deleted your home named %s!";
    }
}