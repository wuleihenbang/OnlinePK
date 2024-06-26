// Copyright (c) 2022 NetEase, Inc. All rights reserved.
// Use of this source code is governed by a MIT license that can be
// found in the LICENSE file.

package com.beautyFaceunity.gles;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import com.beautyFaceunity.gles.core.Drawable2d;
import com.beautyFaceunity.gles.core.GlUtil;
import com.beautyFaceunity.gles.core.Program;

public class ProgramTextureOES extends Program {

  // Simple vertex shader, used for all programs.
  private static final String VERTEX_SHADER =
      "uniform mat4 uMVPMatrix;\n"
          + "uniform mat4 uTexMatrix;\n"
          + "attribute vec4 aPosition;\n"
          + "attribute vec4 aTextureCoord;\n"
          + "varying vec2 vTextureCoord;\n"
          + "void main() {\n"
          + "    gl_Position = uMVPMatrix * aPosition;\n"
          + "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n"
          + "}\n";

  // Simple fragment shader for use with external 2D textures (e.g. what we get from
  // SurfaceTexture).
  private static final String FRAGMENT_SHADER_EXT =
      "#extension GL_OES_EGL_image_external : require\n"
          + "precision mediump float;\n"
          + "varying vec2 vTextureCoord;\n"
          + "uniform samplerExternalOES sTexture;\n"
          + "void main() {\n"
          + "    gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
          + "}\n";

  private int muMVPMatrixLoc;
  private int muTexMatrixLoc;
  private int maPositionLoc;
  private int maTextureCoordLoc;

  /** Prepares the program in the current EGL context. */
  public ProgramTextureOES() {
    super(VERTEX_SHADER, FRAGMENT_SHADER_EXT);
  }

  @Override
  protected Drawable2d getDrawable2d() {
    return new Drawable2dFull();
  }

  @Override
  protected void getLocations() {
    maPositionLoc = GLES20.glGetAttribLocation(mProgramHandle, "aPosition");
    GlUtil.checkLocation(maPositionLoc, "aPosition");
    maTextureCoordLoc = GLES20.glGetAttribLocation(mProgramHandle, "aTextureCoord");
    GlUtil.checkLocation(maTextureCoordLoc, "aTextureCoord");
    muMVPMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uMVPMatrix");
    GlUtil.checkLocation(muMVPMatrixLoc, "uMVPMatrix");
    muTexMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uTexMatrix");
    GlUtil.checkLocation(muTexMatrixLoc, "uTexMatrix");
  }

  @Override
  public void drawFrame(int textureId, float[] texMatrix, float[] mvpMatrix) {
    GlUtil.checkGlError("draw start");

    // Select the program.
    GLES20.glUseProgram(mProgramHandle);
    GlUtil.checkGlError("glUseProgram");

    // Set the texture.
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);

    // Copy the model / view / projection matrix over.
    GLES20.glUniformMatrix4fv(muMVPMatrixLoc, 1, false, mvpMatrix, 0);
    GlUtil.checkGlError("glUniformMatrix4fv");

    // Copy the texture transformation matrix over.
    GLES20.glUniformMatrix4fv(muTexMatrixLoc, 1, false, texMatrix, 0);
    GlUtil.checkGlError("glUniformMatrix4fv");

    // Enable the "aPosition" vertex attribute.
    GLES20.glEnableVertexAttribArray(maPositionLoc);
    GlUtil.checkGlError("glEnableVertexAttribArray");

    // Connect vertexBuffer to "aPosition".
    GLES20.glVertexAttribPointer(
        maPositionLoc,
        Drawable2d.COORDS_PER_VERTEX,
        GLES20.GL_FLOAT,
        false,
        Drawable2d.VERTEXTURE_STRIDE,
        mDrawable2d.vertexArray());
    GlUtil.checkGlError("glVertexAttribPointer");

    // Enable the "aTextureCoord" vertex attribute.
    GLES20.glEnableVertexAttribArray(maTextureCoordLoc);
    GlUtil.checkGlError("glEnableVertexAttribArray");

    // Connect texBuffer to "aTextureCoord".
    GLES20.glVertexAttribPointer(
        maTextureCoordLoc,
        2,
        GLES20.GL_FLOAT,
        false,
        Drawable2d.TEXTURE_COORD_STRIDE,
        mDrawable2d.texCoordArray());
    GlUtil.checkGlError("glVertexAttribPointer");

    // Draw the rect.
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mDrawable2d.vertexCount());
    GlUtil.checkGlError("glDrawArrays");

    // Done -- disable vertex array, texture, and program.
    GLES20.glDisableVertexAttribArray(maPositionLoc);
    GLES20.glDisableVertexAttribArray(maTextureCoordLoc);
    GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
    GLES20.glUseProgram(0);
  }
}
