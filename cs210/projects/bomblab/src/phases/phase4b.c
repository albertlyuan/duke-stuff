/* 
 * phase4b.c - Access an array of structs
 * User inputs index and sum of two fields in structure
 */

typedef struct mystruct {
    long val1, val2;
} MYSTRUCT;
void phase_4(char *input)
{
#if defined(PROBLEM)
    static MYSTRUCT array[] = {{10,20},{5,2},{3,1},{6,4},{15,30},{1,2},{12,12},{8,7}};

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

    sum = array[p].val1 + array[p].val2;

    if ((sum != result))
	explode_bomb();
#elif defined(SOLUTION)
    switch (COUNT_VALUE_GET) {
    case 0: printf("0 30"); break;
    case 1: printf("1 7"); break;
    case 2: printf("2 4"); break;
    case 3: printf("3 10"); break;
    case 4: printf("4 45"); break;
    case 5: printf("5 3"); break;
    case 6: printf("6 24"); break;
    case 7: printf("7 15"); break;
    default:
	printf("ERROR: bad index value in phase4b\n");
	exit(8);
    }
    printf("\n");
#else
    invalid_phase("4b");
#endif
}

