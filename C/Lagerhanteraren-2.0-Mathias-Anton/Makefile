TARGET = lager
LIBS = -lm
CC = gcc
CFLAGS = -ggdb -Wall -std=c11


default: $(TARGET) run

all: default

OBJECTS = $(patsubst %.c, %.o, $(wildcard *.c))
HEADERS = $(wildcard *.h)

%.o: %.c $(HEADERS)
	$(CC) $(CFLAGS) -c $< -o $@
	$(CC) -MM $(CFLAGS) $*.c > $*.d

$(TARGET): $(OBJECTS)
	$(CC) $(OBJECTS) $(CFLAGS) $(LIBS) -o $@

run:
	./lager
test:
	$(TARGET)
	./lager < test.txt

gdb:
	$(TARGET)
	gdb ./lager

clean:
	-rm -f *.o
	-rm -f $(TARGET)
