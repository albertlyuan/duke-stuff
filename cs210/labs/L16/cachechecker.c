#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char* argv[]) {
	int addr_size = atoi(argv[1]);
	int cache_size = atoi(argv[2]);
	int block_size = atoi(argv[3]);

	int offset_bits = log2(block_size);
	int index_bits = log2(cache_size/block_size);
	int tag_bits = addr_size - index_bits - offset_bits;

	int student_tag, student_index, student_offset;

	printf("Enter number of tag bits, number of index bits, and number of offset bits\n");
	scanf("%d %d %d", &student_tag, &student_index, &student_offset);

	if ((student_tag == tag_bits) && (student_index == index_bits) && (student_offset == offset_bits)) {
		printf("Correct!\n");
	}
	else {
		printf("Incorrect. Try again...\n");
	}
	return 0;
}