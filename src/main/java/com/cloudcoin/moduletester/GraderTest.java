package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.Grader.Grader;
import global.cloudcoin.ccbank.Grader.GraderResult;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class GraderTest {

    private static String RootPath = ModuleTester.RootPath;
    ServantRegistry sr;
    int TestNumber = 1;


    public GraderTest(ServantRegistry sri) {
        createDirectories();
        sr = sri;
        Instant start = Instant.now();
        StartGraderTest();
        Instant end = Instant.now();
        System.out.println("Five Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void setRootPath(String[] args) {
        if (args == null || args.length == 0)
            return;

        if (Files.isDirectory(Paths.get(args[0]))) {
            RootPath = args[0];
        }
    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(RootPath + "Detected\\"));
            Files.createDirectories(Paths.get(RootPath + "Bank\\"));
            Files.createDirectories(Paths.get(RootPath + "Fracked\\"));
            Files.createDirectories(Paths.get(RootPath + "Counterfeit\\"));
            Files.createDirectories(Paths.get(RootPath + "Lost\\"));
            Files.createDirectories(Paths.get(RootPath + "Logs\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartGraderTest() {
        KeyboardReader reader = new KeyboardReader();


        System.out.println("Starting test for Grader");
        System.out.println("Emptying Associated Folders");
        TestUtils.FlushFolder("Bank");
        TestUtils.FlushFolder("Detected");
        TestUtils.FlushFolder("Counterfeit");
        TestUtils.FlushFolder("Lost");
        TestUtils.FlushFolder("Fracked");

            try {


                switch (TestNumber) {
                    case 1:
                        System.out.println("1. Grade 1 CloudCoin (Passing)");
                        TestUtils.saveFile(makeCloudCoinPassing(1), 1, "Detected");

                        break;
                    case 2:
                        System.out.println("2. Grade 1 CloudCoin (Fracked)");
                        TestUtils.saveFile(makeCloudCoinFracked(1), 1, "Detected");

                        break;
                    case 3:
                        System.out.println("3. Grade 1 CloudCoin (Counterfeit)");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Detected");

                        break;
                    case 4:
                        System.out.println("4. Grade 1 CloudCoin (Lost)");
                        TestUtils.saveFile(makeCloudCoinLost(1), 1, "Detected");

                        break;
                    case 5:
                        System.out.println("5. Grade 4 CloudCoins (one of each)");
                        TestUtils.saveFile(makeCloudCoinPassing(2), 2, "Detected");
                        TestUtils.saveFile(makeCloudCoinFracked(3), 3, "Detected");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(4), 4, "Detected");
                        TestUtils.saveFile(makeCloudCoinLost(5), 5, "Detected");

                        break;

                    case 6:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        TestUtils.FlushFolder("Bank");
                        TestUtils.FlushFolder("Counterfeit");
                        TestUtils.FlushFolder("Lost");
                        TestUtils.FlushFolder("Fracked");
                        TestUtils.FlushFolder("Detected");
                        return;
                }

                Grader gd = (Grader) sr.getServant("Grader");
                gd.launch(new GraderAssert());
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }

    }

    class GraderAssert implements CallbackInterface {
        public void callback(Object result) {
            GraderResult gr = (GraderResult) result;

            int statToBankValue = gr.totalAuthenticValue + gr.totalFrackedValue;
            int statToBank = gr.totalAuthentic + gr.totalFracked;
            int statFailed = gr.totalLost + gr.totalCounterfeit + gr.totalUnchecked;

            switch(TestNumber){
                case 1:
                    if(Files.exists(Paths.get(RootPath + "Bank\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {


                        System.out.println("TEST 1 SUCCESS");

                    }
                    else{
                        System.out.println("TEST 1 FAILED: Test coin not found in Bank folder.");
                    }
                    break;
                case 2:
                    if(Files.exists(Paths.get(RootPath + "Fracked\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {


                        System.out.println("TEST 2 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 2 FAILED: Test coin not found in Fracked folder.");
                    }
                    break;
                case 3:
                    if(Files.exists(Paths.get(RootPath + "Counterfeit\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {
                        System.out.println("TEST 3 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 3 FAILED: Test coin not found in Counterfeit folder.");
                    }
                    break;
                case 4:
                    if(Files.exists(Paths.get(RootPath + "Lost\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {
                        System.out.println("TEST 4 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 4 FAILED: Test coin not found in Lost folder.");
                    }
                    break;
                case 5:
                    if(Files.exists(Paths.get(RootPath + "Bank\\" + TestUtils.getDenomination(2) + ".CloudCoin.1." + 2 + ".stack")) &&
                            Files.exists(Paths.get(RootPath + "Fracked\\" + TestUtils.getDenomination(3) + ".CloudCoin.1." + 3 + ".stack")) &&
                            Files.exists(Paths.get(RootPath + "Counterfeit\\" + TestUtils.getDenomination(4) + ".CloudCoin.1." + 4 + ".stack")) &&
                            Files.exists(Paths.get(RootPath + "Lost\\" + TestUtils.getDenomination(5) + ".CloudCoin.1." + 5 + ".stack"))) {
                        System.out.println("TEST 5 SUCCESS");
                    }
                    else{
                        System.out.println("TEST 5 FAILED: Test coin not found in Appropriate folder.");
                    }
                    break;
            }
            TestNumber++;
            StartGraderTest();
        }
    }

    public static byte[] makeCloudCoinPassing(int sn) {
        return makeCloudCoin(sn, "ppppppppppppppppppppppppp");
    }

    public static byte[] makeCloudCoinFracked(int sn) {
        return makeCloudCoin(sn, "ppppppppppppppppppppppppf");
    }

    public static byte[] makeCloudCoinCounterfeit(int sn) {
        return makeCloudCoin(sn, "pppppppppppppppppppffffff");
    }

    public static byte[] makeCloudCoinLost(int sn) {
        return makeCloudCoin(sn, "pppppppppppppppppppnnnnnn");
    }

    public static byte[] makeCloudCoin(int sn, String pown) {
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
                "      \"pown\": \"" + pown + "\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

    public static String ensureFilenameUnique(String filename, String extension, String folder) {
        if (!Files.exists(Paths.get(folder + filename + extension)))
            return filename + extension;

        filename = filename + '.';
        String newFilename;
        int loopCount = 0;
        do {
            newFilename = filename + Integer.toString(++loopCount);
        }
        while (Files.exists(Paths.get(folder + newFilename + extension)));
        return newFilename + extension;
    }
}
