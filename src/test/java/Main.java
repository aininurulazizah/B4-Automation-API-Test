import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Baca isi file schema.json
            String jsonSchemaPath = "../schema.json";
            String content = new String(Files.readAllBytes(Paths.get(jsonSchemaPath)));

            // Cetak isi file
            System.out.println("Isi file schema.json:");
            System.out.println(content);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }
}
