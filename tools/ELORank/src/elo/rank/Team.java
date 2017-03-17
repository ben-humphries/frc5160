package elo.rank;

public class Team implements Comparable<Team> {
	public Team(String id){
		teamID = id; 
	}
	public String teamID = "frc";
	//overall,auto,teleop,fuel,takeoff,gear;
	public static final int ELO_COUNT = 1;
	public double[] elos ={1000,1000,1000,1000,1000,1000};
	public int compareTo(Team team){
		if(team.elos[0] > elos[0]){
			return 1;
		}
		else if(team.elos[0] < elos[0]){
			return -1;
		}
		return 0;
	}
	public String toString(){
//		overall elo, auto elo, teleop elo, fuel elo, takeoff elo, gear elo
		String acc = "";
		for(double elo : elos){
			acc+=elo+", ";
		}
		return teamID+" Elos:  " +acc;
	}
}
