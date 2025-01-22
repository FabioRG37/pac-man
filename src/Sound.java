import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Clip sound(String path) {
        Clip clip = null;
        AudioInputStream ais = null;
        try {
            File arquivo = new File(path);
            if (!arquivo.exists()) {
                System.out.println("Arquivo não encontrado: " + path);
                return null;
            }
            ais = AudioSystem.getAudioInputStream(arquivo);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Erro ao carregar som: " + e.getMessage());
        } finally {
            if (ais != null) {
                try {
                    ais.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }
        return clip;
    }

    public void ponto() {
        Clip som = Sound.sound(getClass().getResource("sounds/point.wav").getFile());
        if (som != null) {
            som.start();
        }
    }

    public void hit() {
        Clip som = Sound.sound(getClass().getResource("sounds/hit.wav").getFile());
        if (som != null) {
            som.start();
        }
    }
}
