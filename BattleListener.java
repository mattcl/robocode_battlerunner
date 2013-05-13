import java.util.Arrays;

import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;

public class BattleListener extends BattleAdaptor {
    RESTClient client;
    Match match;
    boolean done;

    public BattleListener(RESTClient client, Match match) {
        this.client = client;
        this.match = match;
    }

    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("Battle completed");
        MatchResults matchResult = new MatchResults(this.match);
        BattleResults[] results = e.getSortedResults();
        System.out.println("Created new match results");
        for (int i = 0; i < results.length; i++) {
            BattleResults result = results[i];
            System.out.println("Adding result for: " + result.getTeamLeaderName());
            matchResult.addResult(i + 1, result);
        }
        System.out.println("Notifying matchmaker");
        client.reportResults(matchResult);
    }
}
