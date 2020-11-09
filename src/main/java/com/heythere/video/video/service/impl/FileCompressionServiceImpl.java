package com.heythere.video.video.service.impl;

import com.heythere.video.video.service.FileCompressionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

@Service
public class FileCompressionServiceImpl implements FileCompressionService {
    @Override
    public File uploadFileToS3Bucket(final Long id, final String email, final MultipartFile file) throws IOException {
        File compressedImageFile
                = new File(String.format("%s-%s-picture-img.jpg",id,email));

        InputStream inputStream = file.getInputStream();
        OutputStream outputStream = new FileOutputStream(compressedImageFile);

        float imageQuality = 0.3f;


        BufferedImage bufferedImage = ImageIO.read(inputStream);

        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");

        if (!imageWriters.hasNext())
            throw new IllegalStateException("Writers Not Found!!");

        ImageWriter imageWriter = (ImageWriter) imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();

        return compressedImageFile;
    }
}
