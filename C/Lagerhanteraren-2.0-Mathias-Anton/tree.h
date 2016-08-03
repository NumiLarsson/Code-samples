#ifndef __tree_h__
#define __tree_h__

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#include "list.h"

/**
 * @file tree.h
 * @authors: Anton Larsson & Mathias Palm
 * @date: 27 Oct 2015
 * @brief Handles the binary tree for the database
 */

/**
 * @breif struct for the the binary tree
 */
typedef struct tree tree_t;

/**
 * @brief creates a new node
 *
 * @param node name
 *
 * @param node description
 *
 * @param node price
 *
 * @param node shelf
 *
 * @param node quantity
 *
 * @return new node
 */
tree_t *tree_new(char *name, char *description, int price, char *shelf, int quantity);

/**
 * @brief returns the node name
 *
 * @param the tree node
 *
 * @return node name as a string
 */
char *get_node_name(tree_t *node);

/**
 * @brief get the size of the tree
 *
 * @param the root node
 *
 * @return the number of nodes in the tree
 */
int tree_size(tree_t *node);

/**
 * @brief Get the depth of the tree
 *
 * @param the root node
 *
 * @return the depth of the deepest subtree
 */
int tree_depth(tree_t *tree);

/**
 * @brief insert a node in tree
 *
 * @param the binary tree
 *
 * @param the node to insert
 *
 * @return returns 1 for sucssesful 0 for none and 2 for dublicant
 */
int tree_insert(tree_t **tree, tree_t *insert);

/**
 * @brief removes node at index
 *
 * @param the binary tree
 *
 * @param the node to remove
 *
 * @param the index of the node
 */
void tree_remove(tree_t **root, tree_t *node, int index);

/**
 * @brief edit given node
 *
 * @param the binary tree
 *
 * @param the node to edit
 */
void tree_edit(tree_t *root, tree_t *node);

/**
 * @brief gets node at given index
 *
 * @param the binary tree
 *
 * @param index of the node
 *
 * @return the nood at index
 */
tree_t *get_node_at_index(tree_t *tree, int pos);

/**
 * @brief gets node at given shelf
 *
 * @param the binary tree
 *
 * @param shelf as a string
 *
 * @return the nood at given shelf
 */
tree_t *get_goods_for_storage_location(tree_t *node, char* shelf);

/**
 * @brief search for duplicant products
 *
 * @param the binary tree
 *
 * @param product name
 *
 * @return the node if found
 */
tree_t *tree_search_duplicant(tree_t *node, char *name);

/**
 * @brief adds shelf to an existing node
 *
 * @param the binary tree
 *
 * @param product shelf
 *
 * @param quantity to add
 */
void add_shelf_to_existing_tree(tree_t *node, char *shelf, int quantity);

/**
 * @brief prints products names, shelf and count
 *
 * @param the binary tree
 *
 * @param start index
 *
 * @param number of products to show on each page
 */
void print_short_tree(tree_t *tree, int start, int range);

/**
 * @brief prints products full description
 *
 * @param the binary tree
 */
void print_tree_node(tree_t *node);

/**
 * @brief frees the tree from the memory
 *
 * @param the binary tree
 */
void free_tree(tree_t *root);

#endif
