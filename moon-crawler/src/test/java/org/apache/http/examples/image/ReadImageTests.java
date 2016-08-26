package org.apache.http.examples.image;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.moonframework.crawler.http.WebHttpClientUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/30
 */
public class ReadImageTests {
    CloseableHttpClient httpClient = WebHttpClientUtils.getInstance();

    public void a(String imageUrl, String imageName) throws Exception {
        HttpGet httpGet = new HttpGet(new URI(imageUrl));
        try {
            httpClient.execute(httpGet, response -> {
                InputStream is = response.getEntity().getContent();

                BufferedImage src =  ImageIO.read(is);

                byte[] bytes = IOUtils.toByteArray(is);
                InputStream clone = new ByteArrayInputStream(bytes);
                FileUtils.writeByteArrayToFile(new File(imageName), bytes);
                return null;
            });
        } catch (Exception e) {

        }
    }



    public void download(String imageUrl, String imageName) throws Exception {
        URL url = new URL(imageUrl);
        BufferedImage src = ImageIO.read(url);

        int width = src.getWidth(null);
        int height = src.getHeight(null);

        for (int x = 0; x < width; x += 20) {
            for (int y = 0; y < height; y += 20) {
                int rgb = src.getRGB(x, y);
                if (rgb == -1) {
                    continue;
                }
                int r = (rgb >>> 16) & 0xff;
                int g = (rgb >>> 8) & 0xff;
                int b = rgb & 0xff;
                System.out.println();
            }
        }


        BufferedImage tag = new BufferedImage(width / 1, height / 1, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src, 0, 0, width / 1, height / 1, null);

        //FileOutputStream out = new FileOutputStream(imageName);
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //encoder.encode(tag); // JPEG编码
        //out.close();

        String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
        ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */, new File(imageName) /* target */);

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        List<String> images = Arrays.asList(
                //"http://www.jblpro.com/images/default-source/product-slider-images/cinema/high-power-screen-array/5732_front_t.jpg",
                //"http://www.jblpro.com/images/default-source/product-slider-images/cinema/high-power-screen-array/5732_front_r.jpg",
                //"http://eaw.com/wp-content/uploads/2014/01/JF56NT-Top.png",
                //"http://i.stack.imgur.com/2djnp.gif",
                "http://www.sweetwater.com/images/closeup/750-Pacifica_detail1.jpg"
        );

        for (String image : images) {
            long start = System.currentTimeMillis();
            String filename = image.substring(image.lastIndexOf("/") + 1, image.lastIndexOf("."));
            ReadImageTests readImage = new ReadImageTests();

            readImage.download(image, "C:\\Quzile\\zfile\\image\\" + filename + ".jpg");
            long end = System.currentTimeMillis();
            System.out.println(String.format("finished in [%s] ms", end - start));
        }


    }

}
