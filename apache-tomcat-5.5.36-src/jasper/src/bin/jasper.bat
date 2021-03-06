@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Script for Jasper compiler
rem
rem Environment Variable Prequisites
rem
rem   JASPER_HOME   May point at your Catalina "build" directory.
rem
rem   JASPER_OPTS   (Optional) Java runtime options used when the "start",
rem                 "stop", or "run" command is executed.
rem
rem   JAVA_HOME     Must point at your Java Development Kit installation.
rem
rem   JAVA_OPTS     (Optional) Java runtime options used when the "start",
rem                 "stop", or "run" command is executed.
rem
rem $Id: jasper.bat 565193 2007-08-12 22:53:10Z markt $
rem ---------------------------------------------------------------------------

rem Guess JASPER_HOME if not defined
if not "%JASPER_HOME%" == "" goto gotHome
set JASPER_HOME=.
if exist "%JASPER_HOME%\bin\jasper.bat" goto okHome
set JASPER_HOME=..
:gotHome
if exist "%JASPER_HOME%\bin\jasper.bat" goto okHome
echo The JASPER_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end
:okHome

rem Get standard environment variables
if exist "%JASPER_HOME%\bin\setenv.bat" call "%JASPER_HOME%\bin\setenv.bat"

rem Get standard Java environment variables
if exist "%JASPER_HOME%\bin\setclasspath.bat" goto okSetclasspath
echo Cannot find %JASPER_HOME%\bin\setclasspath.bat
echo This file is needed to run this program
goto end
:okSetclasspath
set BASEDIR=%JASPER_HOME%
call "%JASPER_HOME%\bin\setclasspath.bat"

rem Add on extra jar files to CLASSPATH
for %%i in ("%JASPER_HOME%\common\endorsed\*.jar") do call "%JASPER_HOME%\bin\cpappend.bat" %%i
for %%i in ("%JASPER_HOME%\common\lib\*.jar") do call "%JASPER_HOME%\bin\cpappend.bat" %%i
for %%i in ("%JASPER_HOME%\shared\lib\*.jar") do call "%JASPER_HOME%\bin\cpappend.bat" %%i
set CLASSPATH=%CLASSPATH%;%JASPER_HOME%\shared\classes

rem Parse arguments
if ""%1"" == ""jspc"" goto doJspc
echo Usage: jasper ( jspc )
echo Commands:
echo   jspc - Run the offline JSP compiler
goto end
:doJspc
shift

rem Get remaining unshifted command line arguments and save them in the
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

%_RUNJAVA% %JAVA_OPTS% %JASPER_OPTS% -Djava.endorsed.dirs="%JAVA_ENDORSED_DIRS%" -classpath "%CLASSPATH%" -Djasper.home="%JASPER_HOME%" org.apache.jasper.JspC %CMD_LINE_ARGS%

:end
