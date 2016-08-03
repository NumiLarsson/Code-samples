#include "reading.h"

#define Clear_stdin while(getchar() != '\n')

char ask_question_char(char *q, char *alt)
{
  char input = 0;
  printf("\n%s [%s]\n", q, alt);
  do {
    if (strchr(alt, input) == NULL)
      {
        printf("Fel input '%c', prova igen [%s]\n", input, alt);
	Clear_stdin;
      }
    input = getchar();
  } while (strchr(alt, input) == NULL);
  Clear_stdin;
  return input;
}

int ask_question_int(char *q)
{
  char buffer[10];
  bool test = true;
  printf("\n%s \n", q);
  do {
    if (!test)
      {
	printf("Endast interger, prova igen \n");
      }
    read_line(buffer, 10, stdin);
    test = is_valid_int(buffer);
  } while(!test);
  
  return atoi(buffer);
}

bool is_valid_int(const char *str)
{
  if (*str == '-')
    {
      return false;
    }
  if (!*str)
    {
      return false;
    }
  while (*str)
    {
      if (!isdigit(*str))
	{
	  return false;
	}
      else
	{
	  ++str;
	}
    }
  return true;
}

void ask_question_string(char *q, char *string, int size)
{
  printf("\n%s [Sträng]\n", q);
  char buffer[size];
  do {
    read_line(buffer, size, stdin);
    strcpy(string, buffer);
    if (strlen(string) < 1)
      {
	printf("Strängen får inte vara tom, prova igen [Sträng]\n");
      }
  } while (strlen(string) < 1);
}

void read_line(char *dest, int n, FILE *source)
{
  fgets(dest, n, source);
  int len = strlen(dest);
  if(dest[len-1] == '\n')
    dest[len-1] = '\0';
}

bool to_be_sure_to_be_sure()
{
  char sure = ask_question_char("\n\nÄr du säker?", "YyNn");

  if (sure == 'n' || sure == 'N') return false;

  
  char really =  ask_question_char("\nÄr du verkligen säker?", "YyNn");

  if (really == 'n' || really == 'N') return false;

  return true;
}

bool to_be_sure()
{
  char sure = ask_question_char("Är du säker?", "YyNn");

  if (sure == 'n' || sure == 'N') return false;
  
  return true;
}
