package com.recfac.rf;


import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_PLAIN;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.*; 

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class RfApplication {

	public static void main(String[] args) throws FrameGrabber.Exception{
		OpenCVFrameConverter.ToMat convertMat = new OpenCVFrameConverter.ToMat();
		OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
		camera.start();
		CascadeClassifier detectorFace = new CascadeClassifier("src\\main\\java\\recursos\\haarcascade_frontalface_alt.xml");
		CanvasFrame cFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma()/camera.getGamma()); // Aceleracao de Hardware
		Frame frameCapturado = null;
		Mat imagemColorida = new Mat();
		
		while((frameCapturado = camera.grab() ) != null) {
			imagemColorida = convertMat.convert(frameCapturado);
			Mat imagemCinza =new Mat();
			cvtColor(imagemColorida, imagemCinza, COLOR_BGRA2GRAY);
			RectVector facesDetectadas = new RectVector();
			detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size(150,150), new Size(500,500));
			for(int i = 0 ; i < facesDetectadas.size(); i++) {
				Rect dadosFace = facesDetectadas.get(0);
				rectangle(imagemColorida, dadosFace, new Scalar(0,0,255,0) );
			}
			
			if(cFrame.isVisible()) {
				cFrame.showImage(frameCapturado);
			}
		}
		cFrame.dispose();
		camera.stop();
	}

}
