// a simple assembler for our 8-bit stack machine

#include <stdio.h>
#include <stdlib.h>
#include <error.h>
#include <string.h>

#include "isa.h"
#define INST_SIZE 16

int main(int argc, char **argv) {
    FILE *fasm, *fbin;
    char inststr[INST_SIZE];
    char op[8];
    int operand;
    unsigned char inst;

    if (argc < 3) {
        printf("Usage: ./asm prog.s prog.bin\n");
        exit(1);
    }

    fasm = fopen(argv[1],"r");
    if (fasm == NULL) {
        perror("Error opening file");
        exit(1);
    }
    fbin = fopen(argv[2],"w");
    if (fasm == NULL) {
        perror("Error opening file");
        exit(1);
    }
    while (fgets(inststr,INST_SIZE,fasm) != NULL) {
//        printf("read inst = %s\n",inststr);
        int num_read = 0;
        operand = 0;
        num_read = sscanf(inststr,"%s %d\n",op,&operand);
//        printf("num read %d, op %s, operand %d\n",num_read, op, operand);
        if (num_read == 2) {
            if (operand >= (1 << 5)) {
                printf("operand %d too large\n",operand);
                exit(1);
            }
//            printf("%s %d \n",op,operand);
        }
        if (!strcmp("push",op)) {
            inst = (unsigned char) ((PUSH << 5) | (operand & 0x1F));
        } else if (!strcmp("pop",op)) {
            inst = (unsigned char) (POP << 5);
        } else if (!strcmp("add",op)) {
            inst = (unsigned char) (ADD << 5);
        } else if (!strcmp("sub",op)) {
            inst = (unsigned char) (SUB << 5);
        } else if (!strcmp("neg",op)) {
            inst = (unsigned char) (NEG << 5);
        } else if (!strcmp("xor",op)) {
           inst = (unsigned char) (XOR << 5);
        } else if (!strcmp("mul",op)) {
            inst = (unsigned char) (MUL << 5);
        } else if (!strcmp("out",op)) {
            inst = (unsigned char) (OUT << 5);
        }
//        printf("0x%x\n",inst);
        fwrite(&inst,1,1,fbin);
    }
    fclose(fasm);
    fclose(fbin);
    
}
