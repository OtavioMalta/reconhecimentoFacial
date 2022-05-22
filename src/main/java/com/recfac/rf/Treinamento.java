package com.recfac.rf;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.opencv.global.opencv_core.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

public class Treinamento {
	public static void main(String args[]) {
		File diretorio = new File("src\\main\\java\\fotos");
		FilenameFilter filtroImagem = new FilenameFilter() {
			public boolean accept(File dir, String nome) {
				return nome.endsWith(".jpg") || nome.endsWith(".gif") || nome.endsWith(".png");
			}
		};
		File[] arquivos = diretorio.listFiles(filtroImagem);
		MatVector fotos = new MatVector(arquivos.length); // vetor de fotos
		Mat rotulos =  new Mat(arqfuivos.length, 1, CV_32SC1);// ID da pessoa = ( linha, coluna, tipo de arquivo )
		IntBuffer rotulosBuffer = rotulos.createBuffer(); // armazenar corretamente os rotulos
		int contador = 0;
		for(File imagem: arquivos) {
			Mat foto = imread(imagem.getAbsolutePath(), IMREAD_GRAYSCALE); // Carrega a imagem e converte em escala de cinza
			int classe = Integer.parseInt(imagem.getName().split("\\.")[1]);
			resize(foto, foto, new Size(160,160));
			fotos.put(contador, foto);
			rotulosBuffer.put(contador, classe);
			contador++;
		}

		FaceRecognizer eigenfaces = EigenFaceRecognizer.create(); 
		FaceRecognizer fisherfaces = FisherFaceRecognizer.create(); 
		FaceRecognizer lbph = LBPHFaceRecognizer.create(2,9,9,9,1);
		
		eigenfaces.train(fotos, rotulos);
		eigenfaces.save("src\\main\\java\\recursos\\classificadorEigenFaces.yml");
		
		fisherfaces.train(fotos, rotulos);
		fisherfaces.save("src\\main\\java\\recursos\\classificadorFisherFaces.yml");
		
		lbph.train(fotos, rotulos);
		lbph.save("src\\main\\java\\recursos\\classificadorLBPH.yml");
		
		
	}
}
