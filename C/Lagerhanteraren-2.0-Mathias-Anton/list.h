#ifndef _list
#define _list

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

#include "reading.h"

/**
 * @file list.h
 * @authors: Anton Larsson & Mathias Palm
 * @date: 27 Oct 2015
 * @brief handles product shelf and count
 */

/**
 * @brief Struct containging products
 */
typedef struct list list_t;

/**
 * @brief creates a new list
 *
 * @param char *pointer
 *
 * @param int
 *
 * @return list_t *pointer
 */
list_t *list_new(char *shelf, int count);

/**
 * @breif inserts a new elemnt at the end of the list
 *
 * @param list_t **pointer
 *
 * @param char *pointer
 *
 * @param int
 */
void list_append(list_t **list, char *shelf, int quantity);

/**
 * @breif removes an elemnt from the list
 *
 * @param pointer to the list pointer
 *
 * @return the elemnt to remove
 */
list_t *remove_shelf(list_t **list);

/**
 * @breif changes the count for shelf
 *
 * @param pointer to the list pointer
 *
 * @param number to remove from shelf
 */
void list_change_count(list_t **list, int count);

/**
 * @breif checks for duplicant shelfs
 *
 * @param pointer to the list
 *
 * @param shelf as a string
 *
 * @return true if exists
 */
bool check_for_duplicant(list_t *list, char *shelf);
/// Returns the length of the list. It is undefined
/// whether the length is calculated in O(n) time or
/// whether there is a size counter in the list object
/// that is manipulated by insert, remove, etc. 
/// \param list the list
/// \returns the length of list

/**
 * @brief returns the lenght of the list
 *
 * @param pointer to the list
 *
 * @return lenght of the list as int
 */
int list_length(list_t *list);

/**
 * @brief returns the total of product qunatity in the list
 *
 * @param pointer to the list
 *
 * @return total quantity of the list as int
 */
int list_get_count(list_t *list);

/**
 * @brief print outs the list
 *
 * @param pointer to the list
 */
void list_print_shelfs(list_t *list);

/**
 * @brief frees an elemnt from the memory 
 *
 * @param pointer to the list
 */
void free_list_item(list_t *list);

/**
 * @brief frees the list from memery
 *
 * @param pointer to the list
 */
void free_list(list_t *list);
#endif
