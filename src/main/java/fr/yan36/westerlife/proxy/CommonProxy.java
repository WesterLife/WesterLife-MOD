package fr.yan36.westerlife.proxy;

import java.io.File;

public class CommonProxy {

    public void preInit(File configFile)
    {
        System.out.println("pre init côté commun");
    }

    public void init()
    {

    }
}
