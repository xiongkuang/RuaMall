/**
 * Created by xiongkuang on 09/12/2017.
 */
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {


    @Test
    public void testJedis() throws Exception{
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("testKey", "hello");
        System.out.println(jedis.get("testKey"));

        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        //创建一个连接池对象
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        //从连接池获得连接
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.get("testKey"));
        jedis.close();

        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception{
        //创建jediscluster对象。有个参数nodes是个set，包含host和port
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 7001));
        nodes.add(new HostAndPort("127.0.0.1", 7002));
        nodes.add(new HostAndPort("127.0.0.1", 7003));
        nodes.add(new HostAndPort("127.0.0.1", 7004));
        nodes.add(new HostAndPort("127.0.0.1", 7005));
        nodes.add(new HostAndPort("127.0.0.1", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("testKKK", "123333");
        String s = jedisCluster.get("testKKK");
        System.out.println(s);
    }
}
