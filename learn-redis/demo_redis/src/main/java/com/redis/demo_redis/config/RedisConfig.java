package com.redis.demo_redis.config;

import com.redis.demo_redis.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    // Configuration for Redis can be added here if needed
    // For example, you can define RedisTemplate, RedisConnectionFactory, etc.
    // This class can be used to customize Redis settings or to define beans related to Redis operations.

    @Bean //@Bean annotation is used to indicate that a method produces a bean to be managed by the Spring container.
    public String redisBean() {
        return "This is a Redis configuration bean";
    }
    // You can add more beans or configurations as needed for your Redis setup.
    // For example, you might want to configure a RedisTemplate or a RedisConnectionFactory.

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // Create a RedisTemplate for serializing and deserializing User objects
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Set the connection factory, key serializer, and value serializer
        //connection factory is used to create connections to the Redis server
        // Here, we are using Lettuce as the Redis client, but you can also use Jedis or any other client.
     //  template.setConnectionFactory(new LettuceConnectionFactory());
        template.setConnectionFactory(redisConnectionFactory());

        // Set the key serializer to StringRedisSerializer for string keys
        // Set the value serializer to Jackson2JsonRedisSerializer for User objects
        // Jackson2JsonRedisSerializer is used to serialize and deserialize User objects to and from JSON format
        // StringRedisSerializer is used to serialize and deserialize string keys
        // This allows us to store User objects in Redis as JSON strings.
        //setKeySerializer is used to set the serializer for the keys in the Redis store

        template.setKeySerializer(new StringRedisSerializer());

        // Jackson2JsonRedisSerializer is used to serialize and deserialize User objects to and from JSON format
        // setValueSerializer is used to set the serializer for the values in the Redis store
        // This allows us to store User objects in Redis as JSON strings.
        // Jackson2JsonRedisSerializer is a serializer that uses Jackson library to convert Java objects to JSON and vice versa.
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        return template;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        factory.afterPropertiesSet(); // or factory.start();
        return factory;
    }
}
