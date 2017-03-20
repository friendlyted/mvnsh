# mvnsh

Maven tools to manage shell/bat scripts in maven artifactory

## Quick start

```
mvn archetype:generate -DarchetypeArtifactId=mvnsh-archetype -DarchetypeGroupId=pro.friendlyted -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=group -DartifactId=artifact -Dversion=123
cd artifact
mvn install
mvn pro.friendlyted:mvnsh-maven-plugin:1.0-SNAPSHOT:exec -Dmvnsh.artifact=group/artifact/123/bat/win.one
```

## Requirements

- JRE >= 1.8.0
- maven >= 3.3.9

There is no java compilation in mvnsh, so JDK is unnecessary.

## Overview

Working with mvnsh consist of uploading scripts into remote artifactory and download them back to local.

### Upload (install/deploy)

After developing scripts they may be upload to artifactory with **install** (for locally) or **deploy** (for remotely) goals.
To execute goal correctly, its need to specify plugin's parameter **scriptsList** with existed scripts directory.
All relative directories inside of basedir will be replaced with dot-names (relative/path.sh -> relative.path.sh).

### Download (download/collect/exec)

When scripts was installed into artifactory, we can download them with **download** goal into local artifactory.
**collect** goal assumes allows to extract script with all dependencies into specified directory.
**exec** goal includes scripts collecting as well, but also executes them after.

Parameter **mvnsh.artifact** using to specify script to download/execute in format &lt;group_id>/&lt;artifact_id>/&lt;version>/&lt;type>/&lt;script>.

## Dependencies

Scripts can use @mvnsh &lt;source> directive to include external scripts.

## Configuration

### Example of install configuration:

```
<project>
	...
    <build>
        <pluginManagement>
            <plugins>
				...
                <plugin>
                    <groupId>pro.friendlyted</groupId>
                    <artifactId>mvnsh-maven-plugin</artifactId>
                    <version>1.0-SNAPSHOT</version>
                    <executions>
                        <execution>
                            <id>mvnsh-install</id>
                            <phase>install</phase>
                            <goals>
                                <goal>install</goal>
                            </goals>
                        </execution>
                    </executions>
                    <configuration>
                        <scriptsList>
                            <scripts>
                                <basedir>${basedir}/src/main/resources</basedir>
                                <includes>
                                    <include>**/*.sh</include>
                                    <include>**/*.bat</include>
                                </includes>
                            </scripts>
                        </scriptsList>
                    </configuration>
                </plugin>
				...
            </plugins>
        </pluginManagement>
        <plugins>
			...
            <plugin>
                <groupId>pro.friendlyted</groupId>
                <artifactId>mvnsh-maven-plugin</artifactId>
            </plugin>
			...
        </plugins>
    </build>
</project>
```

### Example of execution installed scripts:

```
mvn pro.friendlyted:mvnsh-maven-plugin:1.0-SNAPSHOT:exec -Dmvnsh.artifact=pro.friendlyted/mvnsh-scripts/1.0-SNAPSHOT/bat/bat.one.bat_one
```

## Shorter plugin name

You can shorten plugin name in shell by adding following instructions inside of maven's local settings.xml:

```
<settings>
	...
	<pluginGroups>
		...
		<pluginGroup>pro.friendlyted</pluginGroup>
	</pluginGroups>
	...
</settings>
```

After that, you can execute plugin with following instruction:

```
mvn mvnsh:exec -Dmvnsh.artifact=pro.friendlyted/mvnsh-scripts/1.0-SNAPSHOT/bat/bat.one.bat_one
```
