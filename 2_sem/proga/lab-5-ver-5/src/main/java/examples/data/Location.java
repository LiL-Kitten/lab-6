package examples.data;

public class Location implements Validator {
    private Double x;
    private long y;
    private String name;

    public Location(Double x, long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean validate() {
        if (this.x == null) return false;
        return !(this.name == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (y ^ (y >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ')';
    }
}
