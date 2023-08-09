// Written by Alvin Lebeck (alvy@cs.duke.edu) 
// adapted from Porter Jones (pbjones@cs.washington.edu)
//
// This is a file for managing a store of various aisles,
// represented by an array of 64-bit integers

#include <stddef.h>  // To be able to use NULL
#include "aisle_manager.h"
#include "store_manager.h"
#include "store_util.h"
#include <stdio.h>
// Number of aisles in the store
#define NUM_AISLES 10

// Number of sections per aisle
#define SECTIONS_PER_AISLE 4

// Number of items in the stockroom (2^6 different id combinations)
#define NUM_ITEMS 64

// Global array of aisles in this store. Each unsigned long in the array
// represents one aisle.
unsigned long aisles[NUM_AISLES];

// Array used to stock items that can be used for later. The index of the array
// corresponds to the item id and the value at an index indicates how many of
// that particular item are in the stockroom.
int stockroom[NUM_ITEMS];


/* Starting from the first aisle, refill as many sections as possible using
 * items from the stockroom. A section can only be filled with items that match
 * the section's item id. Priotizes and fills sections with lower indicies
 * first. Sections with lower indicies should be fully filled (if possible)
 * before moving onto the next section.
 */
void refill_from_stockroom() {
  for (int idx=0;idx<NUM_AISLES;idx++){
    unsigned long aisle = aisles[idx];
    int usedStock = 0;
    for (int section=0;section<4;section++){
      unsigned short sectionID = get_id(aisle,section);
      int stock = stockroom[sectionID];

      usedStock = 10 - num_items(aisle, section);
      aisle = add_items(aisle, section, stock);
      stock -= usedStock;
      if (stock <= 0){
        stock = 0;
      }
      usedStock = 0;
      stockroom[sectionID] = stock;

    }
    aisles[idx] = aisle;
  }
}

/* Remove at most num items from sections with the given item id, starting with
 * sections with lower addresses, and return the total number of items removed.
 * Multiple sections can store items of the same item id. If there are not
 * enough items with the given item id in the aisles, first remove all the
 * items from the aisles possible and then use items in the stockroom of the
 * given item id to finish fulfilling an order. If the stockroom runs out of
 * items, you should remove as many items as possible.
 */
int fulfill_order(unsigned short id, int num) {
  int initNum = num;
  for (int idx=0;idx<NUM_AISLES;idx++){
    unsigned long aisle = aisles[idx];
    for (int section=0;section<4;section++){
      unsigned short sectionID = get_id(aisle,section);

      if (sectionID == id){
        int sectionAmount = num_items(aisle, section);

        if (num - sectionAmount >= 0){
          aisle = remove_items(aisle,section,sectionAmount);
          num -= sectionAmount;
        }else{
          aisle = remove_items(aisle,section,num);
          num = 0;
        }
      
      } 
    }
    aisles[idx] = aisle;
  }

  if (num > 0){
    int stock = stockroom[id];
    if (num > stock){
      num -= stock;
      stockroom[id] = 0;
    } else{
      stockroom[id] -= num;
      num = 0;
    }
  }
  return initNum - num;
}

#ifdef NOT
/* Return a pointer to the first section in the aisles with the given item id
 * that has no items in it or NULL if no such section exists. Only consider
 * items stored in sections in the aisles (i.e., ignore anything in the
 * stockroom). Break ties by returning the section with the lowest address.
 */
unsigned short* aisle_section empty_section_with_id(unsigned short id){
  // TODO: implement this function
  return NULL;
}

/* Return a pointer to the section with the most items in the store. Only
 * consider items stored in sections in the aisles (i.e., ignore anything in
 * the stockroom). Break ties by returning the section with the lowest address.
 */

void* section_with_most_items() {
  // TODO: implement this function
  return NULL;
}
#endif

// Returns the aisle and section index of the first section with the given id 
// that has no items in it.
// Returns -1, -1 if no such section exists.
//
// Breaks ties by returning the section with the lowest aisle index then section index.
struct aisle_section empty_section_with_id(unsigned short id) {
//TODO: implement this function
  struct aisle_section ret = {-1,-1};
  for (int idx=0;idx<NUM_AISLES;idx++){
    unsigned long aisle = aisles[idx];
    for (int section=0;section<4;section++){
      unsigned short sectionID = get_id(aisle,section);
      if (sectionID == id){
        unsigned short items = num_items(aisle,section);
        if (items == 0){
          ret.aisle_index = idx;
          ret.section_index = section;
          return ret; 
        }
      }
    }
  }
  return ret;
}

// Returns the aisle and section index of the section with the highest inventory in the store.
//
// Breaks ties by returning the section with the lowest aisle index then section index.
struct aisle_section section_with_most_items() {
//TODO: implement this function
  struct aisle_section ret = {0,0};
  unsigned short maxItems = 0;
  for (int idx = 0;idx < NUM_AISLES;idx++){
    unsigned long aisle = aisles[idx];
    for (int section=0;section<4;section++){
      unsigned short items = num_items(aisle,section);
      if (items > maxItems){
        ret.aisle_index = idx;
        ret.section_index = section;
        maxItems = items;
      }
    }
  }
  return ret;
}

