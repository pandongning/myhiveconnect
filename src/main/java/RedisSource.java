import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RedisSource {
    public static void main(String[] args) {
        
        Jedis jedis = new Jedis("192.168.28.200",6379);

        jedis.auth("1");

        Map<String, String> hashOne = jedis.hgetAll("hashone");
        Set<Map.Entry<String, String>> entries = hashOne.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key+"\t"+value);
        }

    }
}
