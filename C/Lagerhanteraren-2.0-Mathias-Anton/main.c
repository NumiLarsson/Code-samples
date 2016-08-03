#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "reading.h"
#include "controller.h"

#include "tree.h"
#include "pallet.h"

int main(int argc, char *argv[])
{
  char choice = 1;
  char *choiceChars = "LlTtRrGgHhPpAa";
  tree_t *root = NULL;
  pallet_t *pallet = NULL;
  Action_t *action = calloc(1,sizeof(struct action_t));
  
  //Dummies
  tree_t *newNode = tree_new("a", "asdfasdf", 23, "c32", 20);
  tree_insert(&root, newNode);

  tree_t *newNode1 = tree_new("b", "asdfasdf", 23, "c322", 20);
  tree_insert(&root, newNode1);

  tree_t *newNode2 = tree_new("c", "asdfasdf", 23, "c3232", 20);
  tree_insert(&root, newNode2);

  tree_t *newNode3 = tree_new("d", "mmmmdf", 23, "f44", 20);
  tree_insert(&root, newNode3);
  do
    {
        puts("\n\nvälkommen till lagerhatering 2.0");
	puts("================================ \n");
	puts("[L]ägga till en vara");
	puts("[T]a bort en vara");
	puts("[R]edigera en vara");
	if (action->type != NOTHING)
	  {
	    choiceChars = "LlTtRrGgHhPpAa";
	    puts("Ån[g]ra senaste ändringen"); 
	  }
	else
	  {
	    choiceChars = "LlTtRrHhPpAa";
	  }
	puts("Lista [h]ela varukatalogen");
	puts("[P]acka en pall");
	puts("[A]vsluta\n\n");
	choice  = ask_question_char("Vad vill du göra idag?", choiceChars);
	switch(choice)
	  {
	  case 'l': case 'L':
	    //Lägg till vara
	    add_goods(&root, action);
	    break;
	  case 't': case 'T':
	    //Ta bort vara
	    remove_goods(&root, action);
	    break;
	  case 'r': case 'R':
	    //Redigera en vara
	    edit_goods(root, action);
	    break;
	  case 'g': case 'G':
	    //Ångra senaste
	    //undo_action(&list, action);
	    break;
	  case 'h': case 'H':	    
	    //Lista hela varukorgen
	    list_goods(root);
	    //display_goods(list);
	    break;	    
	  case 'p': case 'P':
	    // Packa en pall
	    pack_pallet(root, &pallet);
	    break;
	  case 'a': case 'A':
	    if (to_be_sure())
	      {
	        choice = 0;
	      }
	    break;
	  }
    } while (choice);
  free(action);
  free_pallet(pallet);
  free_tree(root);
  printf("\nProgrammet avslutas \n");
  return 0;
}
