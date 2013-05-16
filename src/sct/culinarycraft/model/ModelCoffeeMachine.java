package sct.culinarycraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCoffeeMachine extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;

	public ModelCoffeeMachine() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-4F, -17F, -4F, 8, 1, 8);
		Shape1.setRotationPoint(0F, 23F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 9);
		Shape2.addBox(-4F, -17F, -1F, 8, 8, 2);
		Shape2.setRotationPoint(0F, 15F, 3F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 19);
		Shape3.addBox(-4F, -17F, -4F, 8, 3, 8);
		Shape3.setRotationPoint(0F, 12F, 0F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 24, 17);
		Shape4.addBox(0F, -17F, 0F, 2, 8, 2);
		Shape4.setRotationPoint(-4F, 15F, 0F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 24, 17);
		Shape5.addBox(0F, -17F, 0F, 2, 8, 2);
		Shape5.setRotationPoint(2F, 15F, 0F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 32, 0);
		Shape6.addBox(-2F, -17F, -2F, 4, 5, 4);
		Shape6.setRotationPoint(0F, 18F, -1F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 32, 0);
		Shape7.addBox(0F, -17F, 0F, 1, 3, 1);
		Shape7.setRotationPoint(2F, 19F, -2F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 32, 17);
		Shape8.addBox(0F, -17F, 0F, 1, 8, 2);
		Shape8.setRotationPoint(-5F, 14F, 1F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
	}
	
	public void render(float f) {
		Shape1.render(f);
		Shape2.render(f);
		Shape3.render(f);
		Shape4.render(f);
		Shape5.render(f);
		Shape6.render(f);
		Shape7.render(f);
		Shape8.render(f);
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
