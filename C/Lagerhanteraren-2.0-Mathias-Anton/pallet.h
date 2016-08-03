#ifndef _pallet_
#define _pallet_

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

#include "tree.h"
#include "reading.h"
#include "list.h"

/**
 * @file pallet.h
 * @authors: Anton Larsson & Mathias Palm
 * @date: 27 Oct 2015
 * @brief Handles packing and logistic on packing a pallet
 */

/**
 * @brief Struct containging a items list_entry_t who been packed on pallet.
 */
typedef struct pallet pallet_t;

/**
 * @brief init an empty pallet
 *
 * Creating an empty linked list for the pallet
 *
 * @return empty linked list of pallet_t
 */
pallet_t *create_pallet(char *name, int cost, int total);

/**
 * @breif free pallet from memory
 *
 * Funtion to delete pallet from memory before closing
 *
 * @param pallet_t *pointer
 *
 */
void free_pallet(pallet_t *pallet);

/**
 * @brief check if pallet is empty
 *
 * Cheaking if linked list pallet is empty or not
 *
 * @param pallet_t *pointer
 *
 * @return bool
 */
bool is_pallet_empty(pallet_t *pallet);

/**
 * @brief pack item on pallet
 *
 * Pdding item to pallet_t and returns void if succsesfull
 *
 * @param pallet_t *pointer
 *
 * @param void* pointer
 * 
 * @return bool 
 */
bool pack_item_on_pallet(pallet_t **pallet, tree_t *item);

/**
 * @brief check if item already exists on pallet
 *
 * Cheacking if item exists on pallet, return true if exists.
 *
 * @param pallet_t *pointer
 *
 * @param void *pointer 
 *
 * @return bool
 */
bool check_if_exists_on_pallet(pallet_t *pallet, char *item);

/**
 * @breif print out current pallet
 * 
 * Print out item name + quantity
 *
 * @param pallet_t *pointer
 */
void list_pallet(pallet_t *pallet);

/**
 * @brief print out detailed list of current pallet
 *
 * Print out the current pallet in detail, 
 * showing item names, quantity, total price and shelf spots
 *
 * @param pallet_t *pointer
 */
void list_detailed_pallet(pallet_t *pallet);

#endif
