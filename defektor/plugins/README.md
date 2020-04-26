# 🔌'in creation manual

Duplicate `TestPlugin` provided under `plugins`directory to start creating your plugin from a template.

## 📝 Config

1. Change `artifactId`, `version`, `name` and `plugin properties` from `pom`file.
1. Update `plugin.properties` file from `src.main.java.resources`directory.
1. Rename `TestPlugin`, as well as its references, and write your plugin.

## 👷‍ Build

Build the new plugin using Maven:

```
mvn clean
mvn package -f pom.xml
```

Copy the generated `.jar` or `zip` file into `defektor/plugins` directory.

```
cp <plugin-dir>/target/<package>.jar defektor/plugins
```