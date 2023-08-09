#include <stdio.h>
#include <string.h>

extern int isNegative(int);
extern int negate(int);
extern int getByte(int, int);
extern int byteSwap(int, int, int);
extern int rotateRight(int, int);
extern int addOK(int, int);

int main(int argc, char **argv) {

    if (argc < 2) {
            printf("Usage: btest <testname> \ntest names:\n");
            printf(" all \n isNegative\n negate\n getByte\n byteSwap\n rotateRight\n addOK\n");
            return(-1);
    }
    if (!strcmp(argv[1], "all")) {
        printf("isNegative 2 = %d\n", isNegative(2));
        printf("isNegative -82 = %d\n", isNegative(-82));
        printf("negate 210 = %d\n", negate(210));
        printf("negate -123 = %d\n", negate(-123));
        printf("getByte 0xABCDEF01 2 = 0x%X\n", getByte(0xABCDEF01, 2));
        printf("getByte 0x12345678 3 = 0x%X\n", getByte(0x12345678, 3));
        printf("byteSwap 0xABCDEF01 1 3 = 0x%X\n", byteSwap(0xABCDEF01, 1, 3));
        printf("byteSwap 0x12345678 0 2 = 0x%X\n", byteSwap(0x12345678, 1, 3));
        printf("rotateRight 0xABCDEF81 8 = 0x%X\n", rotateRight(0xABCDEF81, 8));
        printf("rotateRight 0x12345678 24 = 0x%X\n", rotateRight(0x12345678, 24));
        printf("addOK 1 7FFFFFFF = %d\n", addOK(1, 0x7FFFFFFF));
        printf("addOK -1 4 = %d\n", addOK(-1, 4));
    } else if (!strcmp(argv[1], "isNegative")) {
        printf("isNegative 2 = %d\n", isNegative(2));
        printf("isNegative -82 = %d\n", isNegative(-82));
    } else if (!strcmp(argv[1], "negate")) {
        printf("negate 210 = %d\n", negate(210));
        printf("negate -123 = %d\n", negate(-123));
    } else if (!strcmp(argv[1], "getByte")) {
        printf("getByte 0xABCDEF01 2 = 0x%X\n", getByte(0xABCDEF01, 2));
        printf("getByte 0x12345678 3 = 0x%X\n", getByte(0x12345678, 3));
    } else if (!strcmp(argv[1], "byteSwap")) {
        printf("byteSwap 0xABCDEF01 1 3 = 0x%X\n", byteSwap(0xABCDEF01, 1, 3));
        printf("byteSwap 0x12345678 0 2 = 0x%X\n", byteSwap(0x12345678, 1, 3));
    } else if (!strcmp(argv[1], "rotateRight")) {
        printf("rotateRight 0xABCDEF81 8 = 0x%X\n", rotateRight(0xABCDEF81, 8));
        printf("rotateRight 0x12345678 24 = 0x%X\n", rotateRight(0x12345678, 24));
    } else if (!strcmp(argv[1], "addOK")) {
        printf("addOK 1 7FFFFFFF = %d\n", addOK(1, 0x7FFFFFFF));
        printf("addOK -1 4 = %d\n", addOK(-1, 4));
    }
}
