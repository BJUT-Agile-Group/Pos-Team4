package domains;

/*
 * Created by thinkpad on 2015/1/7.
 */
public class User {
    private String Id="#";
    private int score=0;
    private  boolean IsVip=false;

    public String getId()
    {
        return Id ;
    }

    public void setId(String a)
    {
        this.Id =a;
    }

    public void setScore(int a) {
        this.score = a;
    }

    public void setVip(boolean a) {
        IsVip = a;
    }

    public boolean getVip(){
    return IsVip;
    }

    public int getScore() {
        return score;
    }
}
