import Test.HUnit 
import KeyRing
import Data.Array.IO
import IoTestMod
import Encryption

--Encryption.hs

--test chrCodeToWordInt
testChrInt = TestCase $ assertEqual "Convert Int to Char" ("a") (chrCodeToWordInt 97)

--test chrCodeToWordList 
testChrList = TestCase $ assertEqual "Convert List to Char" ("aaa") (chrCodeToWordList [97,97,97])

--test Encrypt
testEncrypt = TestCase $ assertEqual "Encrypt abc 123" ("///") (encrypt "abc" "123")

--test Decrypt
testDecrypt = TestCase $ assertEqual "Decrypt abc ///" ("123") (decrypt "abc" "///")

--test acctualyEncrypt
testAccEnc = TestCase $ assertEqual "AcctualyEncrypt abc abc 123" ([-48,-48,-48]) (actuallyEncrypt "abc" "abc" "123")

testAccDec = TestCase $ assertEqual "AcctualyDecrypt abc abc ///" ([144,145,146]) (actuallyDecrypt "abc" "abc" "///") 

--KeyRing.hs

--Test newPassword function. 
testPw = TestCase $ assertEqual "Password Creation" (Password "Gmail" "abc") (newPassword "Gmail" "abc")

--Test newKey function
testKey = TestCase $ assertEqual "Key Creation"  ("kliodsp", Password "Gmail" "abc") (newKey "kliodsp" (newPassword "Gmail" "abc"))

--Test hashLabel function
testHash = TestCase $ assertEqual "Hash function" 0 (hashLabel "Gmail" 5)

--Test splitOnChar function
testSplit = TestCase $ assertEqual "Split string" [("Gmail", "Jonas", "pw")] (splitOnChar ';' ["Gmail;Jonas;pw"])
--Test Split case 2
testSplit2 = TestCase $ assertEqual "Split string" [("Gmail", "Jonas", "pw;pw")] (splitOnChar ';' ["Gmail;Jonas;pw;pw"])
--splitOnChar :: Char -> [String] -> [(String, String, String)]
    -- for running all the tests
runtests = runTestTT $ TestList [testPw, testKey, testHash, testSplit, testSplit2, testChrInt, testChrList, testEncrypt, testDecrypt, testAccEnc]


--IO Tests

ioKrToIoStr :: IO (KeyRing) -> IO (String)
ioKrToIoStr kr = do
	k <- kr
	str <- keyRingToStr k
	return str

--Two empty keyrings are considered equal even if they are not the same size. Expected result = SUCCESS
testNKR = IoTestCase AssertEqual (ioKrToIoStr(newKeyRing 10)) (ioKrToIoStr(newKeyRing 5)) "Empty Keyrings Equality (5,10)"

--KeyRings are equal if they are loaded from the same db file. Expected result = SUCCESS
testNKRFDB = IoTestCase AssertEqual (ioKrToIoStr(newKrFromDb "testdb.csv")) (ioKrToIoStr(newKrFromDb "testdb.csv")) "Keyrings from same db Equality"

--KeyRings are not equal if you insert a new key into one of them. Expected result = SUCCESS
testIns = IoTestCase AssertNotEqual	(ioKrToIoStr(newKrFromDb "testdb.csv"))
          (do
             kr <- newKrFromDb "testdb.csv"
             insertKey kr "NewKey" (Password "Gmail" "abc")
             str <- keyRingToStr kr
             return str
          ) "Keyrings not equal after insertion into one"

--KeyRings are not equal if you update a key in one of them. Expected result = SUCCESS
testUpd = IoTestCase AssertNotEqual	(ioKrToIoStr(newKrFromDb "testdb.csv"))
          (do
             kr <- newKrFromDb "testdb.csv"
             updateKey kr "Keofk" (Password "Cooco" "abcdef")
             str <- keyRingToStr kr
             return str
          ) "Keyrings not equal after updating a key in one"

--KeyRings are not equal if you delete a key in one of them. Expected result = SUCCESS
testDel = IoTestCase AssertNotEqual	(ioKrToIoStr(newKrFromDb "testdb.csv"))
          (do
             kr <- newKrFromDb "testdb.csv"
             deleteKey kr "Keofk"
             str <- keyRingToStr kr
             return str
          ) "Keyrings not equal after deleting a key in one"


--Testing that keyrings are equal after deleting a key and inserting it again.
testDelIns = IoTestCase AssertEqual (ioKrToIoStr(newKrFromDb "testdb.csv"))
          (do
             kr <- newKrFromDb "testdb.csv"
             str <- keyRingToStr kr
             deleteKey kr "Keofk"
             insertKey kr "Keofk" (Password "Gmail" (encrypt "testdb" "fdsE24ds"))
             str <- keyRingToStr kr          
             return str
          ) "Keyrings equal after deleting Key and inserting it again"

--Testing that keyrings are equal after deleting a key, saving it to a file, loading it and inserting the same key.
testDelSaveLoadIns = IoTestCase AssertEqual (ioKrToIoStr(newKrFromDb "testdb.csv"))
          (do
             kr <- newKrFromDb "testdb.csv"
             str <- keyRingToStr kr
             deleteKey kr "Keofk"
             writeToDb kr "testdb2.csv"
             kr <- newKrFromDb "testdb2.csv"
             insertKey kr "Keofk" (Password "Gmail" (encrypt "testdb" "fdsE24ds"))
             str <- keyRingToStr kr          
             return str
          ) "Keyrings equal after deleting Key(saving + loading) and inserting it again"          

ioTestList = [testNKR, testNKRFDB, testIns, testUpd, testDel, testDelIns,testDelSaveLoadIns]

runiotests = runIoTests ioTestList