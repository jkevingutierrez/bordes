package edge;

import java.awt.image.BufferedImage;

public class EdgeApp {

	public static void main(String[] args) {
		Image image = new Image();
		BufferedImage originalImage = image.readImage("/Users/macbook/Downloads/bicycle-1280x720.jpg");
		BufferedImage grayScaleImage = image.getGrayScale(originalImage);
		BufferedImage imageWithEdges = image.getEdges(grayScaleImage);
		image.drawImage(imageWithEdges);
	}

}
