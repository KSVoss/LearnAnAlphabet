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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isWeightedRandomize() {
        return weightedRandomize;
    }

    public void setWeightedRandomize(boolean weightedRandomize) {
        this.weightedRandomize = weightedRandomize;
    }

    public int getSelectedAlphabet() {
        return selectedAlphabet;
    }

    public void setSelectedAlphabet(int selectedAlphabet) {
        this.selectedAlphabet = selectedAlphabet;
    }

    public UserLoginBody() {
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String userId) {
        this.id = userId;
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

