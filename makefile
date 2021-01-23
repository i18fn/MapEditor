ENCODING = utf8
PROGRAM  = Main

all:
	$(PROGRAM).class

$(PROGRAM).class: $(PROGRAM).java 
	javac -encoding $(ENCODING) $(PROGRAM).java

run: $(PROGRAM).class
	java $(PROGRAM)