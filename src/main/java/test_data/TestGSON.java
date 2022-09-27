package test_data;

import com.google.gson.Gson;
import test_data.computer.ComputerData;

import java.util.Arrays;

public class TestGSON {

    public static void main(String[] args) {
//        exploreGsonFeatures();
        testDataBuilder();
    }

    private static void testDataBuilder() {
        String fileLocation = "/src/main/java/test_data/computer/CheapComputerDataList.json";
        ComputerData[] computerData = DataObjectBuilder.buildDataObjectFrom(fileLocation, ComputerData[].class);
        System.out.println(Arrays.toString(computerData));
    }

    private static void exploreGsonFeatures() {
        String JSONstring = "{\n" +
                "    \"processorType\": \"Fast\",\n" +
                "    \"ram\": \"8 GB\",\n" +
                "    \"hdd\": \"320 GB\",\n" +
                "    \"software\": \"Image Viever\"\n" +
                "  }";

        Gson gson = new Gson();
        ComputerData computerData = gson.fromJson(JSONstring, ComputerData.class);
        System.out.println(computerData);

        System.out.println(gson.toJson(computerData));
    }


}
