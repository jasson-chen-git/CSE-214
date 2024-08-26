package hw1;

/**
 * This class represents a team consisting of up to 40 players
 *
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 1
 */
public class Team implements Cloneable {
    private static final int MAX_PLAYERS = 40;
    private Player[] players;
    private int current_size;

    /**
     * This Constructor creates a new Team object
     * @custom.postcondition
     *      This team has been initialized to an empty list of players
     */
    public Team() {
        players = new Player[MAX_PLAYERS];
        current_size = 0;
    }

    /**
     * This method creates a deep clone the Team object
     * @return
     *      New object with identical data fields, return value must be typecast to Team
     */
    public Team clone() {
        Team team_copy = new Team();
        for (int i = 0; i < this.size(); i++) {
            try {
                team_copy.addPlayer((Player)this.players[i].clone(), i + 1);
            }
            catch (FullTeamException e) {
                throw new RuntimeException(e);
            }
        }
        return team_copy;
    }

    /**
     * This method compares this team with an object for equality
     * @param obj
     *      The object to be tested for equality
     * @return
     *      Boolean value indicating equality
     */
    public boolean equals(Object obj) {
        boolean answer = true;
        if (obj instanceof Team) {
            Team t = (Team) obj;
            if (t.size() == this.size()) {
                for (int i = 0; i < this.size(); i++) {
                    if (!t.players[i].toString().equals(this.players[i].toString())) {
                        answer = false;
                        break;
                    }
                }
            }
            else{
                answer = false;
            }
        }
        else{
            answer = false;
        }
        return answer;
    }

    /**
     * @custom.precondition
     *      The team has been instantiated
     * @return
     *      The current size of the team
     */
    public int size() {
        return this.current_size;
    }

    /**
     * This method will add a new player to the team at the specified position
     * @param p
     *      Player to be added onto the team
     * @param position
     *      Position of the new player
     * @custom.precondition
     *      Team has been instantiated
     *      1 <= position <= current team size + 1
     *      Team is not full
     * @custom.postcondition
     *      Player is at the desired position
     *      All players that were originally at the occupied position are shifted back one position
     * @throws FullTeamException
     *      When the team is fully occupied by player objects
     * @throws IllegalArgumentException
     *      When the position specified is out of range
     */
    public void addPlayer(Player p, int position) throws FullTeamException {
        if (this.size() == MAX_PLAYERS) {
            throw new FullTeamException("Team is full.");
        } else if (position < 1 || position > size() + 1) {
            throw new IllegalArgumentException("Invalid position for adding the new player");
        } else {
            for (int i = 0; i < size() - position + 1; i++) {
                this.players[this.size() - i] = this.players[this.size() - i - 1];
            }
            this.players[position - 1] = p;
            current_size++;
        }
    }

    /**
     * This method will update the specified player's statistic
     * @param name
     *      Name of player which statistic will be updated
     * @param stat
     *      Name of statistic to be updated
     * @param new_stat
     *      Value of new statistic
     * @throws IllegalArgumentException
     *      When the stat does not exist
     */
    public void updatePlayer(String name, String stat, int new_stat){
        if(!stat.equalsIgnoreCase("hits") & !stat.equalsIgnoreCase("errors")){
            throw new IllegalArgumentException("No such statistic");
        }
        else{
            boolean player_found = false;
            for(Player p : this.players){
                if(p != null && name.equalsIgnoreCase(p.getName())){
                    player_found = true;
                    if(stat.equalsIgnoreCase("hits")){
                        try {
                            p.setNumHits(new_stat);
                            System.out.println("Updated " + p.getName() + " hits");
                        }
                        catch(IllegalArgumentException e){
                            throw new IllegalArgumentException("Invalid update error");
                        }
                    }
                    else{
                        try {
                            p.setNumErrors(new_stat);
                            System.out.println("Updated " + p.getName() + " errors");
                        }
                        catch(IllegalArgumentException e){
                            throw new IllegalArgumentException("Invalid update error");
                        }
                    }
                    break;
                }
            }
            if(!player_found){
                System.out.println("Player not found.");
            }
        }
    }

    /**
     * This method removes a player from the team at the specified position
     * Players after this position will have their positions shifted down
     * @param position
     *      The position of the player to be removed
     * @custom.precondition
     *      Team has been instantiated
     *      1 <= position < current team size
     * @custom.postcondition
     *      Player at the desired position has been removed
     *      All players behind the removed player will be moved forward
     * @throws IllegalArgumentException
     *      When the position specified is out of range
     * @throws IllegalArgumentException
     *      When no player occupies the position specified
     */
    public void removePlayer(int position) {
        if (position < 1 || position > 40) {
            throw new IllegalArgumentException("Invalid position");
        } else if (position > this.size()) {
            throw new IllegalArgumentException("No player at position " + position + " to remove");
        } else {
            String temp = this.players[position - 1].getName();
            for (int i = 0; i < this.size() - position; i++) {
                this.players[position - 1 + i] = this.players[position + i];
            }
            this.players[this.size()] = null;
            current_size--;
            System.out.println("Player removed at position " + position);
            System.out.println(temp + " has been removed from the team");
        }
    }

    /**
     * This method finds the player leading in a given stat: hits or errors
     * @param stat
     *      The statistic to be compared
     * @custom.precondition
     *      Team has been instantiated
     * @return
     *      The leading player of stat specified
     * @throws IllegalArgumentException
     *      When the stat is not hits or errors
     * @throws IllegalArgumentException
     *      When the team is empty
     */
    public Player getLeader(String stat) {
        if (size() == 0) {
            throw new IllegalArgumentException("Team has no players.");
        }
        else if (stat.equals("hits")) {
            Player leader = this.players[0];
            for (int i = 1; i < size(); i++) {
                if (this.players[i].getNumHits() > leader.getNumHits()) {
                    leader = this.players[i];
                }
            }
            return leader;
        }
        else if(stat.equals("errors")){
            Player leader = this.players[0];
            for (int i = 1; i < size(); i++) {
                if (this.players[i].getNumErrors() < leader.getNumErrors()) {
                    leader = this.players[i];
                }
            }
            return leader;
        }
        else{
            throw new IllegalArgumentException("No such statistic");
        }
    }

    /**
     * This method will return the player at the position specified
     * @param position
     *      Desired player's position
     * @custom.precondition
     *      Team has been instantiated
     * @return
     *      Player object at the specified position
     * @throws IllegalArgumentException
     *      When the position is not valid
     * @throws IllegalArgumentException
     *      When no player exists at the position
     */
    public Player getPlayer(int position) {
        if (position < 1 || position > 40) {
            throw new IllegalArgumentException("Invalid position");
        } else if (players[position - 1] == null) {
            throw new IllegalArgumentException("Invalid");
        } else {
            return this.players[position - 1];
        }
    }

    /**
     * Prints out neatly formatted table of each player in the team.
     * @custom.precondition
     *      Team has been instantiated
     * @custom.postcondition
     *      A neatly formatted table of each player in the team with their statistics
     */
    public void printAllPlayers() {
        System.out.println(toString());
    }

    /**
     * @return Formatted string of the team's players and their corresponding stats
     */
    public String toString() {
        String printout = String.format("%-8s %-27s %-5s %-7s", "Player#",
                "Name", "Hits", "Errors");
        printout += ("\n-------------------------------------------------");
        for (int i = 0; i < size(); i++) {
            printout += String.format("\n%-8s %-27s %-5s %-7s", i + 1,
                    this.players[i].getName(), this.players[i].getNumHits(),
                    this.players[i].getNumErrors());
        }
        return printout;
    }
}