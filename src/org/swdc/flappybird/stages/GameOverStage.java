package org.swdc.flappybird.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.swdc.flappybird.GameScene;
import org.swdc.flappybird.MainStart;
import org.swdc.flappybird.actors.BackgroundActor;
import org.swdc.flappybird.actors.FloorActor;
import org.swdc.flappybird.actors.GameOverActor;
import org.swdc.flappybird.actors.ScoreActor;

/**
 * 鸟撞到了管道或者掉到了地上的时候，游戏结束
 */
public class GameOverStage extends Stage {

    private GameScene scene;
    private GameOverActor gameOverActor = new GameOverActor();
    private ScoreActor scoreActor = new ScoreActor();
    private BackgroundActor backgroundActor = new BackgroundActor();
    private FloorActor floorActor = new FloorActor();

    private Label scoreLabel;

    public GameOverStage(GameScene scene) {
        this.scene = scene;
        gameOverActor.setX(MainStart.WIDTH/2 - gameOverActor.getWidth() / 2);
        gameOverActor.setY((MainStart.HEIGHT / 3) * 2);
        scoreActor.setX(MainStart.WIDTH/2 - scoreActor.getWidth() / 2);
        scoreActor.setY(MainStart.HEIGHT / 3 - 20);
        backgroundActor.setY(floorActor.getHeight() - 60);
        backgroundActor.setMoving(true);
        BitmapFont numScoreFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = numScoreFont;
        lblStyle.fontColor = new Color(1,1,1,1);
        scoreLabel = new Label(scene.getScore() + "分",lblStyle);
        scoreLabel.setX(scoreActor.getX() + scoreActor.getWidth()/6 * 5 - 64);
        scoreLabel.setY(scoreActor.getY() + scoreActor.getHeight()/2 - 12);
        scoreLabel.setFontScale(0.8f);
        this.getActors().addAll(backgroundActor,floorActor,gameOverActor,scoreActor,scoreLabel);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        scene.changeState(GameScene.GameStatus.SC_START);
        return true;
    }

    @Override
    public void act() {
        super.act();
        scoreLabel.setText(scene.getScore() + "分");
    }
}
