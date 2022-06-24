package InsertIntoDatabase;

public class Player {

    private String teamname;
    private String name;
    private int pid;

    public Player(String teamname, String name, int pid) {
        this.teamname = teamname;
        this.name = name;
        this.pid = pid;
    }

    public String getTeamname() {
        return teamname;
    }

    public String getName() {
        return name;
    }

    public int getPid(){
        return pid;
    }
}
