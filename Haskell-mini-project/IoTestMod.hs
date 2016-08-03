module IoTestMod(runIoTests, IoTestCase(IoTestCase), TestType(AssertEqual, AssertNotEqual)) where

--import KeyRing(KeyRing)
import Data.Array.IO


{-TestType
  REPRESENTATION CONVENTION: AssertEqual/AssertNotEqual represents different kind of tests(comparisons) to be performed. 
  REPRESENTATION INVARIANT: True
-}
data TestType = AssertEqual | AssertNotEqual deriving (Eq)

{-TestType
  REPRESENTATION CONVENTION: IoTestCase t a1 a2 str represents a testcase where
    t = the type of test(comparison) to perform on a1 and a2 which represents arguments. str represents a label
    for the test, in other words a description. 
  REPRESENTATION INVARIANT: True
-}
data IoTestCase t a1 a2 str = IoTestCase {tType :: t,
                                        arg1 :: a1,
                                        arg2 :: a2,
                                        lbl :: str
                                       }


{-runIoTests testCases
  TYPE: Eq b => [IoTestCase TestType (IO (b)) ((IO b)) String] -> IO ()
  PURPOSE: Run all tests in testCases
  PRE: True
  POST:
  SIDE EFFECTS: Different depending on the kind of functions are used as arguments when creating the IoTestCase
                passed as an argument.
-}
runIoTests :: Eq b => [IoTestCase TestType (IO (b)) ((IO b)) String] -> IO ()
runIoTests tList = do
  mapM perfTest tList
  putStr ""

{-perfTest testCase
  TYPE: Eq b => (IoTestCase TestType (IO (b)) ((IO b)) String) -> IO ()
  PURPOSE: Perform the test using the correct assertFunction depending on what kind of test it is.
  PRE: True
  POST:
  SIDE EFFECTS: Different depending on the kind of functions are used as arguments when creating the IoTestCase
                passed as an argument.
-}
perfTest :: Eq b => (IoTestCase TestType (IO (b)) ((IO b)) String) -> IO ()
perfTest (IoTestCase t a1 a2 lbl) = do
    if t == AssertEqual then
      assertEqualIO a1 a2 lbl
    else
      assertNotEqualIO a1 a2 lbl


{-assertEqualIO arg1 arg2 lbl
  TYPE: Eq b => IO (b) -> IO (b) -> String -> IO ()
  PURPOSE: Check if arg1 and arg2 are equal.
  PRE: True
  POST: 
  SIDE EFFECTS: Prints lbl and a statement of the result to the command line. 
-}
assertEqualIO :: Eq b => IO (b) -> IO (b) -> String -> IO ()
assertEqualIO a1 a2 lbl = do
  first <- a1
  second <- a2
  if first == second then 
    putStrLn (lbl ++ ": SUCCESS")
  else
    putStrLn (lbl ++ ": FAILURE")

{-assertNotEqualIO arg1 arg2 lbl
  TYPE: Eq b => IO (b) -> IO (b) -> String -> IO ()
  PURPOSE: Check if arg1 and arg2 are not equal.
  PRE: True
  POST: 
  SIDE EFFECTS: Prints lbl and a statement of the result to the command line. 
-}
assertNotEqualIO :: Eq b => IO (b) -> IO (b) -> String -> IO ()
assertNotEqualIO a1 a2 lbl = do
  first <- a1
  second <- a2
  if first /= second then 
    putStrLn (lbl ++ ": SUCCESS")
  else
    putStrLn (lbl ++ ": FAILURE")
