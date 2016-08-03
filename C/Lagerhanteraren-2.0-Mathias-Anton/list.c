#include "list.h"

typedef struct list
{
  char *shelf;
  int quantity;
  list_t *next;
} list_t;


list_t *list_new(char *shelf, int count)
{
  list_t *newList = (list_t *)malloc(sizeof(list_t));
  newList->shelf = (char *)calloc(strlen(shelf) + 1,sizeof(char));
  strcpy(newList->shelf, shelf);
  newList->quantity = count;
  newList->next = NULL;
  return newList;
}

int list_length(list_t *list)
{
  list_t *cursor = list;
  int count = 0;
  while (cursor)
    {
      ++count;
      cursor = cursor->next;
    }
  return count;
}

void list_append(list_t **list, char *shelf, int quantity)
{
  if (*list == NULL)
    {
      *list = list_new(shelf, quantity);
    }
  else
    {
      // går till slutet av listan
      list_t *cursor = *list;
      list_t *prev = cursor;
      while (cursor) 
	{
	  prev = cursor;
	  cursor = cursor->next;
	}

      list_t *newItem = list_new(shelf, quantity);
      prev->next = newItem;
    }
  
}

list_t *get_list_for_index(list_t *list, int index)
{
  list_t *cursor = list;
  int count = 1;
  while (cursor) 
    {
      if (count == index)
	{
	  return cursor;
	}
      ++count;
      cursor = cursor->next;
    }
  return NULL;
}

list_t *remove_shelf(list_t **list)
{
  list_t *cursor = *list;
  int count = 1;
  list_t *shelf = NULL;
  while (cursor) 
    {
      printf("%d. %s (%d stycken)\n", count, cursor->shelf, cursor->quantity);
      ++count;
      cursor = cursor->next;
    }
  int input;
  int listSize = list_length(*list);
  do
    {
      input = ask_question_int("Vilken plats skall tas bort (0 för ingen)?");
      if (input > listSize || input < 0)
	{
	  printf("Du kan inte ange en plats som inte finns!\n");
	}
    } while (input > listSize || input < 0);

  if (input != 0)
    {
      bool sure = to_be_sure();
      if (sure)
	{
	  list_t *prev = get_list_for_index(*list, input - 1);
	  list_t *item = get_list_for_index(*list, input);
	  
	  if (prev == NULL)
	    {
	      *list = item;
	    }
	  else 
	    {
	      prev->next = item->next;
	      shelf = item;
	    }
	  
	}
    }
  return shelf;
}


bool check_for_duplicant(list_t *list, char *shelf)
{
  list_t *cursor = list;
  while (cursor)
    {
      if (strcasecmp(shelf, cursor->shelf) == 0) {
	return true;
      }
      cursor = cursor->next;
    }
  return false;
}

void list_change_count(list_t **list, int count)
{
  int x = count;
  list_t *cursor = *list;
  while (cursor)
    {
      if (cursor->quantity <= x)
	{
	  x -= cursor->quantity;
	  list_t *temp = cursor;
	  *list = cursor->next;
	  cursor = *list;
	  free_list_item(temp);
	  if (x == 0) return;
	}
      else
	{
	  cursor->quantity -= x;
	  return;
	}
    }
}

int list_get_count(list_t *list)
{
  list_t *cursor = list;
  int total = 0;
  while (cursor)
    {
      total += cursor->quantity;
      cursor = cursor->next;
    }
  return total;
}

void list_print_shelfs(list_t *list)
{
  list_t *cursor = list;
  int total = 0;
  while (cursor)
    {
      printf("Hyllplats: %s \n    antal: %d st\n", cursor->shelf, cursor->quantity);
      total += cursor->quantity;
      cursor = cursor->next;
    }
  printf("\n Totalt i lager: %d st\n", total);
}

void free_list_item(list_t *list)
{
  if (list)
    {
      free(list->shelf);
      free(list); 
    }
}

void free_list(list_t *list)
{
  while (list)
    {
      if (list->shelf) free(list->shelf);
      list_t *prev = list;
      list = list->next;
      free(prev);
    }
}
