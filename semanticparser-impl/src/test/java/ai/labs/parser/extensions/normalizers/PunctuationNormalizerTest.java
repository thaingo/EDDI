package ai.labs.parser.extensions.normalizers;

import ai.labs.parser.extensions.normalizers.providers.PunctuationNormalizerProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PunctuationNormalizerTest {

    private PunctuationNormalizer normalizer;
    private final String testString =
            "This is!just an:example,of a string,that needs? to be fixed.by inserting:a whitespace;" +
                    "after punctuation marks! ";

    @BeforeEach
    public void setUp() {
        normalizer = new PunctuationNormalizer(
                new PunctuationNormalizerProvider().
                        toRegexPattern(PunctuationNormalizer.PUNCTUATION),
                false);
    }

    @Test
    void normalizeInsertWhitespaces() {
        //setup
        final String expected = "This is ! just an : example , of a string , that needs ? to be fixed . by inserting : a whitespace ; after punctuation marks !";
        normalizer.setRemovePunctuation(false);

        //test
        String actual = normalizer.normalize(testString);

        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void normalizeRemovePunctuation() {
        //setup
        final String expected = "This is just an example of a string that needs to be fixed by inserting a whitespace after punctuation marks";
        normalizer.setRemovePunctuation(true);

        //test
        String actual = normalizer.normalize(testString);

        //assert
        Assertions.assertEquals(expected, actual);
    }
}