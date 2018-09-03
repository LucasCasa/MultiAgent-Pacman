package com.behr.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Assets {
    public static AssetManager manager = new AssetManager();
    public static Skin menuSkin;
    
    public static void queueLoading(){
        loadNinePatches();
        loadFonts();
        makeStyles();
        

    }

    public static boolean update() {
        return manager.update();
    }

    private static void loadNinePatches() {
//        FileHandleResolver resolver = new InternalFileHandleResolver();
//        manager.setLoader(NinePatchDrawable.class, new NinePatchDrawable(resolver));
//        manager.load("ui/NESMENU.9.png", NinePatchDrawable.class);
//        manager.load("ui/NESMENUBLUE.9.png", NinePatchDrawable.class);
//        manager.load("ui/MenuYellow.9.png", NinePatchDrawable.class);
//        manager.load("ui/MenuCyan.9.png", NinePatchDrawable.class);
//        manager.load("ui/MenuPink.9.png", NinePatchDrawable.class);        
//        manager.load("ui/MenuOrange.9.png", NinePatchDrawable.class);        
//        manager.load("ui/MenuRed.9.png", NinePatchDrawable.class);  
        
    }

    private static void loadFonts() {
        // set up the AssetManager to load fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader( // loading freetypefonts
                FreeTypeFontGenerator.class,
                new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader( // loading bitmapfonts for usage
                BitmapFont.class, 
                new FreetypeFontLoader(resolver));
        
        //setup small font params
        FreeTypeFontLoaderParameter size1Params = new FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "font/font1.ttf";
        size1Params.fontParameters.size = 30;
        
        // load small font
        manager.load("font/font1.ttf", BitmapFont.class, size1Params);
        
       
        // setup big font
        FreeTypeFontLoaderParameter size2Params = new FreeTypeFontLoaderParameter();        
        size1Params.fontFileName = "font/font1.ttf";
        size1Params.fontParameters.size = 48;       
        
        // load big font
        manager.load("font/font1.ttf", BitmapFont.class, size2Params);
                 
        
       
    }

    private static void makeStyles() {
    }

}
