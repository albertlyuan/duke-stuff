#include "ast.h"  
#include "check.h"

/* 
 * legallist.c - Null-terminated list of programming rules for the 
 *    standard set of Data Lab functions. If you want to add new 
 *    functions, this is the only dlc file in this directory that you 
 *    need to modify.
 *
 *    See y.tab.h for the names of the multi-character operators such
 *    as LS (left shift) and RS (right shift)
 */

#if 0  
/* Here is the definition and meaning of each entry (see check.h) */
struct legallist {
    char name[MAXSTR]; /* legal function name */
    int whatsok;       /* Mask indicating what language features are allowed */
    int maxops;        /* max operators for this function */
    int ops[MAXOPS];   /* null-terminated list of legal operators for this function.  '$' is wildcard */
};
#endif 

struct legallist legallist[MAXFUNCS] = {
    {{"absVal"}, 0, 10, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"addOK"}, (BIGCONST_OK), 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"allEvenBits"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"allOddBits"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"anyEvenBit"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"anyOddBit"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"bang"}, 0, 12, {'~', '&', '^', '|', '+', LS, RS, 0}},
    {{"bitAnd"}, 0, 8, {'~', '|', 0}},
    {{"bitCount"}, 0, 40, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"bitMask"}, 0, 16, {'!', '~', '&', '^', '|', '+', LS, RS, 0}},
    {{"bitMatch"}, 0, 14, {'~', '&', '|', 0}},
    {{"bitNor"}, 0, 8, {'~', '&', 0}},
    {{"bitOr"}, 0, 8, {'~', '&', 0}},
    {{"bitParity"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"bitReverse"}, 0, 45, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"bitXor"}, 0, 14, {'~', '&', 14}},
    {{"byteSwap"}, (BIGCONST_OK), 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"conditional"}, 0, 16, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"copyLSB"}, 0, 5, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"distinctNegation"}, 0, 5, {'~', '&', '!', '^', '|', '+', 0}},
    {{"dividePower2"}, 0, 15, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"evenBits"}, 0, 8, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"ezThreeFourths"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"fitsBits"}, 0, 15, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"fitsShort"}, 0, 8, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"floatAbsVal"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 10, {'$', 0}},
    {{"floatFloat2Int"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatScale1d2"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatInt2Float"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatIsEqual"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 25, {'$', 0}},
    {{"floatIsLess"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatNegate"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 10, {'$', 0}},
    {{"floatPower2"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatScale2"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatScale4"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"floatScale64"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 35, {'$', 0}},
    {{"floatUnsigned2Float"}, (BIGCONST_OK|CONTROL_OK|OTHERINT_OK), 30, {'$', 0}},
    {{"getByte"}, (BIGCONST_OK), 6, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"greatestBitPos"}, 0, 70, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"howManyBits"}, 0, 90, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"intLog2"}, 0, 90, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"implication"}, 0, 5, {'~', '!', '^', '|', 0}},
    {{"isAsciiDigit"}, 0, 15, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isEqual"}, 0, 5, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isGreater"}, 0, 24, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isLess"}, 0, 24, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isLessOrEqual"}, 0, 24, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isNegative"}, (BIGCONST_OK), 6, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isNonNegative"}, 0, 6, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isNonZero"}, 0, 10, {'~', '&', '^', '|', '+', LS, RS, 0}},
    {{"isNotEqual"}, 0, 6, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isPallindrome"}, 0, 40, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isPositive"}, 0, 8, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isPower2"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"isTmax"}, 0, 10, {'~', '&', '!', '^', '|', '+', 0}},
    {{"isTmin"}, 0, 10, {'~', '&', '!', '^', '|', '+', 0}},
    {{"isZero"}, 0, 2, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"leastBitPos"}, 0, 6, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"leftBitCount"}, 0, 50, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"logicalNeg"}, 0, 12, {'~', '&', '^', '|', '+', LS, RS, 0}},
    {{"logicalShift"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"minusOne"}, 0, 2, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"multFiveEighths"}, 0, 12, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"negate"}, (BIGCONST_OK), 5, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"oddBits"}, 0, 8, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"remainderPower2"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"replaceByte"}, 0, 10, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"reverseBytes"}, 0, 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"rotateLeft"}, 0, 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"rotateRight"}, (BIGCONST_OK), 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"satAdd"}, 0, 30, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"satMul2"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"satMul3"}, 0, 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"sign"}, 0, 10, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"signMag2TwosComp"}, 0, 15, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"subtractionOK"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"twosComp2SignMag"}, 0, 15, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"thirdBits"}, 0, 8, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"specialBits"}, 0, 3, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"tmax"}, 0, 4, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"tmin"}, 0, 4, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"trueFiveEighths"}, 0, 25, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"trueThreeFourths"}, 0, 20, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{"upperBits"}, 0, 10, {'~', '&', '!', '^', '|', '+', LS, RS, 0}},
    {{0} , 0, 0, {0}} /* end of list */
};

