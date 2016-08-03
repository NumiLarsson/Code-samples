

#include "pallet.h"

typedef struct list
{
  char *shelf;
  int quantity;
  list_t *next;
} list_t;

typedef struct tree
{
  char *name;
  char *description;
  int price;
  list_t *shelfs;

  tree_t *right, *left;
} tree_t;
/*
 * Defining pallet_t structure as linked list of items
 */
typedef struct pallet {
  char* name;
  int cost;
  list_t* shelfList;
  int count;
  pallet_t *next; //< Rest of pallet items
} pallet_t;

/*
 * Create an empty pallet_T
 */
pallet_t *create_pallet(char *name, int cost, int total)
{
  pallet_t *pallet = calloc(1,sizeof(pallet_t));
  
  pallet->name = (char *)calloc(51, sizeof(char));
  strcpy(pallet->name, name);
  pallet->cost = cost;
  pallet->shelfList = NULL;
  pallet->count = total;
  pallet->next = NULL;
  return pallet;
}

/*
 * Frees pallet from memory
 */
void free_pallet(pallet_t *pallet) {
  while (pallet) {
     free_list(pallet->shelfList);
     if (pallet->name) free(pallet->name);
     pallet_t *pallet_to_free = pallet;
     pallet = pallet->next;
     free(pallet_to_free);
  }
}

/*
 * Cheack if pallet is empty
 */
bool is_pallet_empty(pallet_t *pallet) {
  if (!pallet) {
    return true;
  }
  return false;
}

bool add_count_to_shelf_if_exists(pallet_t *pallet, int count, char *key)
{
  list_t *cursor = pallet->shelfList;
  bool test = true;
  while (cursor)
    {
      if (strcasecmp(key, cursor->shelf) == 0)
	{
	  cursor->quantity += count;
	  test = false;
	}
      cursor = cursor->next;
    }
  return test;
}


void pack_on_existing(pallet_t *pallet, int count, tree_t *item)
{
  pallet->count += count;
  
  int x = count;
  list_t *cursor = item->shelfs;
  
  while (x > 0 && cursor)
    {
      if (cursor->quantity <= x)
	{
	  if (pallet->shelfList == NULL)
	    {
	      pallet->shelfList = list_new(cursor->shelf, x);
	    }
	  else
	    {
	      bool check = add_count_to_shelf_if_exists(pallet, x, cursor->shelf);
	      if (check)
		{
		  list_append(&pallet->shelfList, cursor->shelf, x);
		}	      
	    }
	  x -= cursor->quantity;
	}
      else
	{
	  if (pallet->shelfList == NULL)
	    {
	      pallet->shelfList = list_new(cursor->shelf, x);
	    }
	  else
	    {
	      bool check = add_count_to_shelf_if_exists(pallet, x, cursor->shelf);
	      if (check)
		{
		  list_append(&pallet->shelfList, cursor->shelf, x);
		}	      
	    }
	  x = 0;
	}
      cursor = cursor->next;
    }
  list_change_count(&item->shelfs, count);
}

/*
 * Pack item on pallet
 */
bool pack_item_on_pallet(pallet_t **pallet, tree_t *item) {
  printf("\n\tNamn: %s\n", item->name);

  int count = list_get_count(item->shelfs);
  printf("\tAntal: %d\n\n", count);
  
  int quantityToPack;
  bool inBounds = true;
  char buf[50];
  do
    {
      snprintf(buf, sizeof(buf), "Välj antal (0--%d)", count);
      quantityToPack = ask_question_int("Välj antal att packa: ");
	if (quantityToPack <= count && quantityToPack >= 0)
	  {
	    inBounds = false;
	  } else
	  {
	    printf("Du har valt ett ogiltigt antal! prova igen.\n");
	  }
    } while (inBounds);
  
  if (quantityToPack > 0)
    {
      bool check_exists = check_if_exists_on_pallet(*pallet, item->name);
      if (check_exists)
	{
	  pallet_t *cursor = *pallet;
	  while (strcasecmp(cursor->name, item->name) != 0)
	    {
	      cursor = cursor->next;
	    }
	  pack_on_existing(cursor, quantityToPack ,item);
	}
      else
	{
	  pallet_t *new = create_pallet(item->name, item->price, 0);
	  pack_on_existing(new, quantityToPack, item);
	  new->next = *pallet;
	  *pallet = new;
	}
    }
  bool add_more = false;
  bool check = false;
  do
    {
      char q = ask_question_char("Vill du packa en till vara? (j/n)", "[JjNn]");
      q = toupper((unsigned char)q);
      check = !(q == 'N' || q == 'J');
      if (check)
	{
	  printf("Du angav fel tecken, använd (j/n)\n");
	} else
	{
	  if (q == 'N')
	    {
	      add_more = false;
	    } else if (q == 'J')
	    {
	      add_more = true;
	    }
	}
    } while (check);
  return add_more;
}

/*
 * Check if item already exists on pallet
 */
bool check_if_exists_on_pallet(pallet_t *pallet, char *key) {

  pallet_t *cursor = pallet;
  bool doesExist = false;
  while (cursor) {
    if (strcasecmp(cursor->name, key) == 0) {
      doesExist = true;
      break;
    }
    cursor = cursor->next;
  }
  return doesExist;
}

/*
 * Print out current pallet
 */
void list_pallet(pallet_t *pallet) {
  printf("Din pall:\n\n");
  pallet_t *cursor = pallet;
  int counter = 1;
  while (cursor != NULL) {
    printf("%d. %s (%d)\n", counter, cursor->name, cursor->count);
    cursor = cursor->next;
    ++counter;
  }
  printf("\n");
}


/*
 * Calculate price for the whole pallet
 */
float get_pallet_price(pallet_t *pallet) {
  pallet_t *cursor = pallet;
  float price = 0.0;
  while (cursor) {
    price += cursor->count * cursor->cost; 
    cursor = cursor->next;
  }
  return price;
}

/*
 * print out shelf position, count and name of item
 */
void print_detailed_item(list_t *list, char *name) {
  list_t *cursor = list;
  while (cursor)
    {
      printf("%s (%d %s)\n", cursor->shelf, cursor->quantity, name);
      cursor = cursor->next;
    }
}

/*
 * Print out detailed description of pallet items
 */
void list_detailed_pallet(pallet_t *pallet) {
  if (is_pallet_empty(pallet)) {
      printf("\nDin pall är tom\n\n");
    } else {
    list_pallet(pallet);
    
    float price = get_pallet_price(pallet);
    printf("Totalt pris för pallen: %f kr\n\n", price);

    printf("Lagerplatser för att packa pallen:\n");
    pallet_t *cursor = pallet;
    while (cursor) {
      print_detailed_item(cursor->shelfList, cursor->name);
      cursor = cursor->next;
    }
  }
}


