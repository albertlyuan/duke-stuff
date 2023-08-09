#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

    FILE *infile;
    FILE *outfile;
    infile = fopen(argv[1], "rb");
    outfile = fopen(argv[2], "wb");
    char p6[32],width[32],height[32],colorLimit[32];

    //copy header
    fscanf(infile,"%s\n%s %s\n%s\n",p6,width,height,colorLimit);
    fprintf(outfile,"%s\n%s %s\n%s\n",p6,width,height,colorLimit);

    //copy and invert pixels
    int numBytes = atoi(width) * atoi(height) * 3;
    char bytes[numBytes];
    printf("%s, %s, %d\n", width, height, numBytes);
        
    //read binary part
    fread(bytes, 1, numBytes, infile);

    for (int idx = 0; idx < numBytes;idx += 3){
        //char uninverted[3] = {bytes[idx],bytes[idx+1],bytes[idx+2]};        
        char inverted[3] = {~bytes[idx],~bytes[idx+1],~bytes[idx+2]};
        //printf("%x\n", uninverted);     
        //printf("%x\n", inverted);     
        fwrite(inverted, 1, 3,outfile);
    }
    fclose(infile);
    fclose(outfile);
    return 0;
}