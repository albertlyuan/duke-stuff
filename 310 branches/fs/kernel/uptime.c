#include "types.h"
#include "param.h"
#include "spinlock.h"
#include "sleeplock.h"
#include "fs.h"
#include "file.h"
#include "memlayout.h"
#include "riscv.h"
#include "defs.h"
#include "proc.h"

struct spinlock uptimelock;

void reverse(char str[], int length)
{
    int start = 0;
    int end = length -1;
    char temp;
    while (start < end)
    {
        temp = *(str+end);
        *(str+end) =  *(str+start);
        *(str+start) = temp;
        start++;
        end--;
    }
}
 
// Implementation of itoa()
char* itoa(int num, char* str, int base)
{
    int i = 0;
    int isNegative = 0;
 
    /* Handle 0 explicitly, otherwise empty string is printed for 0 */
    if (num == 0)
    {
        str[i++] = '0';
        str[i] = '\0';
        return str;
    }
 
    // In standard itoa(), negative numbers are handled only with
    // base 10. Otherwise numbers are considered unsigned.
    if (num < 0 && base == 10)
    {
        isNegative = 1;
        num = -num;
    }
 
    // Process individual digits
    while (num != 0)
    {
        int rem = num % base;
        str[i++] = (rem > 9)? (rem-10) + 'a' : rem + '0';
        num = num/base;
    }
 
    // If number is negative, append '-'
    if (isNegative)
        str[i++] = '-';
 
    str[i] = '\0'; // Append string terminator
 
    // Reverse the string
    reverse(str, i);
 
    return str;
}

int
uptimewrite(struct file *f, int user_src, uint64 src, int n)
{   
    acquire(&uptimelock);
    release(&uptimelock);
    return n;
    
}

int
uptimeread(struct file *f, int user_dst, uint64 dst, int n)
{
    int i = 0;
    uint currticks;
    int buffsize = 16;
    char buf[buffsize];
    
    // convert 123 to string [buf]
    acquire(&uptimelock);   
    currticks = ticks;
    itoa(currticks, buf, 10);
    // printf("itoa test %s\n", buf);
    
    while (buf[i] != 0 && i <buffsize){
        either_copyout(user_dst, dst, &buf[i], 1);
        dst += 1;
        i += 1;
    }
    f->off += i;
    // printf("asdf %d\n", f->off);/
    release(&uptimelock);
    return i;
}


void
uptimeinit(void)
{
    initlock(&uptimelock, "uptime_lock");
    devsw[UPTIME_DEVICE].read = uptimeread;
    devsw[UPTIME_DEVICE].write = uptimewrite;
}
