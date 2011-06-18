# makefile: build and deploy from web/ to publish/, start/stop the server.
# Copyright (c) 2011 Pierre Arlaud & Jan Keromnes. All rights reserved.

RES = resources
JAR = metachess
SOURCE = metachess
MANIFEST = manifest.mf

build:
	javac `find $(SOURCE) -name '*\.java'`
	
run:
	java metachess.game.Game
	
jar:
	jar cvmf $(MANIFEST) $(JAR).jar `find $(SOURCE) -name '*\.class' -print` $(RESOURCES) &> /dev/null
	
clean:
	rm `find $(SOURCE) -name '*\.class'`
	
# hidden bonuses:

coffee :
	@echo "\n           )      (\n           (  )   )\n         _..,-(--,.._\n      .-;'-.,____,.-';\n     (( |            |\n      \`-;            ;\n         \\          /	\n      .-''\`-.____.-'''-.\n     (     '------'     )\n      \`--..________..--'\n";
	
sandwich :
	@if [ `id -u` = "0" ] ; then echo "OKAY." ; else echo "What? Make it yourself." ; fi
	
	
