package fr.gabidut76.westerlife.common.utils.physics;

import com.jme3.bullet.collision.shapes.infos.IndexedMesh;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsShapes {
    public static IndexedMesh createClothGrid(int xLines, int zLines, float lineSpacing) {

        int numVertices = xLines * zLines;
        List<Vector3f> posBuffer = new ArrayList<>(3 * numVertices);
        for (int xIndex = 0; xIndex < zLines; ++xIndex) {
            float x = (2 * xIndex - zLines + 1) * lineSpacing / 2f;
            for (int zIndex = 0; zIndex < xLines; ++zIndex) {
                float z = (2 * zIndex - xLines + 1) * lineSpacing / 2f;
                posBuffer.add(new Vector3f(x, 0, z));
            }
        }

        int numTriangles = 2 * (xLines - 1) * (zLines - 1);
        int numIndices = 3 * numTriangles;
        List<Integer> indexBuffer = new ArrayList<>(numIndices);
        for (int zIndex = 0; zIndex < xLines - 1; ++zIndex) {
            for (int xIndex = 0; xIndex < zLines - 1; ++xIndex) {
                // 4 vertices and 2 triangles forming a square
                int vi0 = zIndex + xLines * xIndex;
                int vi1 = vi0 + 1;
                int vi2 = vi0 + xLines;
                int vi3 = vi1 + xLines;
                if ((xIndex + zIndex) % 2 == 0) {
                    // major diagonal: joins vi1 to vi2
                    indexBuffer.add(vi0);
                    indexBuffer.add(vi1);
                    indexBuffer.add(vi2);

                    indexBuffer.add(vi3);
                    indexBuffer.add(vi2);
                    indexBuffer.add(vi1);
                } else {
                    // minor diagonal: joins vi0 to vi3
                    indexBuffer.add(vi0);
                    indexBuffer.add(vi1);
                    indexBuffer.add(vi3);

                    indexBuffer.add(vi3);
                    indexBuffer.add(vi2);
                    indexBuffer.add(vi0);
                }
            }
        }
        Vector3f[] pos = new Vector3f[3 * numVertices];
        int[] indices = indexBuffer.stream().mapToInt(i -> i).toArray();
        return new IndexedMesh(posBuffer.toArray(pos), indices);
    }
}
