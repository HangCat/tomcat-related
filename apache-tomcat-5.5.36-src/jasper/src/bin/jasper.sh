#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# -----------------------------------------------------------------------------
# Script for Jasper compiler
#
# Environment Variable Prequisites
#
#   JASPER_HOME   May point at your Catalina "build" directory.
#
#   JASPER_OPTS   (Optional) Java runtime options used when the "start",
#                 "stop", or "run" command is executed.
#
#   JAVA_HOME     Must point at your Java Development Kit installation.
#
#   JAVA_OPTS     (Optional) Java runtime options used when the "start",
#                 "stop", or "run" command is executed.
#
# $Id: jasper.sh 966908 2010-07-22 23:57:08Z rjung $
# -----------------------------------------------------------------------------

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
os400=false
case "`uname`" in
CYGWIN*) cygwin=true;;
OS400*) os400=true;;
esac

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`
JASPER_HOME=`cd "$PRGDIR/.." ; pwd`
if [ -r "$JASPER_HOME"/bin/setenv.sh ]; then
  . "$JASPER_HOME"/bin/setenv.sh
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$JASPER_HOME" ] && JASPER_HOME=`cygpath --unix "$JASPER_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# Get standard Java environment variables
if [ -r "$JASPER_HOME"/bin/setclasspath.sh ]; then
  BASEDIR="$JASPER_HOME"
  . "$JASPER_HOME"/bin/setclasspath.sh
else
  echo "Cannot find $JASPER_HOME/bin/setclasspath.sh"
  echo "This file is needed to run this program"
  exit 1
fi

# Add on extra jar files to CLASSPATH
for i in "$JASPER_HOME"/common/endorsed/*.jar; do
  CLASSPATH="$CLASSPATH":"$i"
done
for i in "$JASPER_HOME"/common/lib/*.jar; do
  CLASSPATH="$CLASSPATH":"$i"
done
for i in "$JASPER_HOME"/shared/lib/*.jar; do
  CLASSPATH="$CLASSPATH":"$i"
done
CLASSPATH="$CLASSPATH":"$JASPER_HOME"/shared/classes

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  JASPER_HOME=`cygpath --path --windows "$JASPER_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

# ----- Execute The Requested Command -----------------------------------------

if [ "$1" = "jspc" ] ; then

  shift
  exec "$_RUNJAVA" $JAVA_OPTS $JASPER_OPTS \
    -Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" -classpath "$CLASSPATH" \
    -Djasper.home="$JASPER_HOME" \
    -Dcatalina.home="$JASPER_HOME" \
    org.apache.jasper.JspC "$@"

elif [ "$1" = "debug" ] ; then

  shift
  exec "$_RUNJDB" $JAVA_OPTS $JASPER_OPTS \
    -Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" -classpath "$CLASSPATH" \
    -Djasper.home="$JASPER_HOME" \
    -Dcatalina.home="$JASPER_HOME" \
    org.apache.jasper.JspC "$@"

else

  echo "Usage: jasper.sh ( jspc )"
  echo "Commands:"
  echo "  jspc - Run the offline JSP compiler"
  exit 1

fi
