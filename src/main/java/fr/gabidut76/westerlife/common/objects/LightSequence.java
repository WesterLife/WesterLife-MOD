package fr.gabidut76.westerlife.common.objects;

import com.jme3.math.Vector3f;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileLyre;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.List;

public class LightSequence {
    List<DoubleVector> sequence = new ArrayList<DoubleVector>();
    List<BlockPos> lyres = new ArrayList<BlockPos>();

    public LightSequence() {
    }

    public void add(DoubleVector vec) {
        sequence.add(vec);
    }

    public void add(float x, float y, float time, float x2, float y2, float time2) {
        sequence.add(new DoubleVector(new Vector3f(x, y, time), new Vector3f(x2, y2, time2)));
    }

    public void add(BlockPos lyre) {
        lyres.add(lyre);
    }

    public List<BlockPos> getLyres() {
        return lyres;
    }

    public List<DoubleVector> getSequence() {
        return sequence;
    }

    public void reset(World worldin) {
        Thread th = new Thread(() -> {
            for (BlockPos lyre : lyres) {
                if(lyre.getY() < 0) {
                    // skip
                    continue;
                }
                if(!(worldin.getTileEntity(lyre) instanceof TileLyre) || worldin.getTileEntity(lyre) == null) {
                    // skip
                    continue;
                }
                TileLyre tileEntity = (TileLyre) worldin.getTileEntity(lyre);
                assert tileEntity != null;
                tileEntity.setRotationfrom(new Vector2f(tileEntity.getActualrotation().x, tileEntity.getActualrotation().y));
                tileEntity.setRotationto(new Vector2f(0,0));
                tileEntity.setTimeMax(50);
                tileEntity.sync();
                tileEntity.syncToClient();

            }
        });
        th.start();
    }

    public void play(World worldin) {
        Thread th = new Thread(() -> {
            for (DoubleVector seq : sequence) {
                for (BlockPos lyre : lyres) {
                    if(lyre.getY() < 0) {
                        continue;
                    }
                    if(!(worldin.getTileEntity(lyre) instanceof TileLyre) || worldin.getTileEntity(lyre) == null) {
                        continue;
                    }
                    TileLyre tileEntity = (TileLyre) worldin.getTileEntity(lyre);
                    assert tileEntity != null;
//                    if(tileEntity.getActualrotation().x != seq.from.x || tileEntity.getActualrotation().y != seq.from.y) {
//                        this.reset(worldin);
////                        seq.to.z += 50;
//                    }
                    System.out.println("from: " + seq.from.x + " " + seq.from.y + " to: " + seq.to.x + " " + seq.to.y + " time: " + seq.to.z);
                    tileEntity.setRotationfrom(new Vector2f(seq.from.x, seq.from.y));
                    tileEntity.setRotationto(new Vector2f(seq.to.x, seq.to.y));
                    tileEntity.setTimeMax((int) seq.to.z);
                    tileEntity.sync();
                    tileEntity.syncToClient();
                }
                try {
                    // convert tick to ms
                    Thread.sleep((long) (seq.to.z * 50));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DoubleVector seq : sequence) {
            sb.append(seq.toString()).append(";");
        }
        sb.append("$");
        for (BlockPos lyre : lyres) {
            sb.append(Util.blockPosToString(lyre)).append(";");
        }
        return sb.toString();
    }

    public static LightSequence fromString(String str) {
        LightSequence sequence = new LightSequence();

        String[] split = str.split("\\$");
        if(!(split.length < 1 || split[0].isEmpty())) {
            String[] split2 = split[0].split(";");
            for (String s : split2) {
                sequence.add(DoubleVector.fromstring(s));
            }
        }

        if(split.length < 2 || split[1].isEmpty()) {
            return sequence;
        } else {
            String[] split3 = split[1].split(";");
            for (String s : split3) {
                sequence.add(Util.parseBlockPosFromString(s));
            }
        }
        return sequence;
    }

    public String toNiceString() {
        return "LightSequence{" +
                "sequence=" + sequence +
                ", lyres=" + lyres +
                '}';
    }

    public static class DoubleVector {
        public Vector3f from;
        public Vector3f to;
        private static String separator = "/";

        public DoubleVector(Vector3f from, Vector3f to) {
            this.from = from;
            this.to = to;
        }

        public void setFrom(Vector3f from) {
            this.from = from;
        }

        public void setTo(Vector3f to) {
            this.to = to;
        }

        @Override
        public String toString() {
            return from.x + separator + from.y + separator + from.z + separator + to.x + separator + to.y + separator + to.z;
        }

        public static DoubleVector fromstring(String str) {
            String[] split = str.split(separator);
            return new DoubleVector(new Vector3f(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2])), new Vector3f(Float.parseFloat(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5])));
        }
    }
}
