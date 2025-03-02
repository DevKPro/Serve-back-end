package pers.leanfeng.score.manager.redis;//package pers.leanfeng.listentogether.manager.redis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.Topic;
//
//@Configuration
//public class MessageListenerConfiguration {
//    @Value("${spring.data.redis.listen-pattern}")
//    String pattern;
//
//    @Bean
//    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection){
//        // 负责连接 redis 服务器
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        // 负责把我们写的 listener 绑定到监听器
//        container.setConnectionFactory(redisConnection);
//        Topic topic = new PatternTopic(this.pattern);
//        container.addMessageListener(new TopicMessageListener(), topic);
//        return container;
//    }
//}
