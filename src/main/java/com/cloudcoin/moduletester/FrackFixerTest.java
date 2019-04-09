package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.FrackFixer.FrackFixer;
import global.cloudcoin.ccbank.FrackFixer.FrackFixerResult;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class FrackFixerTest {
    static String TestCoinName = "100.CloudCoin.1.6377544..stack";
    ServantRegistry sr;
    public FrackFixerTest(ServantRegistry sri){
        sr = sri;


        Instant start = Instant.now();
        StartFrackFixTest();
        Instant end = Instant.now();
        System.out.println("One Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");

    }

    public void StartFrackFixTest(){
        try{
            System.out.println("Starting LostFixer Test");

            System.out.println("Flushing Fracked, Bank, and Detected Folders");
            TestUtils.FlushFolder("Bank");
            TestUtils.FlushFolder("Fracked");


            System.out.println("Creating test CloudCoin files");

            Files.copy(Paths.get("C:\\CloudCoin\\TestCoin\\" + TestCoinName), Paths.get(ModuleTester.RootPath + "Fracked\\" + TestCoinName));

            System.out.println("Starting FrackFixer");
            //runProcess("javac -cp src \"C:\\Program Files\\CloudCoin\\ModuleTester.java\"");
            FrackFixer ff = (FrackFixer) sr.getServant("FrackFixer");
            ff.launch(new FrackFixererAssert());




        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("FrackFixer Test Completed. Clearing out Folder used in testing.");
        TestUtils.FlushFolder("Fracked");
        TestUtils.FlushFolder("Bank");


    }
    class FrackFixererAssert implements CallbackInterface {
        public void callback(Object result) {
            FrackFixerResult fr = (FrackFixerResult) result;

            if (fr.status == FrackFixerResult.STATUS_ERROR) {
                System.out.println("Failed to fix");
                return;
            }

            if (fr.status == FrackFixerResult.STATUS_FINISHED) {
                if (fr.fixed + fr.failed > 0) {
                    System.out.println("Fracker fixed: " + fr.fixed + ", failed: " + fr.failed);
                    try {
                        System.out.println("Asserting that test CloudCoin has been fixed");
                        String[] BankFileNames = TestUtils.selectFileNamesInFolder(ModuleTester.RootPath + "Bank" + File.separator);
                        if (BankFileNames.length > 0 && Files.exists(Paths.get(ModuleTester.RootPath + "Bank" + File.separator + BankFileNames[0]))) {
                            System.out.println("Found file in Bank Folder");

                            if (BankFileNames[0] == TestCoinName) {
                                System.out.println("Test coin is fixed. TEST SUCCESSFUL");
                            } else {
                                System.out.println("Test coin not found in Bank Folder. TEST INCONCLUSIVE OR FAILED");

                            }
                            Files.deleteIfExists(Paths.get(ModuleTester.RootPath + "Bank" + File.separator + BankFileNames[0]));
                        } else {
                            System.out.println("No files in Bank Folder. TEST FAILED");
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                    return;
                }
            }
        }
    }



    public static byte[] makeCloudCoinFixed(int sn) {
        return ("{\n" +
                "  \"cloudcoin\": [\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + ",\n" +
                "      \"an\": [\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\"\n" +
                "      ],\n" +
                "      \"ed\": \"11-2020\",\n" +
                "      \"pown\": \"ppppppppppppppppppppppppp\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }
}
