package com.example.crossdatabase.helpers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

public class IconHelper {
    public static ImageView getMsSqlImage() {
        return getImage("/icon/microsoftsqlserver.svg");
    }

    public static ImageView getPostgreSqlImage() {
        return getImage("/icon/postgresql.svg");
    }

    private static ImageView getImage(String path) {
        var image = new ImageView();
        var transcoder = new BufferefImageTranscoder();

        try (InputStream file = IconHelper.class.getResourceAsStream(path)) {
            var transcodeInput = new TranscoderInput(file);
            try {
                transcoder.transcode(transcodeInput, null);
                var img = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);
                image.setImage(img);
            } catch (TranscoderException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        image.setFitHeight(15);
        image.setPreserveRatio(true);

        return image;
    }
}
