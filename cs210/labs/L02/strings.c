#include <stdio.h>
#include <stdlib.h>

#ifdef STUDENT
#else
#define MAXLINE 64
char lineBuff[MAXLINE];
#endif
int main (int argc, char **argv) {
#ifdef STUDENT
  // YOUR CODE HERE...
  return EXIT_SUCCESS;
#else
  char s1[MAXLINE], s2[MAXLINE], s3[MAXLINE];
  printf("Enter string 1.\n");
  if (fgets(lineBuff, MAXLINE, stdin) == NULL) {
    return EXIT_SUCCESS;
  }
  sscanf(lineBuff, "%64s", s1);
  printf("Enter string 2.\n");
  if (fgets(lineBuff, MAXLINE, stdin) == NULL) {
    return EXIT_SUCCESS;
  }
  sscanf(lineBuff, "%64s", s2);
  int i = 0;
  while (s1[i] != '\0') {
    s3[i] = s1[i];
    i++;
  }
  int j = 0;
  while (s2[j] != '\0') {
    s3[i+j] = s2[j];
    j++;
  }
  s3[i+j] = '\0';
  printf("%s, %s, %s\n",s1,s2, s3);
  return (EXIT_SUCCESS);       
#endif
}
