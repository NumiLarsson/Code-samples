#ifndef LIST_H_
#define LIST_H_

struct list_t;
typedef struct list_t list_t;
/* Linked list containing any type of elements*/

list_t* new_list();
void free_list(list_t*);
/*Removes the reserved space for the provided list, doesn't remove the 
 individual elements in the list*/

// void free_list_and_item(); NYI.

int list_length(list_t*);
/*Returns the number of nodes in the list.*/
void* list_get(list_t*, int);

void list_push(list_t*, void*);
/*Pushes the provided item (void*) to the end of the provided list
    "insert"
*/
void list_remove(list_t*, void*);
/* removes an element from a list */

int list_find_index(list_t*, void*);
/* Finds an elements place in a list, returns the position as an integer */
void list_remove_at(list_t*, int);
/* Remove the element at the place corresponding to the input integer */

#endif // LIST_H_