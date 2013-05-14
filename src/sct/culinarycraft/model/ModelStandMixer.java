package sct.culinarycraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStandMixer extends ModelBase {
	// fields
	ModelRenderer Shape5;
	ModelRenderer Shape3;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape4;

	public ModelStandMixer() {
		textureWidth = 64;
		textureHeight = 32;

		Shape5 = new ModelRenderer(this, 0, 0);
		Shape5.addBox(0F, -17F, 0F, 1, 2, 1);
		Shape5.setRotationPoint(0F, 15F, -3F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 0);
		Shape3.addBox(-3F, -17F, -8F, 6, 2, 11);
		Shape3.setRotationPoint(0F, 13F, 3F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 13);
		Shape1.addBox(-4F, -17F, -6F, 8, 1, 12);
		Shape1.setRotationPoint(0F, 23F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 23, 0);
		Shape2.addBox(-2F, -17F, -2F, 4, 8, 3);
		Shape2.setRotationPoint(0F, 15F, 4F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 28, 14);
		Shape4.addBox(-3F, -17F, -2F, 6, 5, 6);
		Shape4.setRotationPoint(0F, 18F, -3F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape5.render(f5);
		Shape3.render(f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape4.render(f5);
	}
	
	public void render(float f) {
		Shape5.render(f);
		Shape3.render(f);
		Shape1.render(f);
		Shape2.render(f);
		Shape4.render(f);
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
