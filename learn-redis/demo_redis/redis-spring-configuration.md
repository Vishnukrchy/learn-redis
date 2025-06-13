# How To Connect and all Configure Redis in Spring Boot
Set 1
# # Redis Spring Configuration
# ## 1. Add Dependencies
To use Redis in a Spring Boot application, you need to add the necessary dependencies in your `pom.xml` file if you are using Maven. Here’s how you can do it:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.6.0</version>
</dependency>
```
Step 2
# ## 2. Configure Redis Properties
You can configure Redis properties in your `application.properties` or `application.yml` file. Here’s an example of how to do it in `application.properties`:

```properties   
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=your_password_here
```
# ## 3. Create a Redis Configuration Class
You can create a configuration class to set up Redis in your Spring Boot application. Here’s an example:

```java
class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(6379);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
```

# ## 4. Use Redis in Your Application
You can now use Redis in your Spring Boot application by autowiring the `RedisTemplate` bean. Here’s an example of how to use it:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

class MyService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
```
# ## 5. Run Your Application
You can now run your Spring Boot application, and it should connect to the Redis server using the configuration you provided. Make sure that the Redis server is running before starting your application.
# ## 6. Testing Redis Connection
You can test the Redis connection by running a simple command in your application. Here’s an example of how to do it:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void testConnection() {
        redisTemplate.opsForValue().set("testKey", "Hello, Redis!");
        String value = (String) redisTemplate.opsForValue().get("testKey");
        System.out.println("Value from Redis: " + value);
    }
}
```
# ## 7. Additional Configuration (Optional)
You can further customize your Redis configuration by setting additional properties such as timeout, SSL, and more. Here’s an example of how to set a timeout:

```java
@Bean
public JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName("localhost");
    factory.setPort(6379);
    factory.setTimeout(2000); // Set timeout in milliseconds
    return factory;
}
```
# ## 8. Using Redis with Spring Data
You can also use Spring Data Redis to interact with Redis in a more object-oriented way. Here’s an example of how to define a repository:

```java
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MyEntityRepository extends CrudRepository<MyEntity, String> {
  @Chacheable(value="myEntityCache" ,key = "#name")
    MyEntity findByName(String name);
  @Cacheable(value="myEntityCache" ,key = "#id")
    List<MyEntity> findByAgeGreaterThan(int age);
    List<MyEntity> findByCity(String city);
    List<MyEntity> findByCountry(String country);
    
}
```
# ## 9. Using Redis with Spring Boot Caching
To use Redis as a caching solution in your Spring Boot application, you can enable caching by adding the `@EnableCaching` annotation to your main application class. Here’s an example:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@EnableCaching
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```
# ## 10. Using Redis with Spring Boot Session Management
To use Redis for session management in your Spring Boot application, you can add the following dependency to your `pom.xml`:

Then, you can configure Redis as the session store in your `application.properties`:
```properties
spring.session.store-type=redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=your_password_here
```

