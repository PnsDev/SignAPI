# SignAPI  ![jitpack](https://img.shields.io/jitpack/v/github/PnsDev/SignAPI)
A simple sign API to make a players interact with signs.

This project was made because I just wanted a simple sign API that I could quickly import into my projects thru JitPack. This has been tested on 1.17 but it should work older versions.

##  Requirements
All you need to run this is Spigot and [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) installed on your server (make sure you add it as depend in your plugin.yml)

## Usage
### As a dependency
You will need to install this thru Maven or something similar.
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>dev.pns</groupId>
    <artifactId>SignAPI</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

### How to use

Initilize the API with the following code:
```java
public class Example extends JavaPlugin {
    private SignAPI signAPI;
    
    @Override
    public void onEnable() {
        signAPI = new SignAPI(this);
    }
}
```

When you want to use the API just call the method inside the api:

#### `createGUI(List<String>, Interface<Player player, String[] lines>)`

This takes a list of strings and an interface which will be called when the player exists the sign. Then you need to open the menu for  a player. Here's how you can do that:

#### `SignGUI.open(Player);`

Make sure you're using the `SignGUI` class which is returned by the createGUI method.

### Example

```java
Player player = (Player) sender;
SignGUI gui = api.createGUI(
        Arrays.asList("", "^^^^^^^^^^^^^^^","Enter the force", "amount above"),
        ((p, strings) -> {
            p.sendMessage("You entered: " + strings[0]);
        })
);
gui.open(player);
```


