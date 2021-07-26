# Clarity
A shard with the main goal of improving performance, both through greater control of settings and general optimizations.

## Performance-Enhancing Changes

 - **Chunk Updater Decrease**: Only use one thread that listens for and executes chunk updates.
 - **Chunk Update Limiter**: Sleep the current thread for a customizable amount of time before loading the next chunk.
 - **Frustum Update Limiter**: Only update the view frustum every so often (customizable).

None of the optimizations do a whole lot currently, and only apply to 1.8.9.

# Setup
Instructions should be relatively clear, but don't be afraid to ask because there is very likely to be something missing.

## Downloading
Download / Clone the github repository to get the contents locally.

## Running

### For IntelliJ IDEA Users:

Run

`./gradlew genRunConfiguration -PminecraftEnvironment=client -PminecraftVersion=1.8.9`

and a run configuration should be created (you will have to go into run configurations and select it)

### Everyone Else:
Kiln does not yet support automatically creating run configurations for any other IDEs, so you will have to create one manually.

 - **Classpath:** {project-name}
 - **JVM Arguments:** see [jvm arguments](#jvm-arguments)
 - **Main Class:** com.github.glassmc.loader.client.GlassClientMain
 - **Program Arguments:** see [program arguments](#program-arguments)  
 - **Working Directory:** run

#### JVM Arguments
To get the proper jvm arguments, run

`./gradlew getRunConfiguration -PminecraftEnvironment={client/server} -PminecraftVersion={version}`

For example, to get the correct arguments for running a 1.8.9 client.

`./gradlew getRunConfiguration -PminecraftEnvironment=client -PminecraftVersion=1.8.9`

You will see a long string printed into the terminal, copy that and add it to your jvm arguments.

#### Program Arguments
Most versions will work with supplying

`--accessToken 0 --version {version}`

(for offline mode)

but some versions (at least 1.7.10) require also adding

`--userProperties {}`