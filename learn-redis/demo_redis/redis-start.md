# What is Redis?
Redis is an open-source, in-memory data structure store that can be used as a database, cache, and message broker. It supports various data structures such as strings, hashes, lists, sets, and sorted sets with range queries. Redis is known for its high performance, flexibility, and ease of use.
Its primary use cases include caching, real-time analytics, session management, and pub/sub messaging.
# How to start Redis 
## Install Redis For Windows
1. Download the latest Redis release for Windows from the [Redis for Windows GitHub repository](
2. Extract the downloaded ZIP file to a directory of your choice.
3. Open a command prompt and navigate to the directory where you extracted Redis.
4. Run the following command to start the Redis server:
   ```
   redis-server.exe
   ```
5. To connect to the Redis server using the Redis CLI, open another command prompt and run:
   ```
    redis-cli.exe
    ```
Its recommended to run the Redis server as a service for production use. You can find instructions on how to do this in the [Redis for Windows documentation](

# Redis Commands  Frequently Used
## Basic Commands
### Set a key-value pair
```SET key value
```
example:
```
 SET name "John Doe"
```
### Get the value of a key
```GET key
```
example:
```
GET name
```
### Delete a key
```DEL key
```
example:
```
DEL name
```
### Check if a key exists
```EXISTS key
```
example:
```
EXISTS name
```

### Increment a key's value
```INCR key
```
example:
```
## Redis Server Commands
for starting, stopping, and checking the status of the Redis server, you can use the following commands:
redis-cli ping for checking if the server is running
redis-cli shutdown for stopping the server
```
# Redis local server  steps
1. Open a terminal or command prompt.
2. Navigate to the directory where you have installed Redis.
3. Run the Redis server using the command:
   ```
   redis-server
   ```
4. Open another terminal or command prompt to interact with the Redis server using the Redis CLI:
   ```
    redis-cli
    ```
5. You can now use Redis commands in the CLI to interact with your local Redis server.
6. To stop the Redis server, you can use the command:
   ```
   redis-cli shutdown
   ```
# Redis Configuration
## Configuration File
Redis configuration is typically done through a configuration file named `redis.conf`. This file contains various settings that control the behavior of the Redis server. You can find this file in the Redis installation directory.
## Common Configuration Options
### Port
You can change the port on which Redis listens for connections by modifying the `port` directive in the `redis.conf` file. The default port is 6379.
```conf
port 6379
```
### Bind Address
You can specify the IP address that Redis should bind to using the `bind` directive. By default, Redis binds to all available interfaces.
```conf
bind

## Redis host
for local development, you can use `localhost` or `
for 127.0.0.1`. If you want to allow remote connections, you can bind to a specific IP address or use `






