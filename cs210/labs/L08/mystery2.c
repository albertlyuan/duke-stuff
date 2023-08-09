#include <stdio.h>

int g1(int r, int s) {
  return (r << s) ^ (r >> s);
}

int main() {
  printf("Enter an integer that causes g1 to return 17\n");
  int a1;
  int args = scanf("%d", &a1);
  int q;
  if ((q = g1(a1, 2))==17) {
    printf("Success\n");
  } else {
    printf("Failed\n");
  }
}
