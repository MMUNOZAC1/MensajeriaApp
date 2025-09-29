#!/bin/sh
export JAVA_OPTS="-Xmx64m"
DIR="$( cd "$( dirname "$0" )" && pwd )"
$DIR/gradle/wrapper/gradle-wrapper.jar "$@"
