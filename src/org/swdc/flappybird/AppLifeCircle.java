package org.swdc.flappybird;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

/**
 * 引擎的生命周期
 */
public class AppLifeCircle implements ApplicationListener {

    private GameScene scene;

    @Override
    public void create() {
        scene = new GameScene();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        scene.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        scene.dispose();
    }
}
