package config;

/**
 * Created by AndersWOlsen on 12-06-2017.
 */
public enum Permission {
    USER_READ;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", ".");
    }
}
