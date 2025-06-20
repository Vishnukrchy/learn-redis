# What is @Cacheable and Why Do We Use It?
⚙️ Purpose:
@Cacheable tells Spring:
“Yo, if this method gets called with the same input again, don’t go to the DB — just return the cached value.”
✅ Why we use it?
Avoid repeating DB hits for the same data
Reduce response time
Improve performance & scalability
# How Redis is Used in Author Implementation
```java
@Cacheable(value = "authors", key = "#id")
public Author getAuthorById(Long id) {
    // Simulate a database call
    return authorRepository.findById(id).orElse(null);
}
```
In this example, when `getAuthorById` is called with a specific `id`, Spring checks if the result is already cached under the key "authors::id". If it is, it returns the cached value. If not, it executes the method, retrieves the author from the database, and caches the result for future calls.

🧠 What’s value?
It’s the cache name — think of it as a map/table stored in Redis.
"authors" becomes the name of your Redis cache bucket
🧠 What’s key = "#id"?
This means use the method’s argument id as the Redis key
So if id = 5, Redis key will be: authors::5
#  Example Flow
1. **First Call**: `getAuthorById(5)`
   - Cache miss
   - Calls the database
   - Caches result under key "authors::5"
   - Returns the author object
   - Cache now has "authors::5" with the author data
2. **Subsequent Call**: `getAuthorById(5)`
   - Cache hit
   - Returns the cached author object
   - No database call needed
   - Cache remains unchanged

#🧪 How to Verify if Caching Works
1. **Check Redis**: Use a Redis client or CLI to see if the key "authors::5" exists after the first call.
2. **Logs**: Add logging in your method to see if it’s being executed or if it’s returning from cache.
3. ✅ 2. Use Redis CLI to See Keys

> KEYS *
> KEYS authors:*  # This will show all keys starting with "authors:"
> "authors::10"
  
3. # Spring Boot Actuator
   - If you have Actuator enabled, you can check the cache metrics to see hits and misses.
   - You can access the cache information via `/actuator/caches` endpoint.
   - This will show you the cache names, sizes, and hit/miss statistics.
   - This is useful for monitoring cache performance and ensuring that caching is working as expected.
   - # Redis Cache in Spring Boot
# fOR  Logging its enabled in application.properties
[]: # logging.level.org.springframework.cache=DEBUG
[]: # This will log cache hits and misses, allowing you to see when the cache is being used.
# we can used loging in service class like this:
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Service
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Cacheable(value = "authors", key = "#id")
    public Author getAuthorById(Long id) {
        logger.debug("Fetching author  form db with id : {}", id);
        // Simulate a database call
        return authorRepository.findById(id).orElse(null);
    }
}
```
3. # Use RedisInsight GUI (optional)
   It’s a GUI tool to view keys/values in Redis easily:
Shows all cached keys

You can even manually delete or inspect them

🔁 When Cache Gets Removed or Updated?
Action	Annotation	Purpose
Read (first time)	@Cacheable	Adds to cache
Update the entity	@CachePut	Updates cache with new value
Delete the entity	@CacheEvict	Removes it from cache

# Tips
# Always use a unique key (like id) to avoid clashes
You can cache custom keys like this:
```java
@Cacheable(value = "authors", key = "#author.name")
public Author getAuthorByName(String name) {
    // Simulate a database call
    return authorRepository.findByName(name).orElse(null);
}
@Cacheable(value = "authors", key = "'email:' + #author.email")

```
You can also cache based on multiple params:
```java
@Cacheable(value = "authors", key = "#author.name + ':' + #author.country")
public Author getAuthorByNameAndCountry(String name, String country) {
    // Simulate a database call
    return authorRepository.findByNameAndCountry(name, country).orElse(null);
}
@Cacheable(value = "authorDetails", key = "#id + '-' + #status")

```






