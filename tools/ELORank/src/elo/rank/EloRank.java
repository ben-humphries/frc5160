package elo.rank;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EloRank {
	public static void main(String[] args) throws IOException{
		TeamDB db = new TeamDB();
		Gson gson = new GsonBuilder().create();
		String[] fileHeader = { "id","name","overall elo", "auto elo", "teleop elo", "fuel elo", "takeoff elo", "gear elo"};
		FileWriter fileWrite = new FileWriter("ranking.csv");
		CSVPrinter csvPrinter = new CSVPrinter(fileWrite, CSVFormat.EXCEL);
		csvPrinter.printRecord(fileHeader);
		ArrayList<JSONMatch> matches = getAllMatchesInYear(2017, gson);
		for(JSONMatch m : matches){
			db.Update(m);
		}
		ArrayList<Team> sort = new ArrayList<>(TeamDB.data.size());
		for(Team t : TeamDB.data.values()){
			sort.add(t);
		}
		Collections.sort(sort);
		System.out.println("Rank/team, overall elo, auto elo, teleop elo, fuel elo, takeoff elo, gear elo, team name");
		for(int i = 0; i < sort.size(); i++){
			String teamId = sort.get(i).teamID;
			JSONTeam jteam = getJSONTeam(teamId,gson);
			if(jteam.isNC()){
			Team team = sort.get(i);
			csvPrinter.printRecord(new Object[]{teamId,jteam.nickname, team.elos[0], team.elos[1], team.elos[2], team.elos[3], team.elos[4], team.elos[5]});
			System.out.println("Rank "+(i+1)+"  " +team+ "    " + jteam.nickname);
			}
		}
		fileWrite.flush();
        fileWrite.close();
        csvPrinter.close();
		

	}

	public static String FetchURL(String link) {
		try {
			URL url = new URL(link);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("X-TBA-App-Id", "frcjava:dataminer:2.9");
			httpcon.setRequestMethod("GET");
			httpcon.addRequestProperty("User-Agent", "Mozilla");
			InputStream in = httpcon.getInputStream();
			String ret = IOUtils.toString(in);
			in.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTeamData(String id) {
		return FetchURL("https://www.thebluealliance.com/api/v2/team/" + id);
	}

	public static String getEventMatchesData(int year, String code) {
		return FetchURL("https://www.thebluealliance.com/api/v2/event/" + year + code + "/matches");
	}

	public static String getEventMatchesData(JSONEvent event) {
		return FetchURL("https://www.thebluealliance.com/api/v2/event/" + event.year + event.event_code + "/matches");
	}

	public static String getYearEvents(int year) {
		return FetchURL("https://www.thebluealliance.com//api/v2/events/" + year);
	}

	public static JSONEvent[] getJSONEvents(int year, Gson gson) {
		return gson.fromJson(getYearEvents(year), JSONEvent[].class);
	}

	public static JSONMatch[] getJSONMatches(JSONEvent event, Gson gson) {
		return gson.fromJson(getEventMatchesData(event), JSONMatch[].class);
	}

	public static JSONTeam getJSONTeam(String id, Gson gson) {
		return gson.fromJson(getTeamData(id), JSONTeam.class);
	}

	public static ArrayList<JSONMatch> getAllMatchesInYear(int year, Gson gson) {
		JSONEvent[] events = getJSONEvents(year, gson);
		Arrays.sort(events);
		ArrayList<JSONMatch> all = new ArrayList<>(events.length * 70);
		for (JSONEvent e : events) {
			JSONMatch[] matches = getJSONMatches(e, gson);
			System.out.println(e.event_code + ", " + matches.length);
			for (JSONMatch m : matches) {
				all.add(m);
			}
		}
		return all;
	}
}
