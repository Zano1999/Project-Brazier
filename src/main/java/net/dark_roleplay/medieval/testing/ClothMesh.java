package net.dark_roleplay.medieval.testing;

import javafx.geometry.Point2D;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class ClothMesh {

    //TODO Continue Testing
    Vec3d[][] mesh = null;

    public static void main(String[] args){
       // ClothMesh mesh = new ClothMesh(6, 6);

    }

    public ClothMesh(int width, int length, Vec3d x0y0, Vec3d x1y0, Vec3d x0y1){
        this.mesh = new Vec3d[width][length];


        QuadFunction funcA = new QuadFunction(7, -2.5), funcB = new QuadFunction(10,-2);

       // double a = b.calcB(c), b = b.calcA(c);

    }

    private class QuadFunction{
        protected double x = 0, y = 0, xPow = 0;

        public QuadFunction(double x, double y){
            this.x = x;
            this.y = y;
            this.xPow = x * x;
        }

        public void calcB(QuadFunction other){
            double b = (y * other.xPow - other.y * xPow) / (x * other.xPow - other.x * xPow);
            System.out.println(b);
        }

        public void calcA(QuadFunction other){
            double a = (y * other.x - other.y * x) / (xPow * other.x - other.xPow * x);
            System.out.println(a);
        }
    }

}
