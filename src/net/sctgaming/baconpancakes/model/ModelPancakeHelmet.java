package net.sctgaming.baconpancakes.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPancakeHelmet extends ModelBiped
{
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
  
  public ModelPancakeHelmet()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(-3F, -8F, -3F, 6, 1, 6);
      Shape1.setRotationPoint(0F, -1F, 0F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 0);
      Shape2.addBox(-3F, -9F, -3F, 6, 1, 6);
      Shape2.setRotationPoint(0F, -1F, 0F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0.122173F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(-3F, -10F, -3F, 6, 1, 6);
      Shape3.setRotationPoint(0F, -1F, 0F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, -0.1380772F, 0F);
      Shape4 = new ModelRenderer(this, 0, 0);
      Shape4.addBox(0F, -11F, 0F, 1, 1, 1);
      Shape4.setRotationPoint(0F, -1F, 0F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, -1.226894F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	float newX = this.bipedHeadwear.rotateAngleX;
	float newY = this.bipedHeadwear.rotateAngleY;
	setRotation(Shape1, newX, newY, 0);
	setRotation(Shape2, newX, newY, 0);
	setRotation(Shape3, newX, newY, 0);
	setRotation(Shape4, newX, newY, 0);
    
  }

}
