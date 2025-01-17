/*
 * config.h - Configuration info for the bomb's automatic notification feature
 */
#ifndef __CONFIG_H__
#define __CONFIG_H__

/*
 * We don't want copies of bombs from all over the world contacting 
 * our server, so restrict bomb execution to one of the machines on 
 * the following NULL-terminated comma-separated list:
 */
char *host_table[128] = {
#ifdef NOPE
    /* Put your host names here */
    "courses.cs.duke.edu"
#endif

    0 /* The zero terminates the list */
};


#endif /* __CONFIG_H__ */

