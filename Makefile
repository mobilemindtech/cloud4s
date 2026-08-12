

all: compile

clean: ; sbt clean && rm -rf target

compile: ; sbt release

install: ; cp ./dist/cloud4s /usr/bin