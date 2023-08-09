// Written by Alvin Lebeck (alvy@cs.duke.edu) 
// adapted from Porter Jones (pbjones@cs.washington.edu)
//
// This is a file for managing a store of various aisles,
// represented by an array of 64-bit integers

#include <stddef.h>
#include "test_util.h"
#include "store_manager.h"

#define NUM_AISLES 10

#define SECTIONS_PER_AISLE 4

#define NUM_ITEMS 64

// Fully refills as many sections as can be filled by
// using items from the stockroom
//
// Prioritizes and fills sections with lower addresses first 
void refill_from_stockroom_sol() {
  unsigned long curr_aisle;

  for (int i = 0; i < NUM_AISLES; i++) {
    curr_aisle = aisles[i];
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      unsigned short id = get_id_sol(curr_aisle, j);
      int empty_spots = 10 - num_items_sol(curr_aisle, j);
      int to_add = empty_spots < stockroom[id] ? empty_spots : stockroom[id];
      curr_aisle = add_items_sol(curr_aisle, j, to_add);
      stockroom[id] -= to_add;
    }
    aisles[i] = curr_aisle;
  }
}

// Removes at most num items from sections with the given
// id, starting by removing items from sections with lower addresses
// 
// Returns the number of items removed
// If there are not enough items of the particular in the aisles
// your solution should use items in the stockroom to fulfill an order.
// If the stockroom runs out of items, you should remove as many items as possible. 
int fulfill_order_sol(unsigned short id, int num) {
  unsigned long curr_aisle;
  int original = num;

  for (int i = 0; i < NUM_AISLES; i++) {
    curr_aisle = aisles[i];
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      if (id == get_id_sol(curr_aisle, j)) {
        int my_num_items = num_items_sol(curr_aisle, j);
        int to_remove = my_num_items > num ? num : my_num_items;
        curr_aisle = remove_items_sol(curr_aisle, j, to_remove);
        num -= to_remove;
      }
    }
    aisles[i] = curr_aisle;
  }

  if (num > 0) {
    int to_remove = num > stockroom[id] ? stockroom[id] : num;
    num -= to_remove;
    stockroom[id] -= to_remove;
  }

  return original - num;
}

// Returns the aisle and section index of the first section with the given id 
// that has no items in it.
// Returns -1, -1 if no such section exists.
//
// Breaks ties by returning the section with the lowest aisle index then section index.
struct aisle_section empty_section_with_id_sol(unsigned short id) {
  unsigned long curr_aisle;
  struct aisle_section my_info;
  my_info.aisle_index = -1;
  my_info.section_index = -1;

  for (int i = 0; i < NUM_AISLES; i++) {
    curr_aisle = aisles[i];
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      if (get_id_sol(curr_aisle, j) == id && num_items_sol(curr_aisle, j) == 0) {
        my_info.aisle_index = i;
        my_info.section_index = j;
        return my_info;
      }
    }
  }
  return my_info;
}

// Returns the aisle and section index of the section with the highest inventory in the store.
//
// Breaks ties by returning the section with the lowest aisle index then section index.
struct aisle_section section_with_most_items_sol() {
  unsigned long curr_aisle;
  int max;
  struct aisle_section my_info;
  my_info.aisle_index = -1;
  my_info.section_index = -1;
  max = -1;

  for (int i = 0; i < NUM_AISLES; i++) {
    curr_aisle = aisles[i];
    for (int j = 0; j < SECTIONS_PER_AISLE; j++) {
      int inventory = num_items_sol(curr_aisle, j);
      
      if (inventory > max) {
        max = inventory;
        my_info.aisle_index = i;
        my_info.section_index = j;
      }
    }
  }

  return my_info;
}

char *store_academic_misconduct() {
	return "Warning: Disassembly of this source code constitutes academic misconduct";
}
