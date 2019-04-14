/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose Exist so that a jar file can be created to launch CenterView
 * @Dependencies CenterView
 * @Uses: Only used for testing independent of the rest of the project
 */


package center.external;

public class CenterMain {

    /**
     * @purpose this is a one-line main method that simply calls another main method. The purpose for this is so that
     * a jar file can be created that runs the game center so that it can be tested on its own.
     * @param args the standard arguments passed into main
     */
    public static void main(String[] args) {
        CenterView.main(args);
    }

}
