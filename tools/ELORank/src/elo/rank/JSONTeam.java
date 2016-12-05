package elo.rank;

public class JSONTeam {
	public String region;
	public String nickname; 
	public boolean isNC(){
		return region.equals("North Carolina");
	}
}
