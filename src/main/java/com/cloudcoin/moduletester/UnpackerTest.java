package com.cloudcoin.moduletester;

import global.cloudcoin.ccbank.Unpacker.Unpacker;
import global.cloudcoin.ccbank.core.CallbackInterface;
import global.cloudcoin.ccbank.core.ServantRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class UnpackerTest {

    private static String RootPath = ModuleTester.RootPath;
    ServantRegistry sr;
    int TestNumber = 1;

    String testfilename = "";
    String testfilename2 = "";
    String testfilename3 = "";
    String testcoin1 = "";
    String testfilename4 = "";


    public UnpackerTest(ServantRegistry sri) {
        createDirectories();
        sr = sri;
        Instant start = Instant.now();
        StartUnpackerTest();
        Instant end = Instant.now();
        System.out.println("Four Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");

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
            Files.createDirectories(Paths.get(RootPath + "Import\\"));
            Files.createDirectories(Paths.get(RootPath + "Imported\\"));
            Files.createDirectories(Paths.get(RootPath + "Suspect\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartUnpackerTest() {
        //KeyboardReader reader = new KeyboardReader();

        System.out.println("Starting test for Unpacker");
        System.out.println("Emptying Import and Suspect Folder");
        TestUtils.FlushFolder("Import");
        TestUtils.FlushFolder("Imported");
        TestUtils.FlushFolder("Suspect");

            try {


                switch (TestNumber) {
                    case 1:
                        System.out.println("1. Unpack CloudCoin (Single Stack)");
                        byte[] testcoin = makeCloudCoinSingle(1);
                        testfilename = TestUtils.saveFile(testcoin, 1, "Import",  ".stack");

                        break;
                    case 2:
                        System.out.println("2. Unpack CloudCoins (Multi Stack: 2)");
                         testfilename2 = TestUtils.saveFile(makeCloudCoinStack(2), 2, "Import", ".stack");

                        break;
                    case 3:
                        System.out.println("3. Unpack CloudCoin (JPG)");
                         testfilename3 = TestUtils.saveFile(makeCloudCoinJpg(), 1346931, "Import",".jpg");

                        break;
                    case 4:
                        System.out.println("4. Unpack 4 CloudCoin files (one of each type)");
                         testcoin1 = TestUtils.saveFile(makeCloudCoinSingle(4), 4, "Import",".stack");
                         testfilename4 = TestUtils.saveFile(makeCloudCoinStack(5), 5, "Import",".stack");
                        //TestUtils.saveFile(makeCloudCoinJpg(), 6, "Import",".jpg");

                        break;
                        /*
                    case 5:
                        System.out.println("5. Unpack CloudCoins (Multi Stack: 100)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(7, 100), 7, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 6:
                        System.out.println("6. Unpack CloudCoins (Multi Stack: 1000)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(8, 1000), 8, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 7:
                        System.out.println("7. Unpack CloudCoins (Multi Stack: 10000)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(9, 10000), 9, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                        */
                    case 5:
                        System.out.println("Finished Testing. Clearing out tested Folders.");
                        TestUtils.FlushFolder("Import");
                        TestUtils.FlushFolder("Imported");
                        TestUtils.FlushFolder("Suspect");
                        return;
                }

                Unpacker up = (Unpacker) sr.getServant("Unpacker");
                up.launch(new UnpackerAssert());
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }

    }

    class UnpackerAssert implements CallbackInterface {
        public void callback(Object result) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }

            switch(TestNumber){
                case 1:
                    if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                        if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename)))
                            System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                        else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                    }else {
                        System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                    }
                    break;
                case 2:
                    if(Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(2) + ".CloudCoin.1." + 2 + ".stack"))
                            &&Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(102) + ".CloudCoin.1." + 102 + ".stack"))
                            &&Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(202) + ".CloudCoin.1." + 202 + ".stack")))
                    {
                        if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename2)))
                            System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                        else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");
                    }else {
                        System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                    }
                    break;
                case 3:
                    if(Files.exists(Paths.get(RootPath+ "Suspect\\" + "1.CloudCoin.1.1346931.stack"))){

                        if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename3)))
                            System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                        else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                    }else {
                        System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                    }
                    break;
                case 4:
                    if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testcoin1))
                            &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(5) + ".CloudCoin.1." + 5 + ".stack"))
                            &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(105) + ".CloudCoin.1." + 105 + ".stack"))
                            &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(205) + ".CloudCoin.1." + 205 + ".stack"))){

                        if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename4)) && !Files.exists(Paths.get(RootPath + "Imported\\" + testcoin1)))
                            System.out.println("An unpacked coin is not in Imported folder. TEST FAILED.");
                        else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                    }else {
                        System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                    }
                    break;
            }
            TestNumber++;
            StartUnpackerTest();
        }
    }



    public static byte[] makeCloudCoinSingle(int sn) {
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

    public static byte[] makeCloudCoinStack(int sn) {
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + (sn + 100) + ",\n" +
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + (sn + 200) + ",\n" +
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

    public static byte[] makeCloudCoinJpg() throws IOException {
        return Files.readAllBytes(Paths.get("1.CloudCoin.1.1346931.jpg"));
    }

    public static byte[] makeCloudCoinStackCustom(int sn, int size) {
        StringBuilder cloudCoin = new StringBuilder("{\n" +
                "  \"cloudcoin\": [\n");
        for (int i = 0; i < size; i++) {
            cloudCoin.append("    {\n" +
                    "      \"nn\": 1,\n" +
                    "      \"sn\": " + (sn + i) + ",\n" +
                    "      \"an\": [\n");
            for (int j = 0; j < 24; j++)
                cloudCoin.append("        \"00000000000000000000000000000000\",\n");
            cloudCoin.append("        \"00000000000000000000000000000000\"\n");
            cloudCoin.append("      ],\n" +
                    "      \"ed\": \"11-2020\",\n" +
                    "      \"pown\": \"ppppppppppppppppppppppppp\",\n" +
                    "      \"aoid\": []\n" +
                    "    }");
            if (i != size - 1)
                cloudCoin.append(",\n");
        }
        cloudCoin.append("\n" +
                "  ]\n" +
                "}");
        return cloudCoin.toString().getBytes();
    }


}
