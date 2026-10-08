package wcc.test;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import wcc.components.WsdlRequestGenerator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * WsdlRequestGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十二月 28, 2018</pre>
 */
public class WsdlRequestGeneratorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: generate(String url)
     */
    @Test
    public void testGenerate() throws Exception {
        WsdlRequestGenerator generator = new WsdlRequestGenerator();
        generator.setCreateResponse(false);
        String res = generator.generate("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl");
        System.out.print(res);

        // A WSDL that binds its operations for SOAP 1.1 and SOAP 1.2 must still produce
        // one sample per operation, see issue "both outputs are getDatabaseInfo"
        Set<String> operations = new LinkedHashSet<>();
        Matcher matcher = Pattern.compile("^OP:(.*)$", Pattern.MULTILINE).matcher(res);
        while (matcher.find()) {
            assertTrue("operation generated more than once: " + matcher.group(1),
                    operations.add(matcher.group(1)));
        }
        assertFalse("no operation was generated", operations.isEmpty());
    }
}
