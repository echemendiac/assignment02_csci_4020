public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return sequenceCount;
    }

    public void setScore(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }

    private int sequenceCount;

}
