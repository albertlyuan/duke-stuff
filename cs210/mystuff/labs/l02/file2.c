#include <stdio.h>
#include <stdlib.h>
typedef struct foo{
    int a;
    int b;
} foo_t;

int main(int argc, char *argv[])
{
    foo_t lst[10] = {0};
    int idx;
    int x;
    int y;

    FILE *in = fopen("input2.txt","r");
    int count;
    fscanf(in, "%d", &count);
    for (int i = 0; i<count; i++){
        fscanf(in, "%d %d %d", &idx,&x,&y);

        if (idx < 10){
            lst[idx].a = x; 
            lst[idx].b = y;
        }
    }
    fclose(in);
    int sum;
    for (int i = 9; i>=0; i--){
        sum = lst[i].a + lst[i].b;
        printf("idx: %d sum: %d\n", i,sum);
    } 
    return 0;    
    
}