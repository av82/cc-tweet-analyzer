#!/bin/sh
cd src
javac -cp com/twitter/analyzer/*.java
javac  com/twitter/analyzer/TweetManager.java 
java com.twitter.analyzer.TweetManager
