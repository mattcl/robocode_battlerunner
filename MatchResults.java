import java.util.ArrayList;

import robocode.BattleResults;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

public class MatchResults {
    Match match;
    ArrayList<Result> results;

    public MatchResults(Match match) {
        this.match = match;
    }
    
    public void addResult(BattleResults result) {
        // find the id for the bot for this match
        System.out.println(result.getTeamLeaderName());
        for (Entry entry : match.entries) {
            if (entry.properName.equalsIgnoreCase(result.getTeamLeaderName())) {
                results.add(new Result(entry.id, result));
                break;
            }
        }
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        for (Result result : results) {
            obj.append("entries_attributes", result);
        }
        return obj;
    }
    
    public class Result {
        public int id;
        public int totalScore;
        public int bulletDamage;
        public int bulletBonus;
        public int ramDamage;
        public int ramBonus;
        public int survival;
        public int survivalBonus;
        public int firsts;
        public int seconds;
        public int thirds;
        public int rank;
        
        public Result(int id, BattleResults result) {
            this.id = id;
            this.totalScore = result.getScore();
            this.bulletDamage = result.getBulletDamage();
            this.bulletBonus = result.getBulletDamageBonus();
            this.ramDamage = result.getRamDamage();
            this.ramBonus = result.getRamDamageBonus();
            this.survival = result.getSurvival();
            this.survivalBonus = result.getLastSurvivorBonus();
            this.firsts = result.getFirsts();
            this.seconds = result.getSeconds();
            this.thirds = result.getThirds();
            this.rank = result.getRank();
        }
    }
}
