import java.util.ArrayList;

import robocode.BattleResults;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

public class MatchResults {
    Match match;
    ArrayList<Result> results;

    public MatchResults(Match match) {
        this.match = match;
        results = new ArrayList<Result>();
    }
    
    public void addResult(int rank, BattleResults result) {
        for (Entry entry : match.entries) {
            if (entry.properName.equalsIgnoreCase(result.getTeamLeaderName())) {
                results.add(new Result(entry.id, rank, result));
                break;
            }
        }
    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        for (Result result : results) {
            obj.append("entries_attributes", result.toJSON());
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
        
        public Result(int id, int rank, BattleResults result) {
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
            this.rank = rank;
        }
        
        public JSONObject toJSON() throws JSONException {
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            obj.put("total_score", totalScore);
            obj.put("bullet_damage", bulletDamage);
            obj.put("bullet_bonus", bulletBonus);
            obj.put("ram_damage", ramDamage);
            obj.put("ram_bonus", ramBonus);
            obj.put("survival", survival);
            obj.put("survival_bonus", survivalBonus);
            obj.put("firsts", firsts);
            obj.put("seconds", seconds);
            obj.put("thirds", thirds);
            obj.put("rank", rank);
            return obj;
        }
    }
}
