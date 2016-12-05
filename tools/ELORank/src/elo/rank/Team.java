package elo.rank;

public class Team implements Comparable<Team> {
	public Team(String id){
		teamID = id; 
	}
	public String teamID = "frc";
	public int elo = 1000; 
	public int compareTo(Team team){
		if(team.elo > elo){
			return 1;
		}
		else if(team.elo < elo){
			return -1;
		}
		return 0;
	}
	public String toString(){
		return teamID+" Elo  " + elo;
	}
}
