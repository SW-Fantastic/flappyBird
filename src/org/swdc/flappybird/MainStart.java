package org.swdc.flappybird;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * GDX引擎启动引导
 */
public class MainStart {

    static LwjglApplication application;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 520;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width = WIDTH;
        cfg.height = HEIGHT;
        cfg.title = "Flappy Bird";
        cfg.addIcon("icon.png", Files.FileType.Internal);
        application = new LwjglApplication(new AppLifeCircle(),cfg);
    }

}
