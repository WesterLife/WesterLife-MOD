package fr.yan36.westerlife.common;

import es.allblue.mcef.api.*;

import java.io.File;
import java.io.IOException;

public class CommonProxy implements API {

    public void preInit() throws IOException {
        System.out.println("pre init côté commun");
    }

    public void init()
    {

    }


    @Override
    public IBrowser createBrowser(String s, boolean b) {
        return null;
    }

    @Override
    public IBrowser createBrowser(String s) {
        return null;
    }

    @Override
    public void registerDisplayHandler(IDisplayHandler iDisplayHandler) {

    }

    @Override
    public void registerJSQueryHandler(IJSQueryHandler ijsQueryHandler) {

    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public void openExampleBrowser(String s) {

    }

    @Override
    public String mimeTypeFromExtension(String s) {
        return null;
    }

    @Override
    public void registerScheme(String s, Class<? extends IScheme> aClass, boolean b, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {

    }

    @Override
    public boolean isSchemeRegistered(String s) {
        return false;
    }

    @Override
    public String punycode(String s) {
        return null;
    }
}
