package net.sctgaming.baconpancakes.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCuttingBoard extends ModelBase {
	
	ModelRenderer board;

	public ModelCuttingBoard() {
		textureWidth = 64;
		textureHeight = 32;

		board = new ModelRenderer(this, 0, 0);
		board.addBox(-6F, -17F, -5F, 13, 1, 10);
		board.setRotationPoint(0F, 23F, 0F);
		board.setTextureSize(64, 32);
		board.mirror = true;
		setRotation(board, 0F, -0.1115358F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		board.render(f5);
	}
	
	public void render(float f) {
		board.render(f);
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
