package org.swdc.flappybird.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.swdc.flappybird.GameScene;
import org.swdc.flappybird.MainStart;
import org.swdc.flappybird.actors.BackgroundActor;
import org.swdc.flappybird.actors.BirdActor;
import org.swdc.flappybird.actors.FloorActor;
import org.swdc.flappybird.actors.GDActor;

import java.util.ArrayList;
import java.util.List;

/**
 *  游戏的主画面以及逻辑处理
 */
public class GameStage extends Stage {

    /**
     * 鸟
     */
    private BirdActor birdActor = new BirdActor();
    /**
     * 背景的城市和草
     */
    private BackgroundActor backgroundActor = new BackgroundActor();
    /**
     * 地面
     */
    private FloorActor floorActor = new FloorActor();

    /**
     * 柱子之间的间隔
     */
    private int gdSpace = 180;

    /**
     * 一组柱子的actor
     */
    private List<GDActor> gdActors;

    /**
     * 鸟撞到了柱子或者鸟落到了地上
     */
    private boolean isGameOver = false;

    private Sound soundDie;
    private Sound soundCollision;
    private Sound soundFly;
    private Sound soundPass;

    private GameScene scene;

    private Label scoreLabel;

    public GameStage(GameScene scene){
        super(new StretchViewport(MainStart.WIDTH,MainStart.HEIGHT));
        this.scene = scene;
        gdActors = new ArrayList<>();

        BitmapFont numScoreFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = numScoreFont;
        lblStyle.fontColor = new Color(1,1,1,1);
        scoreLabel = new Label("0",lblStyle);
        scoreLabel.setX(16);
        scoreLabel.setY(MainStart.HEIGHT - 24);
        scoreLabel.setSize(MainStart.WIDTH, 24);

        for (int gdCount = MainStart.WIDTH / gdSpace; gdCount > 0; gdCount --) {
            GDActor actor = new GDActor();
            actor.setY(floorActor.getHeight() - 50);
            gdActors.add(actor);
        }

        backgroundActor.setY(floorActor.getHeight() - 60);
        birdActor.setY(240);
        birdActor.setX(120);
        birdActor.setMoving(true);
        birdActor.setDeathHeight(floorActor.getHeight() - 20);
        floorActor.setMoving(true);
        backgroundActor.setMoving(true);

        soundCollision = Gdx.audio.newSound(Gdx.files.internal("sound/sfx_hit.ogg"));
        soundDie = Gdx.audio.newSound(Gdx.files.internal("sound/sfx_die.ogg"));
        soundFly = Gdx.audio.newSound(Gdx.files.internal("sound/sfx_wing.ogg"));
        soundPass = Gdx.audio.newSound(Gdx.files.internal("sound/sfx_point.ogg"));

        gdActors.get(0).setMoving(true);
        this.getActors().addAll(backgroundActor, floorActor, gdActors.get(0), birdActor, scoreLabel);
    }

    @Override
    public void act() {
        super.act();
        if (birdActor.isDeath()) {
            // 鸟掉到了地上
            stopAll();
            if(!isGameOver) {
                soundDie.play();
                isGameOver = true;
            }
        } else {
            float prevX = gdActors.get(0).getX();
            for (GDActor actor : gdActors) {
                if (actor.isBirdPassed() && actor.isMoveOut()) {
                    // 鸟已经通过了管道，增加分数
                    scene.setScore(scene.getScore() + 1);
                    scoreLabel.setText(scene.getScore() + "");
                }
                if (actor.isCollision(birdActor,soundCollision,soundPass)) {
                    //鸟撞到了管道，终止画面
                    if (actor.isMoving()) {
                        getActors().forEach(act->act.addAction(Actions.moveBy(48,0,0.2f)));
                        stopAll();
                    }
                    scoreLabel.setX(16);
                }
                if (actor.isMoveOut()) {
                    // 管道从画面中移出了，重新计算他的位置和高度
                    actor.reset();
                    actor.setMoving(true);
                } else if (actor.isMoving()) {
                    // 记录上一个x
                    prevX = actor.getX();
                } else if (!actor.isMoving() && !getActors().contains(actor,false) && MainStart.WIDTH - prevX + actor.getWidth() >= gdSpace) {
                    // 当前管道不在actor列表中，但是前一个管道已经移出了一段距离，所以可以把它放入舞台了
                    getActors().add(actor);
                    actor.setMoving(true);
                    prevX = actor.getX();
                    // 重新添加鸟，确保他在管道上方
                    getActors().removeValue(birdActor,false);
                    getActors().add(birdActor);
                    getActors().removeValue(scoreLabel,false);
                    getActors().add(scoreLabel);
                }
            }
        }
        if (isGameOver && !birdActor.isMoving()) {
            scene.changeState(GameScene.GameStatus.SC_OVER);
        }
    }

    /**
     * 终止画面，停下所有actor的动画
     */
    private void stopAll() {
        backgroundActor.setMoving(false);
        floorActor.setMoving(false);
        gdActors.forEach(act-> act.setMoving(false));
    }

    public void reset() {
        this.scene.setScore(0);
        this.scoreLabel.setText("0");
        scoreLabel.setX(16);
        this.gdActors.forEach(GDActor::reset);
        this.gdActors.forEach(act->this.getActors().removeValue(act,false));
        this.getActors().add(gdActors.get(0));
        birdActor.reset();
        birdActor.setY(240);
        birdActor.setX(120);
        birdActor.setMoving(true);
        gdActors.get(0).setMoving(true);
        backgroundActor.setMoving(true);
        floorActor.setMoving(true);
        this.isGameOver = false;
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isGameOver) {
            this.birdActor.flyUp();
            soundFly.play();
        }
        return true;
    }
}
