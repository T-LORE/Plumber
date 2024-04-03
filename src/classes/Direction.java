package classes;

public enum Direction{
    UP, RIGHT, DOWN, LEFT;

    public Direction rotateClockwise() {
        int nextIndex = this.ordinal() + 1;
        if (nextIndex > 3)
        {
            nextIndex = 0;
        }
        return Direction.values()[nextIndex];
    }

    public Direction rotateAntiClockwise() {
        int nextIndex = this.ordinal() - 1;
        if (nextIndex < 0)
        {
            nextIndex = 3;
        }
        return Direction.values()[nextIndex];
    }

    public Direction turnAround() {
        int nextIndex = this.ordinal() - 2;
        if (nextIndex < 0)
        {
            nextIndex = nextIndex + 4;
        }
        return Direction.values()[nextIndex];
    }

}
