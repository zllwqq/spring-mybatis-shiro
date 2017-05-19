package com.github.zllwqq.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;
import java.util.ArrayList;
import java.util.List;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByBufferedImageOp;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.OverlapGlyphsUsingShapeVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateGlyphsVerticalRandomVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

public class GMailEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		
		int fontSize = 30;
		
		//word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		
		//wordtoimage components
        TextPaster randomPaster = new GlyphsPaster(4, 4,
                new RandomListColorGenerator(
                        new Color[]{
                                new Color(23, 170, 27),
                                new Color(220, 34, 11),
                                new Color(23, 67, 172)})
                ,new GlyphsVisitors[]{
                new TranslateGlyphsVerticalRandomVisitor(1),
               // new RotateGlyphsRandomVisitor(Math.PI/32),
                new OverlapGlyphsUsingShapeVisitor(3),
                new TranslateAllToRandomPointVisitor()
                //,

               //
                });
        /*
         new TextVisitor[]{
                new OverlapGlyphsTextVisitor(6)
        }, null
         */
        BackgroundGenerator back = new UniColorBackgroundGenerator(
                100, 41, Color.white);

        FontGenerator shearedFont = new RandomFontGenerator(fontSize,
        		fontSize,
                new Font[]{
                        new Font("nyala",Font.BOLD, fontSize)
                        ,
                        new Font("Bell MT",  Font.PLAIN, fontSize)
                        ,
                        new Font("Credit valley",  Font.BOLD, fontSize)
                }
        ,false);


//        PinchFilter pinch = new PinchFilter();
//
//        pinch.setAmount(-.5f);
//        pinch.setRadius(70);
//        pinch.setAngle((float) (Math.PI/16));
//        pinch.setCentreX(0.5f);
//        pinch.setCentreY(-0.01f);
//        pinch.setEdgeAction(ImageFunction2D.CLAMP);       
//
//        PinchFilter pinch2 = new PinchFilter();
//        pinch2.setAmount(-.6f);
//        pinch2.setRadius(70);
//        pinch2.setAngle((float) (Math.PI/16));
//        pinch2.setCentreX(0.3f);
//        pinch2.setCentreY(1.01f);
//        pinch2.setEdgeAction(ImageFunction2D.CLAMP);
//
//        PinchFilter pinch3 = new PinchFilter();
//        pinch3.setAmount(-.6f);
//        pinch3.setRadius(70);
//        pinch3.setAngle((float) (Math.PI/16));
//        pinch3.setCentreX(0.8f);
//        pinch3.setCentreY(-0.01f);
//        pinch3.setEdgeAction(ImageFunction2D.CLAMP);
//
//
//
//        List<ImageDeformation> textDef =  new ArrayList<ImageDeformation>();
//        textDef.add(new ImageDeformationByBufferedImageOp(pinch));
//        textDef.add(new ImageDeformationByBufferedImageOp(pinch2));
//        textDef.add(new ImageDeformationByBufferedImageOp(pinch3));
        
        ImageDeformation postDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation backDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(
                new ImageFilter[]{});

        //word2image 1
        WordToImage word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster,
        		backDef,
        		postDef,
        		textDef


        );


        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image, false));
		
	}

}
