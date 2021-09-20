package com.satoru.blog;

import com.satoru.blog.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import javax.annotation.Resource;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory connection;

    public RedisConfig(RedisConnectionFactory connection) {
        this.connection = connection;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate();
        template.setConnectionFactory(this.connection);
        template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
