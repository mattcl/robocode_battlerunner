import java.net.URI;
import java.net.URISyntaxException;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class BattleRunner {
    private RESTClient client;
    private String robocodePath;
    private RobocodeEngine engine;

    public BattleRunner(String robocodePath, URI uri) {
        this.robocodePath = robocodePath;
        client = new RESTClient(uri);
    }
    
    public void run() {
        while (true) {
            Match match = client.requestMatch();
            if (match == null) {
                // TODO: wait a few seconds
            } else {
                runMatch(match);
                break; // TDOD: for now
            }
        }
    }
    
    public void setUpEngine() {
        RobocodeEngine.setLogErrorsEnabled(false);
        engine = new RobocodeEngine(new java.io.File(robocodePath));
        engine.setVisible(true);
    }
    
    public void runMatch(Match match) {
        BattleListener listener = new BattleListener(client, match);
        engine.addBattleListener(listener);
        
        BattlefieldSpecification battlefield = new BattlefieldSpecification(match.width, match.height);
        RobotSpecification[] selectedRobots = engine.getLocalRepository(match.getBotString());
        BattleSpecification battleSpec = new BattleSpecification(match.numRounds, battlefield, selectedRobots);
        
        engine.runBattle(battleSpec, true);
        engine.removeBattleListener(listener);
    }
    
    public void closeEngine() {
        engine.close();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        URI baseUri = null;
        try {
            baseUri = new URI("foo.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
        }
        BattleRunner runner = new BattleRunner("C:\robocode", baseUri);
        runner.run();
        runner.closeEngine();
        System.exit(0);
    }

}
