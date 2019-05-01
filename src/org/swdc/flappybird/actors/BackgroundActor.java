package org.swdc.flappybird.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.swdc.flappybird.MainStart;

/**
 * Created by lenovo on 2019/1/4.
 */
public class BackgroundActor extends Actor {

    private TextureRegion regionGrass;
    private TextureRegion regionBuilding;

    private Texture building;
    private Texture grass;

    private int buildingOffsetX = 0;
    private int grassOffsetX = 0;

    private int roleStep = 1;

    private boolean moving = false;

    private static final float BUILDING_SEC = 0.2f;
    private static final float GRASS_SEC = 0.1f;

    public BackgroundActor() {
        this.building = new Texture("textures/birdCity.png");
        this.grass = new Texture("textures/birdGrass.png");
        this.regionBuilding = new TextureRegion(building);
        this.regionGrass = new TextureRegion(grass);
        this.setWidth(MainStart.WIDTH);
        this.setHeight(600);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!moving) {
            return;
        }
        // 增加offset偏移，这会导致绘制位置出现变化，从而出现动画效果
       buildingOffsetX += roleStep;
        grassOffsetX += roleStep;
        // 数字是不能无限增大的，在一定程度之后把offset拉回合适的值。
        int buildingWidthAfterSec = (int)(regionBuilding.getRegionWidth() * BUILDING_SEC);
        int buildingRepX = MainStart.WIDTH / buildingWidthAfterSec;
        if (buildingOffsetX % (buildingRepX * buildingWidthAfterSec) >= 0 &&
                buildingOffsetX % (buildingRepX * buildingWidthAfterSec) <= roleStep){
            // 偏移在这个范围内的时候，说明已经走过了现在画出的所有图像的一半
            // offset应该拉回第一个图像绘制结束时的x，这样错位不会很明显
            buildingOffsetX =   buildingWidthAfterSec ;
        }
        // 对于grass同样处理
        int grassWidthAfterSec = (int)(regionGrass.getRegionWidth() * GRASS_SEC);
        int grassRepX = MainStart.WIDTH / (int) (regionBuilding.getRegionWidth() * GRASS_SEC);
        if (grassWidthAfterSec % (grassRepX * grassWidthAfterSec) >= 0 &&
                grassOffsetX % (grassRepX * grassWidthAfterSec) <= roleStep) {
            grassOffsetX = grassWidthAfterSec;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 素材比较大，需要缩放，计算背景建筑图像缩放后的宽度
        int buildingWidthAfterSec = (int)(regionBuilding.getRegionWidth() * BUILDING_SEC);
        // 需要重复的次数
        int buildingRepX = 900 / (int)(regionBuilding.getRegionWidth() * BUILDING_SEC);
        for (int currX = 0; currX < 2 * buildingRepX; currX ++) {
            // 计算位置，绘制图片（缩放后宽度 * 当前绘制的个数 - x偏移，偏移周期性改变，达到移动的效果
            batch.draw(regionBuilding,buildingWidthAfterSec * currX  -  buildingOffsetX, getY() ,
                    getOriginX(), getOriginY(),
                    getWidth(),getHeight(),
                    BUILDING_SEC,BUILDING_SEC,
                    getRotation());
        }

        int grassWidthAfterSec = (int)(regionGrass.getRegionWidth() * GRASS_SEC);
        int grassRepX = 900 / (int) (regionBuilding.getRegionWidth() * GRASS_SEC);
        for (int currX = 0; currX < 2 * grassRepX; currX ++) {
            batch.draw(regionGrass,currX * grassWidthAfterSec - buildingOffsetX  ,getY() ,
                    getOriginX(), getOriginY(),
                    getWidth(),getHeight(),
                    GRASS_SEC,GRASS_SEC,
                    getRotation());
        }

    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }
}
