package sct.culinarycraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHydroponicDistributor extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape2;

	public ModelHydroponicDistributor() {
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 11, 16);
		Shape1.setRotationPoint(-8F, 13F, -8F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 34);
		Shape3.addBox(0F, 0F, 0F, 4, 6, 4);
		Shape3.setRotationPoint(-3F, 7F, 0F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0.7853982F, 0F);
		Shape4 = new ModelRenderer(this, 0, 47);
		Shape4.addBox(0F, 0F, 0F, 10, 13, 10);
		Shape4.setRotationPoint(-7F, -5F, 0F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0.7853982F, 0F);
		Shape2 = new ModelRenderer(this, 24, 33);
		Shape2.addBox(0F, 0F, 0F, 12, 2, 8);
		Shape2.setRotationPoint(-8F, 11F, -4F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape2.render(f5);
	}
	
	public void render(float f) {
		Shape1.render(f);
		Shape3.render(f);
		Shape4.render(f);
		Shape2.render(f);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
