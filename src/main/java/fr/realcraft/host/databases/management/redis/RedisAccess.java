package fr.realcraft.host.databases.management.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {

    public static RedisAccess Instance;
    private RedissonClient redissonClient;

    public RedisAccess(RedisCredentials redisCredentials) {
        Instance = this;
        this.redissonClient = initRedisson(redisCredentials);
    }

    public static void init() {
        new RedisAccess(new RedisCredentials("ip", "password", 6379));
    }

    public static void close() {
        RedisAccess.Instance.getRedissonClient().shutdown();
    }

    public RedissonClient initRedisson(RedisCredentials redisCredentials) {
        final Config config = new Config();

        config.setCodec(new JsonJacksonCodec());
        config.setThreads(6);
        config.setNettyThreads(6);
        config.useSingleServer()
                .setAddress(redisCredentials.toRedisURL())
                .setPassword(redisCredentials.getPassword())
                .setDatabase(4)
                .setClientName(redisCredentials.getClientName());

        return Redisson.create(config);
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
}
