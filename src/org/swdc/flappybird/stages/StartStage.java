package org.swdc.flappybird.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.swdc.flappybird.GameScene;
import org.swdc.flappybird.MainStart;
import org.swdc.flappybird.actors.*;

/**
 * 开始画面，有游戏的logo
 */
public class StartStage extends Stage {

    private GameScene scene;

    private BackgroundActor backgroundActor = new BackgroundActor();
    private LogoActor logoActor = new LogoActor();
    private FloorActor floorActor = new FloorActor();
    private BirdActor birdActor = new BirdActor();
    private TouchActor touchActor = new TouchActor();

    public StartStage(GameScene scene) {
        super(new StretchViewport(MainStart.WIDTH,MainStart.HEIGHT));
        this.scene = scene;
        birdActor.setY(240);
        birdActor.setX(120);
        birdActor.setRotation(20);
        logoActor.setX(MainStart.WIDTH / 2 - logoActor.getWidth() / 2);
        logoActor.setY((MainStart.HEIGHT / 3) * 2);
        touchActor.setX(MainStart.WIDTH / 2 - touchActor.getWidth() / 2);
        touchActor.setY((MainStart.HEIGHT / 3) - 60);
        backgroundActor.setY(floorActor.getHeight() - 60);
        backgroundActor.setMoving(true);
        this.getActors().addAll(logoActor,backgroundActor,floorActor,birdActor,touchActor);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        scene.changeState(GameScene.GameStatus.SC_PLAYING);
        return true;
    }
}
