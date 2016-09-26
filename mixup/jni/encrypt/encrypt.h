#ifndef _ENCRYPT
#define _ENCRYPT
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

unsigned int crc32(unsigned char *, unsigned int);
void rc4(unsigned char *, unsigned int, unsigned char *, unsigned int);
void aes(unsigned char *, unsigned char *, unsigned char *);
void inv_aes(unsigned char *, unsigned char *, unsigned char *);

#endif