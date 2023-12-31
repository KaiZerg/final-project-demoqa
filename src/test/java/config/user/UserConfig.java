package config.user;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:properties/user.properties"})
public interface UserConfig extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();
}
