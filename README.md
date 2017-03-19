# mvnsh

Maven tools to manage shell/bat scripts in artifactory

## Overview

Working with mvnsh consist of uploading scripts into remote artifactory and download them back to local.

### Upload (install/deploy)

After developing scripts they may be upload to artifactory with **install** (for locally) or **deploy** (for remotely) goals.
To execute goal correctly, its need to specify plugin's parameter **scriptsList** with existed scripts directory.

### Download (download/collect/exec)

When scripts was installed into artifactory, we can download them with **download** goal into local artifactory.
**collect** goal assumes allows to extract script with all dependencies into specified directory.
**exec** goal includes scripts collecting as well, but also executes them after.

Parameter **exec.artifact** using to specify script to download/execute in format <group_id>/<artifact_id>/<version>/<type>/<script>.

## Dependencies

Scripts can use @mvnsh <source> directive to include external scripts. <source>

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
mvn pro.friendlyted:mvnsh-maven-plugin:1.0-SNAPSHOT:exec -Dexec.artifact=pro.friendlyted/mvnsh-scripts/1.0-SNAPSHOT/bat/bat.one.bat_one
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

After that, you can execute pluginc with foloowing instruction:

```
mvn mvnsh:exec -Dexec.artifact=pro.friendlyted/mvnsh-scripts/1.0-SNAPSHOT/bat/bat.one.bat_one
```
