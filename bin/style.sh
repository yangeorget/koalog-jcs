#!/usr/bin/sh
find ${1} -name "*.java" -exec \
    java -classpath c:/checkstyle/checkstyle-all-2.0.jar \
    -Dcheckstyle.allow.protected=yes \
    -Dcheckstyle.ignore.whitespace=yes \
    -Dcheckstyle.ignore.whitespace.cast=yes \
    -Dcheckstyle.pattern.type=. \
    -Dcheckstyle.javadoc.scope=protected \
    com.puppycrawl.tools.checkstyle.Main {} \;    