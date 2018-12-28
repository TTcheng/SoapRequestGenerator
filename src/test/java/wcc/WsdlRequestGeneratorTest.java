package wcc;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import wcc.components.WsdlRequestGenerator;

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
        String res = generator.generate("http://localhost:8081/core/ws/helloWs?wsdl");
        System.out.print(res);
    }
} 
