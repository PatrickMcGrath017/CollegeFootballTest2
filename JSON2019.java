import java.io.*;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.*;

class Game {

	// class definition
	int week;
	String venue;
	String homename;
	int homescore;
	String awayname;
	int awayscore;
	float excitementindex;

	Game(int week, String venue, String homename, int homescore, String awayname, int awayscore, float excitementindex){
		this.week = week;
		this.venue = venue;
		this.homename = homename;
		this.homescore = homescore;
		this.awayname = awayname;
		this.awayscore = awayscore;
		this.excitementindex = excitementindex;
	} // end of Game definition
	
	// setters & getters 
	void setWeek(int week){
		this.week = week;
	}

	int getWeek() { return this.week; }

	void setVenue(String venue){
		this.venue = venue;
	}

	String getVenue() { return this.venue; }

	void setHomeName(String homename){
		this.homename = homename;
	}

	String getHomeName() { return this.homename; }

	void setHomeScore(int homescore){
		this.homescore = homescore;
	}

	int getHomeScore() { return this.homescore; }

	void setAwayName(String awayname){
		this.awayname = awayname;
	}

	String getAwayName() { return this.awayname; }

	void setAwayScore(int awayscore){
		this.awayscore = awayscore;
	}

	int getAwayScore() { return this.awayscore; }

	void setExcitementIndex(float excitementindex){
		this.excitementindex = excitementindex;
	}

	float getExcitementIndex() { return this.excitementindex; }
	// end of seters & getters

	public String toString(){
		String s = homename + " - " + homescore + " vs. " + awayname + " - " + awayscore;
	   	return s;	
	} // end of toString
} // end of Game class

public class JSON2019{

		public static Scanner stdin = new Scanner(System.in);

		public static void printall(Game[] games){
			for(Game game : games){
				System.out.println(game);
			}
		}

		public static Game[] parseJsonGames(JSONArray a){
			Game[] games = new Game[a.size()];
			
			int i = 0;

			for(Object o : a){
				JSONObject jo = (JSONObject) o;
				//week
				Long weekLong = (Long) jo.get("week");
				int week = weekLong.intValue();
				//venue
				String venue = (String) jo.get("venue");
				//homename
				String homename = (String) jo.get("home_team");
				//homescore
				Long homescoreLong = (Long) jo.get("home_points");
				int homescore = homescoreLong.intValue();
				//awayname
				String awayname = (String) jo.get("away_team");
				//awayscore
				Long awayscoreLong = (Long) jo.get("away_points");
				int awayscore = awayscoreLong.intValue();
				//excitementindex
				String excitementindexString = (String) jo.get("excitement_index");
				float excitementindex;
				if(excitementindexString == null){
					excitementindex = 0.0f;
				}
				else{
					excitementindex = Float.parseFloat(excitementindexString);
				}
				// add to games list
				games[i] = new Game(week, venue, homename, homescore, awayname, awayscore, excitementindex);
				i++;
			}

			return games;
		} // end of parseJsonGames

		public static void main(String[] args) throws Exception{

				Object obj = new JSONParser().parse(new FileReader("2019season.json"));

				JSONArray ja = (JSONArray) obj;

				Game[] games = parseJsonGames(ja);

				String getInput = 	"Avaliable actions:\n " +
									"printall\t Prints all games.\n " +
									"printTeam\t Prints all games of a team.\n " +
									"printWeek\t Prints all games of a week.\n " +
									"printTeamWeek\t Prints all games of a team in a week.\n " +
									"printDetailedGame\t Prints specific details of a single game.\n " +
									"exit";

				System.out.println(getInput);

				Scanner newScan = new Scanner(System.in);
				while(true){
					System.out.println("Enter action:");
					String option = newScan.nextLine();
					if(option.compareTo("printall") == 0){
						printall(games);
					}
					else if(option.compareTo("printTeam") == 0){
						System.out.println("Enter team:");
						String team = newScan.nextLine();
						int wins, losses;
						wins = losses = 0;
						for(Game game : games){
							if((team.compareTo(game.homename) == 0) || (team.compareTo(game.awayname) == 0)){
								System.out.println(game);
								if((team.compareTo(game.homename) == 0) && game.homescore > game.awayscore){ 
									wins++; 
								}
								else if((team.compareTo(game.awayname) == 0) && game.awayscore > game.homescore){
									wins++; 
								}
								else { losses++; }
							}
						}

						System.out.println(wins + " wins & " + losses + " losses");
					}
					else if(option.compareTo("printWeek") == 0){
						System.out.println("Enter week (1 to 15):");
						int week = newScan.nextInt(); newScan.nextLine();
						for(Game game : games){
							if(week == game.week){
								System.out.println(game);
							}
						}
					}
					else if(option.compareTo("printTeamWeek") == 0){
						System.out.println("Enter team:");
						String team = newScan.nextLine();
						System.out.println("Enter week:");
						int week = newScan.nextInt(); newScan.nextLine();
						boolean playedthisweek = false;
						for(Game game : games){
							if((team.compareTo(game.homename) == 0 && week == game.week) || (team.compareTo(game.awayname) == 0 && week == game.week)){
								System.out.println(game);
								playedthisweek = true;
							}
						}
						if(playedthisweek == false){
							System.out.println(team + " did not play this week");
						}
					}
					else if(option.compareTo("printDetailedGame") == 0){
						System.out.println("Enter home team:");
						String hometeam = newScan.nextLine();
						System.out.println("Enter away team:");
						String awayteam = newScan.nextLine();
						boolean playedthisgame = false;
						for(Game game : games){
							if(hometeam.compareTo(game.homename) == 0 && awayteam.compareTo(game.awayname) == 0){
								playedthisgame = true;
								if(game.homescore > game.awayscore){
									System.out.println("In week #" + game.week + " " + game.homename + " beat " + 
													game.awayname + " by the score of " + game.homescore + " to " + game.awayscore + " at " + game.venue);
								}
								else{
									System.out.println("Int week #" + game.week + " " + game.awayname + " beat " + 
													game.homename +" by the score of " + game.awayscore + " to " + game.homescore + " at " + game.venue);
								}
							}
						}
						if(playedthisgame == false){
							System.out.println(awayteam + " did not play at " + hometeam);
						}
					}
					else if(option.compareTo("exit") == 0){
						break;
					}
					else{
						System.out.println("Invalid Input");
					}
				}

				return;
		} // end of main
} // end of class 
