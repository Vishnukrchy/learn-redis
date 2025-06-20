# Redis Annotation in Spring Boot
1 @EnableCaching
    for enabling caching support in your Spring Boot application.
2 @Cacheable
    we can  notion on layer like service or repository
    for caching the result of a method. It can be used to cache the return value of a method based on its parameters.
    its value attribute specifies the name of the cache, and the key attribute can be used to specify a custom key for the cached value.
    when when the method is called with the same parameters, the cached value will be returned instead of executing the method again.
    in first call, the method will be executed, and the result will be cached. 
    when we call the method again with the same parameters, the cached value will be returned.
example:
```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Service
public class UserService {


    private User findUserById(Long id) {
        // Simulate a database call
        return repository.findById(id);
    }
}
@Respository
public interface UserRepository extends JpaRepository<User, Long> {
    @Cacheable(value = "users", key = "#id")
    User findById(Long id);
}
```
here users is the name of the cache, and `#id` is the key used to store the cached value.
we can give any name to the cache, but it should be unique within the application.
and in @Chaceable annotation, we can use SpEL (Spring Expression Language) to define the key dynamically.
we can use `#` to refer to method parameters, and we can also use `#root` to refer to the root object of the method call.

here, the `findById` method is annotated with `@Cacheable`, which means that the result of this method will be cached in the "users" cache. The key for the cached value will be the `id` parameter passed to the method.
when we call the `findById` method with a specific `id`, the first call will execute the method and call goes to db and cache the result. 
Subsequent calls with the same `id` will return the cached value without executing the method again. and its not call the database again.

@CachePut
    used to update the cache with the result of a method execution. It is typically used when you want to update the cache after a method has been executed.
    its value attribute specifies the name of the cache, and the key attribute can be used to specify a custom key for the cached value.
    it will always execute the method and update the cache with the new value.

```java
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        // Simulate a database update
        return repository.save(user);
    }
}
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @CachePut(value = "users", key = "#user.id")
    User save(User user);
}
here @CachePut is used to update the cache with the result of the `updateUser` method. 
when we call the `updateUser` method, it will always execute the method and update the cache with the new value.

value attribute specifies the name of the cache, and the key attribute can be used to specify a custom key for the cached value.
when we call the `updateUser` method with a specific `user`, it will execute the method and update the cache with the new value.
```
@CacheEvict
    used to remove an entry from the cache. It is typically used when you want to remove a cached value after a method has been executed.
    its value attribute specifies the name of the cache, and the key attribute can be used to specify a custom key for the cached value.
    it will remove the cached value for the specified key.

```java
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        // Simulate a database delete
        repository.deleteById(id);
    }
}
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @CacheEvict(value = "users", key = "#id")
    void deleteById(Long id);
}
```
here `@CacheEvict` is used to remove an entry from the cache after the `deleteUser` method is executed. 
when we call the `deleteUser` method with a specific `id`, it will remove the cached value for that `id`.
# Summary of Redis Annotations in Spring Boot
- **@EnableCaching**: Enables caching support in the Spring Boot application.
- **@Cacheable**: Caches the result of a method based on its parameters. It retrieves the cached value if available, otherwise executes the method and caches the result.
- **@CachePut**: Updates the cache with the result of a method execution. It always executes the method and updates the cache with the new value.
- **@CacheEvict**: Removes an entry from the cache after a method execution. It removes the cached value for the specified key.
- **@CacheConfig**: Used to define common cache configuration for a class, such as the cache name and key generator. It can be applied at the class level to avoid repeating cache configuration in each method annotation.
- **@CacheableCondition**: Allows conditional caching based on a SpEL expression. It can be used to control whether a method's result should be cached or not.

# How to Use Redis Annotations in Spring Boot
1. **Add Dependencies**: Ensure you have the necessary dependencies in your `pom.xml` or `build.gradle` file for Spring Boot and Redis. For Maven, you can add:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-cache</artifactId>
   </dependency>
   ```
2. **Enable Caching**: Annotate your main application class with `@EnableCaching` to enable caching support:
   ```java
    import org.springframework.boot.SpringApplication;
 