package fr.yan36.westerlife.common.utils.list;

public class Warp {

    private String name;
    private int x;
    private int y;
    private int z;

    public Warp(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "{" + "name=" + name + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    public static Warp fromString(String s) {
        String[] split = s.split(";");

        return new Warp(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }
    public String toBeautifulString() {
        return name + " X: " + x + " Y: " + y + " Z: " + z;
    }
}
