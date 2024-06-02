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

    public Direction rotateCounterClockwise() {
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

    @Override
    public String toString() {
        switch (this) {
            case UP:
                return "UP";
            case RIGHT:
                return "RIGHT";
            case DOWN:
                return "DOWN";
            case LEFT:
                return "LEFT";
            default:
                return "UNKNOWN";
        }
    }

}
