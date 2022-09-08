package ksvoss.backend.models;

public class UserLoginBody {
    String id;
    int selectedAlphabet;
    String nickname;
    boolean weightedRandomize;


    public UserLoginBody(String userId,int selectedAlphabet,String nickname, boolean weightedRandomize) {
        this.id = userId;
        this.selectedAlphabet=selectedAlphabet;
        this.nickname=nickname;
        this.weightedRandomize=weightedRandomize;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }






    @Override
    public String toString() {
        return "UserLoginBody{" +
                "id='" + id + '\'' +
                ", selectedAlphabet=" + selectedAlphabet +
                ", nickname='" + nickname + '\'' +
                ", weightedRandomize=" + weightedRandomize +
                '}';
    }
}

