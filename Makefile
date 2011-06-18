# Makefile: build, run and compile MetaChess (see README).
# Copyright (C) 2011 Pierre Arlaud & Jan Keromnes. All rights reserved.

RES = resources
JAR = MetaChess
SOURCE = metachess
MANIFEST = Manifest.mf

build:
	javac `find $(SOURCE) -name '*\.java'`
	
run:
	java metachess.game.Game
	
jar:
	jar cvmf $(MANIFEST) $(JAR).jar `find $(SOURCE) -name '*\.class' -print` $(RESOURCES) &> /dev/null
	
clean:
	rm `find $(SOURCE) -name '*\.class'`
	
# time for a break
coffee :
	@echo "\n           )      (\n           (  )   )\n         _..,-(--,.._\n      .-;'-.,____,.-';\n     (( |            |\n      \`-;            ;\n         \\          /	\n      .-''\`-.____.-'''-.\n     (     '------'     )\n      \`--..________..--'\n";

# http://xkcd.com/149/
sandwich :
	@if [ `id -u` = "0" ] ; then echo "OKAY." ; else echo "What? Make it yourself." ; fi
	
	
