package hw1;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class creates a TeamManager object and contains the main method
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 1
 */
public class TeamManager {
    static final int MAX_TEAMS = 5;
    private static final Team[] teams = new Team[MAX_TEAMS];
    static int current_selected_team = 1;
    static Scanner in = new Scanner(System.in);

    /**
     * This constructor initializes an array of a specified number of teams
     */
    public TeamManager() {
        for (int i = 0; i < MAX_TEAMS; i++) {
            teams[i] = new Team();
        }
    }

    /**
     * Main method
     */
    public static void main(String args[]) throws FullTeamException {
        TeamManager main = new TeamManager();
        char c;
        System.out.println("Welcome to TeamManager!\n");
        do {
            c = ' ';
            System.out.print(menu());
            c = in.next().toLowerCase().charAt(0);
            if(in.hasNextLine()){
                in.nextLine();
            }
            navigate(c);
        } while (c != 'q');
        System.out.println("Program terminating normally...");
    }

    /**
     * This method formats the selection menu into a string
     * @return
     *      The formatted string containing the selection menu
     */
    public static String menu() {
        return "Team " + current_selected_team + " is currently selected.\n\n" +
                "Please select an option:\n" +
                "A)\tAdd Player\n" +
                "G)\tGet Player stats\n" +
                "L)\tGet leader in a stat\n" +
                "R)\tRemove a player\n" +
                "P)\tPrint all players\n" +
                "S)\tSize of team\n" +
                "T)\tSelect team\n" +
                "C)\tClone team\n" +
                "E)\tTeam equals\n" +
                "U)\tUpdate stat\n" +
                "Q)\tQuit\n\n" +
                "Select a menu option: ";
    }

    /**
     * This method reads the input and redirects to the appropriate menu
     * @param c
     *      Menu selection input
     */
    public static void navigate(char c) {
        System.out.flush();
        switch (c) {
            case 'a':
                try {
                    Player p = new Player();
                    System.out.print("Enter the player's name: ");
                    p.setName(in.nextLine());
                    System.out.print("Enter the number of hits: ");
                    p.setNumHits(in.nextInt());
                    System.out.print("Enter the number of errors: ");
                    p.setNumErrors(in.nextInt());
                    System.out.print("Enter the position: ");
                    teams[current_selected_team - 1].addPlayer(p, in.nextInt());
                    System.out.println("Player added: " + p.toString());
                } catch (IllegalArgumentException | InputMismatchException | FullTeamException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 'g':
                System.out.print("Enter the position: ");
                try {
                    System.out.println(teams[current_selected_team - 1].getPlayer(in.nextInt()).toString());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 'l':
                System.out.print("Enter the stat: ");
                String stat = in.next().toLowerCase();
                try {
                    System.out.println("Leader in " + stat + ": " +
                            teams[current_selected_team - 1].getLeader(stat).toString());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 'r':
                System.out.print("Enter the position: ");
                try {
                    teams[current_selected_team - 1].removePlayer(in.nextInt());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 'p':
                System.out.print("Enter the team index: ");
                teams[in.nextInt() - 1].printAllPlayers();
                break;
            case 's':
                System.out.println("There are " + teams[current_selected_team - 1].size() +
                        " player(s) in the current Team.");
                break;
            case 't':
                System.out.print("Enter the team index to select: ");
                int team_selection = in.nextInt();
                if(team_selection <= MAX_TEAMS){
                    current_selected_team = team_selection;
                    System.out.println("Team " + current_selected_team + " has been selected");
                }
                else{
                    System.out.println("Invalid index for team");
                }
                break;
            case 'c':
                System.out.print("Select team to clone from: ");
                int clone_from = in.nextInt();
                System.out.print("Select team to clone to: ");
                int clone_to = in.nextInt();
                teams[clone_to - 1] = (Team)teams[clone_from - 1].clone();
                System.out.println("Team " + clone_from + " has been copied to Team " + clone_to);
                break;
            case 'e':
                System.out.print("Select first team index: ");
                int team_1 = in.nextInt();
                System.out.print("Select second team index: ");
                int team_2 = in.nextInt();
                if(teams[team_1 - 1].equals(teams[team_2 - 1])){
                    System.out.println("These teams are equal.");
                }
                else{
                    System.out.println("These teams are not equal.");
                }
                break;
            case 'u':
                System.out.print("Enter the player to update: ");
                String player_name = in.nextLine();
                System.out.print("Enter stat to update: ");
                String player_stat = in.nextLine();
                System.out.print("Enter the new number of " + player_stat + ": ");
                int new_stat = in.nextInt();
                try{
                    teams[current_selected_team - 1].updatePlayer(player_name, player_stat, new_stat);
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'q':
                break;
            default:
                System.out.println("Invalid menu option. Try again.");
        }
        System.out.println("\n");
    }
}