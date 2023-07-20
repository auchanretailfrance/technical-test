package technical.test.api.sku;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Utility class which generates random secure Product SKUs.
 */
@Component
public class SKUGenerator {
    private static final int SKU_LENGTH = 8;
    private static final String SKU_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateSku() {
        StringBuilder skuBuilder = new StringBuilder();

        // Randomized generation of character sequence
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < SKU_LENGTH; i++) {
            int randomIndex = random.nextInt(SKU_CHARACTERS.length());
            char randomChar = SKU_CHARACTERS.charAt(randomIndex);
            skuBuilder.append(randomChar);
        }

        return skuBuilder.toString();
    }
}

