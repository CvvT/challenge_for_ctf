#define _GNU_SOURCE
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>
#include <android/log.h>
#include <signal.h>
#include "com_ctf_crackme1_Check.h"
#include "antidebug/antidebug.h"
#include "encrypt/encrypt.h"
#include "hook/hook.h"
#include "hook/base.h"

#ifndef PAGE_SIZE
#define PAGE_SIZE 0x1000
#endif

#ifndef LOG_TAG
#define LOG_TAG "cc"
#define LOGE(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#endif

#undef log

#define log(buf) \
        {FILE *fp = fopen("/data/data/com.ctf.crackme1/crackme.log", "a+"); if (fp) {\
        fwrite(buf, 1, sizeof(buf), fp);\
        fclose(fp);}}

extern void decryptAndcall(void *, int);
extern size_t my_fwrite_arm(const void* buffer, size_t size, size_t count, FILE* stream);

// static int counter = 1;
static struct hook_t eph;

static char *dat;
static unsigned char key[] = {
   0x00, 0x01, 0x02, 0x03,
   0x04, 0x05, 0x06, 0x07,
   0x08, 0x09, 0x0a, 0x0b,
   0x0c, 0x0d, 0x0e, 0x0f,
   0x10, 0x11, 0x12, 0x13,
   0x14, 0x15, 0x16, 0x17,
   0x18, 0x19, 0x1a, 0x1b,
   0x1c, 0x1d, 0x1e, 0x1f};

//CTF{Y0u_Ar3_Go0d_a7_1t}
//-103, -123, 100, -95, -25, -93, -27, 12, 48, 26, -18, 37, -13, 25, -55, -87, -80, 32, -7, 77, 34, -41, -52 
//0x0c, 0xa4, 0x2f, 0xc7, 0x38, 0xd2, 0xa4, 0xbb, 0xbe, 0x18, 0xb2, 0x4b, 0x0c, 0x22, 0xdd, 0xc9, 
//  0x50, 0xaf, 0x33, 0x67, 0x91, 0xda, 0x4e, 0x04, 0x63, 0x49, 0xc0, 0xb7, 0xa8, 0x2f, 0x99, 0xc5
static unsigned char ans[] = { 0x5f, 0x29, 0xd2, 0x62, 0x97, 0x5d, 0xcc, 0x47, 0xc5, 0x0f, 0xd3, 0xeb, 0x04, 
  0x7e, 0x8d, 0x1a, 0x89, 0x5f, 0xaf, 0x8f, 0x1f, 0xb2, 0x3b, 0x50, 0xa8, 0xa8, 0xdf, 0x8c, 0x02, 0xed, 0x03, 0xe1 };

static unsigned char key2[32] = {0};

int main(void)	__attribute__ ((__constructor__));
void initialize(void) __attribute__ ((__constructor__));
// void show(int param) {
//   int base = (int)Java_com_ctf_crackme1_Check_check-1;
//   int i;
//   if (param == 1) {
//     for (i = 0; i < 256; i++) {
//       LOGE("0x%02x", *(unsigned char*)(base+i));
//     }
//     LOGE("-----------------------------------");
//   }
//   for (i = 0; i < 32; i++) {
//     LOGE("0x%02x", dat[i]);
//   }
//   LOGE("-----------------------------------");
// }

void _e_first(void) {
  int i;
  unsigned char tmp;
  alarm(20);
  for (i = 0; i < 32; i++) { 
    tmp = key2[31-i];
    key2[31-i] = key2[i];
    key2[i] = tmp;
  }
  rc4((char *)((int)Java_com_ctf_crackme1_Check_check-1), 256, dat, 32);
}

void _e_checkStatus(void) {
  int i;
  for (i = 0; i < 32; i++)
    key2[i] = i;
  readStatus();
}

size_t my_fwrite(const void* buffer, size_t size, size_t count, FILE* stream) {
  size_t (*orig_fwrite)(const void* buffer, size_t size, size_t count, FILE* stream);
  orig_fwrite = (void*)eph.orig;
  int i;

  hook_precall(&eph);
  size_t res = orig_fwrite(buffer, size, count, stream);

  hook_postcall(&eph);
  LOGE("write once");

  for (i = 0; i < 32; i++)
    dat[i] = dat[i] ^ 0x13;
  return res;
}

static int check_len = 176;
static int first_len = 252; 

void handler_kill(int sig) {
  exit(0);
}

void handler_user(int sig) {
  int i = 0;
  for ( ; i < 32; i++) {
    ans[i] = i;
  }
}

void initialize(void) {
  struct sigaction act;
  act.sa_handler = handler_user;
  act.sa_flags = 0;
  sigaction(SIGALRM, &act, NULL);
  signal(SIGCONT, handler_kill);
  signal(SIGCHLD, handler_kill);
}

int main(void)
{
	// LOGE("start!");
  decryptAndcall(_e_checkStatus, check_len);
  hook(&eph, getpid(), "libc.", "fwrite", my_fwrite_arm, my_fwrite);
  // _e_checkStatus();
  // calculate();
	// decrypt_rodata();
	// LOGE("Second");
	return 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;

    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }

    result = JNI_VERSION_1_4;
    return result;
}  

JNIEXPORT jboolean JNICALL Java_com_ctf_crackme1_Check_check
  (JNIEnv *env, jclass clazz, jbyteArray array) {
  	jbyte *str = (*env)->GetByteArrayElements(env, array, 0);
    int str_len = (*env)->GetArrayLength(env, array);
    unsigned char out[32] = {0};
  	dat = (char *)malloc(32);
  	int len = sizeof(ans);
  	int i = 0;
  	jboolean res = 1;
  	memset(dat, 0, 32);
  	strncpy(dat, (char *)str, str_len);
    // show(1);
    decryptAndcall(_e_first, first_len);
    // _e_first();
    // show(0);
    aes((unsigned char *)dat, out, key);

    for (i = 0; i < 32; i++) {
      if (i < 16)
        dat[i] = out[i];
      else
        dat[i] = (dat[i]+i)&0xff;
    }

    log("start to compare")

  	for (i = 0; i < len; i++) {
  		if (dat[i] != ans[i]) {
  			res = 0;
  			break;
  		}
  	}
  	(*env)->ReleaseByteArrayElements(env, array, str, 0);
    // show(0);
    free(dat);
    alarm(0);
  	return res;
  }
