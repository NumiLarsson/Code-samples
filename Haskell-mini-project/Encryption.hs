module Encryption (chrCodeToWordInt, chrCodeToWordList, encrypt, decrypt, actuallyEncrypt, actuallyDecrypt) where

import Data.Char (ord)
import Data.Char (chr)

{-
    chrCodeToWordInt headObj
        PURPOSE: convert a single Int to its corresponding character
        Pre: True
        POST: headObj converted to its corresponding character.
        VARIANT: headObj
-}

chrCodeToWordInt :: Int -> [Char]
chrCodeToWordInt headObj
        |headObj > 126 = chrCodeToWordInt (headObj-95)
        |headObj < 32 = chrCodeToWordInt (headObj+95)
        |otherwise = [chr headObj]

{-
    chrCodeToWordList list
        PURPOSE: Convert the char codes in list to characters
        PRE: True
        POST: All objects in list converted to their corresponding characters.
        VARIANT: length list             
-}

chrCodeToWordList :: [Int] -> [Char]
chrCodeToWordList (headObj:tailObj)
        |tailObj == [] = chrCodeToWordInt headObj
        |headObj > 126 = (chrCodeToWordList ((headObj-95):tailObj))
        |headObj < 32 = (chrCodeToWordList ((headObj+95):tailObj))
        |otherwise = (chr headObj) : chrCodeToWordList tailObj

{-
    encrypt key object
        PURPOSE: To encrypt a specific String or list of characters using a supplied list of characters or "key".
        PRE: True
        POST: object encrypted using the character code values of key.
        
        Example:
                encrypt "abcabcabc" "abc123abc123" = "   ///   ///"
                encrypt "teg" "ajzakf" = "LdrLe^"
-}
encrypt :: [Char] -> [Char] -> [Char]
encrypt key object
        |key == [] = []
        |object == [] = []
        |otherwise = chrCodeToWordList (actuallyEncrypt key key object)

{-
    decrypt key object
        PURPOSE: To decrypt a specific String or list of characters using a supplied list of characters or "key".
        PRE: True
        POST: object decrypted using the character code values of key.
        
        Example:
                decrypt "abcabcabc" "   ///   ///" = "abc123abc123"
                decrypt "teg" "LdrLe^" = "ajzakf"
-}
decrypt :: [Char] -> [Char] -> [Char]        
decrypt key object
        |key == [] = []
        |object == [] = []
        |otherwise = chrCodeToWordList (actuallyDecrypt key key object)
        
{-
    actuallyEncrypt currentKey inputKey password
        PURPOSE: encrypt a string (password) using the supplied key.
        PRE: True
        POST: the char codes of password modified using currentKey
        VARIANT: length of password
        
        Example:
                actuallyEncrypt "abc" "abc" "123" = [-48,-48,-48]
-}
       
actuallyEncrypt :: [Char] -> [Char] -> [Char] -> [Int]
actuallyEncrypt (headKey:tailKey) inputKey (headPass:tailPass)
        |inputKey == [] = []
        |tailPass == [] = [((ord headPass) - (ord headKey))]
        |tailKey == [] = ((ord headPass) - (ord headKey)) : actuallyEncrypt inputKey inputKey tailPass
        |otherwise = ((ord headPass) - (ord headKey)) : actuallyEncrypt tailKey inputKey tailPass
        
        
{-
    actuallyEncrypt currentKey inputKey password
        PURPOSE: decrypt a string (password) using the supplied key.
        PRE: True
        POST: the char codes of password modified using currentKey
        VARIANT: length of password
        
        Example:
                actuallyEncrypt "abc" "abc" "123" = [146,148,150]
-}
        
        
actuallyDecrypt (headKey:tailKey) inputKey (headPass:tailPass)
        |inputKey == [] = []
        |tailPass == [] = [((ord headPass) + (ord headKey))]
        |tailKey == [] = ((ord headPass) + (ord headKey)) : actuallyDecrypt inputKey inputKey tailPass
        |otherwise = ((ord headPass) + (ord headKey)) : actuallyDecrypt tailKey inputKey tailPass