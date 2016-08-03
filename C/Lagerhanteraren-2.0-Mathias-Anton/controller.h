#ifndef _model
#define _model

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#include "tree.h"
#include "pallet.h"
#include "reading.h"

/**
 * @file controller.h
 * @authors: Anton Larsson & Mathias Palm
 * @date: 27 Oct 2015
 * @brief Handels the main controls of the program
 */


/**
 * @breif Struct for handling undo action
 */
typedef struct action_t
{
  enum { NOTHING, ADD, REMOVE, EDIT } type;
  union
  {
    struct { tree_t *saved; tree_t *removed;};      // REMOVE
    struct { tree_t *edited; tree_t *original; }; // EDIT
  };
} Action_t;

/**
 * @brief add goods to the storage
 *
 * @param pointer to tree_t pointer
 *
 * @param Action_t pointer
 */
void add_goods(tree_t **root, Action_t *action);

/**
 * @brief removes goods from the storage
 *
 * @paramtree_t pointer
 *
 * @param Action_t pointer
 */
void remove_goods(tree_t **root, Action_t *action);

/**
 * @brief edit goods in the storage
 *
 * @param pointer to tree_t pointer
 *
 * @param Action_t pointer
 */
void edit_goods(tree_t *root, Action_t *action);

/**
 * @brief prints out the whole storage
 *
 * @param pointer to tree_t pointer
 */
void list_goods(tree_t *tree);


/*
 * ångra
 */
//void undo_action(Product **list, Action_t *action);

/**
 * @brief pack goods from storage to pallet
 *
 * @param pointer to tree_t pointer
 *
 * @param pointer to pallet_t pointer
 */
void pack_pallet(tree_t *root, pallet_t **pallet);

#endif
