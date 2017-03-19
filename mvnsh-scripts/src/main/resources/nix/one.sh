#!/bin/bash
echo "executing one.sh..."

# "\${this}" will be replaced with full maven artifact identifier when maven resources processing. See pom.xml properties
@mvnsh ${this}/sh/nix.two