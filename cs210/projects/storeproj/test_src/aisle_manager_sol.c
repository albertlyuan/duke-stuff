// ------------------------------------------------------------------------------------------------
// Overview
// ------------------------------------------------------------------------------------------------
//  This is a program to keep track and control a drone used for delivery.
//
//  A drone is represented by a 64-bit long integer, which is divided
//  into three sections: ID, status and bins.
//
//  Drone Layout:
//
//      Within a drone, the bin field is 20 bits starting form the least-significant bit, the
//      status field is 37 bits, and the ID is 7 bits.
//
//      Example drone:
//
//                msb                                                           lsb
//                  +-------+--------------------------------+------------------+
//                  |  ID 7 |          Status 37             |     Bins 20      |
//                  +-------+--------------------------------+------------------+
//                  |       |                                |                  |
//     bit offset: 64      57                               20                  0
//  
//  Bins Layout:
//
//      The bins of a drone are divided into 2 parts. The 16 least significant bits indicate
//      bins that can contain an item for delivery. For each of the 16 bits/bins, a 1 indicates
//      that the bin is full (i.e., contains an item) and a 0 indicates the bin is empty.
//      The bins are organized on a circular platform and to deliver an item the corresponding
//      bin must be over a door that is under the zeroth position (least significant bit).
//      The 4 upper-most significant bits of the bin represent a unique identifier for the bin
//      that is currently in the zeroth position.
//
//  
//      Example drone bins: 0xB651A
//  
//                msb                                         lsb
//                  +---------+---------------------------------+
//                  | 1 0 1 1 | 0 1 1 0 0 1 0 1 0 0 0 1 1 0 1 0 |
//                  +---------+---------------------------------+
//                  |Bin at 0 |              Slots              |
//      bit offset: 20       16                                 0
//
//      For this example, the bin in the 0th position is 1011 (0xB, 11 in decimal), and there are currently 7 
//      full bins (bit offsets 1, 3, 4, 8, 10, 13, and 14) and 9 empty bins. 
// 
//  Written by Alvin Lebeck (alvy@cs.duke.edu) inspired by problems from
//  Porter Jones (pbjones@cs.washington.edu)
// ------------------------------------------------------------------------------------------------

#include "test_util.h"

#define NUM_SPACES 10

// TODO: Fill in these with the correct hex values

// Mask for extracting a section from the least significant bits of an aisle.
// (aisle & SECTION_MASK) should preserve a section's worth of bits at the lower
// end of the aisle and set all other bits to 0
#define SECTION_MASK 0xFFFF

// Mask for extracting the spaces bits from a section
// (section & SPACES_MASK) should preserve all the spaces bits in a section
// and set all non-spaces bits to 0.
#define SPACES_MASK 0x03FF

// Mask for extracting the ID bits from a section
// (section & ID_MASK) should preserve all the id bits in a section
// and set all non-id bits to 0.
#define ID_MASK 0xFC00

// Given a an aisle and a section index.
//
// Returns the section at the given index of the given aisle.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned short get_section_sol(unsigned long aisle, int index) {
  unsigned long shifted = aisle >> (index * 16);
  return (unsigned short)(shifted & SECTION_MASK);
}

// Given an aisle and a section index.
//
// Returns the id of the section at the given index of the given aisle.
// The returned short should have the least 6 significant bits set to the id
// and the 10 most significant bits set to 0.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned short get_id_sol(unsigned long aisle, int index) {
  unsigned short section = get_section_sol(aisle, index);
  return (unsigned short) ((section & ID_MASK) >> NUM_SPACES);
}

// Given an aisle and a section index.
//
// Returns the spaces of the section at the given index of the given aisle.
// The returned short should have the least 10 significant bits set to the spaces
// and the 6 most significant bits set to 0.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned short get_spaces_sol(unsigned long aisle, int index) {
  unsigned short section = get_section_sol(aisle, index);
  return (unsigned short) (section & SPACES_MASK);
}

// Given an aisle, a section index, and a short representing a 
// new bit pattern to be used for section.
//
// Sets the section at the given index of the given aisle to
// to the new bit pattern.
//
//Return the updated aisle
//
// Can assume the index is a valid index (0-3 inclusive)

// Alternative solution w/shifting
unsigned long set_section_sol(unsigned long aisle, int index, unsigned short new_section) {
  int shift_amount = index * 16;
  unsigned long mask = (unsigned long) SECTION_MASK << shift_amount;
  aisle &= ~mask;
  aisle |= (unsigned long) new_section << shift_amount;
/*  
  unsigned long mask = (1 << shift_amount) - 1;
  unsigned long below_section = aisle & mask;

  unsigned long section_in_ls = aisle >> shift_amount;
  section_in_ls &= ~SECTION_MASK;
  section_in_ls |= new_section; 

  aisle = (section_in_ls << shift_amount) | below_section;
*/
  return aisle;
}

// Given an aisle, a section index, and a short representing a 
// new bit pattern to be used for the id of the section.
//
// Sets the id for the section at the given index of the given aisle to
// to the new bit pattern. The new id will be in the least significant bits
// of the given bit pattern. If the new bit pattern does not fit in 6 bits,
// then the method should leave the id unchanged.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned long set_id_sol(unsigned long aisle, int index, unsigned short new_id) {
  unsigned short curr_section;

  if ((new_id & 0xFFC0) != 0) 
    return aisle;

  curr_section = get_section_sol(aisle, index);

  curr_section &= ~ID_MASK;
  curr_section |= new_id << NUM_SPACES;

  return(set_section_sol(aisle, index, curr_section));
}

// Given an aisle, a section index, and a short representing a 
// new bit pattern to be used for the spaces of the section.
//
// Sets the spaces for the section at the given index of the given aisle to
// to the new bit pattern. The new spaces pattern will be in the least significant bits
// of the given bit pattern. If the new bit pattern does not fit in 10 bits,
// then the method should leave the spaces unchanged.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned long set_spaces_sol(unsigned long aisle, int index, unsigned short new_spaces) {
  unsigned short curr_section;

  if ((new_spaces & ~SPACES_MASK) != 0) 
    return aisle;


  curr_section = get_section_sol(aisle, index);

  curr_section &= ~SPACES_MASK;
  curr_section |= new_spaces;

  return (set_section_sol(aisle, index, curr_section));
}

// Given an aisle and a section index.
// 
// Returns the number of items in the section 
// at the given index of the given aisle.
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned short num_items_sol(unsigned long aisle, int index) {
  unsigned short spaces = get_spaces_sol(aisle, index);
  unsigned short count = 0;

  for (int i = 0; i < NUM_SPACES; i++) {
    count += spaces & 1;
    spaces >>= 1;
  }

  return count;
}


// Given an aisle, a section index, and the desired
// number of items to add.
//
// Add at most the given number of items to the section at the given index in the
// given aisle. Items should be added to the least significant spaces possible.
// If a given n is larger than or equal to the number of empty spaces in the 
// section, then the section should appear full after the method finishes.
// 
// Return the updated aisle
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned long add_items_sol(unsigned long aisle, int index, int n) {  
  unsigned short spaces = get_spaces_sol(aisle, index);

  unsigned short mask = 1;
  int space_index = 0;
  while (n > 0 && space_index < NUM_SPACES) {
    if ((spaces & mask) == 0) {
      spaces |= mask;
      n--;
    }
    space_index++;
    mask <<= 1;
  }
  
  return (set_spaces_sol(aisle, index, spaces));
}

// Given an aisle, a section index, and the desired
// number of items to remove.
//
// Remove at most the given number of items from the section at the given index in the
// given aisle. Items should be removed from the least significant spaces possible.
// If a given n is larger than or equal to the number of items in the 
// section, then the section should appear empty after the method finishes.
//
// Return the updated aisle
//
// Can assume the index is a valid index (0-3 inclusive)
unsigned long remove_items_sol(unsigned long aisle, int index, int n) {
  unsigned short spaces = get_spaces_sol(aisle, index);

  unsigned short mask = 1;
  int space_index = 0;
  while (n > 0 && space_index < NUM_SPACES) {
    if ((spaces & mask) != 0) {
      spaces &= ~mask;
      n--;
    }
    space_index++;
    mask <<= 1;
  }

  return(set_spaces_sol(aisle, index, spaces));
}

// Given an aisle, a section index, and a space index.
//
// Toggle the item in the given space index of the section at 
// the given section index in the given aisle.
//
// Toggling means that if the space was previously empty, it should now be filled with an item, 
// and if the space was previously filled it should now be empty.
//
// Can assume the section index is a valid index (0-3 inclusive)
// Can assume the spaces index is a valid index (0-9 inclusive)
unsigned long toggle_space_sol(unsigned long aisle, int section_index, int space_index) {
  unsigned short spaces = get_spaces_sol(aisle, section_index);
  unsigned short mask = 1 << space_index;

  spaces ^= mask;

  return(set_spaces_sol(aisle, section_index, spaces));
}

// Given an aisle, a section index, and a number
// of slots to rotate by.
//
// Rotate the items in the section at the
// given index of the given aisle to the left by the given number of slots
//
// For example, if the spaces are 0b0111100001, then rotating
// left by 2 results in the spaces 0b1110000101
//
// Can assume the index is a valid index (0-3 inclusive)
// NOTE: do not assume n < NUM_SPACES 
// (hint: think about what rotating by a value >= NUM_SPACES is equivalent to)
unsigned long rotate_items_left_sol(unsigned long aisle, int index, int n) {
  unsigned short spaces = get_spaces_sol(aisle, index);

  n = n % NUM_SPACES;
  unsigned short upper_bits = spaces << n;
  unsigned short lower_bits = spaces >> (NUM_SPACES - n);

  spaces = (upper_bits | lower_bits) & SPACES_MASK;

  return(set_spaces_sol(aisle, index, spaces));
}

// Given an aisle, a section index, and a number
// of slots to rotate by.
//
// Rotate the items in the section at the
// given index of the given aisle to the right by the given number of slots
//
// For example, if the spaces are 0b1000011110, then rotating
// right by 2 results in the spaces 0b1010000111
//
// Return the updated aisle.
//
// Can assume the index is a valid index (0-3 inclusive)
// NOTE: do not assume n < NUM_SPACES 
// (hint: think about what rotating by a value >= NUM_SPACES is equivalent to)
unsigned long rotate_items_right_sol(unsigned long aisle, int index, int n) {
  unsigned short spaces = get_spaces_sol(aisle, index);

  n = n % NUM_SPACES;
  unsigned short lower_bits = spaces >> n;
  unsigned short upper_bits = spaces << (NUM_SPACES - n);


  spaces = (upper_bits | lower_bits) & SPACES_MASK;

  return(set_spaces_sol(aisle, index, spaces));
}

char *aisle_academic_misconduct() {
	return "Warning: Disassembly of this source code constitutes academic misconduct";
}
