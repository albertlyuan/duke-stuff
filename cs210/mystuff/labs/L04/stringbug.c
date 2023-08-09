#include <stdio.h>

int main(int argc, char **argv) {

   // Do Not change the array declarations
    char s2[8] = "ABCDEFG";
    char s1[12] = "Clobber";
    char s3[8] = "1234567";

    // This program is supposed to overwrite string s1 with string s2 and as much of
    // string s3 as possible.  Unfortunately the prof doesn't quite get how strings
    // work in C and has a bug in this program.
    // Only s1 should change, s2 and s3 should remain the same.
    // Find and fix the bug.
    // If you add any printf statements be sure to remove them before testing
    // or submitting your solution.

    printf("Before: s1: %s S2: %s S3: %s\n",s1,s2,s3);

    // First copy string s2
    int s1idx = 0;
    for (int i = 0; i < 7; i++)
        s1[s1idx++] = s2[i];

    // Now add string s3
    for (int i = 0; i < 4; i++)
        s1[s1idx++] = s3[i];

    // s2, and s3 should not change during the course of execution because we never assign values to them!
    printf("After: s1: %s S2: %s S3: %s\n",s1,s2,s3);

}
