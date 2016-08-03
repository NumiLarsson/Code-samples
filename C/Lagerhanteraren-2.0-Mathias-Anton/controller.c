#include "controller.h"

void add_goods(tree_t **root, Action_t *action)
{
  puts("Lägg till vara");
  puts("==============\n");

  char *name = (char *)calloc(50, sizeof(char));
  
  ask_question_string("Namn", name, sizeof(name));

  tree_t *duplicant = tree_search_duplicant(*root, name);

  
  char *des = NULL;

  if (duplicant != NULL)
    {
      printf("%s finns redan i databasen.\n", name);
      puts("Använder samma pris och beskrivning.");
    }
  else
    {
      des = (char *)calloc(200, sizeof(char));
      ask_question_string("Beskrivning", des, sizeof(des));
    }
  
  char place[sizeof(char) + sizeof(int)];

  tree_t *testPlace;
  char shelf;
  int row;
  do
    {
      shelf = ask_question_char("Hylla","abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
      row = ask_question_int("Rad");
      snprintf(place, sizeof(place), "%c%d", shelf, row);
      
      testPlace = get_goods_for_storage_location(*root, place);
      
      if (testPlace != NULL) {
	printf("\nHyllPlatsen är upptagen av: %s, prova igen!\n", get_node_name(testPlace));
      }
    } while (testPlace != NULL);

  
  char *shelfToUse = (char *)calloc(strlen(place) + 1,sizeof(char));
  strcpy(shelfToUse, place);
   
  int quantity = ask_question_int("Antal");
  int price;
  if (duplicant == NULL)
    {
      price = ask_question_int("Pris");     
    }

  bool sure = to_be_sure();
  if (sure)
    {
      if (duplicant != NULL)
	{
	  add_shelf_to_existing_tree(duplicant, shelfToUse, quantity);
	}
      else
	{
	  tree_t *newNode = tree_new(name, des, price, shelfToUse, quantity);
	  tree_insert(root, newNode);
	  //action->type = ADD;
	}
    }
  else
    {
      if (name) free(name);
      free(shelfToUse);
      if (des != NULL)
	{
	  free(des);
	}
      printf("\nGår tillbak till startmenyn \n");
    }
  if (name) free(name);
  if (des) free(des);
  if (shelfToUse) free(shelfToUse);
}

int print_range_list(tree_t *tree, int pos, int range, char *question, bool useNext, int treeSize)
{
  print_short_tree(tree, pos, range);
  char q[51];
  int i;
  bool stayInLoop = true;
  do
    {
      ask_question_string(question, q, 50);
      if (q[0] == 'a' || q[0] == 'A')
	{
	  stayInLoop = false;
	  i = -2;
	}
      else if ((q[0] == 'n' || q[0] == 'N') && useNext)
	{
	  stayInLoop = false;
	  i = -1;
	}
      else if (is_valid_int(q))
	{
	  i = atoi(q);
	  if (i <= treeSize)
	    {
	      stayInLoop = false;
	    }
	  else 
	    {
	      printf("\nDu angav ett nummer utanför listan\n");
	    }
	}
      else
	{
	  printf("\nFel input! Prova igen\n");
	}
    } while (stayInLoop);
  return i;
}

int range_check(int range, int treeSize, tree_t *tree, int start)
{
  int index;
  if (range > treeSize) 
    {
      index = print_range_list(tree, start, range, "Välj vara eller [a]vbryt", false, treeSize);
    }
  else
    {
      index = print_range_list(tree, start, range, "Välj vara eller [a]vbryt eller visa [n]ästa sida:", true, treeSize);
    }
  return index;
}
void remove_goods(tree_t **root, Action_t *action)
{
  int start = 0;
  int treeSize = tree_size(*root);
  int range = 20;
  
  int index;
  if (treeSize > 0)
    {
      index = range_check(range, treeSize, *root, start);
      while (index < 0 && index != -2) 
	{
	  start += 20;
	  range += 20;
	  index = range_check(range, treeSize, *root, start); 
	}
      if (index > 0)
	{
	  tree_t *node = get_node_at_index(*root, index);
	  tree_remove(root, node, index);
	} 
    }
}

void edit_goods(tree_t *root ,Action_t *action)
{
  int start = 0;
  int treeSize = tree_size(root);
  int range = 20;
  
  int index;
  if (treeSize > 0)
    {
      index = range_check(range, treeSize, root, start);
      while (index < 0 && index != -2) 
	{
	  start += 20;
	  range += 20;
	  index = range_check(range, treeSize, root, start); 
	}
      if (index > 0)
	{
	  tree_t *node = get_node_at_index(root, index);
	  tree_edit(root, node);
	} 
    }
}

void pack_pallet(tree_t *root, pallet_t **pallet)
{
  bool addMore = true;
  
  do {
    if (is_pallet_empty(*pallet)) {
      printf("\nDin pall är tom\n\n");
    } else {
      list_pallet(*pallet);
    }

   int start = 0;
   int treeSize = tree_size(root);
   int range = 20;
  
   int index;
   if (treeSize > 0)
     {
       index = range_check(range, treeSize, root, start);
       while (index < 0 && index != -2) 
	 {
	   start += 20;
	   range += 20;
	   index = range_check(range, treeSize, root, start); 
	 }
       if (index > 0)
	 {
	   tree_t *node = get_node_at_index(root, index);
	   addMore = pack_item_on_pallet(pallet, node);
	 } 
     }
  } while (addMore);
  list_detailed_pallet(*pallet);
}


void list_goods(tree_t *tree)
{
  int start = 0;
  int treeSize = tree_size(tree);
  int range = 20;
  
  int index;
  if (treeSize > 0)
    {
      index = range_check(range, treeSize, tree, start);
      while (index < 0 && index != -2) 
	{
	  start += 20;
	  range += 20;
	  index = range_check(range, treeSize, tree, start); 
	}
      if (index > 0)
	{
	  tree_t *node = get_node_at_index(tree, index);
	  print_tree_node(node);
	} 
    }
}
