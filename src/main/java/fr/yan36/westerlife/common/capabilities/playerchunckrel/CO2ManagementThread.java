package fr.yan36.westerlife.common.capabilities.playerchunckrel;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.List;

public class CO2ManagementThread extends Thread {

    public CO2ManagementThread(List<WorldServer> w) {
        fireToReplace = new HashMap<>();
        affectedWorld = w;
    }

    public static HashMap<Long, List<BlockPos>> fireToReplace;
    public static List<WorldServer> affectedWorld;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                if(fireToReplace.size() > 0) {
                    for (Long l : fireToReplace.keySet()) {
                        if (l < System.currentTimeMillis()) {
                            System.out.println("There is a fire to replace");
                            for (BlockPos pos : fireToReplace.get(l)) {
                                for (WorldServer worldServer : affectedWorld) {
                                    Chunk c = worldServer.getChunk(pos);
                                    if (c.hasCapability(PlayerChunkRelCapability.CAPABILITY, null)) {
                                        System.out.println("There is a fire to replace in a chunk");
                                        IPlayerChunk cap = c.getCapability(PlayerChunkRelCapability.CAPABILITY, null);
                                        cap.setCO2(cap.getCO2() - 10);
                                    }
                                }
                            }
                            fireToReplace.remove(l);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addFireToReplace(Long l, BlockPos pos) {
        System.out.println("add fire to replace");
        fireToReplace.get(l).add(pos);
    }
}
