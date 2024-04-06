package examples.data;

import java.util.Objects;


public class Coordinates
{
    private Integer x;
    private double y;

    public Coordinates(Integer x, double y)
    {
        this.y = y;
        this.x = x;
    }

    public Integer getX () {return x;}

    public void setX(Integer x) {this.x = x;}

    public double getY() {return y;}

    public void setY(double y) {this.y = y;}

    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return Integer.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0;
    }

    @Override
    public int hashCode() {return Objects.hash(x, y);}

    @Override
    public String toString() {return "( x: " + this.x + ", y: " + this.y + ")";}
}
