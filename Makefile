.PHONY: run clean

all: ./src/com/astedt/robin/cellularsoftbody/Program.scala
	mkdir build
	scalac src/com/astedt/robin/cellularsoftbody/Program.scala -d build

run:
	cd build; scala com.astedt.robin.cellularsoftbody.Program

clean:
	rm -rf build
