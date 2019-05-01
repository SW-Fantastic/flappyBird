package org.swdc.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import org.swdc.flappybird.stages.GameOverStage;
import org.swdc.flappybird.stages.GameStage;
import org.swdc.flappybird.stages.StartStage;

/**
 * 主游戏视图
 */
public class GameScene implements Screen {

    public enum GameStatus {
        SC_START, SC_OVER, SC_PLAYING
    }

    private GameStatus status = GameStatus.SC_START;

    private GameStage gameStage = new GameStage(this);
    private StartStage startStage = new StartStage(this);
    private GameOverStage overStage = new GameOverStage(this);

    private int score;

    private Music background;

    GameScene() {
        Gdx.input.setInputProcessor(startStage);
        background = Gdx.audio.newMusic(Gdx.files.internal("music/bgm.mp3"));
        background.setLooping(true);
        background.setVolume(0.4f);
        background.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        // 原始RGB，分别除以255可以得到近似的颜色
        Gdx.gl.glClearColor(0.3f,0.75f,0.79f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (status) {
            case SC_START:
                startStage.act();
                startStage.draw();
                break;
            case SC_PLAYING:
                gameStage.act();
                gameStage.draw();
                break;
            case SC_OVER:
                overStage.act();
                overStage.draw();
                break;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void changeState(GameStatus status) {
        switch (status) {
            case SC_START:
                Gdx.input.setInputProcessor(startStage);
                break;
            case SC_PLAYING:
                gameStage.reset();
                Gdx.input.setInputProcessor(gameStage);
                break;
            case SC_OVER:
                Gdx.input.setInputProcessor(overStage);
                break;
        }
        this.status = status;
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameStage.dispose();
        startStage.dispose();
        background.dispose();
    }
}
