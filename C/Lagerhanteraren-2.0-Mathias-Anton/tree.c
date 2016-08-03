#include "tree.h"

typedef struct tree
{
  char *name;
  char *description;
  int price;
  list_t *shelfs;

  tree_t *right, *left;
} tree_t;

tree_t *tree_new(char *name, char *description, int price, char *shelf, int quantity)
{
  tree_t *newNode = calloc(1, sizeof(tree_t));
  
  newNode->name = (char *)calloc(51, sizeof(char));
  strcpy(newNode->name, name);
  
  newNode->description =(char *)calloc(201, sizeof(char));
  strcpy(newNode->description, description);
  
  newNode->price = price;
  newNode->shelfs = list_new(shelf, quantity);
  newNode->right = NULL;
  newNode->left = NULL;
  return newNode;
}

char *get_node_name(tree_t *node)
{
  return node->name;
}

int tree_size(tree_t *node)
{
  if (node == NULL)
    {
      return 0;
    }
  else
    {
      return (tree_size(node->left) + 1 + tree_size(node->right));
    }
}

int tree_depth(tree_t *tree)
{
  if (tree == NULL) 
    return 0;
  else
    {
      int leftDepth = tree_depth(tree->left);
      int rightDepth = tree_depth(tree->right);

      if (leftDepth > rightDepth)
	{
	  return (leftDepth + 1);
	}
      else
	{
	  return (rightDepth + 1);
	}
    }
  
}

int tree_insert(tree_t **tree, tree_t *insert)
{
  if (insert == NULL) return 0;

  tree_t *root = *tree;
  if (root == NULL)
    {
      *tree = insert;
      return 1;
    }
  
  if (strcasecmp(insert->name, root->name) == 0)
    {
      //Duplicant!
      return 2;
    }
  else if (strcasecmp(insert->name, root->name) > 0)
    {
      if (root->right != NULL)
	{
	  tree_insert(&root->right, insert);	  
	}
      else
	{
	  root->right = insert;
	}
    }
  else if (strcasecmp(insert->name, root->name) < 0)
    {
      if (root->left != NULL)
	{
	  tree_insert(&root->left, insert);
	}
      else
	{
	  root->left = insert;
	}
    }
  return 1;
}

void migrate_info(tree_t *to, tree_t *from)
{
  char *tmp = to->name;
  to->name = from->name;
  free(tmp);
  char *temp = to->description;
  to->description = from->description;
  free(temp);
  to->price = from->price;
  to->shelfs = from->shelfs;
}

tree_t *tree_find_parrent(tree_t *root, char *key)
{
  tree_t *current = root;
  tree_t *parent = NULL;
  while(current)
    {
      if(current->name == key)
	{
	  return parent;
	}
      else
	{
	  parent = current;
	  if(strcasecmp(key, current->name) < 0)
	    {
	      current = current->left;    
	    }
	  else
	    {
	      current = current->right; 		
	    }
	}
    }
  return parent;
}
tree_t *minNode(tree_t *node)
{
   tree_t *current = node;
 
    /* loop down to find the leftmost leaf */
    while (current->left != NULL)
        current = current->left;
 
    return current;
}

tree_t *remove_node(tree_t *root, char *key)
{
  if (root == NULL) return root;

  if (strcasecmp(key, root->name) < 0)
    {
      root->left = remove_node(root->left, key);
    }
  else if (strcasecmp(key, root->name) > 0)
    {
      root->right = remove_node(root->right, key);
    }
  else
    {
      if (root->left == NULL)
	{
	  tree_t *temp = root->right;
	  free(root->name);
	  free(root->description);
	  free_list(root->shelfs);
	  free(root);
	  print_short_tree(temp, 0 , 20);
	  return temp;
	}
      else if (root->right == NULL)
	{
	  tree_t *temp = root->left;
	  free(root->name);
	  free(root->description);
	  free_list(root->shelfs);
	  free(root);
	  return temp;
	}
      
      tree_t *tmp = minNode(root->right);
      migrate_info(root, tmp);
      root->right = remove_node(root->right, tmp->name);
    }
  return root;
}

void tree_remove(tree_t **root, tree_t *node, int index)
{
  int listSize = list_length(node->shelfs);
  if (listSize < 2)
    {
      *root = remove_node(*root, node->name);
    }
  else
    {
      printf("\n%s finns på följande platser:\n\n", node->name);
      list_t *shelf = remove_shelf(&node->shelfs);
      free_list_item(shelf);
    }
}

void print_edit_goods(char *name, char *strVal, int intVal)
{
  if (strlen(strVal) == 0)
    {
      printf("\nNuvarande %s: %d\n", name, intVal);
    }
  else
    {
      printf("\nNuvarande %s: %s\n", name, strVal);
    }
  printf("\n----------------------------------------------\n");
}

void tree_edit(tree_t *root, tree_t *node)
{
  bool exit = false;
  do
    {
      printf("\n[N]anmn\n");
      printf("[B]eskrivning\n");
      printf("[P]ris\n");
      printf("An[t]al\n");

      char q = ask_question_char("Välj åtgärd eller [a]vbryt:", "nNbBPpLltTaA");

      switch (q) {
      case 'a': case 'A':
	exit = false;
	break;
      case 'n': case 'N': {
	print_edit_goods("namn", node->name, 0);
	char *tmp = node->name;
	char *name = calloc(1, 51 * sizeof(char));  
	ask_question_string("Namn", name, sizeof(name));
	node->name = name;
	free(tmp);
	exit = false;
	break;
      }
      case 'b': case 'B': {
	print_edit_goods("beskrivning", node->description, 0);
	char *des = calloc(1, 201 * sizeof(char));
	ask_question_string("Ny beskrivning: ", des, sizeof(des));
	char *tmp = node->description;
	node->description = des;
	free(tmp);
	exit = false;
	break;
      }
      case 'p': case 'P': {
	print_edit_goods("pris", "", node->price);
	node->price = ask_question_int("Nytt pris: ");
	exit = false;
	break;
      }
      case 't': case 'T': {
	int total = list_get_count(node->shelfs);
	print_edit_goods("antal", "", total);
	int count = 0;
	do
	  {
	    count = ask_question_int("Ta bort antal: ");
	    if (count > total)
	      {
		printf("Du kan inte ta bort fler än vad det finns i lager!\n");
	      }
	  } while (count > total);
	if (count != 0)
	  {
	    if (count == total)
	      {
		remove_node(root, node->name);
	      }
	    else
	      {
		list_change_count(&node->shelfs, count); 		
	      }
	  }
	exit = false;
	break;
      }
      default:
	printf("Något gick snett vir provar igen!\n");
	exit = true;
	break;
      }
    } while (exit);
}

tree_t *get_goods_for_storage_location(tree_t *node, char *shelf)
{
  if (node == NULL) return NULL;

  if (node->right == NULL && node->left == NULL)
    {
      if (check_for_duplicant(node->shelfs, shelf))
	{
	  return node;
	}
    }
  else
    {
      get_goods_for_storage_location(node->left, shelf);
      get_goods_for_storage_location(node->right, shelf);
    }
  return NULL;
}

tree_t *tree_search_duplicant(tree_t *node, char *name)
{
  if (node == NULL) return NULL;

  if (strcasecmp(name, node->name) == 0)
    {
      return node;
    }
  else if (strcasecmp(name, node->name) > 0)
    {
      return tree_search_duplicant(node->right, name);
    }
  else if(strcasecmp(name, node->name) < 0)
    {
      return tree_search_duplicant(node->left, name);
    }
  return NULL;
}

tree_t *tree_get_inorder_traverse(tree_t *node, int *pos, int posToFind)
{

  tree_t *temp;
  
  if (node->left)
    {
      temp = tree_get_inorder_traverse(node->left, pos, posToFind);
      if (temp != NULL)
	return temp;
    }
  
  ++*pos;
  if (*pos == posToFind) return node;
  
  if (node->right)
    {
      temp = tree_get_inorder_traverse(node->right, pos, posToFind);
      if (temp != NULL)
	return temp;
    }
  
  return NULL;
}

tree_t *get_node_at_index(tree_t *tree, int pos)
{
  int i = 0;
  tree_t *node = tree_get_inorder_traverse(tree, &i, pos);
  return node;
}

void add_shelf_to_existing_tree(tree_t *node, char *shelf, int quantity)
{
  list_append(&node->shelfs, shelf, quantity);
}

void tree_print_inorder_traverse(tree_t *node, int *pos, int range)
{
  if (node == NULL) return;

  tree_print_inorder_traverse(node->left, pos, range);

  if (*pos > range)
    return;
  else
    {
      ++*pos;
      printf("\t%d %s\n", *pos, node->name);
    }

  tree_print_inorder_traverse(node->right, pos, range);
}

void print_short_tree(tree_t *tree, int start, int range)
{
  printf("\tVaror på lager\n");
  printf("\t==============\n\n");
  if (tree == NULL) return;
  
  int pos = start;
  tree_print_inorder_traverse(tree, &pos, range);
}

void print_tree_node(tree_t *node)
{
  if (node == NULL) return;
  printf("\nNamn: %s\n", node->name);
  printf("Beskrivning: %s\n", node->description);
  printf("pris: %d kr\n", node->price);
  list_print_shelfs(node->shelfs);
}
void free_tree(tree_t *root)
{
  if (root == NULL) return;
 
    /* first delete both subtrees */
    free_tree(root->left);
    free_tree(root->right);
   
    /* then delete the node */
    if (root->name) free(root->name);
    if (root->description) free(root->description);
    free_list(root->shelfs);
    free(root);
}
