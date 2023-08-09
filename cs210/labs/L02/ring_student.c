#include <stdio.h>
#include <stdlib.h>

void doEncode (
  const unsigned char encode[], 
  const unsigned char str[], 
  unsigned char res[],
  size_t len
) {
#ifdef STUDENT
  // YOUR CODE HERE...
#else
  size_t i;
  for (i = 0; i < len && str[i] != '\0'; i++) {
    res[i] = encode[str[i]];
  }
  if (i == len) {
    fprintf(stderr, "ERROR: doEncode(): string overflow\n");
    exit(1);
  }
  res[i] = '\0';
#endif
}

void doDecode (
  const unsigned char encode[], 
  const unsigned char str[],
  unsigned char res[],
  size_t len
) {
#ifdef STUDENT
  // YOUR CODE HERE...
#else
  unsigned char decode[256];
  size_t i;
  for (i = 0; i < 256; i++) {
    decode[encode[i]] = i;
  }
  for (i = 0; i < len && str[i] != '\0'; i++) {
    res[i] = decode[str[i]];
  }
  if (i == len) {
    fprintf(stderr, "ERROR: doDecode(): string overflow\n");
    exit(1);
  }
  res[i] = '\0';
#endif
}
