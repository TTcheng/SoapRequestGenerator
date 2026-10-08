package wcc.components;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class WsdlRequestGenerator {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Boolean createRequest = true;
    private Boolean createResponse = true;

    public String generate(String url) throws Exception {
        WsdlProject project = new WsdlProject();
        WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, url);
        StringBuilder res = new StringBuilder();
        Set<String> generatedOperations = new HashSet<>();
        for (WsdlInterface wsdl : wsdls) {
            StringBuilder section = new StringBuilder();
            for (Operation operation : wsdl.getOperationList()) {
                WsdlOperation wsdlOperation = (WsdlOperation) operation;
                // A WSDL normally binds the same operations once per SOAP version
                // (SOAP 1.1 and SOAP 1.2), one sample per operation is enough.
                if (!generatedOperations.add(wsdlOperation.getName())) {
                    continue;
                }
                section.append("OP:").append(wsdlOperation.getName()).append('\n');
                if (createRequest) {
                    section.append("Request:").append('\n')
                            .append(wsdlOperation.createRequest(createRequest)).append('\n');
                }
                if (createResponse) {
                    section.append("Response:").append('\n')
                            .append(wsdlOperation.createResponse(createResponse)).append('\n');
                }
            }
            if (!section.isEmpty()) {
                res.append("==========================\n")
                        .append("Interface:").append(wsdl.getName()).append('\n')
                        .append("==========================\n")
                        .append(section);
            }
        }
        return res.toString();
    }

    public Boolean getCreateResponse() {
        return createResponse;
    }

    public void setCreateResponse(Boolean createResponse) {
        this.createResponse = createResponse;
    }

    public Boolean getCreateRequest() {
        return createRequest;
    }

    public void setCreateRequest(Boolean createRequest) {
        this.createRequest = createRequest;
    }
}
