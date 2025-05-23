CC=gcc
FILES=main.c
OUT_EXE=main
build: $(FILES)
	$(CC) -o $(OUT_EXE) $(FILES)
run: build
	@if [ -z "$(file)" ]; then \
	echo "Usage: make run file=your_input.txt"; \
	else \
	./$(OUT_EXE) $(file); \
	fi
clean:
	rm -f *.o $(OUT_EXE)