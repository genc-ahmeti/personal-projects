#!/bin/bash
gtk-builder-convert gui/pm.glade gui/pm.xml
gcc -Wall -g -o bin/pm.bin main.c `pkg-config --cflags --libs gtk+-2.0` -export-dynamic
