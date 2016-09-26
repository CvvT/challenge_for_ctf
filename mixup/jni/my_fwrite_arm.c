#include <stdint.h>
#include <stdio.h>

extern size_t my_fwrite(const void* buffer, size_t size, size_t count, FILE* stream);

size_t my_fwrite_arm(const void* buffer, size_t size, size_t count, FILE* stream) {
	return my_fwrite(buffer, size, count, stream);
}