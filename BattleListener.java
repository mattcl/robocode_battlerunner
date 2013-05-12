import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;

public class BattleListener extends BattleAdaptor {
    RESTClient client;
    Match match;

    public BattleListener(RESTClient client, Match match) {
        this.client = client;
        this.match = match;
    }

    public void onBattleCompleted(BattleCompletedEvent e) {
        MatchResults matchResult = new MatchResults(this.match);
        for (BattleResults result : e.getSortedResults()) {
            matchResult.addResult(result);
        }
        client.reportResults(matchResult);
    }
}
