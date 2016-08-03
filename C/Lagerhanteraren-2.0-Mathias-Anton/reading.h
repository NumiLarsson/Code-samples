#ifndef _reading
#define _reading

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>

/**
 * @file reading.h
 * @authors: Anton Larsson & Mathias Palm
 * @date: 27 Oct 2015
 * @brief handels all the io
 */

/**
 * @brief Prints a question and acceptable alternatives and waits for user input. 
 * If input is not one of the accepted characters then the answer will be rejected 
 * and the user will be prompted to input a new answer.
 *
 * @param char pointer 
 *
 * @param char pointer
 *
 * @return a char from the user
 */
char ask_question_char(char *q, char *alt);

/**
 * @brief Prints a question and awaits for user input. 
 * if not an int user will be promted to reenter
 *
 * @param char pointer 
 *
 * @return a int from the user
 */
int ask_question_int(char *q);

/**
 * @brief Checks if a char is equal to in and returns true or false
 *
 * @param char pointer 
 *
 * @return bool
 */
bool is_valid_int(const char *str);

/**
 * @brief Prints a question and awaits for user input. 
 * Stores user input in the provided string if the provided size is right
 *
 * @param char pointer
 *
 * @param char pointer
 *
 * @param int
 */
void ask_question_string(char *q, char *string, int size);

/**
 * @brief reads line from stdin
 *
 * @param char pointer
 * 
 * @param int
 *
 * @param FILE
 */
void read_line(char *dest, int n, FILE *source);

/**
 * @brief aks the user to procede two times, the returns true or false
 *
 * @return a int from the user
 */
bool to_be_sure_to_be_sure();

/**
 * @brief aks the user to procede, the returns true or false
 *
 * @return a int from the user
 */
bool to_be_sure();
#endif
