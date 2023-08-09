/* 
 * phase4a.c - Traverses an array from starting index and sum the values.  User inputs
 * location to start and sum.
 */
void phase_4(char *input)
{
#if defined(PROBLEM)
    static int array[] = {
     2,
     4,
     3,
     7,
     0,
     6,
     5,
     1
    };

    int sum;
    int p, result;
    int numScanned;

    numScanned = sscanf(input, "%d %d", &p, &result);
    
    if (numScanned < 2)
        explode_bomb();

    if (p != COUNT_VALUE_SET)
        explode_bomb();
   
 //   p = p & 0x0f;
 //   start = p; /* debug */

    sum = 0;
    while(p < 8) {
	    sum += array[p];
        p++;
    }

    if ((sum != result))
	explode_bomb();
#elif defined(SOLUTION)
    switch (COUNT_VALUE_GET) {
    case 0: printf("0 28"); break;
    case 1: printf("1 26"); break;
    case 2: printf("2 22"); break;
    case 3: printf("3 19"); break;
    case 4: printf("4 12"); break;
    case 5: printf("5 12"); break;
    case 6: printf("6 6"); break;
    case 7: printf("7 1"); break;
    default:
	printf("ERROR: bad index value in phase4a\n");
	exit(8);
    }
    printf("\n");
#else
    invalid_phase("4a");
#endif
}

