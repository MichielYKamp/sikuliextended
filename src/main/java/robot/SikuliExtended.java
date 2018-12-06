package robot;

import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.remoteserver.RemoteServer;

public class SikuliExtended extends AnnotationLibrary {

    public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";

    public SikuliExtended() {
        super("robot/keywords/*.class");
    }

    @Override
    public String getKeywordDocumentation(String keywordName) {
        if (keywordName.equals("__intro__")) {
            return "Intro";
        }

        return super.getKeywordDocumentation(keywordName);
    }

    public static void main(String[] args) throws Exception {

        // write your code here
        RemoteServer.configureLogging();
        RemoteServer server = new RemoteServer();
        server.addLibrary(SikuliExtended.class, 8270);
        server.start();
    }
}
