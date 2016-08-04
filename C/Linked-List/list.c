#include <stdlib.h>

#include "list.h"

struct list_entry_t {
    void* element;
    struct list_entry_t* next;
    struct list_entry_t* prev;
};

typedef struct list_entry_t list_entry_t;
/* Linked list containing the current object and the next object in line,
 when the user first creates a list, it does so using the head, then from
there on all new entries are push to the back.*/

static list_entry_t* new_entry(void* element) {
  list_entry_t* e = (list_entry_t*)calloc(1, sizeof(list_entry_t));
    e->element = element;
    e->next = NULL;
    e->prev = NULL;
    return e;
}

static void free_entry(struct list_entry_t* entry) {
    free(entry);
} /*Deletes the reserved memory for the input entry*/

struct list_t {
    struct list_entry_t* head;
}; /**/

static list_entry_t* get_entry_at(list_t* list, int i) {
    list_entry_t* e = list->head;

    do {
        if(!e) return NULL;

        if(i-- == 0) return e;

        e = e->next;
    } while(1);
}

static list_entry_t* get_last_entry(list_t* list) {
    return get_entry_at(list, list_length(list)-1);
} /*Pulls the last entry from the provided list. */

list_t* new_list() {
    return (list_t*)malloc(sizeof(list_t));
} /*Creates a new empty list with reserved space for one object.*/

void free_list(list_t* list) {
    list_entry_t* e = list->head;

    while(e) {
        list_entry_t* old_e = e;
        e = e->next;
        free_entry(old_e);
    };

    free(list);
} /*Removes the reserved space for the provided list and all its items.*/

int list_length(list_t* list) {
    int l = 0;
    for(list_entry_t* e = list->head; e != NULL; e = e->next) l++;
    return l;
} /*Walks through a list to count the number of objects in it. Lists are
    terminated by a NULL at the next pointer*/

void* list_get(list_t* list, int i) {
    list_entry_t* e = get_entry_at(list, i);

    if(!e) { return NULL; }
    else { return e->element; }
} /*Get element at position i*/

void list_push(list_t* list, void* element) {
    list_entry_t* entry_to_insert = new_entry(element);

    if(!list->head) {
        list->head = entry_to_insert;
    }
    else {
        list_entry_t* e = get_last_entry(list);
        e->next = entry_to_insert;
        entry_to_insert->prev = e;
    }
}  /*Checks if the provided list is empty, if so, puts it in the first
    free space, if it's not empty, puts it in the back by asking for the
   last item in the list, and changing the pointer to the last object*/

void list_remove(list_t* list, void* element) {
    int index = list_find_index(list, element);

    if(index >= 0) { list_remove_at(list, index); }
}

int list_find_index(list_t* list, void* element) {
    int len = list_length(list);

    for(int i = 0; i < len; i++) {
        if(list_get(list, i) == element) return i;
    }

    return -1;
}

void list_remove_at(list_t* list, int index) {
    list_entry_t* e = get_entry_at(list, index);

    if(!e) { return; }

    if(e->prev) { e->prev->next = e->next; }
    else { list->head = e->next; }

    if(e->next) { e->next->prev = e->prev; }
}
