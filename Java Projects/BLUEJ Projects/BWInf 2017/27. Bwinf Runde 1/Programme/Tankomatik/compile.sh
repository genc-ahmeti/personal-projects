#!/bin/bash
gtk-builder-convert gui/tkm.glade gui/tkm.xml
gcc -Wall -g -o bin/tkm.bin main.c `pkg-config --cflags --libs gtk+-2.0` -export-dynamic
