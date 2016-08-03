module KeyRing (Password(Password), KeyRing (KR), Key, Ring,  newPassword, newKey, newKeyRing, newKrFromDb, insertKey, updateKey, 
    searchKey, deleteKey, listKeys, printKey, writeToDb, keyRingToStr, hashLabel, splitOnChar) where

import Data.Array.IO
import Test.HUnit 


{-Password
  REPRESENTATION CONVENTION: Password lbl pw represents a Password to be stored in a database together with a keyword.
                             lbl is a label/description, for example Gmail and pw is any String password.
  REPRESENTATION INVARIANT: pw must only consist of signs present in the ASCII-table. length lbl > 0, length pw > 0. English language characters only.
-}


data Password = Password {label :: String,
                          pw :: String
                         } deriving (Eq)
instance Show Password where
    show (Password label pw) = "Desc: " ++ label ++ " Pw: " ++ pw


type Key = (String, Password) 
type Ring = IOArray Int [Key]

{-KeyRing
  REPRESENTATION CONVENTION: KR size ring represents a KeyRing which basicly is a hashtable with some modifications.
                             size represents the size of the keyring. ring represents an IOArray in which each slot is stored a list of type [Key] 
                             or in other words [(String, Password)]
  REPRESENTATION INVARIANT: size > 0
  -}

data KeyRing = KR {size :: Int,
                   ring :: Ring
                  } deriving (Eq)

--Create and save Stuff----------------------------------------------------------------

{-newPassword label pw
  TYPE: String -> String -> Password
  PURPOSE: Create a new Password.
  PRE: True
  POST: returns password with label and pw.
  Examples: newPassword "Gmail" "fdsf" = Desc: Gmail Pw: fdsf
-}

newPassword :: String -> String -> Password
newPassword d p = (Password d p)

{-newKey key pw
  TYPE: String -> Password -> Key
  PURPOSE: Create a new Key.
  PRE: True
  POST: returns (key, pw)
  Examples: newKey "FdksDL" (newPassword "Gmail" "fdsf") =
    ("FdksDL", Desc: Gmail Pw fdsf)
-}
newKey :: String -> Password -> Key
newKey k p = (k, p)

{-newKeyRing n
  TYPE: Int -> IO (KeyRing)
  PURPOSE: Create a new KeyRing.
  PRE: True
  POST: returns IO (KeyRing) with size n.
  Examples: newKeyRing n = IO (KR n ring) where ring is a new IOArray of size n.
-}

newKeyRing :: Int -> IO (KeyRing)
newKeyRing n = do
    ring <- newArray (0, n - 1) ([] :: [Key]) 
    return $ KR n ring

{-newKrFromDb path
  TYPE: String -> IO (KeyRing)
  PURPOSE: Load a KeyRing from a csv Database.
  PRE: File to load = correctly formatted csv file.
  POST: new KeyRing with keys from the file(path) inserted.
  SIDE EFFECTS: Opens and reads from a file on the file system. 
-}

newKrFromDb :: String -> IO (KeyRing)
newKrFromDb path = do
    file <- readFile path
    let lFile = splitOnChar ';' (lines file)
    keyRing <- newKrFromList lFile
    return keyRing

{-newKrFromList list
  TYPE: [(String, String, String)] -> IO (KeyRing)
  PURPOSE: Create a keyring from a list of tripples(Strings).
  PRE: True
  POST: new KeyRing with the tripples from list converted into keys and inserted.
-}

newKrFromList :: [(String, String, String)] -> IO (KeyRing)
newKrFromList list = do
    let lList = if (length list) < 10 then 10 else length list
    ring <- newArray (0, (lList - 1)) ([] :: [Key])
    let keyRing = (KR lList ring)
    mapM (\(key, lbl, pw) -> insertKey keyRing key (Password lbl pw)) list
    return keyRing

{-writeToDb keyring filepath
  TYPE: KeyRing -> String -> IO ()  
  PURPOSE: Save a keyring to a csv file for persistent storage.
  PRE: True
  SIDE EFFECTS: creates a new file(filepath) and writes the keys in keyring to it.
  If the file exists it is truncated and written to.
-}

writeToDb :: KeyRing -> String -> IO ()
writeToDb (KR n ring) path = do
    dbStr <- keyRingToStr (KR n ring) 
    writeFile path dbStr
     
-----------------------------------------------------------------------------
--The functions for inserting, updating, deleting, searching are inspired by
--the lecture notes on Hashtables (with chaining)
--Operations on KeyRing------------------------------------------------------

{-insertKey keyring key pw
  TYPE: KeyRing -> String -> Password -> IO ()
  PURPOSE: Insert a Key into a KeyRing at the correct location.
  PRE: True
  SIDE EFFECTS: Inserts (key, pw) into keyring and by that alters one of the slots of the array. (Alters memory in place)
-}

insertKey :: KeyRing -> String -> Password -> IO ()
insertKey (KR n ring) key pw = do
    let pos = hashLabel key n
    slot <- readArray ring pos
    writeArray ring pos (newKey slot key pw)
      where
        newKey :: [Key] -> String -> Password -> [Key]
        newKey s k p = s ++ [(k, p)]

{-updateKey keyring key pw
  TYPE: KeyRing -> String -> Password -> IO ()
  PURPOSE: Updates an existing Key with a new Password(pw)
  PRE: True
  SIDE EFFECTS: Updates an existing Key in keyring, replacing it's password element with pw. (Alters memory in place)
-}
updateKey :: KeyRing -> String -> Password -> IO ()
updateKey (KR n ring) key pw = do
    let pos = hashLabel key n
    slot <- readArray ring pos
    writeArray ring pos (updateKey' slot key pw)
      where
        updateKey' :: [Key] -> String -> Password -> [Key]
        updateKey' slot key pw = map (\(k@(kw, _)) -> if kw == key then (kw, pw) else k) slot

{-searchKey keyring key
  TYPE: KeyRing -> String -> IO (Maybe Key)
  PURPOSE: Search for a Key in keyring returning it if found.
  PRE: True
  POST: if a match is found IO (Just Key) else IO (Nothing)
-}

searchKey :: KeyRing -> String -> IO (Maybe Key)
searchKey (KR n ring) key = do
    let pos = hashLabel key n
    slot <- readArray ring pos
    return $ findKey slot key 

{-findKey keyring key
  TYPE: [Key] -> String -> IO (Maybe Key)
  PURPOSE: Find a Key in a list of Keys matching key against the "key" element of each key.
  PRE: True
  POST: if a match is found Just Key else Nothing, first match is returned.
-}

findKey :: [Key] -> String -> Maybe Key
findKey [] s = Nothing
findKey (k@(kw, _):xs) s = if kw == s then (Just k) else findKey xs s
    
{-deleteKey keyring key
  TYPE: KeyRing -> String -> IO ()
  PURPOSE: Remove a Key from keyring.
  PRE: True
  SIDE EFFECTS: Removes all Keys from keyring if their "key" element matches key. (Alters memory in place)
-}

deleteKey :: KeyRing -> String -> IO ()
deleteKey (KR n ring) key = do
    let pos = hashLabel key n
    slot <- readArray ring pos
    writeArray ring pos (removeKey slot key)
      where
        removeKey s k = filter (\(key, _) -> key /= k) s

-----------------------------------------------------------------------------





--Helper functions----------------------------------------------------------

{-hashLabel key size 
  TYPE: String -> Int -> Int
  PURPOSE: Find out which slot a key should go in an array.
  PRE: size > 0
  POST: number of the slot in which key should be placed.
-}

--Hash function. Converts a string into the sum of the ascii value of each character modulus the size of the array.
hashLabel :: String -> Int -> Int
hashLabel key n  = (sum $ map (\x -> fromEnum x) key) `mod` n 




--The listKeys and printKey functions are inspired by an answer to a question asked on the website stackoverflow.com
--Link to the thread can be found below.
--http://stackoverflow.com/questions/15776937/displaying-io-arrays


{-listKeys keyring 
  TYPE: KeyRing -> KeyRing -> IO ()
  PURPOSE: List all Keys in keyring as a table.
  PRE: True
  SIDE EFFECTS: Prints keyring to the command prompt in a table like format.
  
  listKeys' ring uB acc
  Helper function that "loops" through the array and prints the Keys found in each slot to the command line.
  Variant: Size of ring(Differance between accumulator(starts at lower bound of ring) and the upper bound.)
-}

listKeys :: KeyRing -> IO ()
listKeys (KR n ring) = do
    (a, b) <- getBounds ring
    putStr ("Key" ++ (spaces 20) ++ "Label" ++ (spaces 20) ++ "Password" ++ "\n\n")
    listKeys' ring b a
      where
        spaces l = replicate l ' '
        sp s p = let l = length s in if l < 20 then spaces (20 - l + p) else spaces (p)
        listKeys' ring uB acc = do
            if acc > uB
                then return ()
            else
                do
                    val <- readArray ring acc
                    mapM (\(key, (Password lbl pw)) -> 
                        putStr $ (take 20 key) ++ (sp key 3) ++ (take 20 lbl) ++ (sp lbl 5) ++ (take 20 pw) ++ "\n") val
                    listKeys' ring uB (acc+1)

{-printKey key 
  TYPE: Maybe Key -> IO ()
  PURPOSE: Prints a key to the command prompt in a list like structure. 
           If key == Nothing print an error message.
  PRE: True
  SIDE EFFECTS: Prints the keyring or an error message to the command prompt.
-}

printKey :: Maybe Key -> IO ()
printKey (Just (key, (Password lbl pw))) = do
    putStr $ "Key: " ++ key ++ "\nLabel: " ++ lbl ++ "\nPw: " ++ pw
printKey _ = putStr $ "Key not found"


{-splitOnChar char strList 
  TYPE: Char -> [String] -> [(String, String, String)]
  PURPOSE: Split each string in a list of strings turning each into a tripple.
  PRE: There must be exactly two instances of char in each string for it to split correctly.
  POST: List of tripples where each tripple is a string in strList split up using the delimiter char.
  Examples: splitOnChar ';' ["Gmail;Jonas;23s"] = [("Gmail", "Jonas", "23s")]
-}

splitOnChar :: Char -> [String] -> [(String, String, String)]
splitOnChar char str = map (split char) str
    where
        split :: Char -> String -> (String, String, String)
        split c s =
            let 
                key = takeWhile (/=c) s
                lKey = length key
                lbl = takeWhile (/=c) (drop (lKey + 1) s)
                pw = drop (lKey + length (lbl) + 2) s 
            in
                (key, lbl, pw)


{-keyRingToStr keyring
  TYPE: KeyRing -> IO (String)  
  PURPOSE: Convert a keyring into a string.
  PRE: True
  POST: string representing all Keys in keyring.
-}

{-strList keyList
  TYPE: [Key] -> [String]   
  PURPOSE: Convert a list of keys into a list of their string representations.
  PRE: True
  POST: List of keys from keyList converted to Strings.
  Examples: strList [(KR "Gmail" (Password "Mail" "fds"))] = ["Gmail;Mail;fds"]
-}

{-getStr ring uB acc str
  TYPE: Ring -> Int -> Int -> String -> IO (String) 
  PURPOSE: Accumulate a string of all keys(converted to strings) in a Ring.
  PRE: True
  POST: str accumulated to represent all keys in ring as a semicolon separated string.
  Variant: difference of acc(starts as lower bound of ring) and uB(upper bound of ring).
  Examples: strList [(KR "Gmail" (Password "Mail" "fds"))] = ["Gmail;Mail;fds"]
-}

keyRingToStr :: KeyRing -> IO (String)
keyRingToStr (KR n ring) = do
    (a, b) <- getBounds ring
    dbStr <- getStr ring b a ""
    return dbStr
      where
        strList :: [Key] -> [String]
        strList keyList = map (\(key, (Password l p))  -> (key ++ ";" ++ l ++ ";" ++ p)) keyList
        getStr :: Ring -> Int -> Int -> String -> IO (String)
        getStr ring uB acc str = do
            if acc > uB
                then return str
            else
                do
                    val <- readArray ring acc                 
                    let newStr = str ++ (unlines (strList val))
                    getStr ring uB (acc+1) newStr


