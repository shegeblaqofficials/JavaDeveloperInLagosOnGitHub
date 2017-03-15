package tech.damisa.developerlist;

/**
 * Created by BLAQ on 3/13/2017.
 */
public class ListItem {

    private String image, username, score,type,pageurl,githuburl;

    public ListItem() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score =score;
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageurl(){
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getGithuburl(){
        return githuburl;
    }

    public void setGithuburl(String githuburl) {
        this.githuburl = githuburl;
    }

}
