package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.gabidut76.westerlife.common.utils.list.Job;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSFranceTravail extends GuiFrame {
    public CSSFranceTravail(List<String> jobList) {
        super(new GuiScaler.AdjustToScreenSize(true,1,1));
        // Get jobs ICI !

        for (String s : jobList) {
            Job job = Job.fromString(s.replaceAll(" ", ""));
            if(job.getIsParticular() != 3)  { // Ne pas toucher aux jobs particuliers
                System.out.println(job.getTitle());
            }
        }
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/createprofil.css"));
    }
}
