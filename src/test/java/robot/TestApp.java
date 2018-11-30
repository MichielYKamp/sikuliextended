package robot;

import robot.keywords.SikuliExtendedKeywords;

import java.util.*;

public class TestApp {

    public static void main(String[] args) throws Exception {

        HashMap<String, String> images = new HashMap<String, String>();

        Thread.sleep(2000);

        //SikuliExtendedKeywords.setSearchRegion(90, 90, 1740, 700);
        //SikuliExtendedKeywords.setScalingFactor(0.5f);
        //images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/rond leeg.png", "0.50");
        images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/rond half.png", "0.80");
        //images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/rond vol.png", "0.75");
        //images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/vierkant leeg.png", "0.75");
        //images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/vierkant half.png", "0.75");
        //images.put("C:/TA/RF.Library.SikuliExtended/src/main/resources/vierkant vol.png", "0.75");

        SikuliExtendedKeywords.findAllOccurancesDebug(images);

        //SikuliExtendedKeywords.regionShouldContain(473, 79, 100, 100, list,0.75f);

    }
}
