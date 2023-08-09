//
// Created by cmk91 on 8/20/21.
//

#include "spin_condition_lock.h"

void SCL_init(spin_condition_var * lk, int * var, int expected) {
  lk->ptr = var;
  lk->expected = expected;
}

void SCL_wait(const spin_condition_var * lk) {
  while(*(lk->ptr) != lk->expected) {
    usleep(5);
  }
}
