package github.phelpsyt.dynamicmovement.mixin.client;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import github.phelpsyt.dynamicmovement.SharedStateManager;
@Mixin(Camera.class)
public abstract class CameraMixin {

    private float animationValue = 4.0f;
    private static final float TRANSITION_SPEED = 0.05f; // Speed of the transition

    @Shadow private Vec3d pos;
    @Shadow private float pitch;
    @Shadow private float yaw;
    @Shadow private float lastCameraY;
    @Shadow private float cameraY;
    @Shadow private boolean thirdPerson;
    @Shadow private boolean ready;
    @Shadow private BlockView area;
    @Shadow private Entity focusedEntity;
    @Shadow private float lastTickDelta;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void setPos(Vec3d pos);

    @Shadow
    protected abstract void setPos(double x, double y, double z);

    @Shadow
    protected abstract void moveBy(float f, float g, float h);

    @Shadow
    protected abstract float clipToSpace(float f);

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = thirdPerson;
        this.lastTickDelta = tickDelta;
        this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
        this.setPos(
                MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()),
                MathHelper.lerp((double)tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double)MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY),
                MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ())
        );
        if (thirdPerson) {
            float f = focusedEntity instanceof LivingEntity livingEntity ? livingEntity.getScale() : 1.0F;
            if (inverseView) {
                if(animationValue > 0.2F ){
                    animationValue -= TRANSITION_SPEED;
                    this.moveBy(-this.clipToSpace(animationValue * f), 0.0F, 0.0F);
                }else{
                    SharedStateManager.setAnimationFinished(true);
                    this.moveBy(this.clipToSpace(animationValue * f), 0.1F, 0.0F);
                }

            }else{
                SharedStateManager.setAnimationFinished(false);
                if(animationValue < 4.0F ){
                    animationValue += TRANSITION_SPEED;
                }
                this.moveBy(-this.clipToSpace(animationValue * f), 0.0F, 0.0F);
            }

        } else if (focusedEntity instanceof LivingEntity && ((LivingEntity)focusedEntity).isSleeping()) {
            Direction direction = ((LivingEntity)focusedEntity).getSleepingDirection();
            this.setRotation(direction != null ? direction.asRotation() - 180.0F : 0.0F, 0.0F);
            this.moveBy(0.0F, 0.3F, 0.0F);
        }
    }
}
