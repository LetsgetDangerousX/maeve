#include <stdio.h>
//argv[0] => main
//argv[1] => infile
//argc = 2

int main(int argc, char *argv[]) {
    if(argc < 2) {
        fprintf(stderr, "Usage: %s <input_file>\n", argv[0]);
        return 1;
    }
    FILE *file = fopen(argv[1], "r");
    if(file == NULL) {
        perror("Error opening file");
        return 1;
    }
    printf("File opened\n");
    fclose(file);
    return 0;
    
}