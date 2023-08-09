#include "assert.h"
#include <stdio.h>
#include <stdlib.h>

void binHex (char *src, char *dst, size_t max);
void hexBin (char *src, char *dst, size_t max);

#ifdef STUDENT
#else
char map[] = "0123456789abcdef";
#endif
void binHex (char *src, char *dst, size_t max)
{
#ifdef STUDENT
  // YOUR CODE HERE...
#else
  int i=0, j=0, k=0;
  int hit = 0;
  int pow = 0;

  assert(max>0);
  while (src[i] != '\n' && src[i] != '\0') {
    hit=0;
    pow=8;
    for (k=0; k<4; k++) {
      if (i+k >= max) {
        fprintf(stderr, "Ran out of input bits.  Stopping scan.\n");
        dst[j]=0;
        return;
      }
      if (src[i+k] == '1') {
        hit += pow;
      } else {
        if (src[i+k] != '0') {
          fprintf(stderr, "Malformed input.  Stopping scan. (Encountered %c)\n", src[i+k]);
          dst[j]=0;
          return;
        }
      }
      pow = pow/2;
    }
    assert(hit < 16);
    dst[j] = map[hit];
    i+=4;
    j+=1;
  }
  dst[j] = 0;
#endif
}

void hexBin(char* src, char* dst, size_t max)
{
#ifdef STUDENT
  // YOUR CODE HERE...
#else
  int i=0, j=0;

  assert(max>0);
  while (src[i] != '\n' && src[i] != '\0') {
    if (j+5 > max) {
      fprintf(stderr, "Output buffer filled.  Stopping scan.  (%d max %zu)\n", j, max);
      dst[j] = 0;
      return;
    }
    switch(src[i]) {
    case '0':
      dst[j] = '0'; dst[j+1]='0'; dst[j+2] = '0'; dst[j+3]='0';
      break;
    case '1':
      dst[j] = '0'; dst[j+1]='0'; dst[j+2] = '0'; dst[j+3]='1';
      break;
    case '2':
      dst[j] = '0'; dst[j+1]='0'; dst[j+2] = '1'; dst[j+3]='0';
      break;
    case '3':
      dst[j] = '0'; dst[j+1]='0'; dst[j+2] = '1'; dst[j+3]='1';
      break;
    case '4':
      dst[j] = '0'; dst[j+1]='1'; dst[j+2] = '0'; dst[j+3]='0';
      break;
    case '5':
      dst[j] = '0'; dst[j+1]='1'; dst[j+2] = '0'; dst[j+3]='1';
      break;
    case '6':
      dst[j] = '0'; dst[j+1]='1'; dst[j+2] = '1'; dst[j+3]='0';
      break;
    case '7':
      dst[j] = '0'; dst[j+1]='1'; dst[j+2] = '1'; dst[j+3]='1';
      break;
    case '8':
      dst[j] = '1'; dst[j+1]='0'; dst[j+2] = '0'; dst[j+3]='0';
      break;
    case '9':
      dst[j] = '1'; dst[j+1]='0'; dst[j+2] = '0'; dst[j+3]='1';
      break;
    case 'a': case 'A':
      dst[j] = '1'; dst[j+1]='0'; dst[j+2] = '1'; dst[j+3]='0';
      break;
    case 'b': case 'B':
      dst[j] = '1'; dst[j+1]='0'; dst[j+2] = '1'; dst[j+3]='1';
      break;
    case 'c': case 'C':
      dst[j] = '1'; dst[j+1]='1'; dst[j+2] = '0'; dst[j+3]='0';
      break;
    case 'd': case 'D':
      dst[j] = '1'; dst[j+1]='1'; dst[j+2] = '0'; dst[j+3]='1';
      break;
    case 'e': case 'E':
      dst[j] = '1'; dst[j+1]='1'; dst[j+2] = '1'; dst[j+3]='0';
      break;
    case 'f': case 'F':
      dst[j] = '1'; dst[j+1]='1'; dst[j+2] = '1'; dst[j+3]='1';
      break;
    default:
      fprintf(stderr, "Invalid hex digit.  Stopping scan.\n");
      dst[j]=0;
      return;
    }

    i+=1;
    j+=4;
  }
  dst[j] = 0;
#endif
}
