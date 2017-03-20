@echo off
echo executing one.bat...

@REM "\${this}" will be replaced with full maven artifact identifier when maven resources processing. See pom.xml properties
@mvnsh "${this}/bat/win.two"
