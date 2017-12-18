import com.xiongkuang.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

/**
 * Created by xiongkuang on 10/12/2017.
 */

public class JedisClientTest {

    @Test
    public void testJedisClient() throws Exception{

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient client = applicationContext.getBean(JedisClient.class);
        client.set("hhhaaa", "xiongkuang");
        String name = client.get("myName");
        System.out.println(name);

    }


}
