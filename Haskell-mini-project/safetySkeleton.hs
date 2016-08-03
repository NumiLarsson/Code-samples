import Control.Monad
import System.IO
import System.Directory
import KeyRing
import Data.Array.IO
import System.Exit (exitSuccess)
import Encryption

--WARNING, PROGRAM CURRENTLY DOES NOT ACCEPT Å Ä Ö AS PASSWORD.

{-
    main
        PURPOSE: Provide a basic interface for the user to navigate the program with.
        PRE: True
        POST: Calls the function goToOption with the provided user input, if the input is not "", in which case it calls itself again.

        SIDE EFFECTS: 
                        Reads user input twice, once when the function is first called and once if input == ""
-}

main :: IO ()
main = do
    putStrLn " "
    putStrLn "-----------------"
    putStrLn "-- Choose your option"
    putStrLn "1. Login"
    putStrLn "2. Handle users"
    putStrLn "3. Exit"
    option <- getLine
    if option == ""
        then do
            putStrLn "Input invalid, please try again, press enter to continue"
            main
        else do
            goToOption option
            main

{-
    goToOption option
        PURPOSE: Send the user off to the requested function.
        PRE: True
        POST: A succesfull exit of the program shows "Error" code: *** Exception: ExitSuccess".

        SIDE EFFECTS: 
                        prints "-- Exiting" if option 3 is selected, prints "Input invalid, please try again, press enter to continue" 
                                if the input is not defined (input /= 1, 2 or 3)
                        Reads user input if option is outside the defined input
-}

goToOption :: [Char] -> IO ()
goToOption option
    |(head option == '1') = do
        login
        return ()
    |(head option == '2') = do
        handleUsers
        return ()
    |(head option == '3') = do                              --Exiting the program here calls an error message "*** Exception: ExitSuccess"
                                                            --This is intended and is to show a succesfull exit.
        putStrLn "-- Exiting"
        exitSuccess
    |otherwise = do
        putStrLn "Input invalid, please try again, press enter to continue"
        nothing <- getLine
        main

--------------------- Login

{-
    login
        PURPOSE: Checks if a file with the provided username exists, if so, starts the function loggedInOption with username as its input.
        PRE: True
        POST: Depending on the input, either loggedInOption or login is called.

        SIDE EFFECTS: 
                        Checks if a file namned "username.csv" exists.
                        Reads user input once, when the function first is run.
-}

login :: IO ()
login = do
        putStrLn "-- Enter username (Blank to return)"
        username <- getLine
        if null username
                then return ()
                else do
                userExists <- doesFileExist (username ++ ".csv")
                if userExists
                        then do
                                putStrLn "-- Logged in!"
                                loggedInOption username
                                return ()
                        else do
                                putStrLn "-- User does not exist!"
                                login
                                return ()


--------------------- Handle Users

{-
    listUsers list
        PURPOSE: To print the strings in list which ends in ".csv"  (without the ending ".csv")
        PRE: True
        POST: Every string in list which ends in ".csv" is printed.
        VARIANT: length of list

        SIDE EFFECTS: 
                        If a string in list ends in ".csv" it is printed.
-}

listUsers :: [String] -> IO ()
listUsers [] = return ()
listUsers (x:xs)
        |xs == [] = do
            isFile <- doesFileExist x
            if (drop (length x - 4) x) == ".csv"
                then do
                    if (isFile)
                        then do
                            putStrLn (take ((length x) - 4) x)
                        else 
                            return()
                else
                        return()
        |(drop (length x - 4) x) == ".csv" = do
            isFile <- doesFileExist x
            if (isFile)
                    then do
                            putStrLn (take ((length x) -4) x)
                            listUsers xs
                    else
                            listUsers xs
        |otherwise = listUsers xs

{-
    handleUsers
        PURPOSE: List all available files (users) which ends in ".csv", then accept different inputs to allow the user to manipulate these users.
        PRE: True
        POST: Calls different functions depending on user input, 1 -> newUserOption, 2 -> deleteUser, 3 -> handleUsers

        SIDE EFFECTS: 
                        Prints the line "Input invalid, please try again, press enter to continue" if input = "" or 
                                input is not among the defined inputs (1, 2, 3, "")
                        Reads user input in two cases, once when the function is first run, and then again if that input is invalid (see above)
-}

handleUsers :: IO ()
handleUsers = do
        putStrLn "-- Available users --"

        currDir <- getCurrentDirectory
        dirCont <- getDirectoryContents currDir
        listUsers dirCont

        putStrLn "---------------------"
        putStrLn " "
        putStrLn "1. New user"
        putStrLn "2. Delete user"
        putStrLn "3. Return"

        option <- getLine
        if option == ""
            then do
                putStrLn "Input invalid, please try again, press enter to continue"
                nothing <- getLine
                handleUsers
            else if head option == '1'
                then newUserOption
                else if (head option) == '2'
                    then deleteUser
                    else if (head option) == '3'
                        then return ()
                        else do
                            putStrLn "Input invalid, please try again, press enter to continue"
                            nothing <- getLine
                            handleUsers

{-
    newUserOption
        PURPOSE: Create a new file ("user") in current directory. 
        PRE: True
        POST: Creates a file or calls different functions depending on user input, " " (all spaces) = newUserOption, 
                Empty input = handleUsers, or any other input it creates a file namned (user input).csv

        SIDE EFFECTS: 
                        Prints multiple lines of strings, depending on user input.
                        Reads user input in two cases, once when the function is first called, once if user input is purely spaces.
                        Creates a .csv file ("user") namned using user input, if the input is neither all spaces nor empty.

-}

newUserOption:: IO ()
newUserOption = do

        putStrLn "-- New user (BLANK TO RETURN)"
        putStrLn "Enter username:"

        name <- getLine
        if null name
            then handleUsers
            else if (isNotAllSpaces name)
                then do
                    userExists <- doesFileExist (name ++ ".csv")
                    if userExists
                            then do
                                    putStrLn "\nUsername is taken, please choose another one"
                                    newUserOption
                            else do
                                    writeFile (name ++ ".csv") ""
                                    putStrLn ("\nSuccess, your username is: " ++ name)
                else do
                    putStrLn "Username can not be all spaces, please try again, press enter to continue"
                    nothing <- getLine
                    newUserOption

{-
    deleteUser
        PURPOSE: Remove a user specified file ("user") csv file.
        PRE: True
        POST: Removes the csv file the user specified in his input.

        SIDE EFFECTS: 
                        Prints the line "Input invalid, please try again, press enter to continue" if input = "" or 
                                input is not among the defined inputs (1, 2, 3, "")
                        Reads user input twice, once when the function is first called and once if user 
                                                input specifies a csv file that do not exist.
-}

deleteUser :: IO ()
deleteUser = do
        putStrLn "Enter username to be deleted:"
        name <- getLine
        fileExists <- doesFileExist (name ++ ".csv")
        if (fileExists)
                then do
                        removeFile (name ++ ".csv")
                        putStrLn "User deleted"
                else do
                        putStrLn "User does not exist!"
                        putStrLn "Returning to main menu, press enter to continue"
                        nothing <- getLine
                        return ()


--------------------- Logged in

{-
    loggedInOption name
        PURPOSE: Give the user a managable interface to view keys of type Key (specified in KeyRing.hs)
        PRE: name is the name of a .csv file in the same folder as the program, that file must be properly formatted according to .csv standards.
        POST: Calls different functions depending on user input, 1 = viewEntry, 2 = createNewKey, 3 = logout, "" = loggedInOption.
                name is simply passed to the functions that requires it, (createNewKey, viewEntry, loggedInOption)

        SIDE EFFECTS: 
                        Prints multiple lines depending on user input.
                        Reads user input a possible three times, once when the function is first called or once if user input = ""
                                    or once if user input is not defined (1, 2, 3, "")
-}

loggedInOption :: String -> IO ()
loggedInOption name = do
        putStrLn " "

        currRing <- newKrFromDb (name ++ ".csv")
        listKeys currRing

        putStrLn " "
        putStrLn "Choose an option:"
        putStrLn "1. View / Edit key"
        putStrLn "2. Create New Key"
        putStrLn "3. Logout"

        entry <- getLine
        putStrLn ""
        if entry == "" then do
                putStrLn "Input invalid, try again, press enter to coninue"
                nothing <- getLine
                loggedInOption name
            else if (head entry == '2')
                    then do
                        putStrLn "Creating new key"
                        createNewKey currRing name

                    else if head entry == '3'
                            then
                                    logout
                            else if head entry == '1'
                                then do
                                    putStrLn "-- Choose a key to view or edit (BLANK TO RETURN)"
                                    entryKey <- getLine
                                    currKey <- searchKey currRing entryKey
                                    viewEntry currRing currKey name
                                    loggedInOption name
                                else do
                                    putStrLn "Input invalid, try again, press enter to continue"
                                    nothing <- getLine
                                    loggedInOption name

{-
    isNotAllSpaces list 
        PURPOSE: Check to see if a string contains only spaces.
        PRE: True
        POST: Returns true if list contains characters that are not space or if list is empty.

        Examples:
            Valid:  
                isNotAllSpaces "" = True
                isNotAllSpaces "Hejsan" = True
                isNotAllSpaces "    " = False
-}

isNotAllSpaces :: [Char] -> Bool
isNotAllSpaces "" = True
isNotAllSpaces (x:xs)
    |xs == [] && x == ' ' = False
    |xs == [] && x /= ' ' = True
    |x == ' ' = isNotAllSpaces xs
    |otherwise = True


{-
    correctKeys newKeyName newKeyDesc newKeyPass name
        PURPOSE: Checks if any of the input is an empty string or a string consisting of all spaces, then returns a bool depending on the results.
        PRE: True
        POST: if any of newKeyName, newKeyDesc or newKeyPass is either an empty string or consists of only spaces it returns false, otherwise true.

        SIDE EFFECTS: 
                        Prints multiple lines depending on the variables provided.
                        Reads user input if any of the checks fail.
-}


correctKeys :: String -> String -> String -> String -> IO Bool
correctKeys newKeyName newKeyDesc newKeyPass name
    |newKeyName == "" = do
        putStrLn "Key name is not valid, cancelling creation of new key, press enter to continue"
        nothing <- getLine
        return (False)
    |newKeyDesc == "" = do
        putStrLn "Key description is not valid, cancelling creation of new key, press enter to continue"
        nothing <- getLine
        return (False)
    |newKeyPass == "" = do
        putStrLn "Password is not valid, cancelling creation of new key, press enter to continue"
        nothing <- getLine
        return (False)

    |isNotAllSpaces newKeyName && isNotAllSpaces newKeyDesc && isNotAllSpaces newKeyPass = return (True)
    |otherwise = do
        putStrLn "One input is all spaces which is invalid, cancelling creation of new key, press enter to continue"
        nothing <- getLine
        return (False)


{-
    createNewKey currRing name
        PURPOSE: create a new key in the provided keyring, assuming user input is correct.
        PRE: True
        POST: Creates a new key in currRing, provided it passes the tests in function validKeys.

        SIDE EFFECTS: 
                        Prints multiple lines depending on user input.
                        Reads user input a possible three times, once when the function is first called or once if user input = ""
                                    or once if user input is not defined (1, 2, 3, "")
-}


createNewKey :: KeyRing -> String -> IO ()
createNewKey currRing name = do
    putStrLn "Please enter new key name, leave empty to cancel"
    newKeyName <- getLine
    putStrLn ("Key name: " ++ newKeyName)

    putStrLn "Please enter new description, leave empty to cancel"
    newKeyDesc <- getLine
    putStrLn ("Key description: " ++ newKeyDesc)

    putStrLn "Please enter new password, leave empty to cancel"
    newKeyPass <- getLine
    putStrLn ("Password: " ++ newKeyPass)

    validKeys <- correctKeys newKeyName newKeyDesc newKeyPass name
    if validKeys
        then do
            putStrLn ("Creating key with name: " ++ newKeyName ++ ", description: "
                        ++ newKeyDesc ++ " and password: " ++ newKeyPass)
            insertKey currRing newKeyName (Password newKeyDesc (encrypt name newKeyPass))
            writeToDb currRing (name ++ ".csv")
            loggedInOption name
        else loggedInOption name

{-
    logout
        PURPOSE: print "-- Logged out!" and then call main
        PRE: True
        POST: calls main

        SIDE EFFECTS: 
                        Prints "-- Logged out!"
-}

logout :: IO ()
logout = do
        putStrLn "-- Logged out!"
        main

{-
    viewEntry currRing currKey name
        PURPOSE: calls modifyKey to manipulate currKey in different ways, depending on user input.
        PRE: True
        POST: Calls modifyKey with user input if user input is not empty or 
                    starts with a 4, in which case it prints an error message before calling itself to start over.

        SIDE EFFECTS: 
                        Prints multiple lines depending on user input.
                        Reads user input in three cases, once when the function is first called, once if user input = "", one if currKey is Nothing.
-}

viewEntry :: KeyRing -> Maybe Key -> String -> IO ()
viewEntry currRing (Just (key, (Password lbl pw))) name = do
        putStrLn "\n"

        printKey (Just (key, (Password lbl (decrypt name pw))))
        
        putStrLn "\n"
        putStrLn "1. Remove Key"
        putStrLn "2. Edit description"
        putStrLn "3. Edit password"
        putStrLn "4. Return"

        option <- getLine
        if option == ""
            then do
                putStrLn "Invalid input, try again. Press enter to continue"
                nothing <- getLine
                viewEntry currRing (Just (key, (Password lbl pw))) name
            else if head option == '4'
                    then return ()
                    else
                        modifyKey option currRing (key, (Password lbl pw)) name

viewEntry _ _ _ = do
        putStrLn "Could not find key"
        putStrLn "Press enter to continue"
        nothing <- getLine
        return ()

{-
    modifyKey option currRing currKey name
        PURPOSE: manipulates the currKey in different ways, depending on option and then saves the changes.
        PRE: True
        POST: Deletes currKey out of currRing if the first char of option = '1', if it's '2', it changes the password of currKey, 
                    if it's '3' it changes the password of key, or calls itself if input is undefined (not '1', '2' or '3')
                In all cases, the resulting currKey replaces the old currKey in currRing.

        SIDE EFFECTS: 
                        Prints multiple lines depending on user input.
                        Reads user input in three cases, once when the function is first called, once if user input = "" or 
                                    once if option is undefined (see above)
-}


modifyKey :: String -> KeyRing -> Key -> String -> IO ()
modifyKey option currRing (key, (Password lbl pw)) name
        |head option == '1' = do
                putStrLn ("\n" ++ key ++ " is now deleted.\n")
                deleteKey currRing key
                writeToDb currRing (name ++ ".csv")
        |head option == '2' = do
                putStrLn "Please enter new description:"
                entry <- getLine
                if entry == ""
                    then do
                        putStrLn "Description can't be empty, please try again"
                    else do
                        updateKey currRing key (Password entry pw)
                        writeToDb currRing (name ++ ".csv")
                viewEntry currRing (Just (key, (Password lbl pw))) name
        |head option == '3' = do
                putStrLn "Please enter new password:"
                entry <- getLine
                if entry == ""
                    then do
                        putStrLn "Password can't be empty, please try again"
                        viewEntry currRing (Just (key, (Password lbl pw))) name
                    else do
                        updateKey currRing key (Password lbl (encrypt name entry))
                        writeToDb currRing (name ++ ".csv")
                        viewEntry currRing (Just (key, (Password lbl (encrypt name entry)))) name
                
        |otherwise = do
            putStrLn "Input invalid, please try again. Press enter to continue"
            nothing <- getLine
            viewEntry currRing (Just (key, (Password lbl pw))) name