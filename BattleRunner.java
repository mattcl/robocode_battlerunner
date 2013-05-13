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
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                runMatch(match);
            }
        }
    }
    
    public void setUpEngine() {
        RobocodeEngine.setLogErrorsEnabled(false);
        engine = new RobocodeEngine(new java.io.File(robocodePath));
        engine.setVisible(true);
    }
    
    public void runTestMatch() {
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Crazy 1.0,sample.RamFire 1.0");
        BattleSpecification battleSpec = new BattleSpecification(2, battlefield, selectedRobots);
        
        engine.runBattle(battleSpec, true);
    }
    
    public void runMatch(Match match) {
        BattleListener listener = new BattleListener(client, match);
        engine.addBattleListener(listener);
        
        BattlefieldSpecification battlefield = new BattlefieldSpecification(match.width, match.height);
        RobotSpecification[] selectedRobots = engine.getLocalRepository(match.getBotString());
        System.out.println(selectedRobots.length);
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
        if (args.length < 2) {
            System.out.println("Must specify at least the PATH to robocode AND the URI for the matchmaker");
            System.exit(1);
        }
        String robocodePath = args[0];
        String uri = args[1];
        
        URI baseUri = null;
        try {
            baseUri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Creating a new battle runner with path: " + robocodePath);
        BattleRunner runner = new BattleRunner(robocodePath, baseUri);
        runner.setUpEngine();
        //runner.runTestMatch();
        runner.run();
        runner.closeEngine();
        System.exit(0);
    }

}
