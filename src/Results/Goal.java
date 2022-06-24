package Results;

import tournamentplan.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Goal {
    private int gid;
    private int pid;
    private int mid;
    private int minute;

    public Goal(int gid, int pid, int mid, int minute) {
        this.gid = gid;
        this.pid = pid;
        this.mid = mid;
        this.minute = minute;
    }

    public int getGid() {
        return gid;
    }

    public int getPid() {
        return pid;
    }

    public int getMid() {
        return mid;
    }

    public int getMinute() {
        return minute;
    }
}
