import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

public class RedisSource {
    public static void main(String[] args) {
        
        Jedis jedis = new Jedis("192.168.28.200",6379);

        jedis.auth("1");

        Map<String, String> hashOne = jedis.hgetAll("hashone");
        Set<Map.Entry<String, String>> entries = hashOne.entrySet();
        for (Map.Entry<String, String> next : entries) {
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key + "\t" + value);
        }

    }
}
