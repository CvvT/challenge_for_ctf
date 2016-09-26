#include "antidebug.h"
#include <stdint.h>
#include <android/log.h>

#define CHECK_TIME 10
#define MAX 128
#ifndef LOG_TAG
#define LOG_TAG "cc"
#define LOGE(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#endif


void readStatus() {
 
	int pid;
	FILE *fd;
	char filename[MAX];
	char line[MAX];
	pid = getpid();
	sprintf(filename,"/proc/%d/status",pid);// 读取proc/pid/status中的TracerPid
	if(fork()==0)
	{
		int pt;
		pt = ptrace(PTRACE_TRACEME, 0, 0, 0); //子进程反调试
		if (pt == -1)
			exit(0);
		
		while(1)
		{
			fd = fopen(filename,"r");
			while(fgets(line,MAX,fd))
			{
				if(strncmp(line,"TracerPid",9) == 0)
				{
					int statue = atoi(&line[10]);
					// LOGE("########## statue = %d,%s", statue,line);
					fclose(fd);
					if(statue != 0)
					{
                        // LOGE("########## here");
						int ret = kill( pid,SIGKILL);
						// LOGE("########## kill = %d", ret);
						return ;
					}
					break;
				}
			}
			sleep(CHECK_TIME);
		}
	}
}


