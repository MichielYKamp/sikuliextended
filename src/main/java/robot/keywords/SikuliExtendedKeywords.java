package robot.keywords;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import org.sikuli.basics.Settings;
import org.sikuli.script.*;

import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;

class HashMapComparator implements Comparator
{
    public int compare ( Object object1 , Object object2 )
    {
        Integer obj1Value = ( Integer ) ( ( HashMap ) object1 ).get ( "x" ) ;
        Integer obj2Value = ( Integer ) ( ( HashMap ) object2 ).get ( "x" ) ;

        return obj1Value.compareTo ( obj2Value ) ;
    }
}

@RobotKeywords
public class SikuliExtendedKeywords {

    private static String cwd = System.getProperty("user.dir");
    private static Region reg = new Screen();

    @RobotKeyword("Set Search Region")
    @ArgumentNames({"x", "y", "width", "height"})
    public static void setSearchRegion(int x, int y, int width, int height) {

        reg = new Region(x, y, width, height);
    }

    @RobotKeyword("Reset Search Region")
    @ArgumentNames({})
    public static void resetSearchRegion() {

        reg = new Screen();
    }

    @RobotKeyword("Click At Coordinates")
    @ArgumentNames({"x", "y"})
    public static void clickAtCoordinates(int x, int y) throws Exception {

        Location loc = new Location(x, y);
        reg.click(loc);
        System.out.println("Clicked at " + loc.x + ", " + loc.y + "!");
    }

    @RobotKeyword("Double Click At Coordinates")
    @ArgumentNames({"x", "y"})
    public static void doubleClickAtCoordinates(int x, int y) throws Exception {

        Location loc = new Location(x, y);
        reg.doubleClick(loc);
        System.out.println("Double clicked at " + loc.x + ", " + loc.y + "!");
    }

    @RobotKeyword("Region Should Contain")
    @ArgumentNames({"x", "y", "width", "height", "imagePath", "similarity"})
    public static void regionShouldContain(int x, int y, int width, int height, List<String> images, float similarity) throws Exception {

        Region reg = new Region(x, y, width, height);

        int notFound = 0;
        for (String imagePath : images) {

            Pattern p = new Pattern(imagePath).similar(similarity);
            try {
                Match match = reg.find(p);

                System.out.println("Found in region!");
                return;
            }
            catch (FindFailed e) {
                System.out.println("Not found in region: " + e.getMessage());
            }
        }

        throw new Exception("Region did not contain any of the searched images: " + images);
    }

    @RobotKeyword("Set Scaling Factor")
    @ArgumentNames({"factor"})
    public static void setScalingFactor(float factor) {

        Settings.AlwaysResize = factor;
    }

    @RobotKeyword("Find All Occurances")
    @ArgumentNames({"images"})
    public static List<HashMap> findAllOccurances(HashMap<String, String> images) throws Exception {

        return _findAllOccurances(images, false);
    }

    @RobotKeyword("Find All Occurances Debug")
    @ArgumentNames({"images"})
    public static List<HashMap> findAllOccurancesDebug(HashMap<String, String> images) throws Exception {

        return _findAllOccurances(images, true);
    }

    private static List<HashMap> _findAllOccurances(HashMap<String, String> images, boolean visualize) throws Exception {

        List<HashMap> list = new ArrayList<>();

        for (Map.Entry image : images.entrySet()) {

            String imagePath = image.getKey().toString();
            float similarity = Float.valueOf(image.getValue().toString());

            if(!Files.exists(Paths.get(imagePath))) {
                throw new FileNotFoundException(imagePath);
            }

            Pattern p = new Pattern(imagePath).similar(similarity);

            try {
                Iterator<Match> m = reg.findAll(p);

                int i = 0;
                while (m.hasNext()) {
                    Match result = m.next();
                    i++;

                    if (visualize)
                        reg.mouseMove(result);

                    HashMap<String, Integer> mMap = new HashMap<>();
                    mMap.put("x", result.getTarget().getX());
                    mMap.put("y", result.getTarget().getY());

                    list.add(mMap);
                }
                System.out.println(i + " occurances found: " + imagePath);
            }
            catch (FindFailed e) {
                System.out.println("No occurances found: " + e.getMessage());
            }
        }

        Collections.sort(list, new HashMapComparator());

        System.out.println(list.size() + " total matches found.");
        return list;
    }


}
