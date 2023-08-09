#include <stdio.h>
#include <stdlib.h>

int my_recurse (int n) {
#ifdef STUDENT
  // YOUR CODE HERE... You should change the return value. This is here to avoid
  // compilation errors.
  return EXIT_SUCCESS;
#else
  if (n <= 1)
    return 1;
  else
    return (my_recurse(n-1) * (n + my_recurse(n-2)));
#endif
}
