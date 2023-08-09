//
// Created by cmk91 on 8/20/21.
//

#ifndef THREADPROJ_SPIN_CONDITION_LOCK_H
#define THREADPROJ_SPIN_CONDITION_LOCK_H

#include <unistd.h>

typedef struct {
  int * ptr;
  int expected;
} spin_condition_var;

void SCL_init(spin_condition_var * lk, int * var, int expected);

void SCL_wait(const spin_condition_var * lk);


#endif //THREADPROJ_SPIN_CONDITION_LOCK_H
