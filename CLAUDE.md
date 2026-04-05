# Seed Packets Mod — Project Notes

## Overview

A **Fabric mod** for **Minecraft 1.20.1** called "Seed Packets". Currently based on the Fabric example mod template — the core logic and description are still placeholders waiting to be filled in.

- Mod ID: `seed-packets`
- Version: `1.0.0`
- Group/package: `com.example`
- License: CC0-1.0

## Tech Stack

| Component       | Version         |
|----------------|-----------------|
| Minecraft       | 1.20.1          |
| Fabric Loader   | 0.18.6          |
| Fabric API      | 0.92.7+1.20.1   |
| Fabric Loom     | 1.15-SNAPSHOT   |
| Java            | 17              |
| Gradle          | 9.3.0 (wrapper) |

Mappings: **Official Mojang mappings** (via `loom.officialMojangMappings()`)

## Project Structure

```
src/
  main/
    java/com/example/
      SeedPackets.java            # Main mod initializer (server + common)
    resources/
      fabric.mod.json             # Mod metadata, entrypoints, dependencies
      seed-packets.mixins.json    # Common mixin config
  client/
    java/com/example/client/
      SeedPacketsClient.java      # Client-only initializer
      mixin/ExampleClientMixin.java
    resources/
      seed-packets.client.mixins.json  # Client mixin config
```

The project uses **split source sets** (`splitEnvironmentSourceSets()`) — common/server code goes in `src/main`, client-only code goes in `src/client`. This is important: never put client-only classes (rendering, screens, etc.) in `src/main`.

## Key Entry Points

- `SeedPackets.java` — `onInitialize()` runs at mod load for both sides. Register blocks, items, events here.
- `SeedPacketsClient.java` — `onInitializeClient()` runs on the client only. Register renderers, key bindings, HUD here.

## Build Commands

```bash
# Build the mod jar
./gradlew build

# Run the Minecraft client (for testing)
./gradlew runClient

# Run the Minecraft server (for testing)
./gradlew runServer

# Generate Minecraft source mappings (useful for browsing vanilla code)
./gradlew genSources
```

Output jar lands in `build/libs/`.

## Gradle Config

- `gradle.properties` — edit this to bump mod version, MC version, or dependency versions.
- `build.gradle` — add new dependencies or repositories here.
- Gradle JVM heap is capped at 1G (`-Xmx1G`). Increase in `gradle.properties` if builds are slow.
- Configuration cache is disabled (IntelliJ compatibility issue with Loom).

## Things Still Needing Setup

- `fabric.mod.json`: update `description`, `authors`, `contact.homepage`, `contact.sources`, and `icon` path from their placeholder values.
- `SeedPackets.java`: replace the "Hello Fabric world!" log with actual mod initialization.
- Mixins (`ExampleMixin`, `ExampleClientMixin`) are template stubs — replace or remove.

## Mixin Notes

- Common mixins declared in `src/main/resources/seed-packets.mixins.json`
- Client mixins declared in `src/client/resources/seed-packets.client.mixins.json`
- Mixin classes must live in the correct source set (client mixins in `src/client/java/...`)

## IDE

Project is set up for **IntelliJ IDEA** (`.idea/` config present). Run configurations for Minecraft Client and Minecraft Server are already included in `.idea/runConfigurations/`.
